<template>
	<view class="container">

		<u-divider text="建议使用微信头像和微信昵称" textSize="8"></u-divider>

		<view class="example">
			<uni-forms ref="form" :model="user" labelWidth="80px">
				<uni-forms-item label="头像">
					<view class="avatar-container">
						<button class="avatar-container-button" open-type="chooseAvatar"
							@chooseavatar="onChooseAvatar">点击此处更换为微信头像</button>
						<image class="avatar-container-image" mode="aspectFill" :src="user.avatar"></image>
					</view>
				</uni-forms-item>
				<uni-forms-item label="昵称" name="nickName" required>
					<!-- <uni-easyinput v-model="user.nickName" type="nickname" @change="nickNameChange" placeholder="请输入昵称" /> -->
					<input class="nick-name-input-box" v-model="user.nickName" type="nickname" @change="nickNameChange"
						placeholder="请输入昵称" />
				</uni-forms-item>
				<uni-forms-item label="手机" name="phonenumber" required>
					<uni-easyinput v-model="user.phonenumber" placeholder="请输入手机号码" />
				</uni-forms-item>
				<uni-forms-item label="邮箱" name="email">
					<uni-easyinput v-model="user.email" placeholder="请输入邮箱" />
				</uni-forms-item>
				<uni-forms-item label="性别" name="sex" required>
					<uni-data-checkbox v-model="user.sex" :localdata="sexs" mode="button" />
				</uni-forms-item>
			</uni-forms>
			<button type="primary" @click="submit">提交</button>
		</view>
		<u-divider text="首次完善信息可以获取积分" textSize="10" textColor="#717171"></u-divider>
	</view>
</template>

<script>
	import {
		getUserProfile,
		updateAvatarUrl,
		uploadAvatar,
	} from "@/api/system/user"
	import {
		updateUserProfile
	} from "@/api/system/user"
	import {
		baseUrl
	} from "../../../config"

	export default {
		data() {
			return {
				user: {
					avatar: "",
					nickName: "",
					phonenumber: "",
					email: "",
					sex: ""
				},
				sexs: [{
					text: '男',
					value: "0"
				}, {
					text: '女',
					value: "1"
				}],
				rules: {
					nickName: {
						rules: [{
								required: true,
								errorMessage: '用户昵称不能为空'
							},
							{
								minLength: 2,
								maxLength: 10,
								errorMessage: '昵称长度在 {minLength} 到 {maxLength} 个字符',
							}
						]
					},
					phonenumber: {
						rules: [{
							required: true,
							errorMessage: '手机号码不能为空'
						}, {
							pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
							errorMessage: '请输入正确的手机号码'
						}]
					},
					email: {
						rules: [{
							required: false,
							errorMessage: '邮箱地址不能为空'
						}, {
							format: 'email',
							errorMessage: '请输入正确的邮箱地址'
						}]
					}
				}
			}
		},
		onLoad() {
			this.getUser()
		},
		onReady() {
			this.$refs.form.setRules(this.rules)
		},
		methods: {
			nickNameChange(e) {
				this.user.nickName = e.detail.value
			},

			getUser() {
				getUserProfile().then(response => {
					this.user = response.data
					this.user.avatar = baseUrl + this.user.avatar
				})
			},

			submit(ref) {
				var that = this;
				this.$refs.form.validate().then(res => {
					updateUserProfile(this.user).then(response => {
						this.$modal.msgSuccess("修改成功");
						that.$store.state.user.nickName = that.user.nickName;
						  setTimeout(() => {
						    uni.switchTab({
						      url: '/pages/mine/index'
						    });
						  }, 1000);
					})
				})
			},

			onChooseAvatar(res) {
				// console.log(res)
				// console.log(res.detail.avatarUrl)
				let avatar = res.target.avatarUrl;
				if (avatar == null || avatar == "" || avatar == undefined) {
					uni.showToast({
						title: "未通过微信验证!"
					})
					return;
				}

				uploadAvatar({
					name: "avatarfile",
					filePath: avatar
				}).then((res => {
					if (res.code == 200) {
						this.$store.state.user.avatar = baseUrl + res.imgUrl;
						this.user.avatar = baseUrl + res.imgUrl;
						uni.showToast({
							title: "头像更新成功!"
						})
					}
				})).catch(err => {
					uni.showToast({
						title: "服务异常, 更新失败!"
					})
					console.error(err)
				})

				// 下载头像图片
				// wx.downloadFile({
				// 	url: avatar,
				// 	success(res) {
				// 		console.log(res)
				// 	},
				// 	fail(err) {
				// 		console.warn(err)
				// 	}
				// })


			},
		}
	}
</script>

<style lang="scss">
	page {
		background-color: #ffffff;
	}

	.example {
		padding: 15px;
		background-color: #fff;
	}

	.segmented-control {
		margin-bottom: 15px;
	}

	.button-group {
		margin-top: 15px;
		display: flex;
		justify-content: space-around;
	}

	.form-item {
		display: flex;
		align-items: center;
		flex: 1;
	}

	.button {
		display: flex;
		align-items: center;
		height: 35px;
		line-height: 35px;
		margin-left: 10px;
	}

	// //头像部分
	.avatar-container {
		width: 100%;
		height: 75rpx;
		display: flex;
		justify-content: space-between;
		overflow: hidden;

		.avatar-container-button {
			text-align: left;
			color: #ACACAC;
			font-size: medium;
			flex: 1;
			background-color: #FAFAFA;

		}

		.avatar-container-image {
			border: solid #ACACAC 1rpx;
			border-radius: 15rpx;
			width: 75rpx !important;
			height: 75rpx !important;
		}
	}

	.nick-name-input-box {
		border: 1rpx solid #dadbde;
		padding: 12rpx 18rpx;
		border-radius: 8rpx;
		height: 75rpx;
		line-height: 75rpx;
		display: flex;
		flex-direction: column;
		justify-content: center;
	}
</style>