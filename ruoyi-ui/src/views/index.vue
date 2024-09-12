<template>
  <div class="app-container home">
    <div v-if="isAdmin">
      <div>
        <h1 style="color: #C03639">点击图中的“按钮”，可以查看对应的数据<span>（注：为了保证数据的准确性，每一次查看时刷新一下页面或点击图中的”日、月、年按钮“）</span>
        </h1>
      </div>

<!-- 不同时间段内业务量统计图     -->
      <el-row>
        <el-col :span="24">
          <el-card>
            <button @click="initLineFourChat"> 周</button>&nbsp;&nbsp;
            <button @click="getBusByThisMonth"> 月</button>&nbsp;&nbsp;
            <button @click="getBusByThisYear"> 年</button>
            <span style="color: #C03639">（注：日期为更新该订单的时间）</span>
            <div style="width: 100%; height: 550px" id="line_four"></div>
          </el-card>
        </el-col>
      </el-row>

      <el-row :gutter="10">
        <el-col :span="12">
          <el-card>
            <button @click="getMonth"> 近30天</button>&nbsp;&nbsp;
            <button @click="getPerMonthOrder"> 月</button>&nbsp;&nbsp;
            <button @click="getYear"> 年</button>
            <span style="color: #C03639">（注：日期为订单交付的时间）</span>
            <div style="width: 100%; height: 400px" id="line"></div>
          </el-card>
        </el-col>
        <el-col :span="12">
          <el-card>
            <button @click="getMonthBar"> 近30天</button>&nbsp;&nbsp;
            <button @click="getPerMonthOrderBar"> 月</button>&nbsp;&nbsp;
            <button @click="getYearBar"> 年</button>
            <span style="color: #C03639">（注：日期为订单交付的时间）</span>
            <div style="width: 100%; height: 400px" id="bar"></div>
          </el-card>
        </el-col>
<!--        用户数量统计图-->
        <el-col :span="12">
          <el-card>
            <div style="width: 100%; height: 400px" id="pie"></div>
          </el-card>
        </el-col>
<!--        文档数量统计图-->
        <el-col :span="12">
          <el-card>
            <div style="width: 100%; height: 400px" id="documentPie"></div>
          </el-card>
        </el-col>
      </el-row>



      <div>
        <h1 style="text-align: center">申请、检测、维修、交付中“已同意”和”已拒绝“的比例图</h1>
      </div>
      <el-row :gutter="10" :style="{width: '2600px',marginTop:'20px'}">
        <el-col :span="3">
          <el-card>
            <div style="width: 100%; height: 400px" id="pie1"></div>
          </el-card>
        </el-col>
        <el-col :span="3">
          <el-card>
            <div style="width: 100%; height: 400px" id="pie2"></div>
          </el-card>
        </el-col>
        <el-col :span="3">
          <el-card>
            <div style="width: 100%; height: 400px" id="pie3"></div>
          </el-card>
        </el-col>
        <el-col :span="3">
          <el-card>
            <div style="width: 100%; height: 400px" id="pie4"></div>
          </el-card>
        </el-col>
      </el-row>
    </div>

    <div v-else-if="!isAdmin">
      <h1 style="text-align: center">对不起，您没有权限查看!</h1>
    </div>

    <el-dialog
      title="提示"
      :close-on-click-modal="false"
      :close-on-press-escape="false"
      :visible.sync="centerDialogVisible"
      width="30%"
      top="150px"
      center>
      <span>
        <div>当前共有<b style="color:red;">{{ num }}名</b>普通用户达到成为代理商的门槛</div>
        <br/>
        <div>是否<b style="color: red">一键同意</b>这些用户成为代理商</div>
      </span>
      <span slot="footer" class="dialog-footer">
    <el-button @click="handleAllAgree3">取 消</el-button>
    <el-button type="primary" @click="handleAllAgree"
               v-hasPermi="['system:distri:all']">同 意</el-button>
  </span>
    </el-dialog>
  </div>

</template>

