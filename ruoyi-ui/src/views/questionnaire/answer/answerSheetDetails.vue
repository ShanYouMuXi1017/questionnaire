<template>
  <div class="app-container">
    <el-card  >
       
      <el-button type="success" round v-show="paramRouteId ==null" >      
        <router-link  to="read" >
          选择路线
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
      <el-table-column label="得分" align="center" prop="answer"  width="50"/>
      <el-table-column label="答案" align="center" prop="answerResult"  width="100"></el-table-column>
      <el-table-column label="回答类型" align="center" prop="answerType"  width="80">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.quest_answer_type" :value="scope.row.answerType"/>
        </template>
      </el-table-column>
    </el-table>
   </div>
</template>

<script> 
import { selectEveryoneAnswerRouter } from "@/api/questionnaire/questAnswerSheetVo";
export default {
  name: "AnswerSheetDetails",
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
      paramUserId: null,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 100,
        routerId: null,
        userId: null,
        
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
    this.paramUserId = this.$route.params.paramUserId;
    console.log("this.paramRouteId");
    console.log(this.paramRouteId);
    console.log("this.paramRouteName");
    console.log(this.paramRouteName);
    console.log("this.paramUserId");
    console.log(this.paramUserId);
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
      this.queryParams.userId = this.paramUserId;
      console.log("this.queryParams");
      console.log(this.queryParams);
      selectEveryoneAnswerRouter(this.queryParams).then(response => {
        console.log("response");
        console.log(response.rows);
        this.questionList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },



    /** 导出按钮操作 */
    handleExport() {
      this.download('questionnaire/question/export', {
        ...this.queryParams
      }, `question_${new Date().getTime()}.xlsx`)
    },
    indexMethod(index) {
        return index+1;
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
