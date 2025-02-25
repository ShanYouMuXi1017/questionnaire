<template>
  <view class="normal-login-container">
    <!-- 标题和图片 -->
    <view class="logo-content">
      <text class="title" style="padding-top:50px;font-size:44px">昆明市骑行路线</text>
      <text class="title" style="padding-top:20px;padding-bottom:50px;font-size:34px">问卷调查</text>
    </view>

    <!-- 访谈意图文字 -->
    <view class="intro-container">
      <text class="intro-text text-content">
        为优化昆明市郊骑行线路的环境，现需广大骑友的帮助，真诚的希望您花点时间，选择一条或多条自己骑行过的线路，进行评价打分。以便我们更好的掌握该线路的优缺点，并完成该线路的环境提升改造设计。
      </text>
    </view>

    <!-- 构建登录表单 -->
    <view class="login-form-container">
      <!-- 登录按钮 -->
      <view class="login-form-button">
        <u-button text="微信授权登录" size="large" type="success" @click="handleLogin" :ripple="true"></u-button>
      </view>

      <view class="login-form-message">
        <text>
          首次登录将会自动注册
        </text>
      </view>
    </view>

    <!-- 页尾信息 -->
    <view class="login-footer-container">
      <view class="agreement text-center">
        <u-checkbox-group v-model="isCheck" placement="column" @change="clickChange">
          <u-checkbox :customStyle="{marginBottom: '8px'}" v-for="(item, index) in radiolist1" :key="index"
                      :label="item.name" :name="item.name">
          </u-checkbox>
        </u-checkbox-group>
        <text @click="handleUserAgrement" class="text-blue">《用户协议》和《隐私协议》</text>
      </view>
    </view>

    <!-- 输入验证码弹窗 -->
    <u-modal title="点击图片可以刷新验证码" :show="showModal" @confirm="captchaConfirm()">
      <view class="login-modal-container">
        <view class="login-modal-code-container">
          <image :src="codeUrl" @click="getCode" class="login-modal-code-img"></image>
        </view>
        <u-divider :hairline="true" style="margin: 0;padding: 0;"></u-divider>
        <view class="login-modal-code-input-container">
          <view class="iconfont icon-code icon"></view>
          <input v-model="loginForm.code" type="number" placeholder="请输入验证码" maxlength="4"
                 class="login-modal-code-input-view" />
        </view>
      </view>
    </u-modal>

    <!-- 定义页面显示工具 -->
    <u-toast ref="uToast"></u-toast>
    <u-loading-page :loadingText="loadingText" loadingMode="spinner" bgColor="#fafafa" :loading="loading"
                    color="#3c3f41" iconSize="32" loadingColor="#3c3f41" />
  </view>
</template>

