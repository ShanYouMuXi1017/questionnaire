<template>
	<view class="m-list">
		<view class="x-panel-top">
			<!-- 搜索框 -->
			<slot name="topstyle">
				<view class="x-row" style="background-color: #ffffff; border-radius: 45px; width: 90%; margin-left: 5%; margin-right: 5%; margin-top: 15px">
					<view class="x-col bgcss" style="text-align: left">
						<input v-model="keyword" style="padding-left: 25px" placeholder="输入路线名称查询" />
					</view>
					<view class="x-col-20 bgclass" @click="searchlist">搜索</view>
				</view>
			</slot>
		</view>

		<view class="x-panel x-panel-content">
			<slot name="main">
				<view v-if="filteredRoutes.length > 0">
					<!-- 遍历显示路线列表 -->
					<view class="itemcss" v-for="(route, index) in filteredRoutes" :key="index">
						<view class="x-row">
							<view class="x-col col-left">
								<text>{{ route.name }}</text>
							</view>
						</view>
						<view class="x-row" style="display: flex; align-items: center">
							<view class="x-col col-left" style="flex: 1; display: flex; align-items: center; text-align: left">
								<!-- 图片 -->
								<image style="width: 30%;height: 60px; margin-right: 10px" :src="route.image"></image>
								<!-- 全长 -->
								<view style="margin-right: 10px">
									<text>全长: {{ route.length }} km</text>
								</view>
								<!-- 爬升高度 -->
								<view>
									<text>爬升高度: {{ route.elevation }} m</text>
								</view>
							</view>
						</view>
					</view>
					<view style="height: 10px"></view>
				</view>
				<view v-else>
					<sa-empty></sa-empty>
				</view>
			</slot>
		</view>
	</view>
</template>

<script>
export default {
	name: 'm-list',
	props: {
		// 路线列表
		list: {
			type: Array,
			default: () => []
		}
	},
	data() {
		return {
			keyword: '',
			filteredRoutes: this.list // 默认显示所有列表
		};
	},
	watch: {
		// 监听 keyword 的变化
		keyword(newVal) {
			if (newVal === '') {
				// 如果 keyword 为空，重置 filteredRoutes 为原始列表
				this.filteredRoutes = this.list;
			} 
			// else {
			// 	// 否则根据关键词过滤
			// 	this.filteredRoutes = this.list.filter((route) =>
			// 		route.name.includes(newVal)
			// 	);
			// }
		}
	},
	methods: {
		// 点击搜索按钮也可以触发过滤
		searchlist() {
			this.filteredRoutes = this.list.filter((route) =>
				route.name.includes(this.keyword)
			);
		}
	}
};
</script>

<style>
input {
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
	position: absolute;
	left: 0;
	right: 0;
	width: 100%;
	overflow: auto;
	background-color: #f8f8f8;
}

.x-panel {
	bottom: 0px;
	top: 50px;
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

.bgcss {
	background-image: url('/static/m_list/searchIcon.png');
	background-size: 15px 15px;
	background-repeat: no-repeat;
	background-position-y: center;
}

.bgclass {
	text-align: center;
	background: -webkit-gradient(linear, 0% 0%, 0% 100%, from(#76bef9), to(#4996f7));
	color: white;
	border-radius: 45px;
}
</style>
