export default {
	data() {
		return {
			//设置默认的分享参数
			share: {
				title: '问卷调查小程序',
				path: '/pages/index',
			}
		}
	},
	onShareAppMessage() {
		return {
			title: "问卷调查小程序",
			path: '/pages/index',
		}
	},
	onShareTimeline() {
		return {
			title: "问卷调查小程序",
			path: '/pages/index',

		}
	},
}
