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
            <view
                :class="{'disabled-item': route.isAC === 1}"
                @click="handleClick(route)"
            >
              <view class="x-row">
                <view class="x-col col-left">
                  <text class="tag">{{route.routerId}}</text>
                  <text>{{ route.routeName }}</text>
                </view>
              </view>
              <view class="x-row" style="display: flex; align-items: center; position: relative;">
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

                <!-- 三角形水印 -->
                <view
                    v-if="route.isAC === 1"
                    class="triangle-watermark"
                >
                  <text class="triangle-text">已完成</text>
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
              console.log(this.list);
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
  border-width: 0 66px 66px 0; /* 控制三角形大小和方向 */
  border-color: transparent red transparent transparent; /* 透明边框+红色 */
  z-index: 10; /* 确保显示在最前面 */
}

/* 水印文字样式 */
.triangle-text {
  position: absolute;
  top: 3px; /* 调整文字位置 */
  right: -60px;
  transform: rotate(45deg); /* 旋转文字以适配三角形 */
  color: white; /* 文字颜色 */
  font-size: 12px; /* 字体大小 */
  font-weight: bold; /* 字体加粗 */
  text-align: center;
}
.tag {
  padding: 4px 8px;
  //border-radius: 12px;
  font-size: 12px;
  font-weight: bold;
  color: #fff;
  text-align: center;
  margin-right: 8px; /* 标签与问题文本之间的间距 */
  background-color: #1A98DA; /* 蓝色标识单选题 */
}


</style>
