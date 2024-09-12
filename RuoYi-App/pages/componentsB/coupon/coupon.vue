<template>
	<view class="container999" @touchstart="refreshStart" @touchmove="refreshMove" @touchend="refreshEnd">
		<!-- 刷新组件需搭配scroll-view使用，并在pages.json中添加 "disableScroll":true-->
		<refresh ref="refresh" @isRefresh="isRefresh"></refresh>
		<view class="nav">
			<!-- 导航栏 agents导航栏标题 -->
			<navTab ref="navTab" :tabTitle="tabTitle" @changeTab="changeTab"></navTab>
		</view>
		<!-- swiper切换 swiper-item表示一页 scroll-view表示滚动视窗 -->
		<swiper style="min-height: 100vh" :current="currentTab" @change="swiperTab">
			<swiper-item v-for="(listItem, listIndex) in list" :key="listIndex">
				<scroll-view style="height: 100%" scroll-y="true" scroll-with-animation :scroll-into-view="toView">
					<view :id="'top' + listIndex" style="width: 100%; height: 120upx">边距盒子</view>
					<view class="content">
						<view class="card" v-for="(item, index) in listItem" v-if="listItem.length > 0" :key="index">
							<view style="display: flex; justify-content: space-between; font-size: 12px; padding: 4px 5px">
								<lxCoupon 
									:isUsed="item.isUsed"
									:usageTime="item.usageTime"
									:couponName="item.couponName"
									:couponValue="item.couponValue"
									:couponThreshold="item.couponThreshold"
									:status="item.status"
									@onClick="click"
									:endTime="item.endTime"
								/>
							</view>
						</view>
					</view>
					<view class="noCard" v-if="listItem.length === 0">暂无数据</view>
					<view style="width: 100%; height: 100upx; opacity: 0">底部占位盒子</view>
				</scroll-view>
			</swiper-item>
		</swiper>
	</view>
</template>

<script>
const util = require('../../../utils/util.js');
import refresh from '../components/refresh/refresh.vue';
import navTab from '../components/navTab/navTab.vue';
import lxCoupon from '../components/lx-coupon/lx-coupon.vue';
import { getCoupon } from '../../../api/system/coupon';
export default {
	components: { refresh, navTab, lxCoupon },
	data() {
		return {
			id: this.$store.state.user.userId,
			currentPage: 'index',
			tabTitle: ['未使用', '已使用', '已过期'], //导航栏格式 --导航栏组件
			currentTab: 0, //sweiper所在页
			list: [[], [], []] //数据格式
		};
	},
	onLoad: function (options) {
		this.currentTab = options.currentTab;
	},
	onShow() {},
	onHide() {},
	mounted() {
		getCoupon().then((response) => {
			console.log(response);
			//0 已经达到开始日期，但未使用
			//1 已经使用
			//2 已经达到结束日期，未使用
			this.list[0] = response[0];
			this.list[1] = response[1];
			this.list[2] = response[2];
			this.$forceUpdate(); // 手动更新组件
		});
	},
	methods: {
		click() {
			uni.switchTab({
				url: '/pages/index'
			});
		},
		changeTab(index) {
			//切换顶部tab
			this.currentTab = index;
		},
		// swiper 滑动
		swiperTab: function (e) {
			var index = e.detail.current; //获取索引
			this.$refs.navTab.longClick(index);
		},
		// 刷新touch监听
		refreshStart(e) {
			this.$refs.refresh.refreshStart(e);
		},
		refreshMove(e) {
			this.$refs.refresh.refreshMove(e);
		},
		refreshEnd(e) {
			this.$refs.refresh.refreshEnd(e);
		},
		isRefresh() {
			setTimeout(() => {
				uni.showToast({
					icon: 'success',
					title: '刷新成功'
				});
				this.$refs.refresh.endAfter(); //刷新结束调用
			}, 1000);
		}
	}
};
</script>

<style lang="scss">
.container999 {
	width: 100vw;
	font-size: 28upx;
	min-height: 100vh;
	overflow: hidden;
	color: #6b8082;
	position: relative;
	background-color: #f6f6f6;
}
.content {
	width: 100%;
}

.card {
	width: 95%;
	height: 255rpx;
	background-color: white;
	margin: 0 auto 15upx auto;
	background: #ffffff;
	box-shadow: 0 0 5px 0 rgba(0, 0, 0, 0.1);
	border-radius: 5px;
	position: relative;
}

.noCard {
	width: 90%;
	height: 82%;
	margin: auto;
	background-color: white;
	display: flex;
	align-items: center;
	justify-content: center;
	color: #999999;
	box-shadow: 0 0 10upx 0 rgba(0, 0, 0, 0.1);
	border-radius: 10upx;
}

.nav {
	position: fixed;
	left: 0;
	top: 0;
	color: white;
	width: 100%;
	display: flex;
	flex-direction: column;
	align-items: flex-start;
	justify-content: flex-start;
	font-size: 24upx;
	background-color: #317eff;
	z-index: 996;
}

.searchInput999 {
	width: 90%;
	margin: 0 auto;
	background: white;
	border-radius: 30upx;
	display: flex;
	align-items: center;
	justify-content: center;
	height: 56upx;
}

.search999 {
	width: 32upx;
	height: 32upx;
}

.searchBox999 {
	width: 56upx;
	height: 56upx;
	display: flex;
	justify-content: center;
	align-items: center;
}

.input999 {
	color: #999;
	width: 80%;
}

page {
	background: #efefef;
}
</style>
