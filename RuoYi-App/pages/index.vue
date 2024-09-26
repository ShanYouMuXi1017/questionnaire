<template>
	<view class="work-container">

		<u-navbar @leftClick="handlerNoticeClick" safeAreaInsetTop fixed placeholder title="问卷">
			<view slot="left">
				<u-badge isDot="true" :show="showNoticeDot" type="error"  :offset="[8,5]" absolute="true"></u-badge>
				<u-icon name="bell-fill" color="#4b95df" size="20"></u-icon>
			</view>
		</u-navbar>
		
		
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
		<view class="devide_line"></view>
		<!-- 公告 -->
		<u-notice-bar :text="appAnnouncementTitle" mode="link" color="#2979ff" bgColor="#FFFFFF" is-circular="false" @click="toAnnouncementPage"></u-notice-bar>

		<!-- <u-divider text="首页功能" textColor="#2979ff" lineColor="#2979ff"></u-divider> -->

		<!-- 首页宫格组件 -->
		<!-- 图标自行更改 https://xuqu.gitee.io/components/icon.html -->
		<view>
			<u-grid :border="false">
				<u-grid-item v-for="(listItem, listIndex) in list" :key="listIndex" @click="onFunClick" customStyle="padding-top: 20px; padding-bottom: 20px">
					<image :src="listItem.icon" mode="aspectFit" style="width: 30px; height: 30px"></image>
					<text class="grid-text">{{ listItem.title }}</text>
				</u-grid-item>
			</u-grid>
		</view>
	</view>
</template>

<script>
 
import { getUser } from '@/api/system/user';
import { getLastAnnouncement, listBanner, listNotice } from '../api/home';
import { baseUrl } from '../config';
 
export default {
	data() {
		return {
			userId: this.$store.state.user.userId,
			swiperData: [
				{
					url: '/static/logo_200.png'
				}
			],
			// 公告栏信息
			showNoticeDot: false,
			appAnnouncementTitle: '公告内容',
			appAnnouncementText: '',
			total: 1, // 假设 total 是你的一个数据属性，表示产品维修进度的总数
			stepsList: [],
			progressData: {
				// 假设 progressData 包含产品维修进度信息
				application: 1,
				delivery: 0,
				flag: 0,
				id: 4,
				inspection: 0,
				makeOffer: 0,
				productId: 1122,
				repair: 0,
				userId: 26,
				userName: '新用户'
			},
			active: 0, // 当前活动的步骤
			list: [],
			coupons: {},
			paramsList: {
				bannerType: 1
			}
		};
	},
	// onLoad() {
	// 	console.log("执行了这个")
	// 	this.getUserInfo();
	// },
	created() {
	},

	methods: {
		parseDate(dateTimeString) {
			var date = new Date(dateTimeString);
			var dateOnlyString = date.toISOString().slice(0, 10);
			return dateOnlyString;
		},
	 
		/**
		 * 轮播图点击处理
		 * @param {Object} e 第几张图片
		 */
		onSwiperClick(e) {
			console.log('点击了第' + e + '张轮播图!');
		},

		/**
		 * 点击公告条跳转想看详细
		 */
		toAnnouncementPage() {
			this.$tab.navigateTo(`/pages/componentsB/common/notice/announcement?title=`);
		},

		change() {
			if (this.active < this.list1.length - 1) {
				this.active += 1;
			} else {
				this.active = 0;
			}
		},

		/**
		 * 点击顶部通知按钮
		 */
		handlerNoticeClick() {
			this.$tab.navigateTo(`/pages/componentsB/common/notice/notificationlist`);
		},

		/**
		 * 首页功能点击监听
		 * @param {Object} e 点击了第几个功能
		 */
		onFunClick(listIndex) {
			// 获取要跳转到的页面路径
			const pagePath = this.list[listIndex].page;
			// 页面跳转
			uni.navigateTo({
				url: pagePath
			});
		},
		fetchData() {},
		onLoad: function (query) {
				this.list = [
					{
						icon: '/static/icon/repair.png',
						title: '问卷调查',
						page: '/pages/questionnaire/route_choice',
						visible: true // 添加 visible 属性
					}
				];
			// 页面加载同步设置公告栏信息
			getLastAnnouncement()
				.then((response) => {
					if (response.code == 200) {
						this.appAnnouncementTitle = response.data.noticeTitle;
						this.appAnnouncementText = response.data.noticeContent;
					}
				})
				.catch((error) => {
					this.appAnnouncementTitle = '公司公告内容 . . .';
				});
			listNotice().then(res=>{
				let noticeListTmp = res.data;
				if(noticeListTmp.length != 0){
					let noticeTmpTamp = new Date(noticeListTmp[0].createTime).getTime()
					noticeListTmp.forEach(item=>{
						let tamp = new Date(item.createTime).getTime()
						if(tamp > noticeTmpTamp){
							noticeTmpTamp = tamp;
						}
					})
					if(new Date().getTime() - noticeTmpTamp <= 86400000){
						this.showNoticeDot = true
					}
				}
			})
			listBanner(this.paramsList).then((response) => {
				if (response.code == 200 && response.rows.length != 0) {
					this.swiperData = [];
					this.swiperData = response.rows;
					this.swiperData.forEach((item) => {
						item.url = baseUrl + item.banner;
					});
				}
			});
		}
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

view {
	font-size: 14px;
	line-height: inherit;
}

.cont {
	padding-top: 40rpx;
	width: 750rpx;
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

.grid-text {
	// color: #000000;
}
</style>
