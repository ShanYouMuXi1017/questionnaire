<template>
	<view class="reseller">
		<view class="top">
			<image style="height: 228px; bottom: 0rpx; margin: auto; " src="/static/logo_200.png" mode="aspectFit">
			</image>
			 
		</view>
		<view class="gongge">
			<view class="content-section">
			      <view class="menu-list">
			         
			        <view class="list-cell  ">
			          <view class="menu-item-box">
			            <view>官方邮箱</view>
						<text class="text-right" selectable='true'>paxzdh@126.com</text>
			          </view>
			        </view>
			        <view class="list-cell ">
			          <view class="menu-item-box">
			            <view>服务热线</view>
						<text class="text-right" selectable='true'>13095328257</text>
			          </view>
			        </view>
					<view class="list-cell ">
					  <view class="menu-item-box">
					    <view>微信联系</view>
						<text class="text-right" selectable='true'>13095328257</text>
					  </view>
					</view>
					<view class="list-cell ">
					  <view class="menu-item-box">
					    <view>公司地址</view>
						<text class="text-right" selectable='true'>昆明市官渡区昆明C谷西区4楼</text>
					  </view>
					</view>
			        <view class="list-cell ">
			          <view class="menu-item-box">
			            <view>公司网站</view>
			            <view class="text-right">
			              <uni-link :href="url" text="www.gkxiu.com" showUnderLine="false"></uni-link>
			            </view>
			          </view>
			        </view>
			      </view>
			    </view>
		</view>
		

 
		
	</view>
</template>

<script>
	export default {
		data() {
			return {
			}
		},
		methods: {
		},
	}
</script>

<style lang="scss">
	page {
		overflow: hidden;
		height: 100%;
		background-color: #F5F5F5;
	}

	.reseller {
		background-color: #F5F5F5;
		min-height: 100vh;
		font-size: 14px;

		.top {
			color: #FFFFFF;
			display: flex;
			justify-content: space-between;
			position: relative;
			width: 100%;
			height: 228px;
			background-image: linear-gradient(to bottom, #2979ff, #bcd5ff);
			// background-color: #2979ff;
			// background: linear-gradient(top, #4EC28E, #2979ff);


			.title {
				background-color: #FFFFFF
			}

			.num1 {
				margin-top: 10%;
				text-align: center;
				width: 100%;
			}

		}
 }
 
		
		.gongge{
			margin-top: 80rpx;
		}
		
 
		
	
</style>
