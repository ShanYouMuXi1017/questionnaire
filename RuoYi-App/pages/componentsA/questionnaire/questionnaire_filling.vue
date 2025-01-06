<template>
  <view class="container">
    <!-- 路线信息卡片 -->
    <view class="card">
      <!-- 默认展示第一个途径 -->
      <text class="route-name">{{ firstRouteName }}</text>

      <image class="route-image" :src="routeObject.imageUrl" mode="aspectFill" />
      <view class="route-info">
        <view class="info">
          <text class="info-label">全程:</text>
          <text class="info-value">{{routeObject.length}} km</text>
        </view>
        <view class="info">
          <text class="info-label" style="margin-left: 10px">爬升高度:</text>
          <text class="info-value">{{ routeObject.elevation }} m</text>
        </view>
      </view>

      <!-- 其他途径展示在下方 -->
      <view class="other-routes" v-if="otherRoutes.length > 0">
        <view class="other-routes-title">其他途径:</view>
        <view class="route-item" v-for="(route, index) in otherRoutes" :key="index">
          <text class="route-name2">● {{ route }}</text>
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
  computed: {
    // 提取第一个途径
    firstRouteName() {
      const routes = this.routeObject.routeName ? this.routeObject.routeName.split("、") : [];
      return routes.length > 0 ? routes[0] : this.routeObject.routeName;
    },
    // 提取其他途径
    otherRoutes() {
      const routes = this.routeObject.routeName ? this.routeObject.routeName.split("、") : [];
      return routes.slice(1);  // 返回除了第一个以外的所有途径
    },
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
  margin-bottom: 10px !important;
}

.route-name2 {
  font-size: 18px;
  font-weight: bold;
  color: #333;
  margin-bottom: 10px !important;
}

.other-routes {
  margin-top: 10px;
  padding-left: 10px;
  display: block; /* 确保是块级布局 */
  clear: both; /* 确保与上方内容分离，换行显示 */
}

.other-routes-title {
  font-size: 18px;
  color: #888;
  padding-top:18px;
  margin-bottom: 5px;
  display: block; /* 标题独占一行 */
}

.route-item {
  margin-top: 5px;
  color: #555;
  font-size: 16px;
  display: block; /* 每个途径独占一行 */
}


/* 信息项 */
.info {
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
