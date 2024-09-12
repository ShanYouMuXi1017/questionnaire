<template>
	<view class="container">
		<uni-list>
			<uni-list-item showExtraIcon="true" :extraIcon="{ type: 'person-filled' }" title="昵称" :rightText="user.nickName" />
			<uni-list-item showExtraIcon="true" :extraIcon="{ type: 'contact' }" title="ID" :rightText="user.userId" />
			<uni-list-item showExtraIcon="true" :extraIcon="{ type: 'phone-filled' }" title="手机号码" :rightText="user.phonenumber" />
			<uni-list-item showExtraIcon="true" :extraIcon="{ type: 'email-filled' }" title="邮箱" :rightText="user.email" />
			<uni-list-item showExtraIcon="true" :extraIcon="{ type: 'auth-filled' }" title="职位" :rightText="postGroup" />
			<uni-list-item showExtraIcon="true" :extraIcon="{ type: 'staff-filled' }" title="角色" :rightText="roleGroup" />
			<uni-list-item showExtraIcon="true" :extraIcon="{ type: 'calendar-filled' }" title="创建日期" :rightText="user.createTime" />
			<uni-list-item showExtraIcon="true" :extraIcon="{ type: 'contact' }" title="上级ID" :rightText="sup.pid" />
			<uni-list-item showExtraIcon="true" :extraIcon="{ type: 'contact-filled' }" title="上级用户" :rightText="sup.supNickName" />
		</uni-list>
	</view>
</template>

<script>
import { getSup } from '../../../api/system/distri';
import { getUserProfile } from '@/api/system/user';

export default {
	data() {
		return {
			user: {},
			roleGroup: '',
			postGroup: '',
			sup: {}
		};
	},
	onLoad() {
		this.getSupById();
		this.getUser();
	},
	methods: {
		getUser() {
			getUserProfile().then((response) => {
				this.user = response.data;
				this.roleGroup = response.roleGroup;
				this.postGroup = response.postGroup;
			});
		},
		getSupById() {
			getSup().then((response) => {
				// this.sup.pid = response.data.pid;
				// this.sup.supNickName = response.data.supNickName;
				
				// 判断response.data的pid是否为0，如果是，则设置sup.pid为'暂无上级'，否则使用response.data的pid
				this.sup.pid = response.data.pid === 0 ? '暂无上级' : response.data.pid;
				
				// 判断response.data的supNickName是否为null，如果是，则设置sup.supNickName为'暂无上级'，否则使用response.data的supNickName
				this.sup.supNickName = response.data.supNickName === null ? '暂无上级' : response.data.supNickName;

			});
		}
	}
};
</script>

<style lang="scss">
page {
	background-color: #ffffff;
}
</style>
