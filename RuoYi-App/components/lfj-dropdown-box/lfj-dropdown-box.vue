<template>
	<view class="uni-stat__select" :class="{ 'uni-stat__select_disabled': disabled }">
		<view class="uni-stat-box">
			<view class="uni-select">
				<view class="uni-select__input-box" :style="{ height: multiple ? '100%' : '25px' }" @click="toggleSelector">
					<!-- 多选不搜索 -->
					<view class="" style="display: flex; flex-wrap: wrap; width: 100%" v-if="multiple && current.length > 0">
						<view class="tag-calss" v-for="(item, index) in collapseTags ? current.slice(0, collapseTagsNum) : current" :key="item[map.value]">
							<span class="text">{{ item[map.text] }}</span>
							<view class="" @click.stop="delItem(item)">
								<uni-icons type="clear" style="margin-left: 4px" color="#c0c4cc" />
							</view>
						</view>
						<view v-if="current.length > collapseTagsNum && collapseTags" class="tag-calss">
							<span class="text">+{{ current.length - collapseTagsNum }}</span>
						</view>
						<input
							v-if="filterable && !disabled"
							@input="inputChange"
							class="uni-select__input-text"
							type="text"
							style="font-size: 12px; height: 52rpx; margin-left: 6px; width: auto"
							confirm-type="search"
							@confirm="confirm"
							v-model="filterInput"
						/>
					</view>

					<view v-else-if="current && current.length > 0 && !showSelector" class="uni-select__input-text">
						{{ current }}
					</view>

					<input
						v-else-if="filterable"
						:focus="isFocus"
						@input="inputChange"
						:disabled="disabled"
						confirm-type="search"
						@confirm="confirm"
						class="uni-select__input-text"
						type="text"
						style="font-size: 12px; position: absolute; z-index: 1"
						:placeholder="placeholderOld"
						v-model="filterInput"
					/>

					<view v-else class="uni-select__input-text uni-select__input-placeholder">{{ placeholderOld }}</view>
					<uni-icons
						v-if="(current.length > 0 && clear && !disabled) || (currentArr.length > 0 && clear && !disabled)"
						type="clear"
						color="#c0c4cc"
						size="24"
						style="position: absolute; right: 0"
						@click="clearVal"
					/>
					<uni-icons
						style="right: 0; position: absolute; top: 50%; transform: translate(0, -50%)"
						v-else
						:type="showSelector ? 'top' : 'bottom'"
						size="14"
						color="#999"
					/>
				</view>
			</view>
		</view>
		<view class="uni-select--mask" v-if="showSelector" @click="toggleSelector" />
		<view class="uni-select__selector" :class="isDown ? 'uni-select__selector__down' : 'uni-select__selector__upwards'" v-if="showSelector">
			<view class="uni-popper__arrow"></view>
			<view class="popup__selector-scroll">
				<view class="popup-scroll-view">
					<scroll-view scroll-y="true" class="uni-select__selector-scroll" @scrolltolower="scrolltolower">
						<view class="uni-select__selector-empty" style="margin: auto" v-if="filterMixinDatacomResData.length === 0">
							<span style="display: inline-block; width: 100%; text-align: center">{{ emptyTips }}</span>
						</view>
						<view
							v-else
							:style="{ fontWeight: multiple && currentArr.includes(item[map.value]) ? 'bold' : '' }"
							class="scroll_item"
							:class="{ scroll_item_disable: item.disable }"
							v-for="(item, index) in filterMixinDatacomResData"
							:key="index"
							@click="change(item)"
						>
							<span class="scroll_item_text" :style="{ color: multiple && currentArr.includes(item[map.value]) ? '#007aff' : '' }">{{ formatItemName(item) }}</span>
							<view class="scroll_item_right">
								<span v-if="rightTextShow" :style="{ color: multiple && currentArr.includes(item[map.value]) ? 'rgb(204, 204, 204)' : '' }">
									{{ formatItemRightName(item) }}
								</span>
								<!-- <uni-icons type="checkmarkempty" color="#007aff" /> -->
								<uni-icons v-if="multiple && currentArr.includes(item[map.value])" type="checkmarkempty" color="#007aff" />
							</view>
						</view>
						<!-- <uni-load-more v-if="filterMixinDatacomResData.length !== 0 && dropDownLoading" :status="loadStatus"></uni-load-more> -->
						<view class="loading_box" v-if="filterMixinDatacomResData.length !== 0 && dropDownLoading">
							<view class="wave-loading" v-if="loadStatus == 'loading'">
								<span style="--time: 1">l</span>
								<span style="--time: 2">o</span>
								<span style="--time: 3">a</span>
								<span style="--time: 4">d</span>
								<span style="--time: 5">i</span>
								<span style="--time: 6">n</span>
								<span style="--time: 7">g</span>
								<span style="--time: 8">.</span>
								<span style="--time: 9">.</span>
								<span style="--time: 10">.</span>
							</view>
							<span v-else-if="loadStatus == 'more'">下拉加载更多</span>
							<span v-else style="color: #999">无更多数据了</span>
						</view>
					</scroll-view>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
