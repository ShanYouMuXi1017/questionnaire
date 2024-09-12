export default {
	data() {
		return {
			//设置默认的分享参数
			share: {
				title: '工控速修小程序',
				path: '/pages/index' + '?pid=' + this.$store.state.user.userId + '&flag=2',
				query: 'pid=' + this.$store.state.user.userId + '&flag=3'+'&path=/pages/index',
				imageUrl: '/static/logo_54.png',
				imageUrl2: '/static/logo_64.png',
			}
		}
	},
	onShareAppMessage(res) {
		return {
			title: this.share.title,
			path: this.share.path,
			imageUrl: this.share.imageUrl,
		}
	},
	onShareTimeline() {
		return {
			title: this.share.title,
			imageUrl: this.share.imageUrl2,
			path: this.share.path,
			query: this.share.query,
		}
	},
}