<template>
	<view class="mine-container" :style="{height: `${windowHeight}px`}">
		<!--顶部个人信息栏-->
		<view class="header-section">
			<view class="flex padding justify-between">
				<text class="title"  style="margin:30px auto;">《隐私政策》</text>
			</view>
		</view>

		<view class="content-section">
			<view class="privacy-content">
				<text class="privacy-title">一、引言</text>
				<br>
				<text class="privacy-text">我们深知隐私对您的重要性，因此致力于保护您在使用本小程序过程中的个人信息。本隐私政策将向您详细说明我们如何收集、使用、存储、共享和保护您的个人信息，以及您对个人信息享有的权利。请您仔细阅读本隐私政策，如您使用本小程序服务，即表示您同意我们按照本隐私政策处理您的个人信息。</text>
<br>
				<text class="privacy-title">二、定义</text>
				<br>
				<view class="privacy-list">
					<text class="privacy-item">1. 个人信息：指以电子或者其他方式记录的能够单独或者与其他信息结合识别特定自然人身份或者反映特定自然人活动情况的各种信息，包括但不限于姓名、身份证号、电话号码、电子邮箱、地址、位置信息等。</text>
					<text class="privacy-item">2. 敏感个人信息：指一旦泄露或者非法使用，容易导致自然人的人格尊严受到侵害或者人身、财产安全受到危害的个人信息，包括生物识别、宗教信仰、特定身份、医疗健康、金融账户、行踪轨迹等信息。</text>
				</view>
<br>
				<text class="privacy-title">三、个人信息的收集</text>
				<br>
				<view class="privacy-list">
					<text class="privacy-item">1. 注册与登录信息：当您注册或登录本小程序时，我们可能会收集您的手机号码、验证码、设置的用户名和密码等信息，用于创建和管理您的账号，确保您正常使用本小程序服务。</text>
					<text class="privacy-item">2. 服务相关信息：在您使用本小程序的服务过程中，我们可能会收集您使用服务的记录（如浏览记录、搜索记录、交易记录等），以便为您提供个性化的服务、优化用户体验以及进行数据分析和统计。</text>
					<text class="privacy-item">3. 设备信息：为了使本小程序能够正常运行并提供更好的服务，我们可能会收集您的设备型号、操作系统版本、设备标识符（如IMEI、MAC地址等）、网络状态等信息。</text>
					<text class="privacy-item">4. 位置信息：当您使用涉及位置服务的功能（如查找附近的商家、导航等）时，我们会收集您的位置信息，以实现相关功能。您可以在设备设置中选择是否允许我们获取您的位置信息。</text>
				</view>
<br>
				<text class="privacy-title">四、个人信息的使用</text>
				<br>
				<view class="privacy-list">
					<text class="privacy-item">1. 我们收集和使用您的个人信息是为了向您提供本小程序的服务，包括但不限于实现注册登录、提供个性化服务、处理交易、推送通知等。</text>
					<text class="privacy-item">2. 我们可能会将您的个人信息用于数据分析和统计，以改进我们的产品和服务，提升用户体验。我们也可能会根据您的使用习惯和偏好，为您提供个性化的推荐内容。</text>
					<text class="privacy-item">3. 在符合法律法规和您同意的情况下，我们可能会将您的个人信息用于其他目的，如开展市场调研、推广活动等。</text>
				</view>
<br>
				<text class="privacy-title">五、个人信息的存储</text>
				<br>
				<view class="privacy-list">
					<text class="privacy-item">1. 我们将在中华人民共和国境内存储您的个人信息。如因业务需要确需向境外传输您的个人信息，我们将按照法律法规的规定进行安全评估，并采取相应的保护措施。</text>
					<text class="privacy-item">2. 我们将根据法律法规的规定和业务需要，确定您个人信息的存储期限。在存储期限届满后，我们将对您的个人信息进行删除或匿名化处理。</text>
				</view>
