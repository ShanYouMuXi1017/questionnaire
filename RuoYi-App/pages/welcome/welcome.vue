<template>
  <view class="welcome-page" v-if="imgSrc">
    <image :src="imgSrc" mode="aspectFill"></image>
    <view class="bottom-box">
      <view>{{time}}</view>
      <view @click="gotoIndex">跳过</view>
    </view>
  </view>
</template>

<script>
import {isFillUserBasic} from "../../api/system/user";

export default {
  data() {
    return {
      userId: this.$store.state.user.userId,
      imgSrc: '',
      time: 5,
      timer: null
    }
  },
  onLoad() {
    console.log(this.userId);
    console.log("用户id");
    this.getImgSrc()
    this.countDown()
  },
  onUnload() {
    if (this.timer) {
      clearInterval(this.timer)
      this.timer = null
    }
  },

  methods: {
    getImgSrc() {
      try {
        this.imgSrc = '/static/welcome/img.png'
      } catch (e) {
        console.log(e);
      }
    },

    /**
     * 跳转页面
     * 如果填了信息，跳转index页面
     * 如果没有填信息，跳转basic页面
     */
    async gotoIndex() {
       await isFillUserBasic(this.userId).then(res=>{
        if(res.data===1){//填了
          uni.reLaunch({
            url: '/pages/questionnaire/route_choice'
          });
        }
        if(res.data===-1){//没填
          uni.reLaunch({
            url: '/pages/basic/basic'
          });
        }
      })
    },

    /**
     * 倒计时
     */
    countDown() {
      this.time = 4
      this.timer = setInterval(() => {
        this.time--;
        if (this.time < 1) {
          clearInterval(this.timer)
          this.time = 0
          this.gotoIndex()
        }
      }, 1000)
    }
  }
}
</script>

<style lang="scss" scoped>
.welcome-page {
  position: relative;
  width: 100vw;
  height: 100vh;

  image {
    display: block;
    width: 100%;
    height: 100%;
  }

  .bottom-box {
    position: absolute;
    left: 0;
    bottom: 80rpx;
    display: flex;
    justify-content: space-between;
    align-items: center;
    width: 100%;
    padding: 0 60rpx;
    box-sizing: border-box;

    view {
      width: 120rpx;
      height: 64rpx;
      background-color: rgba(0, 0, 0, .3);
      border-radius: 999rpx;
      text-align: center;
      line-height: 64rpx;
      color: #fff;
      font-size: 28rpx;
    }
  }
}
</style>
