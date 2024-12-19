<template>
  <div class="app-container">



    <el-card shadow="never" class="box-card">
      <el-row>
        <el-col :span="16">
          <el-button icon="el-icon-top" size="mini" @click="setCardsPerRow(2)">每行2个</el-button>
          <el-button icon="el-icon-top" size="mini" @click="setCardsPerRow(3)">每行3个</el-button>
          <el-button icon="el-icon-top" size="mini" @click="setCardsPerRow(4)">每行4个</el-button>
          <el-button type="primary" icon="el-icon-bottom" size="mini" @click="sumGradeDesc()">降序排序</el-button>
          <el-button icon="el-icon-top" size="mini" @click="sumGradeAsc()">升序排序</el-button>
        </el-col>
        <el-col :span="8">
          <span>
            <el-input v-model="search" size="mini" placeholder="输入路线名称搜索" />
          </span>
        </el-col>

      </el-row>


    </el-card>

    <el-card shadow="never" v-loading="loading" style="margin-top: 20px;">
      <el-row>
        <el-col :span="colSpan"
          v-for="(router, routerId) in listcountRouter.filter(data => !search || data.routeName.toLowerCase().includes(search.toLowerCase()))"
          :key="routerId">
          <el-card :body-style="{ padding: '0px' }" style="margin-right: 40px;margin-bottom: 30px;">
            <image-preview :src="router.imageUrl" class="image" />
            <!-- <img :src="router.imageUrl" class="image"> -->
            <div style="padding: 14px;">
              <div class="bottom clearfix">
                <span>{{ router.routeName }}</span>
              </div>
              <div class="bottom clearfix">
                <span style=" font-size: 13px;    color: #999;"> 共计{{ router.total }}份</span>
                <span style=" font-size: 13px;    color: #999;    float: right;"> 总分：{{ router.core }}</span>
              </div>
              <div class="bottom clearfix">
                <time class="time">{{ parseTime(router.createDate, '{y}-{m}-{d}') }}</time>
                <el-button type="text" class="button"
                  @click="readDetails(router.routerId, router.routeName)">详情</el-button>
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
import { listRouter } from "@/api/questionnaire/router";
import { sumEveryoneAnswerRouter, countRouter, avgEveryoneAnswerRouter, avgEveryAnswerRouter, avgAnswerRouter, selectEveryoneAnswerRouter } from "@/api/questionnaire/questAnswerSheetVo";
import Cookies from 'js-cookie'

export default {
  name: "read",
  dicts: ['sys_normal_disable'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 路线问卷表格数据
      routerList: [],
      listcountRouter: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 9,
        routeName: null,
        status: null,
      },
      // 表单参数
      form: {},
      cardsPerRow: 3, // 每行显示的卡片数量
      search: '',

    };
  },
  
  created() {
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
      countRouter(this.queryParams).then(response => {
        // console.log(response);
        this.listcountRouter = response.rows;
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
    readDetails(routeId, routeName) {
      Cookies.set('routeId', routeId)
      Cookies.set('routeName', routeName)
      this.$router.push({
        name: 'readAnswer',
      })
    },
    sumGradeDesc() {
      this.listcountRouter.sort((a, b) => {
        return b.total - a.total; // 降序排序
      });
    },
    sumGradeAsc() {
      this.listcountRouter.sort((a, b) => {
        return a.total - b.total; // 升序排序
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
