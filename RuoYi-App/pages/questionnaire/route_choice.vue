<template>
  <view>
    <mlist :list="filteredList" @search="search"></mlist>
  </view>
</template>

<script>
  import Mlist from "@/components/m-list/m-list.vue";
  export default {
    components: {
      Mlist,
    },
    data() {
      return {
        // 模拟的路线列表数据
        list: [
          {
            name: "323",
            length: 1.95,
            elevation: 40,
            image: "/static/images/1.jpg", // 使用实际图片路径
            challenges: 1417,
            remark: "风景优美，适合新手"
          },
          {
            name: "世博依碧中心至金殿",
            length: 1.95,
            elevation: 40,
            image: "/static/images/1.jpg", // 使用实际图片路径
            challenges: 1417,
            remark: "风景优美，适合新手"
          },
		  {
		    name: "世博依碧中心至金殿",
		    length: 1.95,
		    elevation: 40,
		    image: "/static/images/1.jpg", // 使用实际图片路径
		    challenges: 1417,
		    remark: "风景优美，适合新手"
		  },
		  {
		    name: "世博依碧中心至金殿",
		    length: 1.95,
		    elevation: 40,
		    image: "/static/images/1.jpg", // 使用实际图片路径
		    challenges: 1417,
		    remark: "风景优美，适合新手"
		  },
		  {
		    name: "世博依碧中心至金殿",
		    length: 1.95,
		    elevation: 40,
		    image: "/static/images/1.jpg", // 使用实际图片路径
		    challenges: 1417,
		    remark: "风景优美，适合新手"
		  },
		  {
		    name: "世博依碧中心至金殿",
		    length: 1.95,
		    elevation: 40,
		    image: "/static/images/1.jpg", // 使用实际图片路径
		    challenges: 1417,
		    remark: "风景优美，适合新手"
		  },
		  {
		    name: "世博依碧中心至金殿",
		    length: 1.95,
		    elevation: 40,
		    image: "/static/images/1.jpg", // 使用实际图片路径
		    challenges: 1417,
		    remark: "风景优美，适合新手"
		  },
		  {
		    name: "世博依碧中心至金殿",
		    length: 1.95,
		    elevation: 40,
		    image: "/static/images/1.jpg", // 使用实际图片路径
		    challenges: 1417,
		    remark: "风景优美，适合新手"
		  },
		  {
		    name: "世博依碧中心至金殿",
		    length: 1.95,
		    elevation: 40,
		    image: "/static/images/1.jpg", // 使用实际图片路径
		    challenges: 1417,
		    remark: "风景优美，适合新手"
		  },
		  {
		    name: "世博依碧中心至金殿",
		    length: 1.95,
		    elevation: 40,
		    image: "/static/images/2.jpg", // 使用实际图片路径
		    challenges: 1417,
		    remark: "风景优美，适合新手"
		  },
		  {
		    name: "世博依碧中心至金殿",
		    length: 1.95,
		    elevation: 40,
		    image: "/static/images/1.jpg", // 使用实际图片路径
		    challenges: 1417,
		    remark: "风景优美，适合新手"
		  }
        ],
        // 用于展示的过滤后列表
        filteredList: [],
      };
    },
    created() {
      // 初始状态展示全部数据
      this.filteredList = this.list;
    },
    methods: {
      // 根据输入的关键字进行搜索
      search({ keyword }) {
        this.filteredList = this.list.filter((item) =>
          item.name.includes(keyword)
        );
      },
    },
  };
</script>

<style>
/* 样式保持不变或根据需要进行调整 */
</style>