<br>
				<text class="privacy-title">六、个人信息的共享、转让与公开披露</text>
				<br>
				<text class="privacy-subtitle">1. 共享：</text>
				<text class="privacy-text">我们不会向第三方共享您的个人信息，除非获得您的明确同意，或者为了实现本小程序的服务功能、履行法律法规规定的义务、保护我们或第三方的合法权益等必要情况下。在共享时，我们会要求第三方遵守相关法律法规和保密义务，采取适当的安全措施保护您的个人信息。</text>
				<text class="privacy-subtitle">2. 转让：</text>
				<text class="privacy-text">我们不会将您的个人信息转让给第三方，除非获得您的明确同意，或者因公司合并、分立、破产清算等原因导致业务转让的情况。在转让前，我们会对受让人进行尽职调查，并要求受让人遵守相关法律法规和保密义务，保护您的个人信息安全。</text>
				<text class="privacy-subtitle">3. 公开披露：</text>
				<text class="privacy-text">我们不会公开披露您的个人信息，除非获得您的明确同意，或者法律法规另有规定。在公开披露前，我们会对公开披露的内容进行审核，确保披露的信息不会对您的权益造成损害。</text>
<br>
				<text class="privacy-title">七、个人信息的保护</text>
				<br>
				<view class="privacy-list">
					<text class="privacy-item">1. 我们采取技术和管理措施，保护您的个人信息安全，防止信息泄露、丢失、篡改和滥用。这些措施包括但不限于加密存储、访问控制、数据备份、安全审计等。</text>
					<text class="privacy-item">2. 我们会定期对员工进行信息安全培训，提高员工的信息安全意识和保密意识，确保员工遵守相关法律法规和公司制度，妥善处理您的个人信息。</text>
					<text class="privacy-item">3. 尽管我们采取了上述措施，但由于技术和其他原因，无法保证您的个人信息绝对安全。若发生个人信息安全事件，我们将立即启动应急预案，采取措施减少损失，并按照法律法规的要求及时通知您和相关监管部门。</text>
				</view>
<br>
				<text class="privacy-title">八、您的权利</text>
				<br>
				<view class="privacy-list">
					<text class="privacy-item">1. 查阅权：您有权查阅您在本小程序中存储的个人信息，您可以通过本小程序的相关功能或联系我们的客服进行查阅。</text>
					<text class="privacy-item">2. 更正权：如果您发现您的个人信息有误，您有权要求我们进行更正。您可以通过本小程序的相关功能或联系我们的客服进行更正。</text>
					<text class="privacy-item">3. 删除权：在符合法律法规规定的情况下，您有权要求我们删除您的个人信息。如我们的处理行为违反法律法规、超出约定的处理目的和范围等，您可以联系我们的客服提出删除请求。</text>
					<text class="privacy-item">4. 撤回同意权：对于您已同意我们收集和使用的个人信息，您有权随时撤回同意。您可以通过本小程序的相关设置或联系我们的客服撤回同意。但撤回同意可能会影响您继续使用本小程序的部分服务。</text>
					<text class="privacy-item">5. 注销账号权：您有权随时注销您在本小程序上的账号。您可以通过本小程序的相关功能或联系我们的客服进行账号注销。账号注销后，我们将按照法律法规的规定删除或匿名化处理您的个人信息。</text>
				</view>
<br>
				<text class="privacy-title">九、隐私政策的变更</text>
				<br>
				<text class="privacy-text">我们可能会根据法律法规、业务发展等情况适时变更本隐私政策，并在本小程序上进行公示。若您在隐私政策变更后继续使用本小程序服务，视为您同意变更后的隐私政策；若您不同意变更后的隐私政策，应停止使用本小程序服务。</text>
<br>
				<text class="privacy-title">十、联系我们</text><br>
				<view class="privacy-list">
					<text class="privacy-item">• 联系电话：18183825969</text>
					<br>
					<text class="privacy-item">• 电子邮箱：smengkai2000@163.com</text>
					<br>
					<text class="privacy-item">• 联系地址：云南省昆明市盘龙区西南林业大学</text>
				</view>
				<br>
				<text class="privacy-text">我们将在收到您的反馈后尽快处理，并在合理期限内回复您。</text>
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
			.privacy-content {
				padding: 10px;
				.privacy-title {
					font-size: 16px;
					font-weight: bold;
					margin-top: 10px;
				}
				.privacy-text {
					font-size: 14px;
					line-height: 1.5;
					margin-top: 5px;
				}
				.privacy-list {
					.privacy-item {
						font-size: 14px;
						line-height: 1.5;
						margin-top: 5px;
					}
				}
				.privacy-subtitle {
					font-size: 15px;
					font-weight: bold;
					margin-top: 10px;
				}
			}
		}
	}
</style>