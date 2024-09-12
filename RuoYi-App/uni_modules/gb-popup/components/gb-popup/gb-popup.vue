<template>
	<view class="men-ban box" v-if="isShow" @touchmove.stop.prevent="">
		<view class="box_next">
			<view class="box_next_title">
				<text class="box_next_title_z">{{ title }}</text>
			</view>
			<slot></slot>
			<view class="box_next_but_d" v-if="isBut">
				<view @click="show" class="box_next_but_d_but">
					<text class="box_next_but_d_but_text">{{ nName }}</text>
				</view>
			</view>
			<view class="box_next_but_s" v-else>
				<view class="box_next_but_s_lbut" @click="lEvent">
					<text class="box_next_but_d_but_text">{{ lName }}</text>
				</view>
				<view @click="rEvent" class="box_next_but_s_rbut" hover-class="zcolor-while1">
					<text class="box_next_but_d_but_text">{{ rName }}</text>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
export default {
	props: {
		title: {
			type: String,
			default: '温馨提示'
		},
		isShow: {
			type: Boolean,
			default: true
		},
		nName: String,
		lName: String,
		rName: String,
		isBut: {
			type: Boolean,
			default: true
		}
	},
	methods: {
		show() {
			this.$emit('show');
		},
		lEvent() {
			this.$emit('lEvent');
		},
		rEvent() {
			this.$emit('rEvent');
		}
	}
};
</script>

<style scoped>
.men-ban {
	position: fixed;
	bottom: 0;
	right: 0;
	left: 0;
	top: 0;
	background-color: rgba(0, 0, 0, 0.5);
}

.box {
	/* #ifndef APP-NVUE */
	display: flex;
	/* #endif */

	flex-direction: row;
	justify-content: center;
	align-items: center;
}

.box_next {
	background-color: #ffffff;
	width: 550rpx;
	border-radius: 30rpx;
}

.box_next_title {
	padding: 30rpx 30rpx 0 30rpx;
}

.box_next_title_z {
	font-size: 31rpx;
	font-weight: bold;
}

.box_next_but_d {
	width: 550rpx;
	/* #ifndef APP-NVUE */
	display: flex;
	/* #endif */

	flex-direction: row;
	justify-content: center;
	align-items: center;
	height: 130rpx;
}

.box_next_but_d_but {
	width: 500rpx;

	/* #ifndef APP-NVUE */
	display: flex;
	/* #endif */

	flex-direction: row;
	justify-content: center;
	align-items: center;
	height: 80rpx;
	border-radius: 50rpx;
	background-color: #fee610;
}

.box_next_but_d_but_text {
	font-size: 30rpx;
	font-weight: bold;
}

.box_next_but_s {
	width: 550rpx;
	/* #ifndef APP-NVUE */
	display: flex;
	/* #endif */

	flex-direction: row;
	justify-content: space-around;
	align-items: center;
	height: 130rpx;
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