/**
 * DataChecklist 数据选择器
 * @description 通过数据渲染的下拉框组件
 * @tutorial https://uniapp.dcloud.io/component/uniui/uni-data-select
 * @property {String} collapseTagsNum 多选时选中值按文字的形式展示的数量
 * @property {String} collapseTags 多选时是否将选中值按文字的形式展示
 * @property {String} rightSideText 作为 value 唯一标识的键名
 * @property {Array} multiple 是否多选
 * @property {Array} filterable 是否开启搜索
 * @property {Array} localdata 本地数据 ，格式 [{text:'',value:''}]
 * @property {Array} map 本地数据 ，格式 {text: 'text', value: 'value', rightText: 'rightText', disable: 'disable'}
 * @property {Boolean} clear 是否可以清空已选项
 * @property {Boolean} emptyText 没有数据时显示的文字 ，本地数据无效
 * @property {String} placeholder 输入框的提示文字
 * @property {Boolean} disabled 是否禁用
 * @property {Boolean} dropDownLoading 是否开启下拉加载
 * @property {Boolean} rightTextShow 是否显示右侧文字
 * @property {String} loadStatus 加载更多状态
 * @event {Function} change  选中发生变化触发
 * @event {Function} lower  触底事件
 */
export default {
	name: 'lfj-uni-data-select',
	props: {
		// #ifndef VUE3
		value: {
			type: [String, Number, Array],
			default: ''
		},
		// #endif
		// #ifdef VUE3
		modelValue: {
			type: [String, Number, Array],
			default: ''
		},
		// #endif
		dropDownLoading: {
			type: Boolean,
			default: false
		},
		localdata: {
			type: Array,
			default() {
				return [];
			}
		},
		emptyTips: {
			type: String,
			default: '无选项'
		},
		placeholder: {
			type: String,
			default: '请选择'
		},
		collapseTagsNum: {
			type: Number,
			default: 1
		},
		collapseTags: {
			type: Boolean,
			default: false
		},
		disabled: {
			type: Boolean,
			default: false
		},
		clear: {
			type: Boolean,
			default: true
		},
		filterable: {
			type: Boolean,
			default: false
		},
		multiple: {
			type: Boolean,
			default: false
		},
		rightTextShow: {
			type: Boolean,
			default: false
		},
		map: {
			type: Object,
			default() {
				return { text: 'text', value: 'value', rightText: 'rightText', disabled: 'disabled' };
			}
		},
		loadStatus: {
			type: String,
			default: 'more'
		},
		// 格式化输出 用法 field="_id as value, version as text, uni_platform as label" format="{label} - {text}"
		format: {
			type: String,
			default: ''
		}
	},
	data() {
		return {
			windowHeight: 0,
			showSelector: false,
			mixinDatacomResData: [],
			isFocus: false,
			placeholderOld: '',
			filterInput: '',
			// 单选
			current: [],
			// 多选数据
			currentArr: [],
			isDown: true //下拉框是否朝下
		};
	},
	watch: {
		localdata: {
			immediate: true,
			handler(val, old) {
				if (Array.isArray(val) && old !== val) {
					console.log('新数据');
					this.mixinDatacomResData = val || [];
				}
			}
		},
		valueCom: {
			handler(newVal, oldVal) {
				this.initDefVal();
			},
			deep: true,
			immediate: true
		},
		mixinDatacomResData: {
			immediate: true,
			handler(val) {
				if (val.length) {
					this.initDefVal();
				}
			}
		}
	},
	created() {
		this.windowHeight = uni.getSystemInfoSync().windowHeight;
		this.placeholderOld = this.placeholder;
		if (this.multiple) {
			// #ifndef VUE3
			this.currentArr = this.value || [];
			// #endif
			// #ifdef VUE3
			this.currentArr = this.modelValue || [];
			// #endif
			if (this.current.length > 0) {
				this.current = [];
			}
			// #ifndef VUE3
			if (this.value && this.value.length > 0 && this.filterMixinDatacomResData.length > 0) {
				this.current = this.value.map((item) => {
					let current = this.mixinDatacomResData.find((e) => e[this.map.value] == item);
					return {
						...current
					};
				});
			}
			// #endif
			// #ifdef VUE3
			if (this.modelValue && this.modelValue.length > 0 && this.filterMixinDatacomResData.length > 0) {
				this.current = this.modelValue.map((item) => {
					let current = this.mixinDatacomResData.find((e) => e[this.map.value] == item);
					return {
						...current
					};
				});
			}
			// #endif
		} else {
			// #ifndef VUE3
			if (this.value || this.value == 0) {
				this.current = this.formatItemName(this.filterMixinDatacomResData.find((e) => e[this.map.value] == this.value));
			}
			// #endif
			// #ifdef VUE3
			if (this.modelValue || this.value == 0) {
				this.current = this.formatItemName(this.filterMixinDatacomResData.find((e) => e[this.map.value] == this.modelValue));
			}
			// #endif
		}

		this.debounceGet = this.debounce(() => {
			this.query();
		}, 300);

		if (this.collection && !this.localdata.length) {
			this.debounceGet();
		}
	},
	computed: {
		filterMixinDatacomResData() {
			if (this.filterable && this.filterInput) {
				return this.mixinDatacomResData.filter((e) => e[this.map.text].includes(this.filterInput));
			} else {
				return this.mixinDatacomResData;
			}
		},
		typePlaceholder() {
			const text = {
				'opendb-stat-app-versions': '版本',
				'opendb-app-channels': '渠道',
				'opendb-app-list': '应用'
			};
			const common = this.placeholder;
			const placeholder = text[this.collection];
			return placeholder ? common + placeholder : common;
		},
		valueCom() {
			// #ifdef VUE3
			return this.modelValue;
			// #endif
			// #ifndef VUE3
			return this.value;
			// #endif
		}
	},
	methods: {
		getIsDown() {
			const query = uni.createSelectorQuery().in(this);
			const _this = this;
			query
				.select('.uni-stat-box')
				.boundingClientRect((data) => {
					if (_this.windowHeight - data.top - data.height > 220) {
						_this.isDown = true;
					} else {
						_this.isDown = false;
					}
				})
				.exec();
		},
		// 执行数据库查询
		query() {
			this.mixinDatacomEasyGet();
		},
		// 监听查询条件变更事件
		onMixinDatacomPropsChange() {
			if (this.collection) {
				this.debounceGet();
			}
		},
		debounce(fn, time = 100) {
			let timer = null;
			return function (...args) {
				if (timer) clearTimeout(timer);
				timer = setTimeout(() => {
					fn.apply(this, args);
				}, time);
			};
		},
		confirm(e) {
			this.$emit('confirm', e);
		},
		toggleSelector() {
			if (this.disabled) {
				return;
			}
			this.getIsDown();
			this.showSelector = !this.showSelector;
			this.isFocus = this.showSelector;
			if (this.filterable && this.current && this.showSelector) {
				if (!this.multiple) {
					this.placeholderOld = this.current;
					// this.filterInput = ""
				}
			} else if (this.filterable && !this.current && !this.showSelector) {
				if (this.placeholderOld != this.placeholder) {
					if (!this.multiple) {
						this.current = this.placeholderOld;
					}
				}
			}

			this.filterInput = '';
		},
		initDefVal() {
			let defValue = '';
			if ((this.valueCom || this.valueCom === 0) && !this.isDisabled(this.valueCom)) {
				defValue = this.valueCom;
			} else {
				let strogeValue;
				if (this.collection) {
					strogeValue = this.getCache();
				}
				if (strogeValue || strogeValue === 0) {
					defValue = strogeValue;
				} else {
					let defItem = '';
					if (this.defItem > 0 && this.defItem <= this.mixinDatacomResData.length) {
						defItem = this.mixinDatacomResData[this.defItem - 1][this.dataValue];
					}
					defValue = defItem;
				}
				if (defValue || defValue === 0) {
					this.emit(defValue);
				}
			}
			if (this.multiple) {
				const mixinDatacomResData = this.mixinDatacomResData || [];
				if (!defValue) defValue = [];
				this.current = defValue.map((item) => {
					const current = mixinDatacomResData.find((e) => {
						return e[this.map.value] == item;
					});
					return {
						...current
					};
				});
				this.currentArr = this.current.map((e) => e[this.map.value]);
				if (defValue.length < 1) {
					this.currentArr = [];
				}
			} else {
				const def = this.mixinDatacomResData.find((item) => item[this.map.value] === defValue);
				this.current = def ? this.formatItemName(def) : '';
			}
		},
		/**
		 * @param {[String, Number]} value
		 * 判断用户给的 value 是否同时为禁用状态
		 */
		isDisabled(value) {
			let isDisabled = false;
			this.mixinDatacomResData.forEach((item) => {
				if (item[this.dataValue] === value) {
					isDisabled = item.disable;
				}
			});
			return isDisabled;
		},
		inputChange(e) {
			this.$emit('inputChange', e.detail.value);
		},
		scrolltolower(e) {
			if (e) {
				this.$emit('lower', this.scrolltolower());
			}
		},
		delItem(item) {
			if (this.disabled) {
				return;
			}
			if (this.currentArr.includes(item[this.map.value])) {
				let index = this.current.findIndex((e) => {
					return e[this.map.value] == item[this.map.value];
				});
				this.current.splice(index, 1);
				this.currentArr.splice(index, 1);
				this.emit(this.current);
			}
		},
		clearVal() {
			if (this.disabled) {
				return;
			}
			if (this.multiple) {
				this.current = [];
				this.currentArr = [];
				this.emit([]);
			} else {
				this.current = '';
				this.currentArr = [];
				this.emit('');
			}
			if (this.collection) {
				this.removeCache();
			}
			this.placeholderOld = this.placeholder;
			this.filterInput = '';
		},
		change(item) {
			// 是否禁用
			if (!item.disabled) {
				// 是否多选
				if (this.multiple) {
					if (!this.current) {
						this.current = [];
					}
					if (!this.currentArr) {
						this.currentArr = [];
					}
					if (this.currentArr.includes(item[this.map.value])) {
						let index = this.current.findIndex((e) => {
							return e[this.map.value] == item[this.map.value];
						});
						this.current.splice(index, 1);
						this.currentArr.splice(index, 1);
						this.emit(this.current);
					} else {
						this.current.push(item);
						this.currentArr.push(item[this.map.value]);
						this.emit(this.current);
					}
					this.filterInput = '';
				} else {
					// 单选
					this.showSelector = false;
					this.current = this.formatItemName(item);
					if (this.filterable) {
						this.filterInput = item[this.map.text];
					}
					this.emit(item[this.map.value]);
				}
			}
		},
		emit(val) {
			// 是否多选
			if (this.multiple) {
				this.$emit('input', this.currentArr);
				this.$emit('update:modelValue', this.currentArr);
				const currentArr = this.mixinDatacomResData.filter((item) => this.currentArr.includes(item[this.map.value]));
				// console.log(currentArr, '多选数据');
				this.$emit('change', currentArr);
			} else {
				this.$emit('input', val);
				this.$emit('update:modelValue', val);
				const current = this.mixinDatacomResData.find((item) => val == item[this.map.value]);
				// console.log(current, '单选数据');
				this.$emit('change', current);
			}
			if (this.collection) {
				this.setCache(val);
			}
		},
		formatItemRightName(item) {
			if (!item) {
				return ' ';
			}
			let text = item[this.map.rightText];
			let value = item[this.map.value];
			let { channel_code } = item;
			channel_code = channel_code ? `(${channel_code})` : '';
			if (this.format) {
				// 格式化输出
				let str = '';
				str = this.format;
				for (let key in item) {
					str = str.replace(new RegExp(`{${key}}`, 'g'), item[key]);
				}
				return str;
			} else {
				return text ? text : `${channel_code}`;
			}
		},
		formatItemName(item) {
			if (!item) {
				return '';
			}
			let text = item[this.map.text];
			let value = item[this.map.value];
			let { channel_code } = item;
			channel_code = channel_code ? `(${channel_code})` : '';
			if (this.format) {
				// 格式化输出
				let str = '';
				str = this.format;
				for (let key in item) {
					str = str.replace(new RegExp(`{${key}}`, 'g'), item[key]);
				}
				return str;
			} else {
				return text ? text : `未命名${channel_code}`;
			}
		},
		// 获取当前加载的数据
		getLoadData() {
			return this.mixinDatacomResData;
		},
		// 获取当前缓存key
		getCurrentCacheKey() {
			return this.collection;
		},
		selClick() {
			this.showSelector = !this.showSelector;
		}
	}
};
</script>

