<template>
	<view class="mask" :class="{ show: props.modelValue }">
		<view
			class="dialog"
			:style="{
				width: props.modelValue ? getEndStyle.width + '%' : getStartStyle.width + '%',
				height: props.modelValue ? 'calc(' + getEndStyle.height + '% - var(--status-bar-height))' : getStartStyle.height + '%',
				borderRadius: props.modelValue ? getEndStyle.borderRadius : getStartStyle.borderRadius,
				top: props.modelValue ? 'calc(' + getToPoi[0] + ' + var(--status-bar-height))' : props.fromPoi[0],
				left: props.modelValue ? getToPoi[1] : props.fromPoi[1],
				opacity: props.modelValue ? 1 : 0,
				backgroundColor: props.backgroundColor,
				transitionDuration: props.duration + 'ms'
			}"
		>
			<view class="content" v-show="props.modelValue">
				<view class="title">
					<h3>{{ props.title }}</h3>
					<span class="close" @click.stop="close()">×</span>
				</view>
				<slot></slot>
			</view>
		</view>
	</view>
</template>

<script>
import { computed, onMounted, watch } from 'vue';
export default {
	props: {
		modelValue: {
			type: Boolean,
			default: false
		},
		title: {
			type: String,
			default: ''
		},
		duration: {
			type: Number,
			default: 300
		},
		fromPoi: {
			type: Array,
			default: () => ['0%', '0%']
		},
		backgroundColor: {
			type: String,
			default: '#f5f5f5'
		},
		startStyle: {
			type: Object,
			default: () => ({ width: 0, height: 0, borderRadius: '0px' })
		},
		endStyle: {
			type: Object,
			default: () => ({ width: 80, height: 80, borderRadius: '8px' })
		}
	},
	data() {
		return {
			showContent: false
		};
	},
	computed: {
		dialogStyle() {
			return {
				width: this.modelValue ? this.endStyle.width + '%' : this.startStyle.width + '%',
				height: this.modelValue ? 'calc(' + this.endStyle.height + '% - var(--status-bar-height))' : this.startStyle.height + '%',
				borderRadius: this.modelValue ? this.endStyle.borderRadius : this.startStyle.borderRadius,
				top: this.modelValue ? 'calc(' + this.getToPoi[0] + ' + var(--status-bar-height))' : this.fromPoi[0],
				left: this.modelValue ? this.getToPoi[1] : this.fromPoi[1],
				opacity: this.modelValue ? 1 : 0,
				backgroundColor: this.backgroundColor,
				transitionDuration: this.duration + 'ms'
			};
		},
		getToPoi() {
			return [(100 - this.endStyle.height) / 2 + '%', (100 - this.endStyle.width) / 2 + '%'];
		}
	},
	methods: {
		close() {
			this.$emit('update:modelValue', false);
			this.$emit('closed');
		}
	}
};
</script>

<style scoped>
.dialog {
	position: absolute;
	z-index: 999;
	overflow: hidden;
	transition-timing-function: cubic-bezier(0.69, 0.15, 0.52, 1.04);
	box-shadow: rgba(0, 0, 0, 0.5) 0px 0px 5px 0px, rgba(0, 0, 0, 0.5) 0px 0px 1px 0px;
	padding: 16rpx 32rpx;
	box-sizing: border-box;
}

.title {
	display: flex;
	position: relative;
	justify-content: space-between;
	align-items: center;
	padding-bottom: 32rpx;
}

.title > h2 {
	letter-spacing: 4rpx;
	color: #333;
	position: relative;
}
.title > .close {
	color: #333;
	font-size: 64rpx;
}

.mask {
	padding-top: var(--status-bar-height);
	position: absolute;
	top: 0;
	width: 100%;
	height: 100%;
	background: #00000099;
	pointer-events: none;
	opacity: 0;
	transition: 0.3s ease-in-out;
}
.mask.show {
	pointer-events: all;
	opacity: 1;
}
</style>
