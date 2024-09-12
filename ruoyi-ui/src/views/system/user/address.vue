<template>
  <div class="app-container">
    <el-card shadow="hover" style="margin: 5px 5px 10px 5px">
      <strong>说明: 用户的经纬度是由小程序端授权获取微信给予的定位，可能略有偏移。输入的地址和经纬度并不关联，可在小程序端查询经纬度后输入。</strong>
      <br/>
      <i>共选中{{ userIds.length }}个用户, 查询到共{{ addressList.length }}条地址数据如下: </i>
    </el-card>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:address:add']"
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
          v-hasPermi="['system:address:edit']"
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
          v-hasPermi="['system:address:remove']"
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
          v-hasPermi="['system:address:export']"
        >导出
        </el-button>
      </el-col>
      <!--      <right-toolbar :showSearch.sync="showSearch" @queryTable="getListByIds"></right-toolbar>-->
    </el-row>

    <el-table v-loading="loading" :data="addressList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <!--      <el-table-column label="地址编号" align="center" prop="id"/>-->
      <el-table-column label="用户ID" :width="flexColumnWidth('用户ID','userId')" align="center" prop="userId"/>
      <el-table-column label="用户头像" align="center" prop="avatar">
        <template slot-scope="scope">
          <!--              <el-image  :min-width="32" :height="32"/>-->
          <el-image :src="scope.row.avatar" style="width: 32px; height: 32px;">
            <div slot="error" class="image-slot" style="line-height: 32px">
              <strong class="el-icon-user-solid"></strong>
            </div>
          </el-image>
        </template>
      </el-table-column>
      <el-table-column label="用户昵称" :width="flexColumnWidth('用户昵称','nickName')" align="center" prop="nickName"/>
      <el-table-column label="用户地址" align="center" prop="address"/>
      <el-table-column label="上传经度" :width="flexColumnWidth('上传经度','longitude')" align="center" prop="longitude"/>
      <el-table-column label="上传纬度" :width="flexColumnWidth('上传纬度','latitude')" align="center" prop="latitude"/>
      <el-table-column label="更新时间" align="center" prop="updateTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updateTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:address:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:address:remove']"
          >删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改用户地址对话框 -->
    <el-dialog :title="title" :visible.sync="open" :closeOnClickModal="false" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="用户编号" v-show="formSelectStatusShow" prop="userId">
          <!--          <el-input v-model="form.userId" clearable placeholder="用户编号参照用户管理页列表第一类"/>-->
          <!--          <el-autocomplete-->
          <!--            class="inline-input"-->
          <!--            clearable-->
          <!--            type="number"-->
          <!--            v-model="form.userId"-->
          <!--            :fetch-suggestions="userQuerySearch"-->
          <!--            placeholder="请输入被拜访的客户ID"-->
          <!--            suffix-icon="el-icon-s-custom"-->
          <!--            @select="handleUserSelect"-->
          <!--          ></el-autocomplete>-->
          <UserIDInput v-model="form.userId" :defaultValue="form.userId" ref="userIdInput"
                       placeholder="输入ID/名称/电话以检索客户"
          />
        </el-form-item>
        <el-form-item label="地区地址" prop="address">
          <el-input v-model="form.address" type="textarea" placeholder="填写用户的所在地区和详细地址"
                    maxlength="120"
                    show-word-limit
          />
        </el-form-item>
        <el-form-item label="上传经度" prop="longitude">
          <el-input v-model="form.longitude" placeholder="小程序->我的地址处查询输入"/>
        </el-form-item>
        <el-form-item label="上传纬度" prop="latitude">
          <el-input v-model="form.latitude" placeholder="小程序->我的地址处查询输入"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listAddress, getAddress, delAddress, addAddress, updateAddress, listAddressByIds } from '@/api/system/address'
import localForage from 'localforage'
import UserIDInput from '@/components/UserIDInputBox'

