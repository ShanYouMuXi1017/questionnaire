// 积分相关接口
import upload from '@/utils/upload'
import request from '@/utils/request'

//查询用户积分余额
export function getUserPoint(){
	return request({
		url:'/system/point/pointBalance',
		method: 'get'
	})
}


//查询获取积分的方式
export function getTask(){
	return request({
		url:'/system/value/list2',
		method: 'get'
	})
}


//查询积分获取明细
export function getDetail(id) {
    return request({
        url: '/system/point/detail/'+id,
        method: 'get'
    })
}

//查询积分使用明细
export function getDetail2(id) {
    return request({
        url: '/system/point/detail2/'+id,
        method: 'get'
    })
}

//处理积分任务----添加到积分明细表
//参数：任务id，用户id，返回：对应任务的值point_value
export function handlePointTask(data) {
	return request({
		url: '/system/point',
		method: 'post',
		data:data
	})
}

// 查询积分包列表
export function listPackage() {
  return request({
    url: '/system/package/list2',
    method: 'get',
    params: null
  })
}

export function getTempBili(pointId) {
  return request({
    url: '/system/value/' + pointId,
    method: 'get'
  })
}


export function queryBaiFang() {
  return request({
    url: '/system/visit/query',
    method: 'get'
  })
}
