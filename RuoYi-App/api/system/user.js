import upload from '@/utils/upload'
import request from '@/utils/request'

// 查询用户基本信息是否填写(骑行)
export function isFillUserBasic(userId) {
	return request({
		url: '/system/user/basic/' + userId,
		method: 'get'
	})
}


// 填写用户基本信息(骑行)
export function fillUserBasic(data) {
	return request({
		url: '/system/user/basic/update',
		method: 'put',
		data:data
	})
}

// 查询路线列表
export function getRouters() {
	return request({
		url: '/system/user/basic/list',
		method: 'get'
	})
}


// 查询用户列表
export function listUser(query) {
	return request({
		url: '/system/user/list',
		method: 'get',
		params: query
	})
}

// 查询用户详细
export function getUser(userId) {
  return request({
    url: '/system/user/' +userId,
    method: 'get'
  })
}

// 用户密码重置
export function updateUserPwd(oldPassword, newPassword) {
	const data = {
		oldPassword,
		newPassword
	}
	return request({
		url: '/system/user/profile/updatePwd',
		method: 'put',
		params: data
	})
}

// 查询用户个人信息
export function getUserProfile() {
	return request({
		url: '/system/user/profile',
		method: 'get'
	})
}

// 修改用户个人信息
export function updateUserProfile(data) {
	return request({
		url: '/system/user/profile',
		method: 'put',
		data: data
	})
}

// 用户头像文件上传
export function uploadAvatar(data) {
	return upload({
		url: '/system/user/profile/avatar',
		name: data.name,
		filePath: data.filePath
	})
}

// 用户头像链接上传
export function updateAvatarUrl(avatar) {
	const data = {
		avatarUrl: avatar
	}
	return request({
		url: '/system/user/profile/avatarUrl',
		method: 'put',
		params: data
	})
}

//获取首页轮播图
export function listBanner(query) {
  return request({
    url: '/system/banner/list',
    method: 'get',
	params: query
  })
}

//获取研发页面轮播图
export function developListBanner(query) {
  return request({
    url: '/system/banner/list',
    method: 'get',
	params: query
  })
}

