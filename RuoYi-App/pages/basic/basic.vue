<template>
  <view class="container">
    <!--<view class="header">骑友信息调查</view>-->

    <view class="form-section">
      <!-- 性别选择 -->
      <view class="form-item">
        <view class="form-label">您的性别</view>
        <lfj-dropdown-box
            placeholder="请选择性别"
            v-model="form.sex"
            :localdata="genderOptions"
            @change="changeSex"
            class="dropdown"
        ></lfj-dropdown-box>
        <view v-if="errors.sex" class="error-message">{{ errors.sex }}</view>
      </view>

      <!-- 年龄段选择 -->
      <view class="form-item">
        <view class="form-label">您的年龄段</view>
        <lfj-dropdown-box
            placeholder="请选择年龄段"
            v-model="form.age_duan"
            :localdata="ageOptions"
            @change="changeAge"
            class="dropdown"
        ></lfj-dropdown-box>
        <view v-if="errors.age_duan" class="error-message">{{ errors.age_duan }}</view>
      </view>

      <!-- 骑龄选择 -->
      <view class="form-item">
        <view class="form-label">您的骑龄</view>
        <lfj-dropdown-box
            placeholder="请选择骑龄"
            v-model="form.riding_age"
            :localdata="ridingAgeOptions"
            @change="changeRidingAge"
            class="dropdown"
        ></lfj-dropdown-box>
        <view v-if="errors.riding_age" class="error-message">{{ errors.riding_age }}</view>
      </view>

      <!-- 最喜欢的骑行路线 -->
      <view class="form-item">
        <view class="form-label">最喜欢的骑行路线(多选)</view>
        <lfj-dropdown-box
            v-model="form.preferred_routers"
            :localdata="routesOptions"
            multiple
            filterable
            @change="changeRoutes"
            placeholder="请选择1-3条路线"
            class="dropdown"
        ></lfj-dropdown-box>
        <view v-if="errors.preferred_routers" class="error-message">{{ errors.preferred_routers }}</view>
      </view>
    </view>

    <!-- 提交按钮 -->
    <view class="submit-btn" @click="submit">提交</view>
  </view>
</template>

<script>
import {fillUserBasic, getRouters} from "../../api/system/user";

