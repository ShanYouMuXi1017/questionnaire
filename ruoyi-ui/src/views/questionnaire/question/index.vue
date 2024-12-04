<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="问题类型" prop="problemType">
        <el-select v-model="queryParams.problemType" placeholder="请选择问题类型" clearable>
          <el-option
            v-for="dict in dict.type.quest_issue_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="问题" prop="problem">
        <el-input
          v-model="queryParams.problem"
          placeholder="请输入问题"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="回答类型" prop="answerType">
        <el-select v-model="queryParams.answerType" placeholder="请选择回答类型" clearable>
          <el-option
            v-for="dict in dict.type.quest_answer_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
<!--      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['questionnaire:question:add']"
        >新增</el-button>
      </el-col>-->
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['questionnaire:question:edit']"
        >修改</el-button>
      </el-col>
<!--      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['questionnaire:question:remove']"
        >删除</el-button>
      </el-col>-->
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['questionnaire:question:export']"
        >导出</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAddExcel"
        >录入问卷问题
        </el-button
        >
      </el-col>

      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-download"
          size="mini"
          @click="handleDownloadExcel"
        >下载录入模板
        </el-button
        >
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="questionList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="问题编号" align="center" prop="issueId" />
      <el-table-column label="问题类型" align="center" prop="problemType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.quest_issue_type" :value="scope.row.problemType"/>
        </template>
      </el-table-column>
      <el-table-column label="问题类型权重（%）" align="center" prop="typeWeight" />
      <el-table-column label="问题" align="center" prop="problem" />
      <el-table-column label="回答类型" align="center" prop="answerType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.quest_answer_type" :value="scope.row.answerType"/>
        </template>
      </el-table-column>
      <el-table-column label="评分" align="center" prop="grade" />
      <el-table-column label="创建时间" align="center" prop="createDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="更新时间" align="center" prop="updateDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updateDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['questionnaire:question:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['questionnaire:question:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改问卷问题对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="60%" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="问题类型" prop="problemType">
          <el-select v-model="form.problemType" placeholder="请选择问题类型">
            <el-option
              v-for="dict in dict.type.quest_issue_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="问题类型权重" prop="typeWeight">
          <el-input v-model="form.typeWeight" placeholder="请输入问题类型权重" />
        </el-form-item>
        <el-form-item label="问题" prop="problem">
          <el-input v-model="form.problem" placeholder="请输入问题" />
        </el-form-item>
        <el-form-item label="回答类型" prop="answerType">
          <el-select v-model="form.answerType" placeholder="请选择回答类型">
            <el-option
              v-for="dict in dict.type.quest_answer_type"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="评分" prop="grade">
          <el-input v-model="form.grade" placeholder="请输入评分" />
        </el-form-item>
        <el-form-item label="创建时间" prop="createDate">
          <el-date-picker clearable
            v-model="form.createDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择创建时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="更新时间" prop="updateDate">
          <el-date-picker clearable
            v-model="form.updateDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择更新时间">
          </el-date-picker>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <el-dialog :title="title" :visible.sync="upload.open" width="35%" append-to-body
               element-loading-text="正在上传">
      <el-form ref="form" :model="uploadForm" :rules="rules" label-width="110px">
        <el-form-item label="附件" prop="photoPath">
          <el-upload
            ref="upload"
            :headers="upload.headers"
            :action="upload.url"
            :multiple="false"
            :file-list="fileList"
            :disabled="upload.isUploading"
            :on-remove="fileRemove"
            :on-success="uploadSuccess"
            :on-error="uploadError"
            :before-upload="beforeUpload"
            :limit="1"
            :on-exceed="beyond"
            :auto-upload="false"
            accept=".xls,.xlsx"
          >
            <el-button size="small">
              上传
              <i class="el-icon-upload el-icon--right"></i>
            </el-button>
            <div class="el-upload__tip" style="color: red" slot="tip">
              提示：仅允许导入“.xls、.xlsx、”格式文件，文件大小小于10MB！
            </div>
          </el-upload>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitExcelForm">确 定</el-button>
        <el-button @click="cancelUpload">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listQuestion,
  getQuestion,
  delQuestion,
  addQuestion,
  updateQuestion,
  getFilePath
} from "@/api/questionnaire/question";
import {getToken} from "@/utils/auth";

