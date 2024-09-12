<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item label="原文档名" prop="filename">
        <el-input
          v-model="queryParams.filename"
          placeholder="请输入原文档名"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item label="类型" prop="status">
        <el-select v-model="queryParams.documentType" placeholder="文档类型" clearable>
          <el-option
            v-for="dict in dict.type.sys_document_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>

      <el-form-item>
        <el-button type="cyan" icon="el-icon-search" size="mini" @click="handleQuery"
        >搜索
        </el-button
        >
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:preview:add']"
        >新增
        </el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:preview:edit']"
        >修改
        </el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:preview:remove']"
        >删除
        </el-button
        >
      </el-col>

      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="previewList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column
        show-overflow-tooltip
        label="文档名"
        align="center"
        prop="filename"
      />
      <!--      <el-table-column
              show-overflow-tooltip
              label="上传后文件路径"
              align="center"
              prop="uploadpath"
            />
            <el-table-column
              show-overflow-tooltip
              label="转换pdf路径"
              align="center"
              prop="pdfpath"
              width="400"
            />
            <el-table-column
              show-overflow-tooltip
              label="预览地址"
              align="center"
              prop="previewUrl"
              width="400"
            />-->
      <el-table-column label="文档类型" align="center" prop="documentType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_document_type" :value="scope.row.documentType"/>
        </template>
      </el-table-column>
      <el-table-column show-overflow-tooltip label="备注" align="center" prop="remark"/>
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
        width="200"
      >

        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:preview:edit']"
          >修改
          </el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handlePreview(scope.row)"
          >预览
          </el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleDownload(scope.row)"
          >下载
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:preview:remove']"
          >删除
          </el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改preview对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="35%" append-to-body v-loading="loading1"
               element-loading-text="正在上传">
      <el-form ref="form" :model="form" :rules="rules" label-width="110px">
        <el-form-item label="文件名" prop="filename">
          <el-input v-model="form.filename" placeholder="请输入文件名" disabled/>
        </el-form-item>
        <el-form-item label="上传后文件路径" prop="uploadpath">
          <el-input v-model="form.uploadpath" placeholder="请输入上传后文件名" disabled/>
        </el-form-item>
        <el-form-item label="文档类型" prop="documentType">
          <el-radio-group v-model="form.documentType">
            <el-radio
              v-for="dict in dict.type.sys_document_type"
              :key="dict.value"
              :label="dict.value"
            >{{ dict.label }}
            </el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入备注"/>
        </el-form-item>
        <el-form-item label="附件" prop="photoPath">
          <el-upload
            :headers="headers"
            :action="url"
            :multiple="false"
            :file-list="fileList"
            :on-remove="fileRemove"
            :on-success="uploadSuccess"
            :on-error="uploadError"
            :on-progress="uploadProgress"
            :before-upload="beforeUpload"
            :limit="1"
            :on-exceed="beyond"
            accept=".doc,.docx,.xls,.ppt,.xlsx,.pptx,.pdf"
          >
            <el-button size="small">
              上传
              <i class="el-icon-upload el-icon--right"></i>
            </el-button>
            <div class="el-upload__tip" style="color: red" slot="tip">
              提示：仅允许导入“.doc、.docx、.xls、.ppt、.xlsx、.pptx、.pdf”格式文件，文件大小小于10MB！
            </div>
          </el-upload>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!--  文档预览弹出框  -->
    <el-drawer title="文档查看" :visible.sync="isShowPDF" direction="btt" size="98%" style="height: 1000px">
      <div class="wrap" style="height: 100%">
        <iframe :src="pSrc" width="100%" height="100%"></iframe>
      </div>
    </el-drawer>
  </div>
</template>

<script>
import {
  listPreview,
  getPreview,
  delPreview,
  addPreview,
  updatePreview, getBaseUrl,
} from "@/api/system/preview";
import {getToken} from "@/utils/auth";

