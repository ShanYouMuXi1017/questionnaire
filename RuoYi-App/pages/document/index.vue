<template>
	<view>

		<view>
			<view style="border: 2px;margin-bottom: 10rpx;">
				<u-picker :show="show" :columns="columns" @confirm="confirm" model="value"
					@cancel="show = false"></u-picker>
				<u-button @click="show = true">点击选择文档类型</u-button>
			</view>

			<view style="display: inline;width: 30px;margin-top: 20px;">
				<u-search v-model="keyword" :show-action="false" shape="round" @change="fillterList()"></u-search>
			</view>

		</view>

		<view class="u-page">
			<u-list @scrolltolower="scrolltolower" showScrollbar="true">
				<u-list-item v-for="(item, index) in indexList" :key="index">
					<u-cell @click="getPreview(index)" :title="item.fileName">
					</u-cell>
				</u-list-item>
			</u-list>
		</view>

	</view>
</template>

<script>
	import {
		listPreview,getBaseUrl,
	} from "@/api/system/preview"
	export default {
		data() {
			return {
				BaseUrl: "",
				keyword: "",
				queryParams: {
					filename: null,
					filetype: null,
					uploadpath: null,
					pdfpath: null,
					pdfname: null,
					documentType: null,
				},
				previewList: [],
				indexList: [],
				indexTestList: [],
				show: false,
				columns: [
					['所有类型', '产品说明书', '常见故障案例']
				],
				//文档类型选择器
				value: "所有类型",
				range: [{
						value: 0,
						text: "产品说明书"
					},
					{
						value: 1,
						text: "常见故障案例"
					},
					{
						value: 2,
						text: "所有类型"
					},
				],
			}
		},
		created(){
			this.getUrl();
		},
		onLoad() {
			// this.loadmore()
			this.getList();
		},
		methods: {
			getUrl(){
				getBaseUrl().then((res) =>{
					this.BaseUrl = res;
				})
			},
			confirm(e) {
				const {
					value,
					columnIndex,
				} = e
				this.value = value
				this.show = false
				this.chooesFillter()
			},
			//在线查看文档
			getPreview(index) {
				wx.showLoading({
					title: '加载中',
					masl: true
				});
				wx.downloadFile({
					url: this.BaseUrl + this.indexList[index].pdfName,
					success: function(res) {
						const Path = res.tempFilePath
						wx.openDocument({
							filePath: Path,
							fileType: "pdf",
							success: function(res) {
								console.log('打开文档成功');
								wx.hideLoading();
							},
							fail: function(res) {
								wx.hideLoading();
								wx.showToast({
									title: '打开失败',
									icon: 'error',
									duration: 2000 //持续的时间

								});
								console.log("打开文件失败");
								console.log(res);
							},
						})
					},
					fail: function(res) {
						wx.hideLoading();
						wx.showToast({
							title: '打开失败',
							icon: 'error',
							duration: 2000 //持续的时间

						});
						console.log("下载失败")
						console.log(res)
					}
				})
				console.log("结束")
			},
			//查询所有文档
			getList() {
				this.indexList = []
				listPreview(this.queryParams).then((response) => {
					this.previewList = response.data;
					this.previewList.forEach(document => {
						this.indexList.push({
							fileName: document.filename,
							fileType: document.fileType,
							filePdf: document.previewUrl,
							filePath: document.uploadpath,
							documentType: document.documentType,
							pdfName: document.pdfname,
							previewUrl: document.previewUrl,
						})
					})

					this.indexTestList = this.indexList

				});
			},
			//根据输入的关键字筛选
			fillterList() {
				if (!this.keyword == '') {
					this.indexList = this.indexTestList.filter(item => item.fileName.includes(this.keyword))
				} else {
					this.indexList = []
					this.indexList = this.indexTestList
				}

			},
			//选择器筛选
			chooesFillter() {
				if (this.value.toString() == "所有类型") {
					this.getList()
				} else if (this.value.toString() == "产品说明书") {
					this.queryParams.documentType = 0
					this.getList()
					this.queryParams.documentType = ''
				} else {
					this.queryParams.documentType = 1
					this.getList()
					this.queryParams.documentType = ''
				}
			},
			scrolltolower() {
				this.loadmore()
			},
			change(e) {
				console.log("e:", e);
			},
		},
	}
</script>


<style lang="scss">
	.text {
		font-size: 12px;
		color: #666;
		margin-top: 5px;
	}

	.uni-px-5 {
		padding-left: 10px;
		padding-right: 10px;
	}

	.uni-pb-5 {
		padding-bottom: 10px;
	}
</style>