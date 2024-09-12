// 分销相关接口
import upload from '@/utils/upload'
import request from '@/utils/request'

//初始化分销首页数据
export function getInfo(userId){
	return request({
		url:'/system/address/user/'+userId,
		method: 'get'
	})
}
 