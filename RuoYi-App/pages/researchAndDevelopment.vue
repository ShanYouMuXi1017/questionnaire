<template>

	<view class="work-container">

		<u-navbar is-back="false" safeAreaInsetTop fixed placeholder leftIconSize="0" title="研发"></u-navbar>
		<!-- 轮播图 -->
		<u-swiper :list="swiperData" previousMargin="30" :height="180" :imgMode="scaleToFill" nextMargin="30"
			circular="true" :autoplay="true" radius="5" bgColor="#ffffff" indicator="true"
			indicatorMode="line"></u-swiper>
		<u-divider textColor="#2979ff" lineColor="#2979ff"></u-divider>
		<!-- 首页宫格组件 -->
		<!-- <view  >
			张总准备写的文字
		</view> -->
		<view class="wrap">
			<view class="announcement-title">{{title}}</view>
			<view class="announcement-meta">
				<uni-dateformat style="display: inline-block;" :date="createTime" :threshold="[60000, 3600000]"
					format="yyyy年MM月dd日"></uni-dateformat>
			</view>
			<view class="announcement-content">
				<rich-text :nodes="content" class="ql-editor"></rich-text>
			</view>
			<view class="announcement-foot"></view>
		</view>
		<view style="width: 150px;margin:0 auto;">
			<u-button type="primary" text="联系我们" shape="circle" :ripple="true" ripple-bg-color="#909399"
				customStyle="margin-top: 20px;" @click="lianxi()"></u-button>
		</view>

	</view>
</template>

<script>
	import {
		developListBanner
	} from '@/api/system/user';
	// import {
	// 	baseUrl
	// } from '../config';
	import {
		getLastAnnouncement,
		getNotification,
		getRAndDNotification
	} from '@/api/home';
	import {
		baseUrl
	} from '@/config';
	export default {
		data() {
			return {
				swiperData: [{
					url: '/static/logo_200.png'
				}],
				paramsList: {
					bannerType: 2
				},
				title: "通知内容",
				content: "通知内容加载中 . . . ",
				createTime: ""
			};
		},
		onLoad() {
			//轮播图
			developListBanner(this.paramsList).then((response) => {
				if (response.code == 200 && response.rows.length != 0) {
					this.swiperData = [];
					this.swiperData = response.rows;
					this.swiperData.forEach((item) => {
						item.url = baseUrl + item.banner;
					});
				}
			});

			// 页面加载同步获取通知详细
			getRAndDNotification(3).then(response => {
				if (response.code == 200) {
					this.title = response.data.noticeTitle;
					this.content = response.data.noticeContent.replace(/<img src="\/dev-api/g,
						'<img style="max-width: 85%; margin: 8px auto" src="' + baseUrl);;
					this.createTime = response.data.createTime;
				} else {
					uni.showToast({
						icon: "error",
						title: "数据加载失败!"
					})
				}
			}).catch(error => {
				uni.showToast({
					icon: "error",
					title: "数据加载失败!"
				})
			});
		},


		mounted() {},
		methods: {
			lianxi() {
				uni.makePhoneCall({
					phoneNumber: '13095328257'
				});

			}


		}
	};
</script>

<style lang="scss">
	/* #ifndef APP-NVUE */
	@import url("@/pages/componentsB/static/scss/quill.bubble.css");
		@import url("@/pages/componentsB/static/scss/quill.core.css");
		@import url("@/pages/componentsB/static/scss/quill.snow.css");
	page {
		display: flex;
		flex-direction: column;
		box-sizing: border-box;
		background-color: #fff;
		min-height: 100%;
		height: auto;
	}

	/* #endif */

	.text {
		text-align: center;
		font-size: 26rpx;
		margin-top: 10rpx;
	}

	.devide_line {
		margin: 5rpx 30rpx;
		height: 3rpx;
		background-color: rgb(228, 228, 228);
		display: inline-block;
		width: 100%;
	}
	page {
			background-color: #ffffff;
		}
	
		.announcement-title {
			font-size: x-large;
			font-weight: 400;
			text-align: left;
			padding-bottom: 10rpx;
			font-weight: bolder;
			margin: 30rpx 30rpx 0;
			color: #333333;
		}
	
		.announcement-meta {
			padding: 10rpx 35rpx 16rpx;
			color: #999999;
			border-bottom: 4rpx solid #ededed;
		}
	
		.announcement-content {
			margin: -16px 0 0 0;
			padding: 0rpx 30rpx 30rpx;
			overflow: hidden;
			font-size: 30rpx;
			line-height: 50rpx;
	
		}
	
		.announcement-foot {
			padding: 0 30rpx 20rpx;
			font-size: 26rpx;
			color: #999999;
		}
</style>