export default {
  name: 'Address',
  components: { UserIDInput },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      //用户管理页选中的user的ids
      userIds: null,
      //缓存的用户信息
      usersInfo: [],
      //缓存的用户ID信息
      userIdsInfo: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      formSelectStatusShow: false,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 用户地址表格数据
      addressList: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userId: null,
        address: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        userId: [
          { required: true, message: '请选择提示输入框输入的客户', trigger: 'blur' }
        ],
        address: [
          { required: true, message: '客户地址不能为空', trigger: 'blur' }
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

    //判断选中委托的业务员ids参数是否存在
    if (this.$route.query.ids) {
      this.userIds = this.$route.query.ids
      this.getListByIds()
    } else {
      this.getList()
    }
  },
  methods: {
    /** 按id数组查询用户地址列表 */
    getListByIds() {
      this.loading = true
      listAddressByIds(this.userIds).then(response => {
        this.addressList = response.rows
        this.total = response.total
        this.loading = false
        this.loadUserInfo()
      })
    },
    /**
     * 列表更新时为用户添加昵称
     */
    loadUserInfo() {
      //添加用户昵称
      for (let i = 0; i < this.addressList.length; i++) {
        if (this.addressList[i] == null) {
          continue
        }
        // this.addressList[i].nickName = this.usersInfo.find((user) => {
        //   return user.userId === this.addressList[i].userId
        // }).nickName
        for (let j = 0; j < this.usersInfo.length; j++) {
          if (this.usersInfo[j].userId === this.addressList[i].userId) {
            this.addressList[i].nickName = this.usersInfo[j].nickName
            this.addressList[i].avatar = this.usersInfo[j].avatar
            break
          }
        }

      }
    },

    /** 查询用户地址列表 */
    getList() {
      this.loading = true
      listAddress(this.queryParams).then(response => {
        this.addressList = response.rows
        this.total = response.total
        this.loading = false
        this.loadUserInfo()
      })
    },
    /**
     * 根据选中的业务员进行提示输入
     */
    userQuerySearch(queryString, cb) {
      cb(this.userIdsInfo)
    },

    /**
     * 处理表单填写的用户id
     */
    handleUserSelect(item) {
      this.form.userId = item.userId
    },

    // 取消按钮
    cancel() {
      this.$refs.userIdInput.reset()
      this.open = false
      this.reset()
    }
    ,
    // 表单重置
    reset() {
      this.form = {
        id: null,
        userId: null,
        address: null,
        longitude: null,
        latitude: null,
        updateTime: null
      }
      this.resetForm('form')
    }
    ,
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    }
    ,
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm('queryForm')
      this.handleQuery()
    }
    ,
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    }
    ,
    /** 新增按钮操作 */
    handleAdd() {
      this.formSelectStatusShow = true
      this.reset()
      this.open = true
      this.title = '添加用户地址'
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.formSelectStatusShow = false
      this.reset()
      const id = row.id || this.ids
      getAddress(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = '修改用户地址'
      })
    }
    ,
    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateAddress(this.form).then(response => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              if (this.userIds != null) {
                this.getListByIds()
              } else {
                this.getList()
              }
            })
          } else {
            addAddress(this.form).then(response => {
              this.$modal.msgSuccess('新增成功')
              this.$refs.userIdInput.reset()
              this.open = false
              if (this.userIds != null) {
                this.getListByIds()
              } else {
                this.getList()
              }
            })
          }
          this.loadUserInfo()
        }
      })
    }
    ,
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$modal.confirm('是否确认永久删除用户地址编号为"' + ids + '"的数据项？').then(function() {
        return delAddress(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('删除成功')
      }).catch(() => {
      })
    }
    ,
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/address/export', {
        ...this.queryParams
      }, `address_${new Date().getTime()}.xlsx`)
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
      const arr = this.addressList.map(x => x[prop])
      arr.push(label) // 把每列的表头也加进去算
      // console.log(arr)
      // 2.计算每列内容最大的宽度 + 表格的内间距（依据实际情况而定）
      return (this.getMaxLength(arr) + 25) + 'px'
    }

  }
}
</script>
<style>

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
</style>
