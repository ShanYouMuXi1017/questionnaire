<template>
	<view class="help-container">
		<view v-for="(item, findex) in list" :key="findex" :title="item.title" class="list-title">
			<view class="text-title">
				<view>
					<uni-icons :type="item.icon" size="16"></uni-icons>
					<!-- {{ item.title }} -->
				</view>
			</view>
			<view class="childList">
				<view v-for="(child, zindex) in item.childList" :key="zindex" class="question" hover-class="hover"
					@click="handleText(child)">
					<view class="text-item">{{ child.title }}</view>
					<view class="line" v-if="zindex !== item.childList.length - 1"></view>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				list: [
					// {
					// 	icon: 'location',
					// 	title: '关于公司',
					// 	childList: [{
					// 		title: '公司地址',
					// 		content: '云南省昆明市经开区云大西路39号创业大厦C栋2F'
					// 	}, {
					// 		title: '公司官网',
					// 		content: 'www.ynpax.com'
					// 	}, {
					// 		title: '公司邮箱',
					// 		content: 'paxzdh@126.com'
					// 	}]
					// },
					{
						// icon: 'chatbubble',
						// title: '',
						childList: [{
							title: '如何退出登录？',
							content: '请点击[我的] - [应用设置] - [退出登录]即可退出登录',
						}, {
							title: '如何添加手机号码？',
							content: '请点击[我的] - [编辑资料] - [输入手机号码] - [提交] 即可',
						},{
							title: '程序运行或数据异常应如何处理？',
							content: '请点击[我的] - [应用设置] - [清理缓存重新登录]'
						},{
							title: '如何成为代理商？',
							content: '当您的维修额度到达代理商最低限额即可申请成为代理商'
						},{
							title: '如何发展下线？下线带来的收益如何计算？',
							content: '发展下线：\n请点击[我的] - [分销首页] - [推广名片] 好友扫描二维码登录维修小程序后即为您的下线   \n\n收益的计算方式：\n您的收益等于您的下线在微信小程序的维修额度*佣金比例 \n\n查看佣金比例：\n请点击[我的] - [积分首页] 在积分任务一栏可以找到“下级返佣奖励” ',
								
						},{
							title: '我的收益如何提现？',
							content: '当您的收益达到最小提现额度即可申请提现，提现会以微信转账的方式完成'
						},{
							title: '优惠卷和限时优惠卷如何使用？',
							content: '优惠券是商家让利行为，您在进行设备维修时可使用优惠券进行抵现；限时优惠券要求在规定时间内使用'
						},{
							title: '如何确认报价？',
							content: '请点击[首页] - [设备维修] - [报价确认] - [点击待确认的订单] - [确认报价]'
						},{
							title: '什么时候需要代签？接受代签意味着什么？',
							content: ' 若客户无法亲自通过小程序确认报价，则我方工作人员可在您的授权同意后代为代签。若您接受代签，则您已有的优惠券或限时优惠券本次无法使用，请在下次维修时使用'
						},{
							title: '如何扫描设备二维码查看维修历史？',
							content: '请点击[首页] - [设备报修] – [维修追溯]，扫描设备上报修二维码即可查看该设备的维修历史信息'
						},{
							title: '如何获取积分？获取积分有哪些技巧？',
							content: '如何获取积分：\n请点击[我的] - [积分首页] 完成任务即可获取相应的积分，积分可以在维修时抵扣 \n\n积分获取技巧：\n发展下线后，下线在微信小程序维修后您可以获取相应积分。'
						},{
							title: '如何修改我的位置？',
							content: '请点击[我的] - [我的地址] - [点击授权当前位置(坐标)](用于业务员上门拜访) - [填写信息] - [提交更新]'
						}]
					}
				]
			}
		},
		methods: {
			handleText(item) {
				this.$tab.navigateTo(`/pages/common/textview/index?title=${item.title}&content=${item.content}`)
			}
		}
	}
</script>

<style lang="scss" scoped>
	page {
		background-color: #f8f8f8;
	}

	.help-container {
		margin-bottom: 100rpx;
		padding: 30rpx;
	}

	.list-title {
		margin-bottom: 30rpx;
	}

	.childList {
		background: #ffffff;
		box-shadow: 0px 0px 10rpx rgba(193, 193, 193, 0.2);
		border-radius: 16rpx;
		margin-top: 10rpx;
	}

	.line {
		width: 100%;
		height: 1rpx;
		background-color: #F5F5F5;
	}

	.text-title {
		color: #303133;
		font-size: 32rpx;
		font-weight: bold;
		margin-left: 10rpx;

		.iconfont {
			font-size: 16px;
			margin-right: 10rpx;
		}
	}

	.text-item {
		font-size: 28rpx;
		padding: 24rpx;
	}

	.question {
		color: #606266;
		font-size: 28rpx;
	}
</style>