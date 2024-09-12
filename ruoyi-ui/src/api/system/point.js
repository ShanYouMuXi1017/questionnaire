import request from '@/utils/request'

// 查询积分主表列表
export function listPoint(query) {
  return request({
    url: '/system/point/list',
    method: 'get',
    params: query
  })
}

// 查询积分主表详细
export function getPoint(userId) {
  return request({
    url: '/system/point/' + userId,
    method: 'get'
  })
}


// 查询积分获取明细列表
export function costPoint(userId) {
  return request({
    url: '/system/point/cost/'+userId+"/",
    method: 'get',
  })
}


// 查询积分使用明细列表
export function spendPoint(userId) {
  return request({
    url: '/system/point/spend/'+userId+"/",
    method: 'get',
  })
}

