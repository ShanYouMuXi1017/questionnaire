<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="方式编号" prop="pointId">
        <el-input
          v-model="queryParams.pointId"
          placeholder="请输入方式编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item label="获取方式" prop="pointWays">
        <el-input
          v-model="queryParams.pointWays"
          placeholder="请输入积分获取方式"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>


      <el-form-item label="积分类型" prop="pointType">
        <el-select v-model="queryParams.pointType" placeholder="积分类型" clearable>
          <el-option
            v-for="dict in dict.type.sys_point"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <!--<el-col :span="1.5">-->
      <!--  <el-button-->
      <!--    type="primary"-->
      <!--    plain-->
      <!--    icon="el-icon-plus"-->
      <!--    size="mini"-->
      <!--    @click="handleAdd"-->
      <!--    disabled-->
      <!--    v-hasPermi="['system:value:add']"-->
      <!--  >新增-->
      <!--  </el-button>-->
      <!--</el-col>-->
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:value:edit']"
        >修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:value:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="valueList" @selection-change="handleSelectionChange">
      <!--<el-table-column type="selection" width="55" align="center"/>-->
      <el-table-column label="方式编号" align="center" prop="pointId"/>
      <el-table-column label="积分获取/使用方式" align="center" prop="pointWays"/>
      <el-table-column label="积分设置数额" align="center" prop="pointValue">
        <template slot-scope="scope">
            <span v-if="scope.row.pointId === 1022 || scope.row.pointId === 1024 || scope.row.pointId === 65535 || scope.row.pointId === 65536"
                  style="color: red;font-weight: bold">
              {{ scope.row.pointValue }} %
            </span>
          <!--<span v-else-if="scope.row.pointId === 1023" style="color: red;font-weight: bold">-->
          <!--    {{ scope.row.pointValue }} 元-->
          <!--  </span>-->
          <span v-else style="color: red;font-weight: bold">
              {{ scope.row.pointValue }} 积分
            </span>
        </template>
      </el-table-column>
      <el-table-column label="折合现金" align="center" prop="cash">
        <template slot-scope="scope">
            <span v-if="scope.row.pointId === 1022 || scope.row.pointId === 1024 || scope.row.pointId === 65535 || scope.row.pointId === 65536"
                  style="font-weight: bold">
              无
            </span>
          <span v-else style="color: red;font-weight: bold">
              {{ (scope.row.pointValue * tempBili).toFixed(2) }} 元
            </span>
        </template>
      </el-table-column>
      <el-table-column label="积分类型" align="center" prop="pointType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_point" :value="scope.row.pointType"/>
        </template>
      </el-table-column>
      <el-table-column label="参数说明" align="center" prop="remark" width="300"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:value:edit']"
          >修改
          </el-button>
          <el-button
            v-if="scope.row.pointId===1024"
            size="mini"
            type="text"
            icon="el-icon-s-tools"
            v-hasPermi="['system:pack:query']"
            @click="handlePointPackage()"
          >积分包设置
          </el-button>
          <!--<el-button-->
          <!--  size="mini"-->
          <!--  type="text"-->
          <!--  icon="el-icon-delete"-->
          <!--  :disabled="scope.row.pointId!=null"-->
          <!--  @click="handleDelete(scope.row)"-->
          <!--  v-hasPermi="['system:value:remove']"-->
          <!--&gt;删除</el-button>-->
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

    <el-dialog
      :close-on-click-modal="false"
      title="积分包设置"
      :visible.sync="dialogVisible"
      width="44%"
      center>
      <el-divider>
        <div style="font-weight:700;font-size: larger">积分包详情</div>
      </el-divider>
      <!--展示-->
      <el-table
        v-loading="loading2"
        :data="packageList"
        border>
        <el-table-column label="积分包id" align="center" prop="id"/>
        <el-table-column label="积分包大小" align="center" prop="packageSize"/>
        <el-table-column label="所能抵扣的金额" align="center" prop="deduction">
          <template slot-scope="scope">
            <span style="color: red;font-weight: bold">
              {{ scope.row.deduction }} 元
            </span>
          </template>
        </el-table-column>
      </el-table>
      <el-divider>
        <div style="font-weight:700;font-size: larger">修改积分包</div>
      </el-divider>
      <!--修改-->
      <el-form ref="elForm" :model="packForm" :rules="rules2" size="medium" label-width="100px">
        <el-row type="flex" justify="start" align="top" gutter="15">
          <el-form-item label="起始积分包" prop="start_pack">
            <el-input class="my_input_disp" v-model="packForm.start_pack" disabled placeholder="请输入起始积分包大小"
                      clearable>
            </el-input>
          </el-form-item>
          <el-form-item label="积分包步长" prop="field103">
            <el-input-number v-model="packForm.step_pack" :min="100" :step="100" step-strictly
                             placeholder="积分包步长"></el-input-number>
          </el-form-item>
        </el-row>
      </el-form>
      <div slot="footer">
        <el-button @click="resetPack">重置</el-button>
        <el-button type="primary" @click="handlePackEdit">提交</el-button>
      </div>

    </el-dialog>

    <!-- 添加或修改积分参数对话框 -->
    <el-dialog :close-on-click-modal="false" :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="获取方式" prop="pointWays">
          <el-input class="my_input_disp" :disabled="form.pointId!=null" v-model="form.pointWays"
                    placeholder="请输入积分获取方式"/>
        </el-form-item>
        <el-form-item label="设置数额" prop="pointValue">
          <el-col :span="10">
            <!--<el-input v-if="form.pointId===1023" v-model="form.pointValue" placeholder="请输入数额">-->
            <!--  <span slot="suffix" style="font-weight: bold;color: red">元</span>-->
            <!--</el-input>-->
            <el-input v-if="form.pointId===1022||form.pointId===1024||form.pointId===65535||form.pointId===65536" v-model="form.pointValue"
                      placeholder="请输入比例">
              <span slot="suffix" style="font-weight: bold;color: red">%</span>
            </el-input>
            <el-input v-else v-model="form.pointValue" placeholder="请输入数额">
              <span slot="suffix" style="font-weight: bold;color: red">积分</span>
            </el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="积分类型" prop="pointType">
          <el-radio-group v-model="form.pointType" :disabled="form.pointId!=null">
            <el-radio :label="1">使用</el-radio>
            <el-radio :label="0">获取</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="参数说明" prop="remark">
          <el-input v-model="form.remark" type="textarea"
                    autosize placeholder="请输入参数说明"/>
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
import {listValue, getValue, delValue, addValue, updateValue} from "@/api/system/point_value";
import dict from "@/utils/dict";
import {listPackage, updatePackage} from "@/api/system/package";

