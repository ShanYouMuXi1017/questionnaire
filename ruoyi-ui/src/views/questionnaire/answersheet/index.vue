<template>
  <div class="app-container">
    <el-card shadow="never" class="box-card">
      <el-row>
        <el-col :span="16">
          <el-button type="primary" icon="el-icon-bottom" size="mini" @click="sumGradeDesc()">得分降序</el-button>
          <el-button icon="el-icon-top" size="mini" @click="sumGradeAsc()">得分升序</el-button>
        </el-col>
        <el-col :span="8">
          <span>
            <el-input v-model="search" size="mini" placeholder="输入路线名称搜索" />
          </span>
        </el-col>

      </el-row>


    </el-card>


    <el-table v-loading="loading" :data="filteredListsumEveryoneAnswer">
      <el-table-column label="答卷序号" align="center" width="80" type="index" :index="indexMethod"></el-table-column>
      <el-table-column label="问卷编号" align="center" prop="routerId" />
      <el-table-column label="路线名称" align="center" prop="routeName" />
      <el-table-column label="路线图片" align="center" prop="imageUrl" width="100">
        <template slot-scope="scope">
          <image-preview :src="scope.row.imageUrl" :width="50" :height="50" />
        </template>
      </el-table-column>
      <el-table-column label="用户编号" align="center" prop="userId" />
      <el-table-column label="总分" align="center" prop="core" />
      <el-table-column label="得分" align="center" prop="sumGrade" />
      <el-table-column label="创建时间" align="center" prop="createDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <!-- <template slot="header" slot-scope="scope">

        </template> -->
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-view" @click="readDetails(scope.row.routerId, scope.row.routeName, scope.row.userId)"
            v-hasPermi="['questionnaire:router:remove']">查看</el-button>
        </template>
      </el-table-column>
    </el-table>


  </div>
</template>

<script>
import { listRouter, getRouter, delRouter, addRouter, updateRouter } from "@/api/questionnaire/router";
import { sumEveryoneAnswerRouter, countRouter, avgEveryoneAnswerRouter, avgEveryAnswerRouter, avgAnswerRouter, selectEveryoneAnswerRouter } from "@/api/questionnaire/questAnswerSheetVo";


export default {
  name: "index",
  dicts: ['sys_normal_disable'],
  data() {
    return {
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
      // 路线问卷表格数据
      routerList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      listsumEveryoneAnswerRouter: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 100000,
        routeName: null,
        beginName: null,
        endName: null,
        status: null,
        createDate: null,
      },
      // 表单参数
      form: {},
      search: null,


    };
  },
  created() {
    this.getList();
  },
  computed: {
    filteredListsumEveryoneAnswer() {
      // 如果没有搜索词，返回原列表
      if (!this.search || this.search.trim() === '') {
        return this.listsumEveryoneAnswerRouter;
      }
      // 使用 filter 方法过滤列表
      return this.listsumEveryoneAnswerRouter.filter(data =>
        data.routeName && data.routeName.toLowerCase().includes(this.search.toLowerCase())
      );
    }
  },
  methods: {
    /** 查询路线问卷列表 */
    getList() {
      this.loading = true;
      // 计算每份每人答卷的总分
      sumEveryoneAnswerRouter().then(response => {
        this.listsumEveryoneAnswerRouter = response.rows;
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
        routerId: null,
        routeName: null,
        beginName: null,
        endName: null,
        status: null,
        imageUrl: null,
        core: null,
        flag: null,
        createDate: null,
        updateDate: null
      };
      this.resetForm("form");
    },
    // 点击路由实现跳转功能
    readDetails(routeId, routeName, userId) {
      this.$router.push({
        name: 'answerSheetDetails',
        params: {
          paramRouteId: routeId,
          paramRouteName: routeName,
          paramUserId: userId,
        }
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('questionnaire/router/export', {
        ...this.queryParams
      }, `router_${new Date().getTime()}.xlsx`)
    },

    indexMethod(index) {
      return index + 1;
    },
    sumGradeDesc() {
      this.listsumEveryoneAnswerRouter.sort((a, b) => {
        return b.sumGrade - a.sumGrade; // 降序排序
      });
    },
    sumGradeAsc() {
      this.listsumEveryoneAnswerRouter.sort((a, b) => {
        return a.sumGrade - b.sumGrade; // 升序排序
      });
    },
  }
}
</script>
