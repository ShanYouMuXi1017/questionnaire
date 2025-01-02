<template>
	<view class="normal-login-container">
		<!-- 标题和图片 -->
		<view class="logo-content">
			<!--<image :src="globalConfig.appInfo.logo200" style="height: 150rpx;" mode="widthFix">-->
			<!--</image>-->
			<text class="title" style="padding-top:12%;font-size:44px">昆明市骑行路线</text>
			<text class="title" style="padding-top:7%;padding-bottom:45%;font-size:34px">问卷调查</text>
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
		<!--<u-modal title="信息未完善!" content="您需要填写手机号码, 完善昵称、头像和地址等信息, 其中电话号码用于小程序维修, 如无维修需求可暂时跳过。"-->
		<!--	:show="showInfoEditModal" showCancelButton @confirm="confirm" @cancel="cancel" cancelText="跳过"-->
		<!--	confirmText="完善"></u-modal>-->

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
				// 验证码开关
				captchaEnabled: true,
				// 用户注册开关
				// register: false,
				globalConfig: getApp().globalData.config,
				// 登录表单
				loginForm: {
					username: "",
					password: "",
					code: "",
					uuid: ''
				},

				// 展示验证码弹窗
				showModal: false,
				// 展示提示填写信息弹窗
				showInfoEditModal: false,
				// 页面载入的js_code
				js_code: '',

				//请求微信服务器, 后端返回用户openid作为唯一标识注入userName
				openId: '',

				// 页面显示工具数据(loading)
				loadingText: '',
				loading: false,
				handCheck: false,
				isCheck: false,
				radiolist1: [{
						name: '我已阅读并同意以下协议',
						disabled: false
					}
				],
				//定义未同意隐私协议后显示的报错信息
				errObj: {
					title: '请阅读并勾选页面底部协议',
					icon: "none",
					duration: 2000,
				},

			}
		},

		// 程序创建后
		created() {
			this.getCode();
		},

		// 进入程序
		onLoad() {
			// 同步获取js_code
			this.getJsCode();
		},

		methods: {
			// 登录方法
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
						if (that.$store.state.user.nickName == null || that.$store.state.user.nickName ==
							undefined) {
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

			// 开始登录
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

			// 登录成功后，处理函数
			loginSuccess(result) {
				var that = this;
				this.loading = false;
				// 设置用户信息
				this.$store.dispatch('GetInfo').then(res => {
					console.log(res);
					console.log(res.user);
					if (res.user.phonenumber == "" || res.user.phonenumber == undefined || res.user.phonenumber ==
						null) {
						this.showInfoEditModal = true;
					} else {
						this.$tab.reLaunch('/pages/welcome/welcome')
					}
					uni.setStorage({
						// key: 'userId',
						// data: res.user.userId,
						// success: function() {
						// 	console.log('success');
						// },
						key: 'role',
						data: res.roles,
						success: function() {
							console.log('success');
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


			// 用户授权获取微信个人信息(此接口已废弃, 只用于暂时获取信息)
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

			// 获取js_code用于微信登录
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

			// 根据用户授权 获取openId
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
							// that.showToast({
							// 	type: "error",
							// 	message: "页面过期, 请求超时, 请重新进入小程序!"
							// })
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
							that.loading = false
							//信息获取完成, 用户输入验证码
							// that.captchaModal()
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

					// })
				}

			},

			// 获取图形验证码
			getCode() {
				getCodeImg().then(res => {
					this.captchaEnabled = res.captchaEnabled === undefined ? true : res
						.captchaEnabled
					if (this.captchaEnabled) {
						this.codeUrl = 'data:image/gif;base64,' + res.img
						this.loginForm.uuid = res.uuid
					}
				})
			},

			// 打开验证码框
			captchaModal() {
				this.loginForm.username = this.openId;
				this.showModal = true
			},

			// 确认验证码输入
			captchaConfirm() {
				this.loginForm.username = this.openId;
				//设置密码
				this.loginForm.password = this.encryption(this.openId)
				//验证数据不为空
				if (this.loginForm.username === "") {
					this.$modal.msg("微信授权异常, 请稍后再试!")
					// } else if (this.loginForm.code === "" && this.captchaEnabled) {
					// this.$modal.msg("未输入验证码!")
				} else {
					// 关闭验证码弹窗, 开始正式登录
					this.showModal = false
					this.loading = true;
					this.loadingText = "请稍后, 正在登录中..."
					this.pwdLogin()
				}

			},

			// 页码的Toast工具
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

			// 用户协议
			handleUserAgrement() {
				let site = this.globalConfig.appInfo.agreements[0]
				this.$tab.navigateTo(`/pages/common/webview/index?title=${site.title}&url=${site.url}`)
			},
			clickChange() {
				this.isCheck = !this.isCheck;
			},
			//显示未同意隐私服务后的报错信息
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

		// logo和标题文字
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

		// 登录表单(登录按钮, 提示信息)
		.login-form-container {

			.login-form-button {
				margin: 0 3%;
			}

			.login-form-message {
				margin-top: 5%;
				display: flex;
				justify-content: center;
				color: #999;
			}

		}

		// 页尾信息
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

		// 弹出的验证码框样式
		.login-modal-code-container {
			width: 100%;
			display: flex;
			justify-content: center;
			align-items: center;

			// 验证码图片
			.login-modal-code-img {
				display: block;
				width: 200rpx;
				height: 80rpx;
			}
		}

		// 输入框和图标
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
</style>
