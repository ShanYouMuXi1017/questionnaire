<template>
	<view class="setting-container" :style="{height: `${windowHeight}px`}">
		<view class="menu-list">
			<view class="list-cell list-cell-arrow" @click="handleToUpgrade">
				<view class="menu-item-box">
					<view class="iconfont icon-refresh menu-icon"></view>
					<view>检查更新</view>
				</view>
			</view>
			<view class="list-cell list-cell-arrow" @click="handleCleanTmp">
				<view class="menu-item-box">
					<view class="iconfont icon-clean menu-icon"></view>
					<view>清理缓存重新登陆</view>
				</view>
			</view>
		</view>
		<view class="exit-button-view menu">
			<view class="exit-button item-box">
				<!-- <view class="content text-center" @click="handleLogout"> -->
				<!-- <text class="text-black">退出登录</text> -->
				<u-button text="退出登录" size="large" type="error" @click="handleLogout" :ripple="true"></u-button>
				<!-- </view> -->
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				windowHeight: uni.getSystemInfoSync().windowHeight
			}
		},
		methods: {
			// handleToPwd() {
			// 	this.$tab.navigateTo('/pages/componentsB/pwd/index')
			// },
			handleToUpgrade() {
				this.$modal.showToast('已是最新版本!')
			},
			handleCleanTmp() {
				this.$modal.confirm('确定清除全部缓存并重新登陆吗？').then(() => {
					uni.clearStorage().then(res => {
						this.$modal.alert("操作成功!", "已清除全部数据, 请重新登陆!")
						uni.reLaunch({
							url: "pages/login"
						})
					})
				})
			},
			handleLogout() {
				this.$modal.confirm('确定退出系统吗？').then(() => {
					this.$store.dispatch('LogOut').then(() => {
						uni.reLaunch({
							url: 'pages/login'
						})
					})
				})
			}
		}
	}
</script>

<style lang="scss" scoped>
	.page {
		background-color: #f8f8f8;
	}

	.item-box {
		background-color: #FFFFFF;
		margin: 30rpx;
		display: flex;
		flex-direction: row;
		justify-content: center;
		align-items: center;
		padding: 10rpx;
		border-radius: 8rpx;
		color: #303133;
		font-size: 32rpx;
	}
</style>
