import request from '@/utils/request'

// 查询分销主表列表
export function listDistri(query) {
  return request({
    url: '/system/distri/list',
    method: 'get',
    params: query
  })
}
// 查询分销主表列表
export function listDistri2() {
  return request({
    url: '/system/distri/list2',
    method: 'get',
  })
}

// 查询分销主表详细
export function getDistri(userId) {
  return request({
    url: '/system/distri/' + userId,
    method: 'get'
  })
}

// 更新分销 代理商状态
export function updateDistri(userId,isAgree) {
  return request({
    url: '/system/distri/' + userId+'/'+isAgree+'/',
    method: 'put'
  })
}


// 查询分销明细列表
export function costDistri(userId) {
  return request({
    url: '/system/distri/cost/'+userId+"/",
    method: 'get',
  })
}


// 删除分销明细
export function delCost(userId) {
  return request({
    url: '/system/distri/'+userId+"/",
    method: 'delete',
  })
}

//一键同意所有已达到门槛的用户成为代理商
export function allAgree() {
  return request({
    url: '/system/distri/allAgree',
    method: 'put'
  })
}
