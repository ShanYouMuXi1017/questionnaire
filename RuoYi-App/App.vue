<script>
import config from './config';
import store from '@/store';
import { getToken } from '@/utils/auth';

export default {
	onLaunch: function (options) {
		this.initApp();

	},
	methods: {
		// 初始化应用
		initApp() {
			// wx.showShareMenu({
			//     withShareTicket: true,
			//     menus: ['shareAppMessage', 'shareTimeline']
			// }) 
			// 初始化应用配置
			this.initConfig();
			// 检查用户登录状态
			//#ifdef H5
			this.checkLogin();
			//#endif

		},
		initConfig() {
			this.globalData.config = config;
		},
		checkLogin() {
			if (!getToken()) {
				this.$tab.reLaunch('/pages/login');
			}
		}
		// 分享好友
		// onShareAppMessage() {
		//     return {
		//       title: '小枣营造社骑行小工具',
		//       path: `pages/index`
		//     }
		//   },
		// // 分享朋友圈
		//   onShareTimeline() {
		//     return {
		//       title: '小枣营造社骑行小工具',
		//       path: `pages/index`
		//     }
		//   }
	}
};
</script>

<style lang="scss">
@import 'uview-ui/index.scss';
@import "colorui/main.css";
@import "colorui/icon.css";

// @import '@/static/scss/index.scss';
@import '@/pages/componentsB/static/scss/index.scss';
</style>
