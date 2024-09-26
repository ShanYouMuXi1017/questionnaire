<template>
	<view class="empty">
		<image class="emptyImg" src="@/static/empty.png" mode="widthFix"></image>
		<view class="emptyText">{{msg}}</view>
	</view>
</template>

<script>
	export default {
		props: {
			msg: {
				type: String,
				default: "暂无数据"
			}
		},
		data() {
			return {

			};
		}
	}
</script>

<style scoped lang="scss">
	.empty {
		width: 100%;

		.emptyImg {
			display: block;
			width: 160px;
			margin: 60px auto 0;
		}

		.emptyText {
			font-size: 14px;
			font-weight: 400;
			color: #333;
			line-height: 1.5;
			text-align: center;
			padding: 10px 0;
		}
	}
</style>
