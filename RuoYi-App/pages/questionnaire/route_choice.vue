<template>
  <view>
    <view class="x-panel-top">
      <!-- 搜索框 -->
      <view class="x-row" style="background-color: #ffffff; border-radius: 45px; width: 90%; margin-left: 5%; margin-right: 5%; margin-top: 15px">
        <view class="x-col bgcss" style="text-align: left">
          <input v-model="keyword" style="padding-left: 25px" placeholder="输入路线名称查询" />
        </view>
        <view class="x-col-20 bgclass" @click="searchlist">搜索</view>
      </view>
    </view>

    <view class="x-panel x-panel-content">
      <view v-if="filteredRoutes.length > 0">
        <!-- 遍历显示路线列表 -->
        <view class="itemcss" v-for="(route, index) in filteredRoutes" :key="index" >
          <view @click="enter(route)">
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
import { toast } from "../../utils/common";
import {getRouters} from "../../api/system/user";

export default {
  name: 'route-choice',
  data() {
    return {
      // 模拟的路线列表数据
      list: [],
      // 用于展示的过滤后列表
      filteredRoutes: [],
      keyword: ''
    };
  },
  created() {
    getRouters().then(res => {
      if (res && res.data) {
        this.list = res.data;
        this.list.forEach(route => {
          // 你可以根据需要赋值不同的 URL，这里是一个占位图片 URL
          // route.imageUrl = route.imageUrl || '/default/path/to/placeholder-image.jpg';
          route.imageUrl = 'https://th.bing.com/th/id/R.7802625ef081ea627fb2bde1777b188e?rik=CaRMWKA8fchmCA&riu=http%3a%2f%2fstatic.imxingzhe.com%2flushu%2f403cbd8a-436b-4af0-9876-af14dc472e9a.png!webActivityPhotos&ehk=0%2fqug0h0tf0iQDJyZ0HTXwAhSz90FYSQp4%2fZHp47C3Q%3d&risl=&pid=ImgRaw&r=0';
        });
        this.filteredRoutes = [...this.list];
      }
    }).catch(error => {
      console.error("请求失败:", error);
    });
    toast("选中路线进行问卷填写");
  },
  watch: {
    // 监听 keyword 的变化
    keyword(newVal) {
      if (newVal === '') {
        this.filteredRoutes = this.list;
      }
    }
  },
  methods: {
    enter(route) {
      const routeStr = JSON.stringify(route);
      // 使用 uni.navigateTo 跳转并传递参数
      uni.navigateTo({
        url: `/pages/questionnaire/questionnaire_filling?route=${encodeURIComponent(routeStr)}`
      });
    },
    // 根据输入的关键字进行搜索
    searchlist() {
      this.filteredRoutes = this.list.filter((route) =>
          route.routeName.includes(this.keyword)
      );
    }
  }
};
</script>

<style>
/* 样式保持不变或根据需要进行调整 */

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
