// 分销相关接口
import upload from '@/utils/upload'
import request from '@/utils/request'

//初始化分销首页数据
export function getData(){
	return request({
		url:'/system/distri/distriData',
		method: 'get'
	})
}


//初始化下级明细页面的数据
export function queryAgentCount(){
	return request({
		url:'/system/distri/agentData',
		method: 'get'
	})
}

//下级明细
export function queryAgent(){
	return request({
		url:'/system/distri/agent',
		method: 'get'
	})
}


//所有代理商明细
export function queryDistris(){
	return request({
		url:'/system/distri/distris',
		method: 'get'
	})
}

//传入一个id，判断这名用户是不是代理商
export function isAgent(id) {
    return request({
        url: '/system/distri/isAgent',
        method: 'get'
    })
}

//传入一个id，判断这名用户是不是代理商
export function isAgent2(pid) {
    return request({
        url: '/system/distri/isAgent2/'+pid+'/',
        method: 'get'
    })
}

// 修改用户个人信息
export function getSup() {
	return request({
		url: '/system/distri/getSup',
		method: 'get',
	})
}

// 修改用户个人信息
export function getBase64() {
	return request({
		url: '/system/distri/getBase64',
		method: 'get',
	})
}


// 更新某名用户的上级信息
export function updateSup(userId,pid) {
  return request({
    url: '/system/distri/updateSup/' + userId+'/'+pid+'/',
    method: 'put'
  })
}