import config from '@/config'
import storage from '@/utils/storage'
import constant from '@/utils/constant'
import {
	login,
	logout,
	getInfo
} from '@/api/login'
import {
	getToken,
	setToken,
	removeToken
} from '@/utils/auth'



const baseUrl = config.baseUrl

const user = {
	state: {
		token: getToken(),
		userId: storage.get(constant.userId),
		name: storage.get(constant.name),
		avatar: storage.get(constant.avatar),
		roles: storage.get(constant.roles),
		permissions: storage.get(constant.permissions),
		nickName: storage.get(constant.nickName)
	},

	mutations: {
		SET_TOKEN: (state, token) => {
			state.token = token
		},
		SET_USERID: (state, userId) => {
			state.userId = userId
			storage.set(constant.userId, userId)
		},
		SET_NAME: (state, name) => {
			state.name = name
			storage.set(constant.name, name)
		},
		SET_AVATAR: (state, avatar) => {
			state.avatar = avatar
			storage.set(constant.avatar, avatar)
		},
		SET_ROLES: (state, roles) => {
			state.roles = roles
			storage.set(constant.roles, roles)
		},
		SET_PERMISSIONS: (state, permissions) => {
			state.permissions = permissions
			storage.set(constant.permissions, permissions)
		},
		SET_NICKNAME: (state, nickName) => {
			state.nickName = nickName
			storage.set(constant.nickName, nickName)
		}
	},

	actions: {
		// 登录
		Login({
			commit
		}, userInfo) {
			const username = userInfo.username.trim()
			const password = userInfo.password
			const code = userInfo.code.trim()
			const uuid = userInfo.uuid
			return new Promise((resolve, reject) => {
				login(username, password, code, uuid).then(res => {
					setToken(res.token)
					commit('SET_TOKEN', res.token)
					resolve()
				}).catch(error => {
					reject(error)
				})
			})
		},

		// 获取用户信息
		GetInfo({
			commit,
			state
		}) {
			return new Promise((resolve, reject) => {
				getInfo().then(res => {
					const user = res.user
					commit('SET_USERID', user.userId)
					const avatar = (user == null || user.avatar == "" || user.avatar == null) ?
						require("@/static/images/profile.jpg") : baseUrl + user.avatar;
					commit('SET_AVATAR', avatar)

					var nickName = user.nickName;
					commit('SET_NICKNAME', nickName);
					const username = (user == null || user.userName == "" || user.userName ==
						null) ? "" : user.userName
					commit('SET_NAME', username)
					if (res.roles && res.roles.length > 0) {
						commit('SET_ROLES', [])
						commit('SET_ROLES', res.user.roles)
						commit('SET_PERMISSIONS', res.permissions)
					} else {
						commit('SET_ROLES', ['ROLE_DEFAULT'])
					}
					resolve(res)
				}).catch(error => {
					reject(error)
				})
			})
		},

		// 退出系统
		LogOut({
			commit,
			state
		}) {
			return new Promise((resolve, reject) => {
				logout(state.token).then(() => {
					commit('SET_NAME', '')
					commit('SET_TOKEN', '')
					commit('SET_ROLES', [])
					commit('SET_PERMISSIONS', [])
					removeToken()
					storage.clean()
					resolve()
				}).catch(error => {
					reject(error)
				})
			})
		}
	}
}

export default user
