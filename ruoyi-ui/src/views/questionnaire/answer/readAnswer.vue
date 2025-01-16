<template>
  <div class="app-container"
       v-loading="downLoading"
       element-loading-text="拼命下载中"
       element-loading-spinner="el-icon-loading"
       element-loading-background="rgba(0, 0, 0, 0.8)">
    <el-card v-show="paramRouteId == null">
      <el-button type="success" round>
        <router-link to="read">
          选择路线
        </router-link></el-button>
    </el-card>

    <el-card shadow="never" class="box-card" v-show="paramRouteId != null">
      <el-row>
        <el-col :span="16">
          <el-button icon="el-icon-top" size="mini" @click="setCardsPerRow(2)">每行2个</el-button>
          <el-button icon="el-icon-top" size="mini" @click="setCardsPerRow(3)">每行3个</el-button>
          <el-button icon="el-icon-top" size="mini" @click="setCardsPerRow(4)">每行4个</el-button>
          <el-button type="primary" icon="el-icon-bottom" size="mini" @click="sumGradeDesc()">降序排序</el-button>
          <el-button icon="el-icon-top" size="mini" @click="sumGradeAsc()">升序排序</el-button>
          <el-button
            size="mini"
            icon="el-icon-download"
            @click="downloadExcel()"
          >下载问卷汇总</el-button>
        </el-col>
        <el-col :span="8">
          <span>
            <el-input v-model="search" size="mini" placeholder="输入路线名称搜索" />
          </span>
        </el-col>

      </el-row>


    </el-card>

    <el-card shadow="never" v-loading="loading" style="margin-top: 20px;" >
      <el-row>
        <el-col :span="colSpan"
          v-for="(router, routerId) in answersheetList.filter(data => !search || data.routeName.toLowerCase().includes(search.toLowerCase()))"
          :key="routerId">
          <el-card :body-style="{ padding: '0px' }" style="margin-right: 40px;margin-bottom: 30px;">
            <image-preview :src="router.imageUrl" class="image" />
            <!-- <img :src="router.imageUrl" class="image"> -->
            <div style="padding: 14px;">
              <div class="bottom clearfix">
                <span>{{ router.routeName }}</span>
                <span style=" font-size: 13px;    color: #999;    float: right;"> 用户：{{ router.userId }}号</span>
              </div>
              <div class="bottom clearfix">
                <span style=" font-size: 13px;    color: #999;">总分：{{ router.core }}</span>
                <span style=" font-size: 13px;    color: #999;    float: right;"> 得分：{{ router.sumGrade }}</span>
              </div>
              <div class="bottom clearfix">
                <time class="time">{{ parseTime(router.createDate, '{y}-{m}-{d}') }}</time>
                <el-button type="text" class="button"
                  @click="readDetails(router.userId)">详情</el-button>
              </div>
            </div>
          </el-card>
        </el-col>

      </el-row>

    </el-card>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

  </div>
</template>

<script>
import {downloadAnswerToExcel, listRouter} from "@/api/questionnaire/router";
import { sumEveryoneAnswerRouter, countRouter, avgEveryoneAnswerRouter, avgEveryAnswerRouter, avgAnswerRouter, selectEveryoneAnswerRouter } from "@/api/questionnaire/questAnswerSheetVo";
import { mount } from "sortablejs";
import Cookies from 'js-cookie'

export default {
  name: "ReadAnswer",
  dicts: ['sys_normal_disable'],
  data() {
    return {
      // 遮罩层
      loading: true,
      //下载汇总表时遮罩层
      downLoading:false,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 路线问卷表格数据
      answersheetList: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 9,
        routeId: null,
        status: null,
      },
      paramRouteId: null,
      paramRouteName: null,
      paramUserId: null,
      // 表单参数
      form: {},
      cardsPerRow: 3, // 每行显示的卡片数量
      search: '',
      routeName: '',
    };
  },
  created() {
      this.paramRouteId =  Cookies.get('routeId');
      this.paramRouteName= Cookies.get('routeName')
      this.getList();
  },
  computed: {
    colSpan() {
      // 计算每个卡片占据的栅格数
      return 24 / this.cardsPerRow - 1;
    }
  },
  methods: {
    /** 查询路线问卷列表 */
    getList() {
      this.loading = true;
      this.queryParams.routerId = this.paramRouteId;
      sumEveryoneAnswerRouter(this.queryParams).then(response => {
        this.answersheetList = response.rows;
        this.routeName = response.rows[0].routeName;
        this.total = response.total;
        this.loading = false;
      });
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


    /** 导出按钮操作 */
    handleExport() {
      this.download('questionnaire/router/export', {
        ...this.queryParams
      }, `router_${new Date().getTime()}.xlsx`)
    },
    setCardsPerRow(num) {
      this.cardsPerRow = num;
    },
    // 点击路由实现跳转功能
    readDetails(userId) {
      Cookies.set('routeUserId', userId)
      this.$router.push({
        name: 'answerSheetDetails',
      })
    },
    sumGradeDesc() {
      this.answersheetList.sort((a, b) => {
        return b.sumGrade - a.sumGrade; // 降序排序
      });
    },
    sumGradeAsc() {
      this.answersheetList.sort((a, b) => {
        return a.sumGrade - b.sumGrade; // 升序排序
      });
    },
    downloadExcel(){
      this.downLoading = true;
      downloadAnswerToExcel(this.queryParams.routerId) .then(response => {
        // 使用 Blob 创建 URL
        let blob = new Blob([response], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
        let url = window.URL.createObjectURL(blob);

        // 创建下载链接并触发点击事件
        let link = document.createElement('a');
        link.href = url;
        link.setAttribute('download', this.routeName+'_路线用户问卷汇总表.xlsx'); // 设置下载文件名
        document.body.appendChild(link);
        link.click();
        link.remove(); // 下载后移除链接

        // 释放 Blob 对象
        window.URL.revokeObjectURL(url);
        this.downLoading = false;
        this.$modal.msgSuccess("下载成功！");
      })
        .catch(error => {
          console.error("下载失败", error);
        });
    },
  }
};
</script>



<style>
.time {
  font-size: 13px;
  color: #999;
  float: left;
}

.bottom {
  margin-top: 13px;
  line-height: 12px;
}

.button {
  padding: 0;
  float: right;

}

.image {
  width: 100%;

  display: block;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}

.clearfix:after {
  clear: both
}
</style>