<script>
import * as echarts from 'echarts';
import {
  getAgentNum, getBusByPMonth, getBusiness, getBusMonth, getBusYear,
  getByMonth,
  getByPerMonth,
  getByYear, getDocuCount,
  getForDelivery,
  getForInspect,
  getForRepair,
  getStForRequests
} from "@/api";
import {parseStrEmpty} from "@/utils/ruoyi";
import {allAgree, listDistri, listDistri2} from "@/api/system/distri";

const option1 = {
  title: {
    text: '近30天维修营业额趋势图',
    left: 'center'
  },
  legend: {
    left: 'left',
    orient: 'vertical',
  },
  tooltip: {
    trigger: 'axis'
  },
  xAxis: {
    type: 'category',
    data: []
  },
  yAxis: {
    type: 'value',
  },
  series: [
    {
      name: '维修营业额（元）',
      data: [],
      type: 'line',
      smooth: true
    },

  ]
};
const option2 = {
  title: {
    text: '近30天维修营业额以及订单数',
    left: 'center'
  },
  legend: {
    left: 'left',
    orient: 'vertical',
  },
  tooltip: {
    trigger: 'axis'
  },
  xAxis: {
    type: 'category',
    data: []
  },
  yAxis: {
    type: 'value'
  },
  series: [
    {
      name: '营业额（元）',
      data: [],
      type: 'bar'
    },
    {
      name: '订单数（笔）',
      data: [],
      type: 'bar'
    }
  ]
};
//根据年月日查看业务量
const optionFour = {
  title: {
    text: '本周业务量统计图',
    left: 'center',
    top:'4%'
  },
  tooltip: {
    trigger: 'axis'
  },
  legend: {
    left: 'center',
  },
  grid: {
    left: '3%',
    right: '4%',
    bottom: '3%',
    top:'12%',
    containLabel: true
  },
  xAxis: {
    type: 'category',
    boundaryGap: false,
    data: []
  },
  yAxis: {
    type: 'value'
  },
  series: [
    {
      name: '待维修订单数',
      type: 'line',
      data: [],
      smooth: true
    },
    {
      name: '正在维修订单数',
      type: 'line',
      data: [],
      smooth: true
    },
    {
      name: '已修复订单数',
      type: 'line',
      data: [],
      smooth: true
    },
    {
      name: '已取消订单数',
      type: 'line',
      data: [],
      smooth: true
    }
  ]
};
const option3 = {
  title: {
    text: '代理商占比统计图',
    subtext: '比例图',
    left: 'center'
  },
  tooltip: {
    trigger: 'item'
  },
  legend: {
    orient: 'vertical',
    left: 'left'
  },
  label: {
    show: true,
    formatter(param) {
      return param.name + '(' + param.percent+'%' + ')';
    }
  },
  series: [
    {
      name: '人数',
      type: 'pie',
      radius: '50%',
      center: ['50%', '60%'],
      data: [
        {value: '', name: '代理商人数'},
        {value: '', name: '非代理商人数'},
        {value: '', name: '用户总人数'},
      ],
      emphasis: {
        itemStyle: {
          shadowBlur: 10,
          shadowOffsetX: 0,
          shadowColor: 'rgba(0, 0, 0, 0.5)'
        }
      }
    }
  ]
};

const documentOption = {
  title: {
    text: '技术文档统计图',
    subtext: '比例图',
    left: 'center'
  },
  tooltip: {
    trigger: 'item'
  },
  legend: {
    orient: 'vertical',
    left: 'left'
  },
  label: {
    show: true,
    formatter(param) {
      return param.name + '(' + param.percent +'%' + ')';
    }
  },
  series: [
    {
      name: '文档数',
      type: 'pie',
      radius: '50%',
      center: ['50%', '60%'],
      data: [
        {value: '', name: '产品说明书'},
        {value: '', name: '常见故障案列'},
        {value: '', name: '文档总数'},
      ],
      emphasis: {
        itemStyle: {
          shadowBlur: 10,
          shadowOffsetX: 0,
          shadowColor: 'rgba(0, 0, 0, 0.5)'
        }
      }
    }
  ]
};
const optionPie1 = {
  title: {
    text: '申请统计图',
    subtext: '比例图',
    left: 'center'
  },
  tooltip: {
    trigger: 'item'
  },
  legend: {
    orient: 'vertical',
    left: 'left'
  },
  series: [
    {
      name: '订单数',
      type: 'pie',
      radius: '50%',
      center: ['50%', '60%'],
      label: {
        show: true,
        formatter(param) {
          return param.name + '(' + param.percent  +'%'+ ')';
        }
      },
      data: [
        {value: "", name: '未处理'},
        {value: "", name: '已同意'},
        {value: "", name: '已拒绝'},
      ],
      emphasis: {
        itemStyle: {
          shadowBlur: 10,
          shadowOffsetX: 0,
          shadowColor: 'rgba(0, 0, 0, 0.5)'
        }
      }
    }
  ]
};

