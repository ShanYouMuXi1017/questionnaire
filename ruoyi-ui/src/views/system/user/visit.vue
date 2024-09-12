<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="业务员">
        <!--        <el-autocomplete-->
        <!--          class="inline-input"-->
        <!--          type="number"-->
        <!--          v-model="queryParams.visitorId"-->
        <!--          :fetch-suggestions="visitorQuerySearch"-->
        <!--          placeholder="请输入要查询的业务员ID"-->
        <!--          suffix-icon="el-icon-user-solid"-->
        <!--          @select="handleUserIdsInfoVisitorQuerySelect"-->
        <!--        ></el-autocomplete>-->
        <!--        <UserIDInput placeholder=""-->
        <UserIDInput v-model="queryParams.visitorId" placeholder="输入ID/名称/电话以检索业务员" ref="searchVisitorIdInput"/>
      </el-form-item>

      <el-form-item label="客户">
        <!--        <el-autocomplete-->
        <!--          class="inline-input"-->
        <!--          v-model="queryParams.customerId"-->
        <!--          type="number"-->
        <!--          :fetch-suggestions="visitorQuerySearch"-->
        <!--          placeholder="请输入要查询的客户ID"-->
        <!--          suffix-icon="el-icon-user-solid"-->
        <!--          @select="handleUserIdsInfoCustomerQuerySelect"-->
        <!--        ></el-autocomplete>-->
        <UserIDInput v-model="queryParams.customerId" placeholder="输入ID/名称/电话以检索客户" ref="searchCustomerIdInput"/>
      </el-form-item>

      <el-form-item label="状态">
        <el-select v-model="queryParams.visitStatus" placeholder="委托的状态">
          <el-option
            v-for="item in visitStatusList"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option>
        </el-select>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">筛选</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-date"
          size="mini"
          v-hasPermi="['system:visit:add']"
          @click="handleCycle"
        > 添加周期拜访
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:visit:add']"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:visit:edit']"
        >修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:visit:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:visit:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="visitList" :row-class-name="tableRowType"
              @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="编号" :width="flexColumnWidth('编号','id')" align="center" prop="id"/>
      <!--      <el-table-column label="创建人" align="center" prop="creatorId"/>-->
      <el-table-column label="创建人" :width="flexColumnWidth('创建人','creatorNickName')" align="center"
                       prop="creatorNickName"
      />
      <el-table-column label="业务员" align="center">
        <el-table-column label="ID" :width="flexColumnWidth('编号','visitorId')" align="center" prop="visitorId"/>
        <el-table-column label="昵称" align="center" prop="visitorNickName"/>
      </el-table-column>
      <el-table-column label="客户" align="center">
        <el-table-column label="ID" :width="flexColumnWidth('编号','customerId')" align="center" prop="customerId"/>
        <el-table-column label="昵称" align="center" prop="customerNickName"/>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="visitStatusText">
        <template slot-scope="scope">
          <el-tag :type="scope.row.rowType" effect="dark">{{ scope.row.visitStatusText }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="签到距离" :width="flexColumnWidth('签到距离','visitRange')" align="center" prop="visitRange"/>
      <el-table-column label="截止时间" align="center" prop="endTime"
                       width="180"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.endTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="完成时间" :width="flexColumnWidth('完成时间','finishTime')" align="center" prop="finishTime"
                       width="180"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.finishTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="visitRemark">
        <template slot-scope="scope">
          <span class="table-visit-remark-text">{{ scope.row.visitRemark }}</span>
        </template>
      </el-table-column>
      <!--      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">-->
      <!--        <template slot-scope="scope">-->
      <!--          <el-button-->
      <!--            size="mini"-->
      <!--            type="text"-->
      <!--            icon="el-icon-edit"-->
      <!--            @click="handleUpdate(scope.row)"-->
      <!--            v-hasPermi="['system:visit:edit']"-->
      <!--          >修改-->
      <!--          </el-button>-->
      <!--          <el-button-->
      <!--            size="mini"-->
      <!--            type="text"-->
      <!--            icon="el-icon-delete"-->
      <!--            @click="handleDelete(scope.row)"-->
      <!--            v-hasPermi="['system:visit:remove']"-->
      <!--          >删除-->
      <!--          </el-button>-->
      <!--        </template>-->
      <!--      </el-table-column>-->
      <!--      <el-table-column label="创建时间" align="center" prop="createTime" width="180">-->
      <!--        <template slot-scope="scope">-->
      <!--          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>-->
      <!--        </template>-->
      <!--      </el-table-column>-->
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改委托拜访对话框 -->
    <el-dialog :title="title" :visible.sync="open" :beforeClose="boxClose" :closeOnClickModal="false" width="500px"
               append-to-body
    >
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="业务员" v-show="!formSelectStatusShow" prop="visitorId">
          <!--          <el-input v-model="form.visitorId" suffix-icon="el-icon-user-solid" placeholder="请输入受委托的业务员ID"/>-->
          <!--          <el-autocomplete-->
          <!--            class="inline-input"-->
          <!--            v-model="form.visitorId"-->
          <!--            clearable-->
          <!--            type="number"-->
          <!--            :fetch-suggestions="visitorQuerySearch"-->
          <!--            placeholder="请输入受委托的业务员ID"-->
          <!--            suffix-icon="el-icon-user-solid"-->
          <!--            @select="handleUserIdsInfoVisitorSelect"-->
          <!--          ></el-autocomplete>-->
          <UserIDInput v-model="form.visitorId" placeholder="输入ID/名称/电话以检索业务员" ref="visitorIdInput"/>

        </el-form-item>
        <el-form-item label="拜访客户" v-show="!formSelectStatusShow" prop="customerId">
          <!--          <el-input v-model="form.customerId" suffix-icon="el-icon-s-custom" placeholder="请输入被拜访的客户ID"/>-->
          <!--          <el-autocomplete-->
          <!--            class="inline-input"-->
          <!--            v-model="form.customerId"-->
          <!--            :fetch-suggestions="visitorQuerySearch"-->
          <!--            type="number"-->
          <!--            clearable-->
          <!--            placeholder="请输入被拜访的客户ID"-->
          <!--            suffix-icon="el-icon-s-custom"-->
          <!--            @select="handleUserIdsInfoCustomerSelect"-->
          <!--          ></el-autocomplete>-->
          <UserIDInput v-model="form.customerId" placeholder="输入ID/名称/电话以检索客户" ref="customerIdInput"
          />
        </el-form-item>


        <el-form-item label="截止时间" prop="endTime">
          <el-date-picker
            style="width: 360px"
            clearable
            v-model="form.endTime"
            type="datetime"
            :validate-event="false"
            value-format="yyyy-MM-dd HH:mm:ss"
            placeholder="委托的签到截止时间"
            align="right"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="签到距离" prop="visitRange">
          <el-autocomplete
            class="inline-input"
            v-model="form.visitRange"
            :fetch-suggestions="visitRangeSuggestion"
            type="number"
            clearable
            style="width: 360px"
            :validate-event="false"
            placeholder="仅输入数字, 单位: 米"
            suffix-icon="el-icon-place"
            @select="handleVisitRangeSelect"
          />
        </el-form-item>
        <el-form-item label="状态" prop="visitStatus" v-if="formSelectStatusShow">
          <el-select v-model="form.visitStatus"
                     placeholder="委托的状态"
                     style="width: 360px"
          >
            <el-option
              v-for="item in visitStatusList"
              :key="item.value"
              :label="item.label"
              :value="item.value"
              :disabled="item.disabled"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="备注信息" prop="visitRemark">
          <el-input v-model="form.visitRemark" type="textarea" maxlength="150"
                    style="width: 360px"
                    show-word-limit placeholder="请输入本次委托的备注内容, 注意不要输入特殊字符, 如括号、引号、斜杠等"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 添加周期拜访对话框 -->
    <el-dialog :title="cycleTaskTitle" :visible.sync="cycleOpen" :beforeClose="cycleBoxClose" :closeOnClickModal="false"
               width="600px"
               append-to-body
    >
      <el-form ref="cycleForm" :model="cycleForm" :rules="rules" label-width="80px">
        <el-form-item label="业务员" prop="visitorId">
          <UserIDInput v-model="cycleForm.visitorId"
                       placeholder="输入ID/名称/电话以检索业务员" ref="cycleVisitorIdInput"
          />
        </el-form-item>
        <el-form-item label="拜访客户" prop="customerId">
          <UserIDInput v-model="cycleForm.customerId"
                       placeholder="输入ID/名称/电话以检索客户" ref="cycleCustomerIdInput"
          />
        </el-form-item>
        <!--        <el-form-item label="执行时间" prop="cronExpression">-->
        <!--          <el-input v-model="cycleForm.cronExpression" :validate-event="false" placeholder="自定义任务发送时间(建议提前1天发送)">-->
        <!--            <template slot="append">-->
        <!--              <el-button type="primary" @click="handleShowCron">-->
        <!--                生成表达式-->
        <!--                <i class="el-icon-time el-icon&#45;&#45;right"></i>-->
        <!--              </el-button>-->
        <!--            </template>-->
        <!--          </el-input>-->
        <!--        </el-form-item>-->
        <el-form-item label="拜访周期" prop="cycleDays">
          <el-input
            class="inline-input"
            v-model="cycleForm.cycleDays"
            type="number"
            clearable
            :validate-event="false"
            placeholder="完成当前拜访后下一次的拜访时间(仅输入数字, 表天数)"
            suffix-icon="el-icon-time"
          />
        </el-form-item>
        <el-form-item label="截止天数" prop="endTime">
          <el-input
            class="inline-input"
            v-model="cycleForm.endTime"
            type="number"
            clearable
            :validate-event="false"
            placeholder="即任务发放后几天截止(输入天数)"
            suffix-icon="el-icon-time"
          />
        </el-form-item>
        <!--          <el-date-picker-->
        <!--            clearable-->
        <!--            v-model="cycleForm.endTime"-->
        <!--            type="datetime"-->
        <!--            :validate-event="false"-->
        <!--            value-format="yyyy-MM-dd HH:mm:ss"-->
        <!--            placeholder="委托的签到截止时间"-->
        <!--            align="right"-->
        <!--          >-->
        <!--          </el-date-picker>-->
        <!--        </el-form-item>-->
        <el-form-item label="签到距离" prop="visitRange">
          <el-autocomplete
            style="width: 480px"
            class="inline-input"
            v-model="cycleForm.visitRange"
            :fetch-suggestions="visitRangeSuggestion"
            type="number"
            clearable
            :validate-event="false"
            placeholder="仅输入数字, 单位: 米"
            suffix-icon="el-icon-place"
            @select="handleVisitRangeSelect"
          />
        </el-form-item>
        <el-form-item label="备注信息" prop="visitRemark">
          <el-input v-model="cycleForm.visitRemark" type="textarea" maxlength="145"
                    show-word-limit placeholder="请输入周期拜访的备注内容, 注意不要输入特殊字符, 如括号、引号、斜杠等"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="handleCycleSubmit">确 定</el-button>
        <el-button @click="handleCycleCancel">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog title="Cron表达式生成器" :visible.sync="openCron" append-to-body destroy-on-close class="scrollbar">
      <crontab @hide="openCron=false" @fill="crontabFill" :expression="expression"></crontab>
    </el-dialog>

  </div>
</template>

<script>
import { listVisit, getVisit, delVisit, addVisit, updateVisit } from '@/api/system/visit'
import UserIDInput from '@/components/UserIDInputBox'
import localForage from 'localforage'
import Crontab from '@/components/Crontab'
import { addJob } from '@/api/monitor/job'

export default {
  name: 'Visit',
  components: { UserIDInput, Crontab },

  data() {
    return {
      userIdTest: null,
      // dateRange: [],
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      openCron: false,
      // 传入的表达式
      expression: '',

      //用户管理页选中的user的ids
      userIds: null,
      //用户管理页选中的user的信息
      userIdsInfo: [],
      //缓存的用户信息
      usersInfo: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 委托拜访表格数据
      visitList: [],
      // 弹出层标题
      title: '',
      cycleTaskTitle: '周期拜访设置(需要定时任务权限)',
      //弹出表单是否显示状态选项
      formSelectStatusShow: true,
      //禁用修改表单
      disableChangeForm: false,
      // 是否显示弹出层
      open: false,
      // 是否显示弹出层
      cycleOpen: false,
      //签到距离提示
      visitRangeSuggestionNumber: [
        { value: 100 },
        { value: 200 },
        { value: 300 },
        { value: 500 }
      ],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        visitStatus: null,
        visitorId: null,
        customerId: null,
        visitRemark: null
      },
      // 表单参数
      form: {},
      // 表单参数
      cycleForm: {},
      // 默认的四种状态
      visitStatusList: [
        {
          label: '正在拜访',
          value: 0
        }, {
          label: '拜访完成',
          value: 1
        }, {
          label: '超时完成',
          value: 2,
          disabled: true
        }, {
          label: '拜访超时',
          value: 3,
          disabled: true
        }
      ],
      // 表单校验
      rules: {
        visitorId: [
          { required: true, message: '请点击选择输入业务员', trigger: 'blur' }
        ],
        customerId: [
          { required: true, message: '请点击选择输入客户', trigger: 'blur' }
        ],
        endTime: [
          { required: true, message: '委托截止时间不能为空', trigger: 'blur' }
        ],
        visitRange: [
          { required: true, message: '签到距离不能为空!', trigger: 'blur' }
        ],
        cronExpression: [
          { required: true, message: '任务发送时间不能为空!', trigger: 'blur' }
        ],
        cycleDays: [
          { required: true, message: '拜访周期不能为空!', trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    //引入缓存数据
    // this.usersInfo = JSON.parse(localStorage.getItem('USERS_INFO'))
    localForage.getItem('usersInfo').then(res => {
      this.usersInfo = res
    }).then(() => {
      //判断选中委托的业务员ids参数是否存在
      if (this.$route.query.ids) {
        this.userIds = this.$route.query.ids
        this.userIds.forEach(id => {
          let temp = this.usersInfo.find((user) => {
            return user.userId == id
          })
          if (temp === undefined || temp == null) {
            this.userIdsInfo.push({
              userId: id,
              value: 'ID = ' + id
            })
          } else {
            this.userIdsInfo.push({
              userId: id,
              value: 'ID = ' + id + ' & ' + temp.nickName
            })
          }
        })
      }
    })
    this.getList()
  },
  methods: {
    /** 查询委托拜访列表 */
    getList() {
      this.loading = true
      listVisit(this.queryParams).then(response => {
        this.visitList = response.rows
        this.total = response.total
        this.loading = false
        this.loadInfo()
      })
    },
    /**
     *  初始化信息
     */
    loadInfo() {
      //添加用户昵称
      for (let i = 0; i < this.visitList.length; i++) {
        if (this.visitList[i] == null) {
          continue
        }
        //创建者
        for (let j = 0; j < this.usersInfo.length; j++) {
          if (this.usersInfo[j].userId === this.visitList[i].creatorId) {
            // this.visitList[i].nickName = this.usersInfo[j].nickName
            // this.visitList[i].avatar = this.usersInfo[j].avatar
            this.visitList[i].creatorNickName = this.usersInfo[j].nickName
            break
          }
        }
        //业务员
        for (let j = 0; j < this.usersInfo.length; j++) {
          if (this.usersInfo[j].userId === this.visitList[i].visitorId) {
            // this.visitList[i].nickName = this.usersInfo[j].nickName
            // this.visitList[i].avatar = this.usersInfo[j].avatar
            this.visitList[i].visitorNickName = this.usersInfo[j].nickName
            break
          }
        }
        //客户
        for (let j = 0; j < this.usersInfo.length; j++) {
          if (this.usersInfo[j].userId === this.visitList[i].customerId) {
            // this.visitList[i].nickName = this.usersInfo[j].nickName
            // this.visitList[i].avatar = this.usersInfo[j].avatar
            this.visitList[i].customerNickName = this.usersInfo[j].nickName
            break
          }
        }
      }

      //  解析列表信息
      this.visitList.forEach((item) => {
        switch (item.visitStatus) {
          case 0:
            item.visitStatusText = this.visitStatusList[0].label
            item.rowType = 'info'
            break
          case 1:
            item.visitStatusText = this.visitStatusList[1].label
            item.rowType = 'success'
            break
          case 2:
            item.visitStatusText = this.visitStatusList[2].label
            item.rowType = 'warning'
            break
          case 3:
            item.visitStatusText = this.visitStatusList[3].label
            item.rowType = 'danger'
            break
          default:
            item.visitStatusText = '未知状态'
            item.rowType = 'danger'
        }
      })
    },

    /**
     * 样式化表格
     */
    tableRowType({ row, rowIndex }) {
      return this.visitList[rowIndex].rowType
    },

    /**
     * 根据选中的业务员进行提示输入
     */
    visitorQuerySearch(queryString, cb) {
      cb(this.userIdsInfo)
    },
    /**
     * 业务员输入框提示选中后
     */
    handleUserIdsInfoVisitorSelect(item) {
      this.form.visitorId = item.userId
    },
    /**
     * 客户输入框提示选中后
     */
    handleUserIdsInfoCustomerSelect(item) {
      this.form.customerId = item.userId
    },
    /**
     * 业务员搜索框提示选中后
     */
    handleUserIdsInfoVisitorQuerySelect(item) {
      this.queryParams.visitorId = item.userId
    },
    /**
     * 客户搜索框提示选中后
     */
    handleUserIdsInfoCustomerQuerySelect(item) {
      this.queryParams.customerId = item.userId
    },
    /**
     * 距离输入框提示
     */
    visitRangeSuggestion(queryString, cb) {
      cb(this.visitRangeSuggestionNumber)
    },
    /**
     * 处理距离提示选择
     */
    handleVisitRangeSelect(item) {
      this.form.visitRange = item.value
    },
    // 取消按钮
    cancel() {
      this.$refs.visitorIdInput.reset()
      this.$refs.customerIdInput.reset()
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        creatorId: null,
        visitorId: null,
        customerId: null,
        visitStatus: null,
        createTime: null,
        finishTime: null,
        endTime: null,
        isDelete: null,
        visitRemark: null
      }
      this.resetForm('form')
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.$refs.searchVisitorIdInput.reset()
      this.$refs.searchCustomerIdInput.reset()
      this.queryParams.visitorId = null
      this.queryParams.customerId = null
      this.queryParams.visitStatus = null
      this.queryParams.visitRemark = null
      this.resetForm('queryForm')
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.disableChangeForm = false
      this.reset()
      this.form.visitorId = ''
      this.open = true
      this.title = '添加委托拜访'
      this.formSelectStatusShow = false
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getVisit(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = '修改委托拜访'
        let status = this.visitList.find(item => {
          return item.id == id
        }).visitStatus
        if (status == 1 || status == 2) {
          this.disableChangeForm = true
          this.title = '完成的委托无法修改'
        } else {
          this.disableChangeForm = false
        }
        this.formSelectStatusShow = true
      })
    },
    /** 提交按钮 */
    submitForm() {
      if (this.form.visitorId != null && this.form.visitorId == this.form.customerId) {
        this.$message({
          message: '业务员ID与客户ID相同!',
          type: 'warning'
        })
        return
      }

      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateVisit(this.form).then(response => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addVisit(this.form).then(response => {
              this.$modal.msgSuccess('新增成功')
              this.$refs.visitorIdInput.reset()
              this.$refs.customerIdInput.reset()
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$modal.confirm('是否确认删除委托拜访编号为"' + ids + '"的数据项？').then(function() {
        return delVisit(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('删除成功')
      }).catch(() => {
      })
    },

    /** cron表达式按钮操作 */
    handleShowCron() {
      this.expression = this.cycleForm.cronExpression
      this.openCron = true
    },

    /** 确定后回传值 */
    crontabFill(value) {
      this.cycleForm.cronExpression = value
    },

    /** 导出按钮操作 */
    handleExport() {
      this.download('system/visit/export', {
        ...this.queryParams
      }, `visit_${new Date().getTime()}.xlsx`)
    },

    /**
     * 周期拜访按钮
     */
    handleCycle() {
      this.resetForm('cycleForm')
      this.cycleOpen = true
      this.cycleForm = {}
      // this.cycleForm.endTime = 3
    },

    /**
     * 周期拜访提交
     */
    handleCycleSubmit() {
      if (this.cycleForm.visitorId != null && this.cycleForm.visitorId == this.cycleForm.customerId) {
        this.$message({
          message: '业务员与客户相同!',
          type: 'warning'
        })
        return
      }
      if (this.cycleForm.endTime <= 0) {
        this.$message({
          message: '截止天数要大于 0!',
          type: 'warning'
        })
        return
      }

      this.$refs['cycleForm'].validate(valid => {
        if (valid) {
          let cycleString = 'com.ruoyi.system.service.impl.SysVisitServiceImpl.insertSysVisitByCycle('
          cycleString += this.cycleForm.cycleDays + ','
          cycleString += this.cycleForm.visitorId + 'L,'
          cycleString += this.cycleForm.customerId + 'L,'
          cycleString += this.cycleForm.endTime * 86400000 + 'L,'
          cycleString += this.cycleForm.visitRange + ',\''
          cycleString += this.cycleForm.visitRemark !== null && this.cycleForm.visitRemark !== '' ?
            this.cycleForm.visitRemark + ' *周期任务' : ' *周期任务'
          cycleString += '\')'

          let cycleTask = {
            concurrent: 0,
            // cronExpression: this.cycleForm.cronExpression,
            cronExpression: '0 0 0 * * ?', // 每天执行一次, 0点
            // cronExpression: '30 * * * * ?', // 每30s执行一次
            invokeTarget: cycleString,
            jobGroup: 'SYSTEM',
            jobName: this.cycleForm.visitorId + '拜访' + this.cycleForm.customerId + '(周期' + this.cycleForm.cycleDays + '天)',
            misfirePolicy: '3',
            status: '0'
          }
          // console.log(cycleTask)
          addJob(cycleTask).then(res => {
            this.$modal.msgSuccess('周期拜访设置成功, 您可以在定时任务中查看!')
            this.$refs.cycleVisitorIdInput.reset()
            this.$refs.cycleCustomerIdInput.reset()
            this.cycleOpen = false
          })
        }
      })
    },

    /**
     * 周期拜访取消
     */
    handleCycleCancel() {
      this.cycleForm = {}
      this.$refs.cycleVisitorIdInput.reset()
      this.$refs.cycleCustomerIdInput.reset()
      this.resetForm('cycleForm')
      this.cycleOpen = false
    },

    /**
     * 生成周期拜访的定时任务调用方法
     */
    getCycleTaskExpression() {
      const endTimestamp = new Date(this.form.endTime) - new Date()
      if (this.form.endTime != null && endTimestamp <= 0) {
        this.$message({
          message: '截止时间需要大于此刻的时间!',
          type: 'warning'
        })
        return
      }

      this.$refs['cycleForm'].validate(valid => {
        if (valid) {
          //数据验证完成, 生成定时任务方法字符串
          let cycleTask = 'com.ruoyi.system.service.impl.SysVisitServiceImpl.insertSysVisitByCycle('
          cycleTask += this.form.visitorId + 'L,'
          cycleTask += this.form.customerId + 'L,'
          cycleTask += endTimestamp + 'L,'
          cycleTask += this.form.visitRange + ',\''
          cycleTask += this.form.visitRemark !== null && this.form.visitRemark !== '' ?
            this.form.visitRemark + ' *周期任务' : ' *周期任务'
          cycleTask += '\')'

          this.$confirm(cycleTask, '生成定时任务调用目标字符串成功', {
            confirmButtonText: '复制',
            cancelButtonText: '取消',
            type: 'success',
            showClose: false,
            closeOnClickModal: false
          }).then(() => {
            let oInput
            if (document.getElementById('copyInput')) {
              oInput = document.getElementById('copyInput')
            } else {
              oInput = document.createElement('input')
            }
            oInput.value = cycleTask
            oInput.id = 'copyInput'
            oInput.className = 'copy-input'
            document.body.appendChild(oInput)
            oInput.select() // 选择对象;
            document.execCommand('Copy') // 执行浏览器复制命令

            this.$message({
              type: 'success',
              message: '复制成功, 请粘贴到定时任务中调用方法中(调用目标字符串)'
            })
            this.open = false
          }).catch(() => {
            this.$message({
              type: 'info',
              message: '未复制'
            })
          })
        }
      })

    },

    cycleBoxClose(done) {
      this.$refs.cycleVisitorIdInput.reset()
      this.$refs.cycleCustomerIdInput.reset()
      done()
    },

    boxClose(done) {
      this.$refs.visitorIdInput.reset()
      this.$refs.customerIdInput.reset()
      done()
    },

    /**
     * 遍历列的所有内容，获取最宽一列的宽度
     * @param arr
     */
    getMaxLength(arr) {
      return arr.reduce((acc, item) => {
        if (item) {
          const calcLen = this.getTextWidth(item)
          if (acc < calcLen) {
            acc = calcLen
          }
        }
        return acc
      }, 0)
    },
    /**
     * 使用span标签包裹内容，然后计算span的宽度 width： px
     * @param valArr
     */
    getTextWidth(str) {
      let width = 0
      const html = document.createElement('span')
      html.innerText = str
      html.className = 'getTextWidth'
      document.querySelector('body').appendChild(html)
      width = document.querySelector('.getTextWidth').offsetWidth
      document.querySelector('.getTextWidth').remove()
      return width
    },
    /**
     * el-table-column 自适应列宽
     * @param prop_label: 表名
     * @param table_data: 表格数据
     */
    flexColumnWidth(label, prop) {
      // 1.获取该列的所有数据
      const arr = this.visitList.map(x => x[prop])
      arr.push(label) // 把每列的表头也加进去算
      // console.log(arr)
      // 2.计算每列内容最大的宽度 + 表格的内间距（依据实际情况而定）
      return (this.getMaxLength(arr) + 25) + 'px'
    }
  }
}
</script>

<style>

.el-table .info {
  background: #e8f4ff;
}

.el-table .danger {
  background: #ffeded;
}

.el-table .warning {
  background: #fff8e6;
}

.el-table .success {
  background: #e7faf0;
}

input[type=number] {
  -moz-appearance: textfield;
}

input[type=number]::-webkit-inner-spin-button,
input[type=number]::-webkit-outer-spin-button {
  -webkit-appearance: none;
  margin: 0;
}


.el-table /deep/ th {
  padding: 0;
  white-space: nowrap;
  min-width: fit-content;
}

.el-table /deep/ td {
  padding: 1px;
  white-space: nowrap;
  width: fit-content;
}

.table-visit-remark-text {
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
}
</style>
