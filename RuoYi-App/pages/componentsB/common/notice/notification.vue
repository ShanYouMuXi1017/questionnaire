<template>
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
</template>
<script>
	import {
		getLastAnnouncement,
		getNotification
	} from '@/api/home';
	import {
		baseUrl
	} from '@/config';
	export default {
		data() {
			return {
				title: "通知内容",
				content: "通知内容加载中 . . . ",
				createTime: ""
			};
		},
		onLoad(option) {
			// 页面加载同步获取通知详细
			getNotification(Number(option.noticeId)).then(response => {
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
			})
		},
		methods: {

		}
	};
</script>
<style lang="scss">
	@import url("@/pages/componentsB/static/scss/quill.bubble.css");
	@import url("@/pages/componentsB/static/scss/quill.core.css");
	@import url("@/pages/componentsB/static/scss/quill.snow.css");

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