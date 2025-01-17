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

					<view v-if="!nickName" @click="handleToLogin" class="login-tip">
						点击登录
					</view>

					<!-- 昵称和角色认证 -->
					<view v-if="nickName" class="user-info">
						<view class="u_title">{{ nickName }}</view>
					</view>
				</view>


			</view>
		</view>

		<view class="content-section">
			<view class="menu-list">
				<view class="list-cell list-cell-arrow" @click="handleToSetting">
					<view class="menu-item-box">
						<view class="iconfont icon-setting menu-icon"></view>
						<view>应用设置</view>
					</view>
				</view>

				<view class="list-cell list-cell-arrow" @click="handleUserAgrement">
					<view class="menu-item-box">
						<view class="iconfont icon-user  menu-icon"></view>
						<view>用户协议</view>
					</view>
				</view>
				<view class="list-cell list-cell-arrow" @click="handleUserPrivacy">
					<view class="menu-item-box">
						<view class="iconfont icon-user menu-icon"></view>
						<view>隐私政策</view>
					</view>
				</view>
			</view>

		</view>
		<!--<u-divider text="若资料更新不及时, 可下拉刷新页面或清除缓存重新登陆" textSize="16"></u-divider>-->
	</view>
</template>

<script>
	export default {
		data() {
			return {
				nickName: this.$store.state.user.nickName,
				version: getApp().globalData.config.appInfo.version,
				modalVisible: false,
				globalConfig: getApp().globalData.config,
			}
		},


		computed: {
			avatar() {
				return this.$store.state.user.avatar
			},
			windowHeight() {
				return uni.getSystemInfoSync().windowHeight - 50
			}
		},
		created() {},
		methods: {
			handleToInfo() {
				this.$tab.navigateTo('/pages/componentsB/info/index')
			},
			handleToEditInfo() {
				this.$tab.navigateTo('/pages/componentsB/info/edit')
			},
			handleToSetting() {
				this.$tab.navigateTo('/pages/componentsB/setting/index')
			},
			handleUserAgrement() {
				this.$tab.navigateTo('/pages/componentsB/user/agreement')
			},
			handleUserPrivacy(){
				this.$tab.navigateTo('/pages/componentsB/user/privacy')
			},
			handleToLogin() {
				this.$tab.reLaunch('/pages/login')
			},
			handleLogout() {
				this.$modal.confirm('确定退出系统吗？').then(() => {
					this.$store.dispatch('LogOut').then(() => {
						this.$tab.reLaunch('/pages/login')
					})
				})
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

			.menu-list {
				.list-cell {
					/* 增加高度 */
					height: 60px;
					/* 你可以根据需要调整高度 */
					padding: 10px 15px;
					/* 为每个项增加内边距，调整项的内容位置 */
					display: flex;
					align-items: center;
					justify-content: flex-start;

					.menu-item-box {
						display: flex;
						align-items: center;
					}

					.iconfont,
					image {
						margin-right: 10px;
						/* 图标与文本之间的间距 */
					}

					/* 调整图标大小，可以根据需要进一步修改 */
					.iconfont {
						font-size: 24px;
					}

					image {
						width: 24px;
						height: 24px;
					}

					/* 如果你需要不同的颜色和字体大小，可以在这里添加样式 */
					.menu-item-box view {
						font-size: 16px;
						/* 增大文本字体 */
						//color: #333; /* 默认文本颜色 */
					}
				}
			}
		}

	}
</style>