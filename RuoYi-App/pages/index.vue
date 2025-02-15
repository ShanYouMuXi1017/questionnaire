<template>
	<view>
		<view class="x-panel-top">
			<view class="user-hint" style="text-align: center">
				<text style="color: #555; font-size: 16px;font-weight:700">请选择你骑行过的路线进行打分吧！</text>
			</view>
		</view>

		<view class="x-panel x-panel-content">
			<view v-if="list.length > 0">
				<!-- 遍历显示路线列表 -->
				<view class="itemcss" v-for="(route, index) in list" :key="index">
					<view :class="{'disabled-item': route.isAC === 1}" @click="handleClick(route)">
						<view class="x-row" style="display: flex; align-items: center; position: relative;">
							<view class="x-col col-left"
								style="flex: 1; display: flex; align-items: center; text-align: left">
								<!-- 图片 -->
								<image style="width: 40%; height: 100px; margin-right: 10px" :src="route.imageUrl">
								</image>
								<!-- 全长 -->
								<view class="x-row">
									<view class="x-col col-left">
										<text class="tag">{{index+1}}</text>
										<text>{{ route.routeName }}</text>
									</view>
								</view>

							</view>

							<!-- 三角形水印 -->
							<view v-if="route.isAC === 1" class="triangle-watermark">
								<text class="triangle-text">已完成</text>
							</view>
						</view>
					</view>
					<view style="height: 10px"></view>
				</view>
			</view>
			<view v-else>
				<sa-empty></sa-empty>
			</view>
			<view v-if="hasMoreData">
				<button class="load-more-button" @click="fetchRoutes" :disabled="isLoading">加载更多</button>
			</view>
		</view>
	</view>
</template>

<script>
	import {
		toast
	} from "utils/common";
	import {
		getRouters
	} from "api/system/user";

	export default {
		name: "route-choice",
		data() {
			return {
				list: [], // 原始路线列表
				isLoading: false, // 是否正在加载
				queryParams: {
					pageNum: 1,
					pageSize: 3
				},
				 query:{
					pageNum: 400,
					pageSize: 1
				},
				endData:{
					routerId:2
				},
				hasMoreData: true // 是否还有更多数据
			};
		},
		created() {
			// 页面创建时加载数据
			this.fetchRoutes();
			wx.showShareMenu({
				withShareTicket: true,
				menus: ['shareAppMessage', 'shareTimeline']
			});
		},
		methods: {
			// 分享好友
			onShareAppMessage() {
				return {
					title: '小枣营造社骑行小工具',
					path: `pages/index`
				}
			},
			// 分享朋友圈
			onShareTimeline() {
				return {
					title: '小枣营造社骑行小工具',
					path: `pages/index`
				}
			},
			fetchRoutes() {
				this.isLoading = true; // 设置为正在加载
				if (this.endData.routerId==2){
					getRouters(this.query)
						.then((res) => {
							this.endData =res.data[0];
						});
				};
				getRouters(this.queryParams)
					.then((res) => {
						if (res.data.length != 0) { // 请求的数据为空，没有数据
							if (res.data.length < this.queryParams.pageSize) { // 请求的数据为4条，说明下一页可能还有数据，列表添加上新的数据，把其他状态设为不显示
								this.list = [...this.list, ...res.data];
								this.hasMoreData = false; // 设置为没有更多数据
							} else { // 请求的数据没有4条，说明下一页已经暂时没有数据了，列表添加上新的数据，底部显示暂无更多数据
								this.queryParams.pageNum += 1;
								this.list = [...this.list, ...res.data];
								if(res.data[this.queryParams.pageSize-1].routerId == this.endData.routerId){
									this.hasMoreData = false; // 设置为没有更多数据
								}
							}
						} else {
																console.log("我被执行了22");
							this.hasMoreData = false; // 设置为没有更多数据
						}
						this.isLoading = false; // 设置为加载完成
					})
					.catch((error) => {
						console.error("路由数据加载失败:", error);
						this.isLoading = false; // 设置为加载完成
					});
			
			
			},
			handleClick(route) {
				if (route.isAC === 1) {
					// 提示用户已经作答
					toast("该路线已作答！");
					return;
				}
				// 跳转到填写问卷页面
				const routeStr = JSON.stringify(route);
				uni.navigateTo({
					url: `/pages/componentsA/questionnaire/questionnaire_filling?route=${encodeURIComponent(
          routeStr
        )}`,
				});
			},
		},
	};
</script>


<style>
	/* 样式更新 */

	/* 禁用样式 */
	.disabled-item {
		opacity: 0.5;
		/* 使项看起来变灰 */
	}

	.input {
		line-height: 35px;
		height: 35px;
	}

	.col-left {
		font-weight: 700;
		padding-left: 15px;
	}

	.col-right {
		padding-right: 15px;
		text-align: right;
	}

	.x-panel-top {
		position: absolute;
		top: 0;
		left: 0;
		bottom: auto;
		width: 100%;
		text-align: center;
		line-height: 50px;
		background-color: #f8f8f8;
	}

	.x-panel-content {
		position: relative;
		left: 0;
		right: 0;
		width: 100%;
		background-color: #f8f8f8;
	}

	.x-panel {
		bottom: 0px;
		top: 39px;
	}

	.itemcss {
		width: 90%;
		margin-left: 5%;
		background-color: white;
		border-radius: 15px;
		margin-top: 15px;
	}

	.x-row {
		display: flex;
		width: 100%;
		position: relative;
		line-height: 35px;
	}

	.x-col {
		flex: 1;
		display: block;
		width: 100%;
	}

	.x-col-20 {
		flex: 20%;
		max-width: 20%;
	}

	/* 三角形水印样式 */
	.triangle-watermark {
		position: absolute;
		top: -30px;
		right: 0;
		width: 0;
		height: 0;
		border-style: solid;
		border-width: 0 66px 66px 0;
		/* 控制三角形大小和方向 */
		border-color: transparent red transparent transparent;
		/* 透明边框+红色 */
		z-index: 10;
		/* 确保显示在最前面 */
	}

	/* 水印文字样式 */
	.triangle-text {
		position: absolute;
		top: 3px;
		/* 调整文字位置 */
		right: -60px;
		transform: rotate(45deg);
		/* 旋转文字以适配三角形 */
		color: white;
		/* 文字颜色 */
		font-size: 12px;
		/* 字体大小 */
		font-weight: bold;
		/* 字体加粗 */
		text-align: center;
	}

	.tag {
		padding: 4px 8px;
		//border-radius: 12px;
		font-size: 12px;
		font-weight: bold;
		color: #fff;
		text-align: center;
		margin-right: 8px;
		/* 标签与问题文本之间的间距 */
		background-color: #1A98DA;
		/* 蓝色标识单选题 */
	}

	/* styles.wxss */
	.load-more-button {
		display: block;
		height: 50px;
		width: 60%;
		/* 按钮宽度 */
		margin: 20px auto;
		/* 水平居中，垂直外边距 */
		padding: 5px;
		/* 内边距 */
		background: linear-gradient(90deg, #00c6ff, #0072ff);
		color: white;
		/* 字体颜色 */
		border: none;
		/* 无边框 */
		border-radius: 30px;
		/* 圆角 */
		text-align: center;
		/* 文本居中 */
		font-size: 16px;
		/* 字体大小 */
	}

	.load-more-button:hover {
		background-color: #179b16;
		/* 悬停时背景颜色 */
	}

	.load-more-button:disabled {
		background-color: #cccccc;
		/* 禁用时背景颜色 */
		cursor: not-allowed;
		/* 禁用时鼠标样式 */
	}
</style>