<style lang="scss" scoped>
$uni-box-shadow: 0 1rpx 8rpx 1rpx rgba(32, 34, 36, 0.2);
$uni-base-color: #6a6a6a !default;
$uni-main-color: #333 !default;
$uni-secondary-color: #909399 !default;
$uni-border-3: #e5e5e5;

.uni-stat__select_disabled {
	background-color: #f5f7fa;
	cursor: not-allowed;
}
.uni-stat__select {
	position: relative;
	.uni-stat-box {
		border: 1px solid rgb(229, 229, 229);
		border-radius: 4px;
		padding: 10rpx;
		min-height: 25px;
		display: flex;
		align-items: center;
		background-color: transparent;
		.uni-select {
			width: 100%;
			height: 100%;
			display: flex;
			align-items: center;
			font-size: 12px;
			position: relative;
			.uni-select__input-text {
				// width: 280px;
				width: 90%;
				color: $uni-main-color;
				white-space: nowrap;
				text-overflow: ellipsis;
				-o-text-overflow: ellipsis;
				overflow: hidden;
			}

			.uni-select__input-box {
				width: 100%;
				height: 100%;
				position: relative;
				/* #ifndef APP-NVUE */
				display: flex;
				/* #endif */
				flex: 1;
				flex-direction: row;
				align-items: center;
				.tag-calss {
					font-family: Helvetica Neue, Helvetica, PingFang SC, Hiragino Sans GB, Microsoft YaHei, SimSun, sans-serif;
					font-weight: 400;
					-webkit-font-smoothing: antialiased;
					-webkit-tap-highlight-color: transparent;
					font-size: 12px;
					border: 1px solid #d9ecff;
					border-radius: 4px;
					white-space: nowrap;
					height: 24px;
					padding: 0 4px 0px 8px;
					line-height: 22px;
					box-sizing: border-box;
					margin: 2px 0 2px 6px;
					display: flex;
					max-width: 100%;
					align-items: center;
					background-color: #f4f4f5;
					border-color: #e9e9eb;
					color: #909399;

					.text {
						font-family: Helvetica Neue, Helvetica, PingFang SC, Hiragino Sans GB, Microsoft YaHei, SimSun, sans-serif;
						font-weight: 400;
						-webkit-font-smoothing: antialiased;
						-webkit-tap-highlight-color: transparent;
						font-size: 12px;
						white-space: nowrap;
						line-height: 22px;
						color: #909399;
						overflow: hidden;
						text-overflow: ellipsis;
					}
				}

				.uni-select__input-placeholder {
					color: $uni-base-color;
					font-size: 12px;
				}
			}
		}
	}
	.uni-select__selector {
		/* #ifndef APP-NVUE */
		box-sizing: border-box;
		/* #endif */
		position: absolute;
		left: 0;
		width: 100%;
		border-radius: 6px;
		z-index: 3;
		.popup__selector-scroll {
			width: 100%;
			// height: 200px;
			display: block;
			max-height: 208px;
			background-color: #ffffff;
			box-shadow: $uni-box-shadow;
			box-sizing: border-box;
			border-radius: 6px;
			overflow: hidden;
			padding: 4px 0;
			.popup-scroll-view {
				width: 100%;
				.uni-select__selector-scroll {
					/* #ifndef APP-NVUE */
					max-height: 200px;
					box-sizing: border-box;
					/* #endif */
				}
				.scroll_item_disable {
					opacity: 0.3;
					pointer-events: none;
				}
				.scroll_item {
					padding: 0px 10px;
					line-height: 35px;
					font-size: 14px;
					text-align: center;
					display: flex;
					justify-content: space-between;
					align-items: center;
					&:hover {
						background-color: #f9f9f9;
					}

					.scroll_item_right {
						color: #ccc;
					}

					.scroll_item_text {
						display: inline-block;
						max-width: 100%;
						white-space: nowrap;
						overflow: hidden;
						text-overflow: ellipsis;
					}
				}
			}
		}
	}
}

