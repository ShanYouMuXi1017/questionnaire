import Vue from 'vue'
import App from './App'
import store from './store' // store
import plugins from './plugins' // plugins
import './permission' // permission
Vue.use(plugins)

// 引入uView框架
import uView from "uview-ui";
Vue.use(uView);

//开启分享
import share from './utils/share.js'
Vue.mixin(share)

//全局绑定
import globalMixin from './utils/globalMixin.js';  // 导入全局混入
Vue.mixin(globalMixin);


Vue.config.productionTip = false
Vue.prototype.$store = store
 
App.mpType = 'app'

const app = new Vue({
  ...App
})

app.$mount()
