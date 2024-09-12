##简单好用  真的爱谁谁   就是好用  我自己都用着上瘾的



##步骤

1.导入

2.使用示例（详细的可导入示例项目进行体验和观看）
```html
<template>
	<view class="">
	<!-- 弹窗1 -->
		<gb-popup :isShow="isShow" nName="我知道了" :isBut="true" lName="取消" @show="show" title="温馨提示">
			<view style="padding: 30rpx;">
				<text style="font-size: 28rpx; color: #1a1a1a;">我就是要提示的内容</text>
			</view>
		</gb-popup>
		<!-- 弹窗2 -->
		<gb-popup :isShow="isShow1" rName="立即复制" :isBut="false" lName="取消" @lEvent="show1" @rEvent="copyData"
			title="识别内容">
			<view style="padding: 30rpx;">
				<text style="font-size: 28rpx; color: #1a1a1a;">我就是要复制的内容</text>
			</view>
		</gb-popup>
        <!-- 演示按钮 -->
		<view class="box_next_but_s">
			<view class="box_next_but_s_lbut" @click="show()">
				<text class="box_next_but_d_but_text">单按钮弹窗</text>
			</view>
			<view @click="show1()" class="box_next_but_s_rbut" hover-class="zcolor-while1">
				<text class="box_next_but_d_but_text">双按钮弹窗</text>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				isShow: false,
				isShow1: false
			}
		},
		onLoad() {
			
		},
		methods: {
			show() {
				this.isShow = !this.isShow
			},
			show1() {
				this.isShow1 = !this.isShow1
			},
			copyData() {
				uni.showToast({
					title: '复制成功'
				})
				this.show1()
			}
		}
	}
</script>

<style>
	.box_next_but_d_but_text {
		font-size: 30rpx;
		font-weight: bold;
	}


	.box_next_but_s {
		
		width: 750rpx;
		/* #ifndef APP-NVUE */
		display: flex;
		/* #endif */

		flex-direction: row;
		justify-content: space-around;
		align-items: center;
		height: 100vh;
	}

	.box_next_but_s_lbut {
		width: 200rpx;
		background-color: #f1f1f1;
		/* #ifndef APP-NVUE */
		display: flex;
		/* #endif */

		flex-direction: row;
		justify-content: center;
		align-items: center;
		height: 80rpx;
		border-radius: 50rpx;
	}

	.box_next_but_s_rbut {
		width: 200rpx;
		background-color: #fee610;
		/* #ifndef APP-NVUE */
		display: flex;
		/* #endif */

		flex-direction: row;
		justify-content: center;
		align-items: center;
		height: 80rpx;
		border-radius: 50rpx;
	}
</style>
```