<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="路线名称" prop="routeName">
        <el-input
          v-model="queryParams.routeName"
          placeholder="请输入路线名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable>
          <el-option
            v-for="dict in dict.type.sys_normal_disable"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="显示" >
        <div  >
    <el-button @click="setCardsPerRow(2)">每行2个</el-button>
    <el-button @click="setCardsPerRow(3)">每行3个</el-button>
    <el-button @click="setCardsPerRow(4)">每行4个</el-button>
  </div>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>


    <el-card shadow="never" v-loading="loading" style="margin-top: 20px;">
      <el-row >
    <el-col :span="colSpan" v-for="(router,routerId) in routerList"    :key="routerId"  >
      <el-card :body-style="{ padding: '0px'}" style="margin-right: 40px;margin-bottom: 30px;">
        <image-preview :src="router.imageUrl" class="image"/>
        <!-- <img :src="router.imageUrl" class="image"> -->
        <div style="padding: 14px;">
          <div class="bottom clearfix">
            <span>{{ router.routeName }}</span>
            <span   style=" font-size: 13px;    color: #999;    float: right;"> 总分：{{ router.core }}</span> 
          </div>
          <div class="bottom clearfix">
            <time class="time">{{ router.beginName }}--</time>
            <time class="time">{{ router.endName }}</time>
          </div>
          <div class="bottom clearfix">
            <time class="time">{{parseTime(router.createDate, '{y}-{m}-{d}') }}</time>
            <el-button type="text" class="button" @click="readDetails(router.routerId,router.routeName)">详情</el-button>
          </div>
        </div>
      </el-card>
    </el-col>

  </el-row>

    </el-card>
   
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
 
  </div>
</template>

<script>
import { listRouter } from "@/api/questionnaire/router";

export default {
  name: "readRouter",
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

    };
  },
  created() {
    this.getList();
  },
  computed: {
    colSpan() {
      // 计算每个卡片占据的栅格数
      return 24 / this.cardsPerRow-1;
    }
  },
  methods: {
    /** 查询路线问卷列表 */
    getList() {
      this.loading = true;
      listRouter(this.queryParams).then(response => {
        this.routerList = response.rows;
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
    readDetails(routeId,routeName) {
      this.$router.push({
      name: 'questionnaireDetails',
      params: {
        paramRouteId: routeId,
        paramRouteName: routeName,
      }
    })
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
