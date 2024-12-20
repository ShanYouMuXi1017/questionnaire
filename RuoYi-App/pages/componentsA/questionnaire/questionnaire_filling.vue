<template>
  <view class="container">
    <!-- 路线信息卡片 -->
    <view class="card">
      <text class="route-name">{{routeObject.routeName}}</text>
      <image class="route-image" :src="routeObject.imageUrl" mode="aspectFill" />
      <view class="route-info">
        <view class="info">
          <text class="info-label">全程:</text>
          <text class="info-value">{{routeObject.length}} km</text>
        </view>
        <view class="info">
          <text class="info-label" style="margin-left: 26px">爬升高度:</text>
          <text class="info-value">{{ routeObject.elevation }} m</text>
        </view>
      </view>
    </view>

    <!-- 开始答题按钮 -->
    <view class="start-btn-container">
      <button class="start-btn" @click="startTest(routeObject.routerId)">开始答题</button>
    </view>
  </view>
</template>

<script>
export default {
  data() {
    return {
      routeObject: {},  // 存储传递的 route
    };
  },
  onLoad(options) {
    const routeStr = options.route;
    if (routeStr) {
      this.routeObject = JSON.parse(decodeURIComponent(routeStr));
    }
  },
  methods: {
    startTest(routerId) {
      uni.navigateTo({
        url: `/pages/componentsA/questionnaire/answer_sheet?routerId=${routerId}`
      });
    },
  },
};
</script>

<style scoped>
/* 页面整体布局 */
.container {
  padding: 20px;
  background-color: #f8f8f8;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  height: 100vh;
}

/* 路线信息卡片 */
.card {
  background-color: white;
  border-radius: 12px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  padding: 20px;
  margin-bottom: 30px;
}

/* 路线图片 */
.route-image {
  width: 100%;
  height: 150px;
  border-radius: 8px;
  margin-top: 10px;
  margin-bottom: 10px;
}

/* 路线信息 */
.route-info {
  padding-left: 10px;
}

.route-name {
  font-size: 26px;
  font-weight: bold;
  color: #333;
  margin-bottom: 15px !important;
}

.info {
  //display: flex;
  //justify-content: space-between;
  float:left;
  margin-top: 10px;
}

.info-label {
  color: #888;
  font-size: 16px;
  margin-right: 5px;
}

.info-value {
  font-size: 16px;
  color: #555;
}

/* 开始答题按钮 */
.start-btn-container {
  display: flex;
  justify-content: center;
  margin-top: auto; /* 把按钮放置在底部 */
  padding-bottom: 20px;
}

.start-btn {
  background: linear-gradient(90deg, #00c6ff, #0072ff);
  border: none;
  color: white;
  font-size: 18px;
  padding: 15px 30px;
  border-radius: 30px;
  width: 70%;
  max-width: 300px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
}

.start-btn:active {
  background: linear-gradient(90deg, #0072ff, #00c6ff);
}
</style>
