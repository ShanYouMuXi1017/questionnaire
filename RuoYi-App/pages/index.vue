<template>
  <view>
    <view class="x-panel-top">
      <!-- 访谈意图文字替代搜索框 -->
      <view class="x-row intro-container" style="width: 90%; margin-left: 5%; margin-right: 5%; margin-top: 15px;">
        <view class="intro-text">
          <text class="text-content">
            为优化昆明市郊骑行线路的环境，现需广大骑友的帮助，真诚的希望您花点时间，选择一条或多条自己骑行过的线路，进行评价打分。以便我们更好的掌握该线路的优缺点，并完成该线路的环境提升改造设计。
          </text>
        </view>
      </view>
      <!-- 用户提示 -->
      <view class="user-hint" style="text-align: center">
        <text style="color: #555; font-size: 16px;font-weight:700">选中路线进行填写问卷</text>
      </view>
    </view>

      <view class="x-panel x-panel-content">
        <view v-if="list.length > 0">
          <!-- 遍历显示路线列表 -->
          <view class="itemcss" v-for="(route, index) in list" :key="index">
            <view
                :class="{'disabled-item': route.isAC === 1}"
                @click="handleClick(route)"
            >
              <view class="x-row">
                <view class="x-col col-left">
                  <text>{{ route.routeName }}</text>
                </view>
              </view>
              <view class="x-row" style="display: flex; align-items: center">
                <view class="x-col col-left" style="flex: 1; display: flex; align-items: center; text-align: left">
                  <!-- 图片 -->
                  <image style="width: 30%; height: 60px; margin-right: 10px" :src="route.imageUrl"></image>
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
          </view>
          <view style="height: 10px"></view>
        </view>
        <view v-else>
          <sa-empty></sa-empty>
        </view>
      </view>
  </view>
</template>

<script>
import { toast } from "utils/common";
import { getRouters } from "api/system/user";

import config from '@/config'

export default {
  name: "route-choice",
  data() {
    return {
      list: [], // 原始路线列表
      keyword: "", // 搜索关键词
      isRefreshing: false, // 下拉刷新状态
    };
  },
  created() {
    // 页面创建时加载数据
    this.fetchRoutes();
  },
  onShow() {
    // 页面从其他页面返回时自动刷新数据
    this.fetchRoutes();
  },
  methods: {
    fetchRoutes() {
      getRouters()
          .then((res) => {
            if (res && res.data) {
              this.list = res.data;
            }
          })
          .catch((error) => {
            console.error("路由数据加载失败:", error);
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
  opacity: 0.5; /* 使项看起来变灰 */
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
  top: 220px;
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

/* 访谈意图文字样式 */
.intro-container {
  background-color: #ffffff;
  border-radius: 15px;
  padding: 15px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.intro-text {
  text-align: left;
  font-size: 16px;
  line-height: 1.8; /* 增大行间距 */
  color: #333333;
  font-weight: 500;
  word-wrap: break-word;
  overflow-wrap: break-word;
  text-indent: 2em;
}

.text-content {
  display: block;
  white-space: normal;
}

</style>
