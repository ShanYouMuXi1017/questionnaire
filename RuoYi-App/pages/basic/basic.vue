<template>
  <view>
    <form @submit="onSubmit">
      <!-- 用户填写基本信息的表单 -->
      <input v-model="userInfo.name" placeholder="请输入姓名" />
      <input v-model="userInfo.age" placeholder="请输入年龄" />
      <button formType="submit">提交</button>
    </form>
  </view>
</template>

<script>
export default {
  data() {
    return {

      userInfo: {
        name: '',
        age: ''
      }
    };
  },
  methods: {
    onSubmit() {
      // 保存用户填写的基本信息
      wx.setStorageSync('hasFilledBasicInfo', true);

      // 跳转到首页
      wx.redirectTo({
        url: '/pages/questionnaire/route_choice'
      });
    }
  }
}
</script>

<style scoped>
/* 基本信息页面样式 */
</style>
