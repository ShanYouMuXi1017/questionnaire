<template>

	<view class="mine-container" :style="{height: `${windowHeight}px`}">
		<u-navbar is-back="false" safeAreaInsetTop fixed placeholder bgColor="#2979ff" leftIconSize="0"
			title="我的"></u-navbar>
		<!--顶部个人信息栏-->
		<view class="header-section">
			<view class="flex padding justify-between">
				<view class="flex align-center">

					<!-- 头像 -->
					<view v-if="!avatar">
						<u-avatar icon="weixin-fill" fontSize="64" shape="square" size="64"></u-avatar>
					</view>
					<view v-if="avatar">
						<u-avatar :src="avatar" shape="square" size="64"></u-avatar>
					</view>

					</image>
					<view v-if="!nickName" @click="handleToLogin" class="login-tip">
						点击登录
					</view>

					<!-- 昵称和角色认证 -->
					<view v-if="nickName" @click="handleToInfo" class="user-info">
						<view class="u_title">{{ nickName }}</view>
						<view v-for="(role,index) in roles" :key="index">
							<u-tag :text="role.roleName" size="mini" icon="level" :type="role.tagType"
								shape="circle"></u-tag>
						</view>
					</view>

				</view>

				<view @click="handleToInfo" class="flex align-center">
					<text>个人信息</text>
					<view class="iconfont icon-right"></view>
				</view>

			</view>
		</view>

		<view class="content-section">
			<view class="mine-actions grid col-4 text-center">
				<view class="action-item" @click="handleDistriCost">
					<view class="iconfont icon-friendfill text-pink icon"></view>
					<!-- <image src="/static/icon/fenxiao.png" mode="aspectFit" style="width: 30px; height: 30px;"></image> -->
					<text class="text">分销首页</text>
				</view>
				<view class="action-item" @click="handlePointCost">
					<image src="/static/icon/jifen.png" mode="aspectFit" style="width: 25px; height: 25px;"></image>
					<text class="text">积分首页</text>
				</view>
				<view class="action-item" @click="handleVisit">
					<uni-icons type="navigate" color="#2979ff" size="28"></uni-icons>
					<!-- <image src="/static/icon/baifang.png" mode="aspectFit" style="width: 30px; height: 30px;"></image> -->
					<text class="text">委托拜访</text>
				</view>
				<view class="action-item" @click="handleCoupon">
					<!-- <view class="iconfont icon-dianzan text-green icon"></view> -->
					<image src="/static/icon/youhuiquan.png" mode="aspectFit" style="width: 25px; height: 25px;">
					</image>
					<text class="text">我的优惠券</text>
				</view>
			</view>

			<view class="menu-list">
				<view class="list-cell list-cell-arrow" @click="handleToEditInfo">
					<view class="menu-item-box">
						<!-- <view class="iconfont icon-user menu-icon"></view> -->
						<image src="/static/icon/book.png" mode="aspectFit"
							style="width: 20px; height: 16px; margin-right: 2px;"></image>
						<view> 编辑资料</view>
					</view>
				</view>
				<view class="list-cell list-cell-arrow" @click="handleMyVisit">
					<view class="menu-item-box">
						<!-- <uni-icons type="map" color="#3093ff" size="16"></uni-icons> -->
						<image src="/static/icon/redFlag.png" mode="aspectFit" style="width: 18px; height:18px;">
						</image>
						<view style="margin-left: 3px">我的地址</view>
					</view>
				</view>
				<view class="list-cell list-cell-arrow" @click="handleHelp">
					<view class="menu-item-box">
						<view class="iconfont icon-help menu-icon"></view>
						<view>常见问题</view>
					</view>
				</view>
				<view class="list-cell list-cell-arrow" @click="handleAbout">
					<view class="menu-item-box">
						<!-- <view class="iconfont icon-aixin menu-icon"></view> -->
						<image src="/static/icon/about.png" mode="aspectFit" style="width: 20px; height:20px;"></image>
						<view>关于我们</view>
					</view>
				</view>
				<view class="list-cell list-cell-arrow" @click="handleToSetting">
					<view class="menu-item-box">
						<view class="iconfont icon-setting menu-icon"></view>
						<view>应用设置</view>
					</view>
				</view>
			</view>

		</view>
		<u-divider text="若资料更新不及时, 可下拉刷新页面或清除缓存重新登陆" textSize="8"></u-divider>
	</view>
</template>