export default {
  name: "Question",
  dicts: ['quest_answer_type', 'quest_issue_type'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 问卷问题表格数据
      questionList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        problemType: null,
        problem: null,
        answerType: null,
        grade: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      },
      // 上传文件
      uploadForm: {},
      fileList: [],
      upload: {
        // 是否显示弹出层（用户导入）
        open: false,
        // 弹出层标题（用户导入）
        title: '',
        // 是否禁用上传
        isUploading: false,
        // 是否更新已经存在的用户数据
        updateSupport: 0,
        // 设置上传的请求头部
        headers: { Authorization: 'Bearer ' + getToken() },
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/questionnaire/question/importQuestion",
      },
      uploadExcelPath: '',
    };
  },
  created() {
    this.getList();
    this.getFilePath();
  },
  methods: {
    //获取上传后的excel文件路径
    getFilePath(){
      getFilePath().then(response => {
        if(response.code === 200){
          this.uploadExcelPath = response.data;
        }
        else {
          this.uploadExcelPath = '';
        }
      })
    },
    /** 查询问卷问题列表 */
    getList() {
      this.loading = true;
      listQuestion(this.queryParams).then(response => {
        this.questionList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        issueId: null,
        problemType: null,
        typeWeight: null,
        problem: null,
        answerType: null,
        grade: null,
        createDate: null,
        updateDate: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.issueId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加问卷问题";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const issueId = row.issueId || this.ids
      getQuestion(issueId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改问卷问题";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.issueId != null) {
            updateQuestion(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addQuestion(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const issueIds = row.issueId || this.ids;
      this.$modal.confirm('是否确认删除问卷问题编号为"' + issueIds + '"的数据项？').then(function() {
        return delQuestion(issueIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('questionnaire/question/export', {
        ...this.queryParams
      }, `question_${new Date().getTime()}.xlsx`)
    },
    /** 新增按钮操作 */
    handleAddExcel() {
      this.fileRemove();
      this.upload.open = true;
      this.title = "添加文件";
    },
    // 移除选择的文件
    fileRemove(file, fileList) {
      this.upload.isUploading = false;
      this.resetUpload();
      this.fileList = [];
    },
    // 表单重置
    resetUpload() {
      this.uploadForm = {
        id: null,
        filename: null,
        uploadpath: null,
      };
      this.resetForm("form");
    },
    // 取消按钮
    cancelUpload() {
      this.upload.open = false;
      this.resetUpload();
    },
    // 文件上传成功
    uploadSuccess(res, file, fileList) {
      this.uploadForm.uploadpath = res.uploadpath;
      this.upload.isUploading = false;
      this.upload.open = false;
      this.$refs.upload.clearFiles()
      this.fileList = fileList;
      this.cancelUpload();
      this.getFilePath();
      this.$message({
        message: "问题录入成功！",
        type: "success",
      });

    },
    beyond(file, fileList) {
      this.$message({
        message: "最多上传一个文件",
        type: "warning",
      });
    },
    // 文件上传失败
    uploadError(err) {
      this.upload.isUploading = false;
      this.$modal.error(err);
    },
    // 上传中
    uploadProgress(e) {
      // this.btnLoding = true;
    },
    // 文件上传之前
    beforeUpload(file) {
      const fileName = file.name;
      const fileType = fileName.substring(fileName.lastIndexOf("."));
      if(file.size > 10485760){
        this.$message({
          message: "文件大小不能超过10MB",
          type: "warning",
        });
        return false;
      }
      if (
        fileType === ".xls" ||
        fileType === ".xlsx"
      ) {
        this.uploadForm.filename = file.name;
        // 不处理
      } else {
        this.$message.error("请上传正确的文件类型,.xls,.xlsx");
        return false;
      }
    },
    // 下载模板
    handleDownloadExcel() {
      if(this.uploadExcelPath === ''){
        this.$message.error("找不到文件，请联系管理员！");
        return;
      }
      const baseURL = process.env.VUE_APP_BASE_API
      window.location.href = baseURL + "/common/downloadResourceeasy?resource=" + encodeURI(this.uploadExcelPath);
    },
    /** 提交按钮 */
    submitExcelForm() {
      this.$refs.upload.submit();
    },
  }
};
</script>