export default {
  name: "Value",
  dicts: ['sys_point'],// 0表示是，1表示否
  data() {
    return {
      packForm: {//积分包表单
        start_pack: 0,
        step_pack: 100
      },
      tempBili:0.0000,
      tempSep: 100,
      rules2: {
        start_pack: [{
          required: true,
          // pattern: /^[1-9]\d*00$/,
          // message: '请输入整百的正数,如:100,1200,12300。',
          trigger: 'blur'
        }],
        step_pack: [{
          required: true,
          pattern: /^[1-9]\d*00$/,
          message: '请输入整百的正数,如:100,1200,12300。',
          trigger: 'blur'
        }],
      },
      packageList: [],//积分包表格
      queryParams2: {
        id: null,
        packageSize: null,//需要展示的参数
      },
      dialogVisible: false,
      // 遮罩层
      loading: true,
      loading2: true,
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
      // 积分参数表格数据
      valueList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        pointId: null,
        pointWays: null,
        pointValue: null,
        pointType: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        pointWays: [{required: true, message: '获取方式不能为空', trigger: 'blur'}],
        pointValue: [
          { required: true, message: '设置数额不能为空', trigger: 'blur' },
          {
            validator: (rule, value, callback) => {
              // 首先判断是否为空或者非数字
              if (value === '' || isNaN(value)) {
                callback(new Error('设置数额必须为数字'));
              } else {
                // 将输入转换为浮点数
                const floatValue = parseFloat(value);
                const pointId = this.form.pointId; // 获取当前的 pointId

                // 判断是否为正数
                if (floatValue >= 0) {
                  if (pointId === 1022||pointId === 1024 || pointId === 65535 || pointId === 65536) {
                    // 判断是否包含两位小数
                    const decimalCount = (value.toString().split('.')[1] || '').length;
                    if (decimalCount <= 2) {
                      // 判断 pointValue 是否为 100
                      if (floatValue > 100) {
                        callback(new Error('输入比例不可大于 100%'));
                      } else {
                        callback();
                      }
                    } else {
                      callback(new Error('设置数额最多包含两位小数'));
                    }
                  } else {
                    // 不允许为小数
                    if (Number.isInteger(floatValue)) {
                      callback();
                    } else {
                      callback(new Error('设置数额必须为整数'));
                    }
                  }
                } else {
                  callback(new Error('设置数额必须为正数'));
                }
              }
            },
            trigger: 'blur'
          }
        ],
        pointType: [{required: true, message: '积分类型不能为空', trigger: 'change'}]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    dict,
    /** 查询积分参数列表 */
    getList() {
      this.loading = true;
      listValue(this.queryParams).then(response => {
        this.valueList = response.rows;
        this.valueList.forEach((item) => {
          if(item.pointId===1024){
            this.tempBili = item.pointValue
          }
          if (item.pointId === 1022||item.pointId === 1024 || item.pointId === 65535 || item.pointId === 65536) {
            item.pointValue *= 100.0
          }
        })
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
        pointId: null,
        pointWays: null,
        pointValue: null,
        pointType: null,
        remark: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null
      };
      this.resetForm("form");
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
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.pointId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加积分参数";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const pointId = row.pointId || this.ids
      getValue(pointId).then(response => {
        this.form = response.data;
        if (this.form.pointId === 1022 || this.form.pointId === 1024 || this.form.pointId === 65535 || this.form.pointId === 65536) {
          this.form.pointValue *= 100.0
        }
        this.open = true;
        this.title = "修改积分参数";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.pointId != null) {
            if (this.form.pointId === 1022 || this.form.pointId === 1024 || this.form.pointId === 65535 || this.form.pointId === 65536) {
              this.form.pointValue /= 100.0
            }
            updateValue(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addValue(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const pointIds = row.pointId || this.ids;
      this.$modal.confirm('是否确认删除积分参数编号为"' + pointIds + '"的数据项？').then(function () {
        return delValue(pointIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/value/export', {
        ...this.queryParams
      }, `value_${new Date().getTime()}.xlsx`)
    },
    resetPack() {//重置表单
      this.packForm.start_pack = 100
      this.packForm.step_pack = this.tempSep
      this.$refs['elForm'].resetFields()
    },
    handlePackEdit() {//提交表单
      this.$refs['elForm'].validate(valid => {
        if (!valid) {
          return
        } else {
          //处理请求
          var start = parseInt(this.packForm.start_pack)
          var step = parseInt(this.packForm.step_pack)
          for (var i = 1; i < 5; i++) {
            updatePackage({id: i, packageSize: start}).then(response => {
            });
            start = start + step
          }
        }
        this.$modal.msgSuccess("修改成功");
        //提交后重置表单
        this.resetPack()
        this.packForm.step_pack = step
        this.handlePointPackage()
      })
    },
    /** 处理积分包弹窗展示*/
    handlePointPackage() {
      this.dialogVisible = true
      this.loading2 = true
      listPackage(this.packageSize).then(response => {
        this.packageList = response.rows
        this.tempSep = response.rows[1].packageSize
        this.packForm.step_pack = this.tempSep
        this.loading2 = false
      })
    }
  }
};
</script>
<style scoped>
.my_input_disp /deep/ .el-input__inner {
  color: red !important;
}
</style>
