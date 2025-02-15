<template>
  <div class="app-container">
    <el-card >
      <el-button type="success" round  v-show="paramRouteId ==null" >      
        <router-link  to="readRouter" >
          选择问卷
        </router-link></el-button>
      <span v-show="questionList.length >0" >问卷名称：{{ paramRouteName }}</span>

        <el-button
        v-show="questionList.length > 0"
          style="float: right;margin-right: 10px;"
          type="success"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['questionnaire:question:export']"
        >导出</el-button>

    </el-card>


    <el-table v-show="questionList.length > 0" v-loading="loading" :data="filteredQuestionList" >
      <el-table-column label="问题序号" align="center" width="80" type="index" :index="indexMethod"></el-table-column>
      <el-table-column label="问题编号" align="center" prop="issueId"  width="80"/>
      <el-table-column label="问题类型" align="center" prop="problemType"  width="120">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.quest_issue_type" :value="scope.row.problemType"/>
        </template>
      </el-table-column>
      <el-table-column label="问题类型权重" align="center" prop="typeWeight"  width="100" />
      <el-table-column label="问题" align="center" prop="problem" />
      <el-table-column label="评分" align="center" prop="grade"  width="50"/>
      <el-table-column label="回答类型" align="center" prop="answerType"  width="80">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.quest_answer_type" :value="scope.row.answerType"/>
        </template>
      </el-table-column>

      <el-table-column label="操作" align="center" class-name="small-padding fixed-width"  width="150">
        <!-- <template slot-scope="scope">
        <el-input
          v-model="search"
          size="mini"
          placeholder="输入关键字搜索"/>
      </template> -->
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['questionnaire:question:edit']"
          >修改</el-button>
        </template>
      </el-table-column>
    </el-table>
 



    <!-- 添加或修改问卷问题对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="60%" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="12">
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
          </el-col>
          <el-col :span="12">
            <el-form-item label="问题类型权重" prop="typeWeight">
          <el-input v-model="form.typeWeight" placeholder="请输入问题类型权重" />
        </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
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
          </el-col>
          <el-col :span="12">
            <el-form-item label="评分" prop="grade">
          <el-input v-model="form.grade" placeholder="请输入评分" />
        </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="问题" prop="problem">
          <el-input v-model="form.problem" placeholder="请输入问题" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listQuestion, getQuestion, delQuestion, addQuestion, updateQuestion } from "@/api/questionnaire/question";

export default {
  name: "QuestionnaireDetails",
  dicts: ['quest_answer_type', 'quest_issue_type'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 总条数
      total: 0,
      // 问卷问题表格数据
      questionList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      paramRouteId: null,
      paramRouteName: null,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 100,
        routerId: null,
      },
      // 表单参数
      form: {},
      search: '',
      rules: {
      }
      
    };
  },
  created() {
    // this.getList();
  },
  mounted() {
    this.paramRouteId = this.$route.params.paramRouteId;
    this.paramRouteName = this.$route.params.paramRouteName;
    if(this.paramRouteId){
      this.getList();
    }
    this.loading = false;
    },
    computed: {
  filteredQuestionList() {
    // 如果没有搜索词，返回原列表
    if (!this.search || this.search.trim() === '') {
      return this.questionList;
    }
    
        // 排序列表
        this.questionList.sort((a, b) => {
      return a.problemType - b.problemType; // 升序排序
    });

    // 使用 filter 方法过滤列表
    return this.questionList.filter(data => 
      data.problem && data.problem.toLowerCase().includes(this.search.toLowerCase())
    );
  }
},
  methods: {
    /** 查询问卷问题列表 */
    getList() {
      this.loading = true;      
      this.queryParams.routerId = this.paramRouteId ;

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
        routerId: null,
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
    /** 导出按钮操作 */
    handleExport() {
      this.download('questionnaire/question/export', {
        ...this.queryParams
      }, `question_${new Date().getTime()}.xlsx`)
    },
    indexMethod(index) {
        return index;
      },
        // 点击路由实现跳转功能
        readDetails() {
      this.$router.push({
      name: 'read',
    })
    },
    

  }
};
</script>