<script>
import {
  getCodeImg,
  getOpenId
} from '@/api/login'
import {
  updateUserProfile,
  getUser
} from '../api/system/user';
import constant from '../utils/constant';
import md5 from 'js-md5';
export default {
  data() {
    return {
      codeUrl: "",
      captchaEnabled: true,
      globalConfig: getApp().globalData.config,
      loginForm: {
        username: "",
        password: "",
        code: "",
        uuid: ''
      },
      showModal: false,
      showInfoEditModal: false,
      js_code: '',
      openId: '',
      loadingText: '',
      loading: false,
      handCheck: false,
      isCheck: false,
      radiolist1: [{
        name: '我已阅读并同意以下协议',
        disabled: false
      }
      ],
      errObj: {
        title: '请阅读并勾选页面底部协议',
        icon: "none",
        duration: 2000,
      },
    }
  },

  created() {
    this.getCode();
  },

  onLoad() {
    this.getJsCode();
  },

  methods: {
    async handleLogin() {
      if (this.isCheck == false) {
        this.showErrMes();
      } else {
        this.loading = true;
        this.loadingText = '正在拉起数据...'
        this.loginForm.password = '123456';
        var o = await this.getOpenId();

        var that = this;
        Promise.all([o]).then((res) => {
          if (that.$store.state.user.nickName == null || that.$store.state.user.nickName == undefined) {
            that.loading = false;
            that.showToast({
              type: 'error',
              message: "信息授权失败, 请重试!"
            });
            return;
          }
        })
      }
    },

    async pwdLogin() {
      this.$store.dispatch('Login', this.loginForm).then(() => {
        this.loginSuccess()
      }).catch((error) => {
        this.loading = false;
        if (this.captchaEnabled) {
          this.showToast({
            type: 'error',
            message: '验证码错误, 请重试!'
          })
          this.showModal = true;
          this.getCode()
          console.error(error)
        }
      })
    },

    loginSuccess(result) {
      var that = this;
      this.loading = false;
      this.$store.dispatch('GetInfo').then(res => {
        if (res.user.phonenumber == "" || res.user.phonenumber == undefined || res.user.phonenumber == null) {
          this.showInfoEditModal = true;
        } else {
          this.$tab.reLaunch('/pages/welcome/welcome')
        }
        uni.setStorage({
          key: 'role',
          data: res.roles,
          success: function() {
            uni.reLaunch({
              url: '/pages/welcome/welcome'
            });
          }
        })
      })
    },

    cancel() {
      this.$tab.reLaunch('/pages/welcome/welcome')
    },

    getUserInfo() {
      var that = this;
      return new Promise((resolve, reject) => {
        uni.getUserProfile({
          desc: '获取个人信息',
          success: (res) => {
            resolve(res.userInfo)
          },
          fail: (err) => {
            reject(err)
          }
        })
      })
          .then((userInfo) => {
            that.$store.state.user.nickName = userInfo.nickName;
            that.$store.state.user.avatar = userInfo.avatarUrl;
          }).catch((err) => {
            that.$store.state.user.nickName = null;
            console.error(err)
          })
    },

    getJsCode() {
      var that = this;
      new Promise((resolve, reject) => {
        uni.login({
          success(loginAuth) {
            resolve(loginAuth.code)
          },
          fail: (err) => {
            reject(null)
          }
        })
      }).then((res) => {
        that.js_code = res;
      }).catch((err) => {
        that.js_code = "error"
        console.error(err)
      })
    },

    async getOpenId() {
      var that = this;
      if ("error" == that.js_code) {
        that.getJsCode()
      } else if ('' == that.js_code) {
        that.showToast({
          type: "warning",
          message: "请稍后,正在拉起数据..."
        })
        that.getJsCode()
      } else {
        getOpenId(that.js_code).then((res) => {
          if ("errorCode" == res || res == undefined || res == null || res == "") {
            that.loading = false;
            uni.showModal({
              title: '页面过期或请求超时!',
              content: '您可以点击右上角的三个小圆点按钮后, 点击重新进入小程序尝试~',
              showCancel: false,
              confirmText: '好的',
              confirmColor: '#eccc4f',
              success(res) {
                if (res.confirm) {
                }
              }
            })
            return;
          } else {
            that.openId = res;
            this.$store.state.user.name = res;
            that.loading = false;
            that.captchaConfirm()
          }
        }).catch((err) => {
          that.loading = false;
          console.error(err)
          that.showToast({
            type: 'error',
            message: "数据异常, 授权失败!"
          })
        })
      }
    },

    getCode() {
      getCodeImg().then(res => {
        this.captchaEnabled = res.captchaEnabled === undefined ? true : res.captchaEnabled
        if (this.captchaEnabled) {
          this.codeUrl = 'data:image/gif;base64,' + res.img
          this.loginForm.uuid = res.uuid
        }
      })
    },

    captchaModal() {
      this.loginForm.username = this.openId;
      this.showModal = true
    },

    captchaConfirm() {
      this.loginForm.username = this.openId;
      this.loginForm.password = this.encryption(this.openId)
      if (this.loginForm.username === "") {
        this.$modal.msg("微信授权异常, 请稍后再试!")
      } else {
        this.showModal = false
        this.loading = true;
        this.loadingText = "请稍后, 正在登录中..."
        this.pwdLogin()
      }
    },

    showToast(params) {
      this.$refs.uToast.show({
        ...params,
        complete() {
          params.url && uni.navigateTo({
            url: params.url
          })
        }
      })
    },

    encryption(str) {
      var passTemp = 'mp'
      var passWord = ''
      for (let indexTemp = str.length - 1; indexTemp >= 0; indexTemp--) {
        if (indexTemp % 4 === 0)
          passTemp += str[indexTemp]
      }
      passTemp = md5(passTemp)
      for (let index = passTemp.length - 1; index >= 0; index--) {
        if (index % 2 === 0) {
          passWord += passTemp[index]
        }
      }
      return passWord
    },

    handleUserAgrement() {
      let site = this.globalConfig.appInfo.agreements[0]
      this.$tab.navigateTo(`/pages/componentsB/user/agreementAndPrivacy`)
    },
    clickChange() {
      this.isCheck = !this.isCheck;
    },
    showErrMes() {
      wx.showToast({
        title: this.errObj.title,
        icon: this.errObj.icon,
        duration: this.errObj.duration
      })
    }
  }
}
</script>

<style lang="scss">
page {
  background-color: #fafafa;
}

.normal-login-container {

  .logo-content {
    width: 100%;
    padding-top: 15%;
    display: flex;
    justify-content: flex-start;
    flex-direction: column;
    align-items: center;

    image {
      border-radius: 4px;
    }

    .title {
      font-size: 24px;
      text-align: center;
      font-weight: bolder;
      color: #3c3f41
    }
  }

  .interview-intent {
    padding: 20px 15px;
    margin: 10px;
    background-color: #f9f9f9;
    border-radius: 8px;
    text-align: justify;

    .intent-text {
      font-size: 16px;
      color: #333;
      line-height: 1.6;
    }
  }

  .login-form-container {
    .login-form-button {
      margin: 0 3%;
    }

    .login-form-message {
      margin-top: 15px;
      display: flex;
      justify-content: center;
      color: #999;
    }
  }

  .login-footer-container {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100%;

    .agreement {
      position: fixed;
      bottom: 5%;
    }
  }

  .login-modal-code-container {
    width: 100%;
    display: flex;
    justify-content: center;
    align-items: center;

    .login-modal-code-img {
      display: block;
      width: 200rpx;
      height: 80rpx;
    }
  }

  .login-modal-code-input-container {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;
    width: 100%;
    height: 80rpx;
    border-bottom: #999 solid 2rpx;
    border-radius: 20rpx;

    .login-modal-code-input-view {
      height: 80rpx;
      margin-left: 15rpx;
      text-align: center;
    }
  }

  .cb {
    transform: scale(0.6, 0.6);
  }
}


// 访谈意图文字样式
.intro-container {
  //background-color: #ffffff;
  //border-radius: 15px;
  padding: 15px;
  margin: 20px 15px;
  //box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.intro-text {
  text-align: left;
  font-size: 16px;
  line-height: 1.8;
  color: #333333;
  font-weight: 500;
  word-wrap: break-word;
  overflow-wrap: break-word;
  text-indent: 2em;
}

.text-content {
  display: block;
  white-space: normal;
}
</style>
