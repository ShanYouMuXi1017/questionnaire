<template>
  <div ref="chartDom" style="width: 600px; height: 400px;"></div>
</template>

<script>
import * as echarts from 'echarts';
import { avgEveryoneAnswerRouter } from "@/api/questionnaire/questAnswerSheetVo";

export default {
  name: 'EChartComponent',
  data() {
    return {
      loading: false,
      myChart: null, // 确保myChart是响应式的
      option: null,
      listavgEveryoneAnswerRouter:[],
      queryParams: {
        pageNum: 1,
        pageSize: 10000,
        nickName: undefined,
        phonenumber: undefined,
        status: undefined,
        deptId: undefined
      },
    };
  },
  mounted() {
    this.getList();
  },
  beforeDestroy() {
    if (this.myChart) {
      this.myChart.dispose();
    }
  },
  methods: {
    initChart() {
      const chartDom = this.$refs.chartDom;
      this.myChart = echarts.init(chartDom);
      const dataAxis = this.listavgEveryoneAnswerRouter.map(item => item.routeName);
      const data = this.listavgEveryoneAnswerRouter.map(item => item.avgGrade);
      const yMax = 100;
      const dataShadow = new Array(data.length).fill(yMax);

      this.option = {
        title: {
          text: '答卷平均分',
          subtext: '统计每份答卷的平均分'
        },
        xAxis: {
          data: dataAxis,
          axisLabel: {
            inside: true,
            color: '#fff'
          },
          axisTick: {
            show: false
          },
          axisLine: {
            show: false
          },
          z: 10
        },
        yAxis: {
          axisLine: {
            show: false
          },
          axisTick: {
            show: false
          },
          axisLabel: {
            color: '#999'
          }
        },
        dataZoom: [
          {
            type: 'inside'
          }
        ],
        series: [
          {
            type: 'bar',
            showBackground: true,
            itemStyle: {
              color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                { offset: 0, color: '#83bff6' },
                { offset: 0.5, color: '#188df0' },
                { offset: 1, color: '#188df0' }
              ])
            },
            emphasis: {
              itemStyle: {
                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                  { offset: 0, color: '#2378f7' },
                  { offset: 0.7, color: '#2378f7' },
                  { offset: 1, color: '#83bff6' }
                ])
              }
            },
            data: data
          }
        ]
      };

      this.myChart.setOption(this.option);

      // Enable data zoom when user click bar.
      const zoomSize = 6;
      this.myChart.on('click', (params) => { // 使用箭头函数保持this上下文
        const start = Math.max(params.dataIndex - zoomSize / 2, 0);
        const end = Math.min(params.dataIndex + zoomSize / 2, data.length - 1);
        this.myChart.dispatchAction({
          type: 'dataZoom',
          startValue: dataAxis[start],
          endValue: dataAxis[end]
        });
      });
    },
    getList() {
      this.loading = true;
          // 计算每份答卷的平均分
        avgEveryoneAnswerRouter(this.queryParams).then(response => {
        this.listavgEveryoneAnswerRouter = response.rows;
        this.loading = false;
        this.initChart(); // 获取数据后再初始化图表
      }).catch(error => {
        console.error("Error fetching data: ", error);
        this.loading = false;
      });
    },
  }
};
</script>

<style scoped>
/* 你可以在这里添加一些样式 */
</style>