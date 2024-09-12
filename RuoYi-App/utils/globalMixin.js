// src/mixins/globalMixin.js
export default {
  onLoad(query) {
    console.log("query")
    console.log(query)
    console.log(query.path)
    if(query.path !== undefined){
      console.log("========")
      console.log(query.flag)
      uni.reLaunch({
        url: query.path+"?pid="+query.pid+"&flag="+query.flag
      });
    }
  }
}