.uni-select__selector__down {
	top: calc(100% + 12px);

	.uni-popper__arrow {
		transform: rotateX(0deg);
		top: -6px;
	}
}

.uni-select__selector__upwards {
	bottom: calc(100% + 12px);

	.uni-popper__arrow {
		transform: rotateX(180deg);
		bottom: -6px;
	}
}

/* picker 弹出层通用的指示小三角 */
.uni-popper__arrow,
.uni-popper__arrow::after {
	position: absolute;
	display: block;
	width: 0;
	height: 0;
	border-color: transparent;
	border-style: solid;
	border-width: 6px;
}

.uni-popper__arrow {
	filter: drop-shadow(0 2px 12px rgba(0, 0, 0, 0.03));

	left: 10%;
	margin-right: 3px;
	border-top-width: 0;
	border-bottom-color: #ebeef5;
}

.uni-popper__arrow::after {
	content: ' ';
	top: 1px;
	margin-left: -6px;
	border-top-width: 0;
	border-bottom-color: #fff;
}

// .uni-select__selector {
// 	/* #ifndef APP-NVUE */
// 	box-sizing: border-box;
// 	/* #endif */
// 	position: absolute;

// 	left: 0;
// 	width: 100%;
// 	background-color: #ffffff;
// 	border: 1px solid #ebeef5;
// 	border-radius: 6px;
// 	box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
// 	z-index: 3;
// 	padding: 4px 0;
// }

.loading_box {
	display: flex;
	justify-content: center;
	padding: 20rpx 0;
	color: #999;

	.wave-loading {
		span {
			display: inline-block;
			font-size: 30rpx;
			font-weight: bold;
			font-family: 'Courier New', Courier, monospace;
			animation: wave-loading 1s ease-in-out infinite;
			animation-delay: calc(0.1s * var(--time));
		}
	}

	@keyframes wave-loading {
		0% {
			transform: translateY(0rpx);
		}

		25% {
			transform: translateY(-20rpx);
		}

		50%,
		100% {
			transform: translateY(0rpx);
		}
	}
}

.uni-select--mask {
	position: fixed;
	top: 0;
	bottom: 0;
	right: 0;
	left: 0;
	z-index: 2;
}
</style>