<script>
	import storage from '@/utils/storage'
	import {
		getUserProfile
	} from '../../api/system/user';

	export default {
		data() {
			return {
				nickName: this.$store.state.user.nickName,
				roles: [],
				version: getApp().globalData.config.appInfo.version,
				modalVisible: false
			}
		},

		mounted() {
			this.updateRoles()
		},

		computed: {
			avatar() {
				return this.$store.state.user.avatar
			},
			windowHeight() {
				return uni.getSystemInfoSync().windowHeight - 50
			}
		},

		onPullDownRefresh() {
			this.updateRoles();
		},
		created() {
		},
		methods: {
      //拜访客户订阅消息
			shouQuan() {
				wx.requestSubscribeMessage({
					tmplIds: ['Wc3cfh3JQwUA4niurZXGWP1rKKiiUhnzxi34ajAc13k'],
					success(res) {
						console.log("授权成功", res)
					},
					fail(res) {
						console.log("授权失败", res)
					}
				})
			},
			updateRoles() {
				var that = this;
				this.$store.dispatch('GetInfo').then(res => {
					that.nickName = that.$store.state.user.nickName;
					that.roles = JSON.parse(JSON.stringify(that.$store.state.user.roles))
					if (that.roles[0].type !== 'basic') {
						if (that.roles) {
							that.roles.sort(that.sortBy('roleSort', 1))
							if (that.roles.length > 1) {
								that.roles.splice(1, that.roles.length - 1)
								that.roles[0].roleName += " . . ."
								that.roles[0].tagType = "error"
							} else {
								that.roles[0].tagType = "warning"
							}
						}
					}
					uni.stopPullDownRefresh();
				}).catch(err => {
					console.error(err)
					uni.stopPullDownRefresh();
				})
			},
			showContactModal() {

				this.modalVisible = true;
			},
			hideContactModal() {
				this.modalVisible = false;
			},

			handleToInfo() {
				this.$tab.navigateTo('/pages/componentsB/info/index')
			},
			handleToEditInfo() {
				this.$tab.navigateTo('/pages/componentsB/info/edit')
			},
			handleToSetting() {
				this.$tab.navigateTo('/pages/componentsB/setting/index')
			},
			handleToLogin() {
				this.$tab.reLaunch('/pages/login')
			},
			handleToAvatar() {
				this.$tab.navigateTo('/pages/componentsB/avatar/index')
			},
			handleMyVisit() {
				this.$tab.navigateTo('/pages/componentsB/address/index')
			},
			handleLogout() {
				this.$modal.confirm('确定退出系统吗？').then(() => {
					this.$store.dispatch('LogOut').then(() => {
						this.$tab.reLaunch('/pages/index')
					})
				})
			},
			handleHelp() {
				this.$tab.navigateTo('/pages/componentsB/help/index')
			},
			handleAbout() {
				this.$tab.navigateTo('/pages/componentsB/about/index')
			},
			handleJiaoLiuQun() {
				this.$modal.showToast('电话： 0871-67322300 67322190 手机： 13095328257 ')
			},
			handlePointCost() {
				this.$tab.navigateTo('/pages/componentsB/point/index')
			},

			handleBuilding() {
				this.$modal.showToast('模块建设中~')
			},
			handleVisit() {
				this.$tab.navigateTo('/pages/componentsB/visit/index')
				this.shouQuan()
			},
			handleCoupon() {
				// this.$tab.navigateTo('/pages/componentsB/coupon/coupon')
				this.$modal.showToast('模块建设中~')
			},
			/**
			 * 用于实现对象数组按某一项属性排序
			 * @param {Object} attr  根据该属性排序
			 * @param {Object} rev   升序1或降序-1，不填则默认为1
			 */
			sortBy(attr, rev) {
				if (rev == undefined) {
					rev = 1
				} else {
					(rev) ? 1: -1;
				}
				return function(a, b) {
					a = a[attr];
					b = b[attr];
					if (a < b) {
						return rev * -1
					}
					if (a > b) {
						return rev * 1
					}
					return 0;
				}
			},



		}
	}
</script>

<style lang="scss">
	page {
		background-color: #f5f6f7;
	}

	.modal-content {
		background-color: #fff;
		padding: 20px;
		border-radius: 10px;
		box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
		/* 添加阴影效果 */
		border: 1px solid #ddd;
		/* 添加边框 */
		position: fixed;
		/* 使用fixed定位 */
		top: 50%;
		/* 设置垂直居中 */
		left: 50%;
		/* 设置水平居中 */
		transform: translate(-50%, -50%);
		/* 通过transform来居中 */
		z-index: 999;
		/* 设置层级高于其他元素 */
	}

	/* Close Button */
	.close {
		position: absolute;
		top: 10px;
		right: 10px;
		color: #aaa;
		font-size: 24px;
		cursor: pointer;
	}

	.wechat-qr {
		display: flex;
		justify-content: space-around;
		margin-top: 20px;
	}

	.wechat-qr-image {
		max-width: 100px;
		max-height: 100px;
		margin-right: 10px;
	}

	.mine-container {
		width: 100%;
		height: 100%;


		.header-section {
			padding: 15px 15px 45px 15px;
			background-image: linear-gradient(to bottom, #2979ff, #4188fe, #f5f6f7);
			color: white;

			.login-tip {
				font-size: 18px;
				margin-left: 10px;
			}

			.cu-avatar {
				border: 2px solid #eaeaea;

				.icon {
					font-size: 40px;
				}
			}

			.user-info {
				margin-left: 15px;

				.u_title {
					font-size: 50rpx;
					line-height: 30px;
				}

				.u_permission {
					background-color: #00aaff;
					border-radius: 5rpx;
					padding: 5rpx;
					color: white;
					font-size: 10px;
					line-height: 15px;
				}
			}
		}

		.content-section {
			position: relative;
			top: -50px;

			.mine-actions {
				margin: 15px 15px;
				padding: 20px 0px;
				border-radius: 8px;
				background-color: white;

				.action-item {
					.icon {
						font-size: 28px;
					}

					.text {
						display: block;
						font-size: 13px;
						margin: 8px 0px;
					}
				}
			}
		}
	}
</style>