export default {
  name: "preview",
  dicts: ['sys_document_type'],
  data() {
    return {
      //网站域名
      baseUrl: "",
      //文档预览
      isShowPDF: false,
      pSrc: '',
      //上传时的遮罩层
      loading1: false,
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
      // preview表格数据
      previewList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        filename: null,
        filetype: null,
        uploadpath: null,
        pdfpath: null,
        pdfname: null,
        documentType: null,
      },
      oldForm: {},
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        filename: [
          {
            required: true,
            message: "文件名称不能为空",
            trigger: "blur",
          },
        ],
      },
      // 上传按钮闸口
      btnLoding: false,
      //  请求头
      headers: {Authorization: "Bearer" + " " + getToken()},
      // 上传地址
      url: process.env.VUE_APP_BASE_API + "/common/uploadWithAbsolutePath",
      // 图片列表
      fileList: [],
    };
  },
  created() {
    this.getList();
    this.getUrl();
  },
  methods: {
    getUrl(){
      getBaseUrl().then((res) =>{
        this.baseUrl = res;
      })
    },
    /** 查询preview列表 */
    getList() {
      this.loading = true;
      listPreview(this.queryParams).then((response) => {
        this.previewList = response.rows;
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
        id: null,
        filename: null,
        uploadpath: null,
        documentType: "0"
      };
      this.resetForm("form");
    },
    /*将搜索信息置为null*/
    restQueryParams() {
      this.queryParams = {
        filename: null,
        documentType: null,
      }
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.restQueryParams();
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.fileRemove();
      this.open = true;
      this.title = "添加文件";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.oldForm = row;
      const id = row.id || this.ids;
      getPreview(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改文件";
      });
    },
    // 预览
    handlePreview(row) {
      this.isShowPDF = true;
      // let baseurl = row.previewUrl;
      let baseurl = this.baseUrl + row.pdfname;
      let pSrc = baseurl + '?r=' + new Date();
      this.pSrc = '../../plugin/pdf/web/viewer.html?file=' + encodeURIComponent(pSrc) + '.pdf';

      /*let url = row.previewUrl;
      window.open(url);*/
    },
    // 下载
    handleDownload(row) {
      const baseURL = process.env.VUE_APP_BASE_API
      window.location.href = baseURL + "/common/downloadResourceeasy?resource=" + encodeURI(row.uploadpath);
    },

    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          this.loading1 = true;
          if (this.form.id != null) {
            updatePreview([this.form, this.oldForm]).then((response) => {
              this.loading1 = false;
              this.uploadFlag = "false";
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.fileList = [];
              this.getList();
            }).catch((error)=>{
              this.loading1 = false;
              this.fileList = [];
            });
          } else {
              addPreview(this.form).then((response) => {
                  this.loading1 = false;
                  this.$modal.msgSuccess("新增成功");
                  this.open = false;
                  this.fileList = [];
                  this.getList();
              }).catch((error) =>{
                this.loading1 = false;
                this.fileList = [];
              });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$confirm('是否确认删除文件编号为"' + ids + '"的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return delPreview(ids);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        });
    },
    // 文件上传失败
    uploadError(err) {
      this.btnLoding = false;
      this.$modal.error(err);
    },
    // 上传中
    uploadProgress(e) {
      this.btnLoding = true;
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
        fileType === ".doc" ||
        fileType === ".docx" ||
        fileType === ".xls" ||
        fileType === ".ppt" ||
        fileType === ".pptx" ||
        fileType === ".xlsx" ||
        fileType === ".pdf"
      ) {
        this.form.filename = file.name;
        // 不处理
      } else {
        this.$message.error("请上传正确的文件类型,.doc,.docx,.xls,.ppt,.xlsx,.pptx,.pdf");
        return false;
      }
    },
    // 文件上传成功
    uploadSuccess(res, file, fileList) {
      this.form.uploadpath = res.uploadpath;
      this.uploadFlag = "true";
      this.btnLoding = false;
      this.fileList = fileList;
      this.$message(res.msg);
    },
    beyond(file, fileList) {
      this.$message({
        message: "最多上传一个文件",
        type: "warning",
      });
    },
    // 移除选择的文件
    fileRemove(file, fileList) {
      this.btnLoding = false;
      this.reset();
      this.fileList = [];
    },
  },
};
</script>
