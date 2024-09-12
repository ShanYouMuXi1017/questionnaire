<template>
	
	<view class="work-container">
	 
		<u-navbar is-back="false" safeAreaInsetTop fixed placeholder leftIconSize="0" title="分销"></u-navbar>
		
		<!-- 轮播图 -->
		<u-swiper
			:list="swiperData"
			previousMargin="30"
			:height="180"
			:imgMode="scaleToFill"
			nextMargin="30"
			circular="true"
			:autoplay="true"
			radius="5"
			bgColor="#ffffff"
			indicator="true"
			indicatorMode="line"
		></u-swiper>
		<u-divider  textColor="#2979ff" lineColor="#2979ff"></u-divider>
		<!-- 首页宫格组件 -->
		<view>
			<u-grid :border="true">
				<u-grid-item v-for="(listItem, listIndex) in list" :key="listIndex" @click="onFunClick" customStyle="padding-top: 20px; padding-bottom: 20px">
					<image :src="listItem.icon" mode="aspectFit" style="width: 30px; height: 30px"></image>
					<text class="grid-text">{{ listItem.title }}</text>
				</u-grid-item>
			</u-grid>
		</view>
	</view>
</template>

<script>
import { listUser, getUser, listBanner } from '@/api/system/user';
import { baseUrl } from '../../config';
import { isAgent } from '@/api/system/distri';
export default {
	data() {
		return {
			isAgent: 0,
			swiperData: [
				{
					url: '/static/logo_200.png'
				}
			],
			list: [
				{
					icon: '/static/icon/fenxiao.png',
					title: '我的分销',
					page: '/pages/componentsB/distri/index',
					visible: true
				},
				{
					icon: '/static/icon/jifen.png',
					title: '我的积分',
					page: '/pages/componentsB/point/index',
					visible: true
				}
			],
			paramsList: {
				bannerType: 0
			}
		};
	},
	onLoad() {
		//轮播图
		listBanner(this.paramsList).then((response) => {
			if (response.code == 200 && response.rows.length != 0) {
				this.swiperData = [];
				this.swiperData = response.rows;
				this.swiperData.forEach((item) => {
					item.url = baseUrl + item.banner;
				});
			}
		});
	},
	onPullDownRefresh() {
		isAgent().then((r) => {
			this.isAgent = r.data[0].isAgent;
		});
	},
	created() {
		isAgent().then((r) => {
			this.isAgent = r.data[0].isAgent;
		});
	},
	mounted() {
		// 页面加载时自动获取数据并更新进度条
		// this.fetchData();
	},
	methods: {
		parseDate(dateTimeString) {
			var date = new Date(dateTimeString);
			var dateOnlyString = date.toISOString().slice(0, 10);
			return dateOnlyString;
		},

		/**
		 * 首页功能点击监听
		 * @param {Object} e 点击了第几个功能
		 */
		onFunClick(listIndex) {
			// 获取要跳转到的页面路径
			const pagePath = this.list[listIndex].page;
			if (listIndex === 0 && this.isAgent !== 0) {//不是代理商
				uni.showToast({
					title: '维修金额尚未达到门槛, 当前用户不是代理商',
					icon: 'none',
					mask: true,
					duration: 2000
				});
			}else{
				// 页面跳转
				uni.navigateTo({
					url: pagePath
				});
			}
		},
		fetchData() {}
	}
};
</script>

<style lang="scss">
/* #ifndef APP-NVUE */
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
</style>
