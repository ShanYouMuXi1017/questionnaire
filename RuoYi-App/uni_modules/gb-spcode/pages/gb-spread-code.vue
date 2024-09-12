<template>
	<view class="demo" >
		<!-- <button @click="created">btn</button> -->
		<view style="display: flex;justify-content: center;align-items: center;flex-direction: column; width: 750rpx; margin-top:100rpx;">
			<canvas :style="{ width: width + 'px', height: canvasH + 'px' }" canvas-id="myCanvas" id="myCanvas"></canvas>
			<view
				style="height: 85rpx; width: 480rpx; margin-top: 100rpx; display: flex; justify-content: center; align-items: center; background-color: #FEE618;  border-radius: 100rpx;"
				@tap="saveImage" v-if="isxs">
				<text class="" style="font-weight: bold;">保存推广海报</text>
			</view>
		</view>
		<view class="qr-box">
			<canvas canvas-id="qrcode" v-show="qrShow" style="width: 300rpx;margin: 0 auto;"/>
		</view>	
	</view>
</template>
<script>
	import uQRCode from '@/common/uqrcode.js'
	export default {
		data() {
			return {
				qrShow: false,
				sharePageUrl:'',
				invitationCode:'',
				width: 280,
				height: 450,
				canvasW:280, // 画布宽
				canvasH:450, // 画布高
				SystemInfo:{}, // 设备信息
				goodsImg: {}, // 商品主图
				goodsImgWidth: 0, // 商品主图宽度
				goodsImgHeight: 0, // 商品主图高度
				bgHeight: 0, // 白色背景高度
				ewmImg:{}, // 二维码图片
				ewmW: '', // 二维码大小
				x: 0,
				tempPath: '',
				yqm:'',
				isxs:false
			}
		},
		onLoad(e) {
			console.log("e: ",e);
			if (!e.link) {
				uni.navigateBack()
			}
			this.text = e.link
			this.sharePageUrl = e.img
			this.yqm = e.code
			this.qrFun()
			
		},
		methods: {
			saveImage() {
				uni.saveImageToPhotosAlbum({
					filePath: this.tempPath,
					success: () => {
						uni.showToast({
							icon: 'none',
							title: '保存成功',
							position: 'bottom'
						})
					}
				});
			},
			qrFun() {
				this.qrShow = true
				uQRCode.make({
					canvasId: 'qrcode',
					componentInstance: this,
					text: this.text,
					size: 110,
					margin: 0,
					backgroundColor: '#ffffff',
					foregroundColor: '#000000',
					fileType: 'jpg',
					errorCorrectLevel: uQRCode.errorCorrectLevel.H,
					success: res => {
						this.qrShow = false
						this.invitationCode = res
						this.created()
					}
				})
			},
			async created() {
				let that = this
				this.SystemInfo = await this.getSystemInfo();
				this.goodsImg = await this.getImageInfo(this.sharePageUrl);
				this.ewmImg = await this.getImageInfo(this.invitationCode);
				
				this.canvasW = 280; // 画布宽度
				this.canvasH = 450  // 画布高度 = 主图高度+二维码高度
				
				this.goodsImgHeight = this.canvasH*982/1137
				this.goodsImgWidth = this.goodsImgHeight*660/982
				
				this.bgHeight = this.goodsImgWidth*230/660
				this.x = (this.canvasW-this.goodsImgWidth)>0?(this.canvasW-this.goodsImgWidth)/2:0
				
				// 如果主图，二维码图片，设备信息都获取成功，开始绘制海报，这里需要用setTimeout延时绘制，否则可能会出现图片不显示。
				if(this.goodsImg.errMsg == 'getImageInfo:ok' && this.ewmImg.errMsg == 'getImageInfo:ok' && this.SystemInfo.errMsg == 'getSystemInfo:ok'){
					uni.showLoading({
						title: '加载中...',
						mask: true
					});
					setTimeout(()=>{
						var ctx = uni.createCanvasContext('myCanvas', this);
						
						// 1.绘制商品主图-- 高度固定
						ctx.drawImage(this.goodsImg.path, this.x, 0, this.goodsImgWidth, this.goodsImgHeight) // drawImage(图片路径,x,y,绘制图像的宽度，绘制图像的高度)
						
						// 1.填充背景色，白色
						ctx.setFillStyle('#fff'); // 默认白色
						ctx.fillRect(this.x, this.goodsImgHeight-75/230*this.bgHeight, this.goodsImgWidth, this.bgHeight) // fillRect(x,y,宽度，高度)
						
						// 3.绘制二维码
						ctx.drawImage(this.ewmImg.path, this.x+this.goodsImgWidth*50/660, this.goodsImgHeight+(this.bgHeight-this.bgHeight*140/230)/2-75/230*this.bgHeight, this.bgHeight*140/230, this.bgHeight*140/230) // drawImage(图片路径,x,y,绘制图像的宽度，绘制图像的高度,二维码的宽,高)
						
						// 4.文字信息
						ctx.setFontSize(30/230*this.bgHeight)
						ctx.setFillStyle('#121212')
						ctx.fillText('扫描二维码，加入我们吧！', this.x+this.goodsImgWidth*220/660, this.goodsImgHeight+this.bgHeight*45/230+this.bgHeight*140/230/5*2-75/230*this.bgHeight);
						ctx.fillText('邀请码：' + this.yqm, this.x+this.goodsImgWidth*220/660, this.goodsImgHeight+this.bgHeight*45/230+this.bgHeight*140/230/5*4-75/230*this.bgHeight);
			
						ctx.draw(true,(ret)=>{ // draw方法 把以上内容画到 canvas 中。
							uni.hideLoading()
							this.isxs = true
							uni.canvasToTempFilePath({ // 保存canvas为图片
								canvasId: 'myCanvas',
								// destWidth:this.canvasW,
								// destHeight:this.canvasH,
								quality: 1, 
								success:(res)=> {
									console.log(res)
									
									this.tempPath = res.tempFilePath
								},
								complete: (res)=> {
									// 在H5平台下，tempFilePath 为 base64, // 图片提示跨域 H5保存base64失败，APP端正常输出临时路径
									this.tempPath = res.tempFilePath
									that.$emit("tellFather",res.tempFilePath); // 将路径传给父组件
								},
							}, this)
						});
					},100)
				}else{
					console.log('err')
				}
			},
			// 获取图片信息
			getImageInfo(image) {
				return new Promise((req, rej) => {
					uni.getImageInfo({
						src: image,
						success: function(res) {
							req(res)
						},
					});
				})
			},
			
			// 获取设备信息
			getSystemInfo(){
				return new Promise((req, rej) => {
					uni.getSystemInfo({
					    success: function (res) {
					        req(res)
					    }
					});
				})
			}
		}
	}
</script>

<style>
	page{
		background: #f8f8f8;
	}
	.qr-box{
		margin-top: -20000rpx;
	}
	canvas {
		display: block;
	}
</style>