export default {
  data() {
    return {
      form: {
        userId:this.$store.state.user.userId,
        sex: '',
        age_duan: '',
        riding_age: '',
        preferred_routers: []
      },
      errors: {
        sex: '',
        age_duan: '',
        riding_age: '',
        preferred_routers: ''
      },
      genderOptions: [
        { text: '男', value: '0' },
        { text: '女', value: '1' }
      ],
      ageOptions: [
        { text: '18岁以下', value: '0' },
        { text: '18~25岁', value: '1' },
        { text: '26~30岁', value: '2' },
        { text: '31~40岁', value: '3' },
        { text: '41~50岁', value: '4' },
        { text: '51~60岁', value: '5' },
        { text: '60岁以上', value: '6' }
      ],
      ridingAgeOptions: [
        { text: '半年以内', value: '0' },
        { text: '1-2年', value: '1' },
        { text: '2-5年', value: '2' },
        { text: '5-10年', value: '3' },
        { text: '10年以上', value: '4' }
      ],
      routesOptions: []
    };
  },
  created() {
    getRouters().then(res=>{
      this.routesOptions = this.transformRoutesData(res.data)
    })
  },
  methods: {
    transformRoutesData(routesData) {
      return routesData.map(route => {
        // 获取路线名称、全长、爬升高度
        const routeName = route.routeName || '';
        const length = route.length || '未知';
        const elevation = route.elevation || '未知';

        // 拼接 text 字符串：路线名称 + 全长 + 爬升高度
        const text = `${routeName} ${length} km 爬升${elevation} m`;

        // 生成一个新的对象，包含 text 和 value
        return {
          text: text,
          value: route.routerId.toString()  // 确保 routerId 是字符串
        };
      });
    },
    changeSex(e) {
      this.form.sex = e !== null ? e.value : '';
      this.clearError('sex');
    },
    changeAge(e) {
      this.form.age_duan = e !== null ? e.value : '';
      this.clearError('age_duan');
    },
    changeRidingAge(e) {
      this.form.riding_age = e !== null ? e.value : '';
      this.clearError('riding_age');
    },
    changeRoutes(e) {
      if (e !== null) {
        this.form.preferred_routers = e.map(item => item.value);
      } else {
        this.form.preferred_routers = [];
      }
      this.clearError('preferred_routers');
    },

    // 清除特定字段的错误信息
    clearError(field) {
      if (this.errors[field]) {
        this.errors[field] = ''; // 清除字段的错误提示
      }
    },

    validate() {
      let valid = true;
      this.errors = {}; // 清空之前的错误信息

      // 验证性别
      if (!this.form.sex) {
        this.errors.sex = '性别为必填项';
        valid = false;
      }

      // 验证年龄段
      if (!this.form.age_duan) {
        this.errors.age_duan = '年龄段为必填项';
        valid = false;
      }

      // 验证骑龄
      if (!this.form.riding_age) {
        this.errors.riding_age = '骑龄为必填项';
        valid = false;
      }

      // 验证骑行路线，最多选择3条
      if (this.form.preferred_routers.length === 0) {
        this.errors.preferred_routers = '请选择至少一条骑行路线';
        valid = false;
      } else if (this.form.preferred_routers.length > 3) {
        this.errors.preferred_routers = '最多选择3条骑行路线';
        valid = false;
      }
      return valid;
    },

    submit() {
      // 在提交前进行验证
      if (this.validate()) {
        // 提交逻辑
        console.log('表单提交:', this.form);
        // 这里可以将表单数据发送到后端
        this.form.preferred_routers = this.form.preferred_routers || []; // 确保它是数组
        this.form.preferred_routers = this.form.preferred_routers.join(",")
        console.log(this.form);
        fillUserBasic(this.form).then(res=>{
          console.log("成功传到后端");
          console.log(res);
          if(res.code===200){
            uni.reLaunch({
              url: '/pages/questionnaire/route_choice'
            });
          }
        })
      } else {
        console.log('表单验证失败:', this.errors);
      }
    }
  }
};
</script>

<style lang="scss">
/* 整体容器 */
.container {
  width: 100%;
  padding: 20px;
  background: linear-gradient(135deg, #4A90E2, #50E3C2); /* 渐变背景 */
  min-height: 100vh;
}

/* 页面标题 */
.header {
  text-align: center;
  font-size: 36px;
  font-weight: bold;
  color: white;
  padding: 20px 0;
  margin-bottom: 30px;
  background-color: rgba(0, 0, 0, 0.5);
  border-radius: 12px;
}

/* 表单部分 */
.form-section {
  background-color: white;
  border-radius: 16px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.15);
  padding: 25px;
  margin-bottom: 30px;
}

/* 每一项 */
.form-item {
  margin-bottom: 25px;
  display: flex;
  flex-direction: column;
}

/* 标签样式 */
.form-label {
  font-size: 20px;
  color: #555;
  margin-bottom: 10px;
  font-weight: bold;
}

/* 下拉框样式 */
.dropdown {
  width: 90%;
  font-size: 16px;
  padding: 15px;
  background-color: #f5f5f5;
  border-radius: 10px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease-in-out;
}

.dropdown:focus {
  background-color: #e7f1ff;
  border-color: #4a90e2;
  box-shadow: 0 0 8px rgba(74, 144, 226, 0.6);
}

/* 错误提示样式 */
.error-message {
  color: #e74c3c;
  font-size: 14px;
  margin-top: 8px;
}

/* 提交按钮 */
.submit-btn {
  width: 90%;
  margin: 0 auto;
  padding: 15px;
  background-color: #4A90E2;
  text-align: center;
  color: white;
  font-size: 18px;
  font-weight: bold;
  border-radius: 10px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  transition: background-color 0.3s ease, transform 0.3s ease;
}

.submit-btn:hover {
  background-color: #357abd;
  transform: translateY(-4px);
}

.submit-btn:active {
  background-color: #357abd;
  transform: translateY(0);
}
</style>