const optionPie2 = {
  title: {
    text: '检测统计图',
    subtext: '比例图',
    left: 'center'
  },
  tooltip: {
    trigger: 'item'
  },
  legend: {
    orient: 'vertical',
    left: 'left'
  },
  series: [
    {
      name: '订单数',
      type: 'pie',
      radius: '50%',
      center: ['50%', '60%'],
      label: {
        show: true,
        formatter(param) {
          return param.name + '(' + param.percent +'%' + ')';
        }
      },
      data: [
        {value: "", name: '未检测'},
        {value: "", name: '已检测'},
        {value: "", name: '拒绝检测'},
      ],
      emphasis: {
        itemStyle: {
          shadowBlur: 10,
          shadowOffsetX: 0,
          shadowColor: 'rgba(0, 0, 0, 0.5)'
        }
      }
    }
  ]
};

const optionPie3 = {
  title: {
    text: '维修统计图',
    subtext: '比例图',
    left: 'center'
  },
  tooltip: {
    trigger: 'item'
  },
  legend: {
    orient: 'vertical',
    left: 'left'
  },
  series: [
    {
      name: '订单数',
      type: 'pie',
      radius: '50%',
      center: ['50%', '60%'],
      label: {
        show: true,
        formatter(param) {
          return param.name + '(' + param.percent +'%' + ')';
        }
      },
      data: [
        {value: '', name: '未维修'},
        {value: '', name: '已维修'},
        {value: '', name: '正在维修'},
      ],
      emphasis: {
        itemStyle: {
          shadowBlur: 10,
          shadowOffsetX: 0,
          shadowColor: 'rgba(0, 0, 0, 0.5)'
        }
      }
    }
  ]
};

