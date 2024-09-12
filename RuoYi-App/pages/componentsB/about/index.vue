<template>
  <view class="about-container">
    <view class="header-section text-center">
      <image style="height: 256rpx;" src="/static/logo_200.png" mode="aspectFit">
      </image>
      <view><uni-title type="h2" title="工业维修  & 产品研发"></uni-title></view>
    </view>

    <view class="content-section">
      <view class="menu-list">
        <view class="list-cell ">
          <view class="menu-item-box">
            <view>版本信息</view>
            <view class="text-right">v{{version}}</view>
          </view>
        </view>
		<view class="list-cell ">
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
            <view>公司网站</view>
            <view class="text-right">
              <uni-link :href="url" text="www.gkxiu.com" showUnderLine="false"></uni-link>
            </view>
          </view>
        </view>
      </view>
    </view>

    <view class="copyright">
      <view>版权所有©：云南硕晨信息技术有限公司(滇ICP备11002993号)</view>
    </view>
  </view>
</template>

<script>
  export default {
    data() {
      return {
        url: getApp().globalData.config.appInfo.site_url,
        version: getApp().globalData.config.appInfo.version
      }
    }
  }
</script>

<style lang="scss">
  page {
    background-color: #f8f8f8;
  }

  .copyright {
    margin-top: 50rpx;
    text-align: center;
    line-height: 60rpx;
    color: #999;
  }

  .header-section {
    display: flex;
    padding: 30rpx 0 0;
    flex-direction: column;
    align-items: center;
  }
</style>
