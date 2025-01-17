<template>
	<view class="mine-container" :style="{height: `${windowHeight}px`}">
		<!--顶部个人信息栏-->
		<view class="header-section">
			<view class="flex padding justify-between">
				<text class="title" style="margin:30px auto;">《用户协议》</text>
			</view>
		</view>

		<view class="content-section" >
			<view class="agreement-content">
				<text class="agreement-title">一、引言</text>
				<br>
				<text class="agreement-text">欢迎您使用[骑行线路问卷调查]（以下简称“本小程序”）！本用户协议（以下简称“本协议”）是您与[骑行线路问卷调查]（以下简称“我们”）之间关于您使用本小程序服务的法律协议。请您仔细阅读本协议的全部内容，特别是以加粗、下划线等方式提示的涉及您重大权益的条款。如您使用本小程序的服务，即表示您已阅读、理解并同意接受本协议的约束。</text>
<br>
				<text class="agreement-title">二、定义</text>
				<br>
				<view class="agreement-list">
					<text class="agreement-item">1. 小程序：指由我们开发并运营的、运行在微信等第三方平台上的应用程序，名称为[骑行线路调查] 。</text>
					<text class="agreement-item">2. 用户：指使用本小程序服务的自然人、法人或其他组织。</text>
					<text class="agreement-item">3. 服务：指本小程序向用户提供的各项功能和服务，包括但不限于信息展示、查询、交易等服务。</text>
				</view>
<br>
				<text class="agreement-title">三、服务内容与使用规则</text>
				<br>
				<text class="agreement-subtitle">1. 服务内容</text>
				<view class="agreement-list">
					<text class="agreement-item">• 我们致力于为用户提供[具体服务内容，如商品购买、资讯浏览、社交互动等]相关服务，具体服务内容以本小程序实际提供的为准。</text>
					<text class="agreement-item">• 我们有权根据实际情况随时变更、中断或终止部分或全部服务，若因此给您造成损失，除法律法规另有规定外，我们不承担责任，但会尽可能提前通知您。</text>
				</view>
				<text class="agreement-subtitle">2. 使用规则</text>
				<view class="agreement-list">
					<text class="agreement-item">• 您在使用本小程序服务时，应遵守法律法规、本协议以及小程序平台的相关规则，不得利用本小程序从事违法违规或损害他人合法权益的活动。</text>
					<text class="agreement-item">• 您应自行负责对您的账号和密码的保管，并对使用您账号和密码进行的一切活动负责。若发现账号或密码被他人非法使用，应立即通知我们。</text>
					<text class="agreement-item">• 您在使用本小程序服务过程中，不得发布虚假、欺诈、淫秽、暴力、侵权等违法违规或不良信息；不得干扰、破坏本小程序的正常运行；不得恶意刷量、滥用服务等。</text>
				</view>
<br>
				<text class="agreement-title">四、知识产权</text>
				<br>
				<view class="agreement-list">
					<text class="agreement-item">1. 本小程序及相关内容（包括但不限于文字、图片、音频、视频、软件等）的知识产权归我们或相关权利人所有。未经我们或相关权利人书面许可，您不得擅自使用、复制、传播、修改、改编等。</text>
					<text class="agreement-item">2. 您在使用本小程序服务过程中产生的内容（如评论、发布的信息等），在您遵守本协议及相关法律法规的前提下，您享有相应的知识产权，但您授予我们在全球范围内、永久性、不可撤销、免费的许可，允许我们使用、复制、传播、展示、修改、改编等，以用于本小程序的运营、推广等目的。</text>
				</view>
<br>
				<text class="agreement-title">五、责任限制与免责声明</text>
				<br>
				<view class="agreement-list">
					<text class="agreement-item">1. 我们尽力为您提供稳定、安全、准确的服务，但由于技术故障、网络问题、不可抗力等原因，可能会导致服务中断、数据丢失、信息错误等情况，我们对此不承担责任，但会积极采取措施减少损失。</text>
					<text class="agreement-item">2. 对于您因使用本小程序服务而遭受的任何直接或间接损失（包括但不限于财产损失、数据丢失、商业机会损失等），除法律法规另有规定外，我们不承担责任。</text>
					<text class="agreement-item">3. 若您违反本协议或法律法规，导致我们或第三方遭受损失的，您应负责赔偿全部损失。</text>
				</view>
<br>
				<text class="agreement-title">六、协议的变更与终止</text><br>
				<view class="agreement-list">
					<text class="agreement-item">1. 我们有权根据法律法规、业务发展等情况随时变更本协议内容，并在本小程序上进行公示。若您在协议变更后继续使用本小程序服务，视为您同意变更后的协议；若您不同意变更后的协议，应停止使用本小程序服务。</text>
					<text class="agreement-item">2. 在下列情况下，我们有权终止本协议及您使用本小程序服务的权利：</text>
					<view class="agreement-sublist">
						<text class="agreement-subitem">• 您违反本协议或法律法规的规定；</text>
						<text class="agreement-subitem">• 我们根据法律法规、政府部门要求或自身业务调整需要；</text>
						<text class="agreement-subitem">• 其他导致本协议无法继续履行的情形。</text>
					</view>
				</view>
<br>
				<text class="agreement-title">七、法律适用与争议解决</text><br>
				<view class="agreement-list">
					<text class="agreement-item">1. 本协议的签订、履行、解释及争议解决均适用[具体法律适用地区]法律。</text>
					<text class="agreement-item">2. 若您与我们之间发生争议，应首先友好协商解决；协商不成的，任何一方均有权向有管辖权的人民法院提起诉讼。</text>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				windowHeight: uni.getSystemInfoSync().windowHeight
			}
		},
	}
</script>

<style lang="scss">
	page {
		background-color: #f5f6f7;
	}
	.mine-container {
		.header-section {
			.title {
				font-size: 18px;
				font-weight: bold;
			}
		}
		.content-section {
			.agreement-content {
				padding: 10px;
				.agreement-title {
					font-size: 16px;
					font-weight: bold;
					margin-top: 10px;
				}
				.agreement-text {
					font-size: 14px;
					line-height: 1.5;
					margin-top: 5px;
				}
				.agreement-list {
					.agreement-item {
						font-size: 14px;
						line-height: 1.5;
						margin-top: 5px;
					}
				}
				.agreement-subtitle {
					font-size: 15px;
					font-weight: bold;
					margin-top: 10px;
				}
				.agreement-sublist {
					.agreement-subitem {
						font-size: 14px;
						line-height: 1.5;
						margin-top: 5px;
					}
				}
			}
		}
	}
</style>