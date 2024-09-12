<template>
	<uni-popup ref="coupon_pop" :mask-click="false">
		<view class="coupon_pop" :class="'theme' + theme">
			<image class="bg" :src="'/uni_modules/liiy-coupon-popup/static/cou_bg' + theme + '.png'"></image>
			<view class="title">{{ title }}</view>
			<view class="content">{{ content }}</view>
			<view class="con_box">
				<view class="box_a">
					<view class="box_a_a">
						<text>￥</text>
						<text>{{ couponValue }}</text>
					</view>
					<text v-if="couponThreshold" class="box_a_b">满{{ couponThreshold }}可用</text>
				</view>
				<view class="box_b"></view>
				<view class="box_c">
					<view class="box_c_1">{{ couponName }}</view>
					<view class="box_c_2">有效期 {{ endTime ? endTime : '无限制' }}</view>
				</view>
			</view>
			<view class="note">系统为你展示当前最大面值的优惠券</view>
			<view class="note2">领取后可在我的优惠券中查看所有优惠券</view>
			<view class="btn" @click="get">
				<image class="bg" :src="'/uni_modules/liiy-coupon-popup/static/cou_btn_' + theme + '.png'"></image>
				<text>立即领取</text>
			</view>
		</view>
	</uni-popup>
</template>

<script>
export default {
	name: 'LiiyCouponPop',
	data() {
		return {
			show: false
		};
	},
	props: {
		theme: { type: String, required: false, default: '2' },
		title: { type: String, required: false, default: '' },
		content: { type: String, required: true, default: '' },
		couponValue: { type: Number, required: true ,default:0},
		couponThreshold: { type: Number, required: false, default: 0 },
		couponName: { type: String, required: true },
		endTime: { type: String, required: false, default: '无限制' }
	},
	watch: {
		show(val) {
			if (val) {
				this.$refs.coupon_pop.open();
			} else {
				this.$refs.coupon_pop.close();
			}
		}
	},
	methods: {
		get() {
			this.$emit('getClick');
		},
	}
};
</script>
 
<style scoped>
.theme2 {
	width: 695rpx;
	height: 707rpx;
}
.theme2 .btn {
	width: 297rpx;
	height: 80rpx;
	position: relative;
	margin-top: 150rpx;
	display: flex;
	justify-content: center;
}
.theme2 .btn text {
	font-size: 36rpx;
	font-weight: 500;
	color: #c00e30;
	margin-top: 14rpx;
}
.theme2 .title {
	margin-top: 6rpx;
	color: #ffffff;
	font-size: 28rpx;
}
.theme2 .content {
	margin-top: 24rpx;
	color: #a66f12;
	font-size: 40rpx;
}
.theme2 .con_box {
	margin-top: 40rpx;
	width: 452rpx;
	border-radius: 8rpx;
	height: 134rpx;
	box-shadow: 0rpx 4rpx 8rpx 0rpx rgba(0, 0, 0, 0.16);
	background-color: #fff;
	display: flex;
}
.theme2 .note {
	margin-top: 40rpx;
	color: #d91338;
	font-size: 26rpx;
}
.theme2 .note2 {
	margin-top: 18rpx;
	color: #d91338;
	font-size: 26rpx;
}
.theme2 .box_a {
	width: 160rpx;
	display: flex;
	flex-direction: column;
	justify-content: center;
	align-items: center;
	color: #fb4b4e;
	background-image: radial-gradient(circle at right top, #faebd4, #faebd4 10px, transparent 10px),
		radial-gradient(circle at right bottom, #f8e0b4, #f8e0b4 10px, transparent 10px);
}
.theme2 .box_a_a {
	font-size: 32rpx;
	font-weight: bold;
}
.theme2 .box_a_a text:nth-child(1) {
	font-size: 24rpx;
}
.theme2 .box_a_b {
	font-size: 22rpx;
	margin-top: 4rpx;
}
.theme2 .box_b {
	height: 80rpx;
	border-left: 1px dashed #fb4b4e;
	margin-top: 28rpx;
	margin-left: -1rpx;
}
.theme2 .box_c {
	width: 290rpx;
	display: flex;
	flex-direction: column;
	justify-content: center;
	color: #fb4b4e;
	padding: 0 24rpx;
	background-image: radial-gradient(circle at left top, #faebd4, #faebd4 10px, transparent 10px), radial-gradient(circle at left bottom, #f8e0b4, #f8e0b4 10px, transparent 10px);
}
.theme2 .box_c_1 {
	color: #000000;
	font-size: 27rpx;
}
.theme2 .box_c_2 {
	color: #999999;
	font-size: 22rpx;
	margin-top: 14rpx;
	padding-top: 14rpx;
	border-top: 1px dashed #999;
}

.coupon_pop {
	position: relative;
	margin: 0 auto;
	display: flex;
	flex-direction: column;
	align-items: center;
}
.coupon_pop image {
	width: 100%;
	height: 100%;
	position: absolute;
	z-index: -1;
}
</style>
