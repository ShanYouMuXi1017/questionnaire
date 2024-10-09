<template>
  <div>
    <div v-for="(item, index) in listcountRouter" :key="index" :ref="`chartDom_${index}`" style="width: 90%; height: 400px;"></div>
  </div>
</template>

<script>
import * as echarts from 'echarts';
import { countRouter, avgEveryAnswerRouter } from "@/api/questionnaire/questAnswerSheetVo";
import { getRouter } from "@/api/questionnaire/router";

export default {
  name: 'EChartComponent',
  data() {
    return {
      loading: false,
      charts: [],
      echartName: '',
      listcountRouter: [],
      listavgEveryAnswerRouter: [],
      queryParams: {
        pageNum: 1,
        pageSize: 10000,
        routerId: undefined,
      },
    };
  },
  mounted() {
    this.getList();
  },
  beforeDestroy() {
    this.charts.forEach(chart => {
      if (chart) {
        chart.dispose();
      }
    });
  },
  methods: {
    initChart(chartDom, dataAxis, data, echartName) {
      const myChart = echarts.init(chartDom);
      const yMax = 100;
      const dataShadow = new Array(data.length).fill(yMax);

      const option = {
        title: {
          text: echartName,
          subtext: '小题平均分'
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
                  { offset: 0.7, color: '#2378f0' },
                  { offset: 1, color: '#83bff6' }
                ])
              }
            },
            data: data
          }
        ]
      };

      myChart.setOption(option);

      const zoomSize = 6;
      myChart.on('click', (params) => {
        const start = Math.max(params.dataIndex - zoomSize / 2, 0);
        const end = Math.min(params.dataIndex + zoomSize / 2, data.length - 1);
        myChart.dispatchAction({
          type: 'dataZoom',
          startValue: dataAxis[start],
          endValue: dataAxis[end]
        });
      });

      return myChart;
    },
    getList() {
      this.loading = true;
      countRouter(this.queryParams).then(response => {
        this.listcountRouter = response.rows;
        this.avg();
      }).catch(error => {
        console.error("Error fetching data: ", error);
        this.loading = false;
      });
    },
    avg() {
      const promises = this.listcountRouter.map(item => {
        return avgEveryAnswerRouter({ ...this.queryParams, routerId: item.routerId })
          .then(response => {
            if (response && response.rows) {
              return getRouter(item.routerId)
                .then(routerResponse => {
                  if (routerResponse && routerResponse.data && routerResponse.data.routeName) {
                    return { avgResponse: response, routerName: routerResponse.data.routeName };
                  } else {
                    console.error("Router response does not contain routeName");
                    return { avgResponse: response, routerName: '' };
                  }
                })
                .catch(error => {
                  console.error("Error fetching router data: ", error);
                  return { avgResponse: response, routerName: '' };
                });
            } else {
              console.error("Avg response does not contain rows", response);
              return { avgResponse: [], routerName: '' };
            }
          })
          .catch(error => {
            console.error("Error fetching avg data: ", error);
            return { avgResponse: [], routerName: '' };
          });
      });

      Promise.all(promises).then(results => {
        this.$nextTick(() => {
          results.forEach((result, index) => {
            const chartDom = this.$refs[`chartDom_${index}`][0];
            if (chartDom) {
              const dataAxis = result.avgResponse.rows.map(item => item.problem);
              const data = result.avgResponse.rows.map(item => item.avgGrade);
              const echartName = result.routerName || `Chart ${index + 1}`;
              this.charts[index] = this.initChart(chartDom, dataAxis, data, echartName);
            } else {
              console.error(`Chart DOM not found for index: ${index}`);
            }
          });
        });
      }).catch(error => {
        console.error("Error in promises: ", error);
      }).finally(() => {
        this.loading = false;
      });
    }
  }
};
</script>

<style scoped>
/* 你可以在这里添加一些样式 */
</style>