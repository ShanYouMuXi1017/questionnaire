<template>
  <div class="container">

    <el-card style="margin-bottom: 20px;">
      <el-row>
        <el-col :span="6">
          <el-card style="width: 70%;margin: auto;">
            <p style="margin: auto;text-align: center;font-size: 30px;">{{ usertotal }}</p>
            <el-divider>用户数</el-divider>
          </el-card>
        </el-col>
        <el-col :span="6">
          <el-card style="width: 70%;margin: auto;">
            <p style="margin: auto;text-align: center;font-size: 30px;">{{ routertotal }}</p>
            <el-divider>问卷数</el-divider>
          </el-card>
        </el-col>
        <el-col :span="6">
          <el-card style="width: 70%;margin: auto;">
            <p style="margin: auto;text-align: center;font-size: 30px;">{{ answertotal }}</p>
            <el-divider>答卷数</el-divider>
          </el-card>
        </el-col>
        <el-col :span="6">
          <el-card style="width: 70%;margin: auto;">
            <p style="margin: auto;text-align: center;font-size: 30px;">{{ avgAnswerRouter }}</p>
            <el-divider>平均分</el-divider>
          </el-card>
        </el-col>
      </el-row>

    </el-card>
    <el-card class="row" style="margin-bottom: 20px;">
      <el-row>
        <el-col :span="12">
          <el-card style="width: 90%;">
            <div id="app">
              <EChartComponentA />
            </div>
          </el-card>
        </el-col>
        <el-col :span="12">
          <el-card style="width: 90%;">
            <div id="app">
              <EChartComponentC />
            </div>

          </el-card>
        </el-col>
      </el-row>


    </el-card>
    <el-card>
      <div slot="header">
        <span style="text-align: center;font-size: 20px;margin: auto;">答卷小题平均分</span>
      </div>
      <div id="app">
        <EChartComponentB />
      </div>
    </el-card>
 
    <el-card  >
      <div class="el-login-footer">
      <div>ICP备案/许可证号：<a href="https://beian.miit.gov.cn/" target="_blank">豫ICP备2024093876号-1</a></div>
      <div><a target="_blank" href="http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=41042502000211">
          <img src="@/assets/icons/备案编号图标.png" style="" />豫公网安备41042502000211号</a></div>
      <div> Copyright © 2025 msun02</div>
    </div>
    </el-card>

  </div>
</template>

<script>
import EChartComponentA from './echarts/countRouter.vue';
import EChartComponentB from './echarts/avgEveryAnswerRouter.vue';
import EChartComponentC from './echarts/avgEveryoneAnswerRouter.vue';

import { listUser } from '@/api/system/user'
import { listRouter, } from "@/api/questionnaire/router";
import { listQuestion } from "@/api/questionnaire/question";
import { sumEveryoneAnswerRouter, countRouter, avgEveryoneAnswerRouter, avgEveryAnswerRouter, avgAnswerRouter, selectEveryoneAnswerRouter } from "@/api/questionnaire/questAnswerSheetVo";


export default {
  name: "Index",
  components: {
    EChartComponentA,
    EChartComponentB,
    EChartComponentC,
  },
  data() {
    return {
      // 遮罩层
      loading: true,

      // 总条数
      usertotal: 0,
      routertotal: 0,
      questiontotal: 0,
      answertotal: 0,
      listsumEveryoneAnswerRouter: [],


      avgAnswerRouter: null,
      listselectEveryoneAnswerRouter: [],
      users: null,
      // 弹出层标题
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10000,
        nickName: undefined,
        phonenumber: undefined,
        status: undefined,
        deptId: undefined
      },
            // 初始数据
      chartData: [
        { 
          number: [100], 
          content: '{nt}个' 
        },
      ],
    };
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.loading = true
      listUser(this.queryParams).then(response => {
        this.usertotal = response.total
      }
      );
      listRouter(this.queryParams).then(response => {
        this.routertotal = response.total;

      });
      listQuestion(this.queryParams).then(response => {
        this.questiontotal = response.total;
      });
      // 计算每份每人答卷的总分
      sumEveryoneAnswerRouter(this.queryParams).then(response => {
        this.listsumEveryoneAnswerRouter = response;
      });
      // 计算每份问卷的答卷数
      countRouter(this.queryParams).then(response => {
        const listcountRouter = response.rows;
        this.answertotal = 0;
        for (let i = 0; i < response.total; i++) {
          this.answertotal += listcountRouter[i].total;
        }
      });
      // 计算总的平均分
      avgAnswerRouter().then(response => {
        this.avgAnswerRouter = response.avgGrade;
      });
      // 展示每份每人的答卷
      selectEveryoneAnswerRouter(this.queryParams).then(response => {
        this.listselectEveryoneAnswerRouter = response;
      });
      this.loading = false
    },
    updateChartData(newData) {
      // 更新图表数据的方法
      this.chartData = newData;
    },


  }
};
</script>

<style scoped lang="scss">

.el-login-footer {
  height: 40px;
  line-height: 40px;

  bottom: 0;
  width: 100%;
  display: flex;
  text-align: center;
  color: #000000;
  font-family: Arial;
  font-size: 12px;
  letter-spacing: 1px;
}
.el-login-footer > div {
  flex: 1; /* 两个子元素平分空间 */
}
</style>