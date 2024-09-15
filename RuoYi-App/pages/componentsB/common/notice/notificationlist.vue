<template>
	<view class="page-container">
		<scroll-view class="scroll-list msg-list-item" scroll-y="true">

			<u-list @scrolltolower="scrolltolower">

				<u-list-item v-for="(item, index) in noticeList" :key="index">
					<view class="notice-item-container" @click="handleNoticeItemClick(item)">
						<view class="notice-item-header">
							<text>通知编号: {{item.noticeId}}</text>
						</view>

						<view class="notice-item-body">
							<text>{{item.title}}</text>
						</view>

						<view class="notice-item-footer">
							<uni-dateformat :date="item.createTime" :threshold="[60000, 3600000]"
								format="yyyy年MM月dd日"></uni-dateformat>
						</view>
					</view>
				</u-list-item>

			</u-list>

		</scroll-view>
	</view>
</template>
<script>
	import {
		listNotice
	} from '@/api/home';

	export default {
		data() {
			return {
				total: 0,
				noticeList: [],
			};
		},
		onLoad() {
			uni.showLoading({
				title: "数据加载中..."
			})
			listNotice().then(response => {
				if (response.code == 200) {
					response.data.forEach(item => {
						this.noticeList.push({
							noticeId: item.noticeId,
							createTime: item.createTime,
							title: item.noticeTitle,
						})
					})
					uni.hideLoading()
					this.total = this.noticeList.length
				} else {
					uni.hideLoading()
					uni.showToast({
						icon: "exception",
						title: "数据拉取失败"
					})
				}

			}).catch(error => {
				uni.hideLoading()
				uni.showToast({
					icon: 'error',
					title: "通知加载失败"
				})
			})
		},
		methods: {
			//通知列表点击事件
			handleNoticeItemClick(item) {
				uni.navigateTo({
					url:"/pages/componentsB/common/notice/notification?noticeId=" + item.noticeId
				})
			},

		}
	};
</script>
<style lang="scss">
	@import '@/uni.scss';

	//通知列表项目
	.notice-item-container {
		background-color: #fafafa;
		border-bottom: #ededed solid 2rpx;
		width: 100%;
		margin: 0rpx 16rpx;
		padding: 8rpx;

		//通知编号header
		.notice-item-header {
			text-align: left;
			padding: 8rpx;
			font-size: small;
		}

		//通知体内容标题body
		.notice-item-body {
			padding-left: 8rpx;
			font-size: medium;
			font-weight: bold;
			white-space: nowrap;
			text-overflow: ellipsis;
			overflow: hidden;
		}

		//通知编号底部创建时间footer
		.notice-item-footer {
			font-size: smaller;
			overflow: right;
			padding: 8rpx 16rpx;
			float: right;
		}
	}
</style>