const optionPie4 = {
  title: {
    text: '交付订单统计图',
    subtext: '比例图',
    left: 'center'
  },
  tooltip: {
    trigger: 'item'
  },
  legend: {
    orient: 'vertical',
    left: 'left'
  },
  series: [
    {
      name: '订单数',
      type: 'pie',
      radius: '50%',
      center: ['40%', '60%'],
      label: {
        show: true,
        formatter(param) {
          return param.name + '(' + param.percent +'%' + ')';
        }
      },
      data: [
        {value: '', name: '未交付'},
        {value: '', name: '已交付'}
      ],
      emphasis: {
        itemStyle: {
          shadowBlur: 10,
          shadowOffsetX: 0,
          shadowColor: 'rgba(0, 0, 0, 0.5)'
        }
      }
    }
  ]
};
export default {
  name: "Index",
  data() {
    return {
      num: 0,
      centerDialogVisible: false,
      userInfo: "",
      //访问权限
      isAdmin: false,
      // 版本号
      version: "3.8.7",
      //折线图的变量
      myLineChart: '',
      chartLineDom: '',
      //柱状图
      myBarChart: '',
      chartBarDom: '',
      //根据年月日展示处于四个维修状态的业务量
      myLineFourChart: '',
      chartLineFourDom: '',

      //饼图
      //申请
      chartPieDom1: '',
      myPieChart1: '',
      //检测
      chartPieDom2: '',
      myPieChart2: '',
      //维修
      chartPieDom3: '',
      myPieChart3: '',
      //交付
      chartPieDom4: '',
      myPieChart4: '',
      //代理商
      chartPieDom: '',
      myPieChart: '',
      //文档
      chartDocuPieDom: '',
      myDocuPieChart: '',
    };
  },
  created() {
    this.isShow();
    this.handleAllAgree2();
  },
  mounted() {
    //初始化营业额折线图与柱状图
    this.initLineChat();
    //初始化申请表饼图
    this.initApplicationChat();
    //初始化检测表饼图
    this.initInspectChat();
    //初始化维修表饼图
    this.initRepairChat();
    //初始化交付表饼图
    this.initDeliveryChat();
    //初始化代理商饼图
    this.initAgentChat();

    //初始化文档数量饼图
    this.initDocumentChat();

    //初始化本日业务量
    this.initLineFourChat();


  },
  watch: {
    '$route' (to, from) {
      this.loadUserData();
    }
  },
  methods: {
    //设置访问首页的权限
    isShow() {
      if (this.$store.getters.userId === 1) {
        this.isAdmin = true;
      } else {
        this.isAdmin = false;
      }
    },
    //清空option1
    clearOption1() {
      option1.xAxis.data = [];
      option1.series[0].data = [];
    },
    //清空option2
    clearOption2() {
      option2.xAxis.data = [];
      option2.series[0].data = [];
      option2.series[1].data = [];
    },
    //清空optionPie1
    clearOptionPie1() {
      optionPie1.series.date[0].value = '';
      optionPie1.series.date[1].value = '';
      optionPie1.series.date[2].value = '';
    },
    goTarget(href) {
      window.open(href, "_blank");
    },
    //折线图
    getMonth() {
      getByMonth().then(res => {
        this.clearOption1();
        option1.title.text = "近30天维修营业额趋势图"
        option1.xAxis.data = res.rows?.map(v => v.date) || []
        option1.series[0].data = res.rows?.map(v => v.value) || []
        this.myLineChart.setOption(option1);
        console.log("营业额："+JSON.stringify(res));
      })
    },
    getPerMonthOrder() {
      getByPerMonth().then(resByMonth => {
        this.clearOption1();
        option1.title.text = "本年度每月维修营业额趋势图"
        option1.xAxis.data = resByMonth.rows?.map(v => v.date) || []
        option1.series[0].data = resByMonth.rows?.map(v => v.value) || []
        this.myLineChart.setOption(option1);
      })
    },
    getYear() {
      getByYear().then(resByYear => {
        this.clearOption1();
        option1.title.text = "历年维修营业额趋势图"
        option1.xAxis.data = resByYear.rows?.map(v => v.date) || []
        option1.series[0].data = resByYear.rows?.map(v => v.value) || []
        this.myLineChart.setOption(option1);
      })
    },
    //柱状图
    getMonthBar() {
      getByMonth().then(res => {
        this.clearOption2();
        option2.title.text = "近30天维修营业额以及订单数"
        option2.xAxis.data = res.rows?.map(v => v.date) || []
        option2.series[0].data = res.rows?.map(v => v.value) || []
        option2.series[1].data = res.rows?.map(v => v.orderCount) || []
        this.myBarChart.setOption(option2);
      })
    },
    getPerMonthOrderBar() {
      getByPerMonth().then(resByMonth => {
        this.clearOption2();
        option2.title.text = "本年度每月维修营业额以及订单数"
        option2.xAxis.data = resByMonth.rows?.map(v => v.date) || []
        option2.series[0].data = resByMonth.rows?.map(v => v.value) || []
        option2.series[1].data = resByMonth.rows?.map(v => v.orderCount) || []
        this.myBarChart.setOption(option2);
      })
    },
    getYearBar() {
      getByYear().then(resByYear => {
        this.clearOption2();
        option2.title.text = "历年维修营业额以及订单数"
        option2.xAxis.data = resByYear.rows?.map(v => v.date) || []
        option2.series[0].data = resByYear.rows?.map(v => v.value) || []
        option2.series[1].data = resByYear.rows?.map(v => v.orderCount) || []
        this.myBarChart.setOption(option2);
      })
    },

    //初始化营业额的折线图与柱状图
    initLineChat() {
      getByMonth().then(res => {
        //折线图
        this.chartLineDom = document.getElementById('line');
        this.myLineChart = echarts.init(this.chartLineDom);
        //初始化折线图
        this.clearOption1();
        option1.xAxis.data = res.rows?.map(v => v.date) || []
        option1.series[0].data = res.rows?.map(v => v.value) || []
        this.myLineChart.setOption(option1);

        //初始化柱状图
        this.clearOption2();
        //柱状图
        this.chartBarDom = document.getElementById('bar');
        this.myBarChart = echarts.init(this.chartBarDom);
        option2.xAxis.data = res.rows?.map(v => v.date) || []
        option2.series[0].data = res.rows?.map(v => v.value) || []
        option2.series[1].data = res.rows?.map(v => v.orderCount) || []
        this.myBarChart.setOption(option2);

      })
    },
    //初始化根据年月日统计处于四个状态的业务量
    initLineFourChat(){
      this.chartLineFourDom = document.getElementById('line_four');
      this.myLineFourChart = echarts.init(this.chartLineFourDom);

      this.clearOptionFour();
      optionFour.title.text = '本周业务量统计图'
      getBusiness().then(res =>{
        optionFour.xAxis.data = res.rows[0]?.map(v => v.date) || []
        optionFour.series[0].data = res.rows[0]?.map(v => v.orderCount) || []
        optionFour.series[1].data = res.rows[1]?.map(v => v.orderCount) || []
        optionFour.series[2].data = res.rows[2]?.map(v => v.orderCount) || []
        optionFour.series[3].data = res.rows[3]?.map(v => v.orderCount) || []
        this.myLineFourChart.setOption(optionFour);
      })
    },

    //初始化申请表的饼图
    initApplicationChat() {
      getStForRequests().then(res => {
        this.chartPieDom1 = document.getElementById('pie1');
        this.myPieChart1 = echarts.init(this.chartPieDom1);
        optionPie1.series[0].data[0].value = res.rows[0];
        optionPie1.series[0].data[1].value = res.rows[1];
        optionPie1.series[0].data[2].value = res.rows[2];
        this.myPieChart1.setOption(optionPie1);
      })
    },
    //初始化检测表饼图
    initInspectChat() {
      getForInspect().then(res => {
        this.chartPieDom2 = document.getElementById('pie2');
        this.myPieChart2 = echarts.init(this.chartPieDom2);
        optionPie2.series[0].data[0].value = res.rows[0];
        optionPie2.series[0].data[1].value = res.rows[1];
        optionPie2.series[0].data[2].value = res.rows[2];
        this.myPieChart2.setOption(optionPie2);
      })
    },
    //初始化维修表饼图
    initRepairChat() {
      getForRepair().then(res => {
        this.chartPieDom3 = document.getElementById('pie3');
        this.myPieChart3 = echarts.init(this.chartPieDom3);
        optionPie3.series[0].data[0].value = res.rows[0];
        optionPie3.series[0].data[1].value = res.rows[1];
        optionPie3.series[0].data[2].value = res.rows[2];
        this.myPieChart3.setOption(optionPie3);
      })
    },
    //初始化交付表饼图
    initDeliveryChat() {
      getForDelivery().then(res => {
        this.chartPieDom4 = document.getElementById('pie4');
        this.myPieChart4 = echarts.init(this.chartPieDom4);
        optionPie4.series[0].data[0].value = res.rows[0];
        optionPie4.series[0].data[1].value = res.rows[1];
        this.myPieChart4.setOption(optionPie4);
      })
    },
    initAgentChat() {
      getAgentNum().then(res => {
        //饼图
        this.chartPieDom = document.getElementById('pie');
        this.myPieChart = echarts.init(this.chartPieDom);
        option3.series[0].data[0].value = res.rows[0];
        option3.series[0].data[1].value = res.rows[1];
        option3.series[0].data[2].value = res.rows[0]+res.rows[1];
        this.myPieChart.setOption(option3);
      })
    },
    /*处理一键同意*/
    handleAllAgree2() {
      listDistri2().then(response=>{
        response.data.forEach(item => {
          if (item.isReach === 0 && item.isAgent === 1) {
            this.num++;
          }
        });
        if (this.num !== 0) {
          this.centerDialogVisible = true;
        }
      })
    },
    handleAllAgree3() {
      this.centerDialogVisible = false;
      this.num = 0;
    },
    handleAllAgree() {
      this.centerDialogVisible = false;
      this.$message({
        message: '数据加载中，请勿重复提交',
        type: 'warning'
      });
      allAgree().then(response => {
        this.$message({
          message: '已处理所有代理商申请',
          type: 'success'
        });
        this.getList()
        this.num = 0;
      })
    },
    //清空业务量统计图
    clearOptionFour(){
      optionFour.xAxis.data = [];
      optionFour.series[0].data = [];
      optionFour.series[1].data = [];
      optionFour.series[2].data = [];
      optionFour.series[3].data = [];
    },
    //获取本月的业务量
    getBusByThisMonth(){
      this.chartLineFourDom = document.getElementById('line_four');
      this.myLineFourChart = echarts.init(this.chartLineFourDom);

      this.clearOptionFour();
      optionFour.title.text = '本月业务量统计图'
      getBusMonth().then(res =>{
        optionFour.xAxis.data = res.rows[0]?.map(v => v.date) || []
        optionFour.series[0].data = res.rows[0]?.map(v => v.orderCount) || []
        optionFour.series[1].data = res.rows[1]?.map(v => v.orderCount) || []
        optionFour.series[2].data = res.rows[2]?.map(v => v.orderCount) || []
        optionFour.series[3].data = res.rows[3]?.map(v => v.orderCount) || []
        this.myLineFourChart.setOption(optionFour);
      })
    },
    //获取今年的业务量
    getBusByThisYear(){
      this.chartLineFourDom = document.getElementById('line_four');
      this.myLineFourChart = echarts.init(this.chartLineFourDom);

      this.clearOptionFour();
      optionFour.title.text = '本年度业务量统计图'
      getBusYear().then(res => {
        optionFour.xAxis.data = res.rows[0]?.map(v => v.date) || []
        optionFour.series[0].data = res.rows[0]?.map(v => v.orderCount) || []
        optionFour.series[1].data = res.rows[1]?.map(v => v.orderCount) || []
        optionFour.series[2].data = res.rows[2]?.map(v => v.orderCount) || []
        optionFour.series[3].data = res.rows[3]?.map(v => v.orderCount) || []
        this.myLineFourChart.setOption(optionFour);
      })
    },
    //获取不同类型文档的数量
    initDocumentChat() {
      getDocuCount().then(res => {
        //饼图
        this.chartDocuPieDom = document.getElementById('documentPie');
        this.myDocuPieChart = echarts.init(this.chartDocuPieDom);
        documentOption.series[0].data[0].value = res.rows[0].docuCount;
        documentOption.series[0].data[1].value = res.rows[1].docuCount;
        documentOption.series[0].data[2].value = res.rows[0].docuCount+res.rows[1].docuCount;
        this.myDocuPieChart.setOption(documentOption);
      })
    },
    refresh() {
      location.reload();
    },
  }
};
</script>

<style scoped lang="scss">
.home {
  blockquote {
    padding: 10px 20px;
    margin: 0 0 20px;
    font-size: 17.5px;
    border-left: 5px solid #eee;
  }

  hr {
    margin-top: 20px;
    margin-bottom: 20px;
    border: 0;
    border-top: 1px solid #eee;
  }

  .col-item {
    margin-bottom: 20px;
  }

  ul {
    padding: 0;
    margin: 0;
  }

  font-family: "open sans", "Helvetica Neue", Helvetica, Arial, sans-serif;
  font-size: 13px;
  color: #676a6c;
  overflow-x: hidden;

  ul {
    list-style-type: none;
  }

  h4 {
    margin-top: 0px;
  }

  h2 {
    margin-top: 10px;
    font-size: 26px;
    font-weight: 100;
  }

  p {
    margin-top: 10px;

    b {
      font-weight: 700;
    }
  }

  .update-log {
    ol {
      display: block;
      list-style-type: decimal;
      margin-block-start: 1em;
      margin-block-end: 1em;
      margin-inline-start: 0;
      margin-inline-end: 0;
      padding-inline-start: 40px;
    }
  }
}
</style>

