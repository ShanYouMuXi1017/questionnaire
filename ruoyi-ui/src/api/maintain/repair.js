import request from '@/utils/request'

// 按条件查询已维修的维修单
export function selectRepair(query) {
  return request({
    url: '/maintain/repair/select',
    method: 'get',
    params: query
  })
}
// 查询已维修的维修单
export function listRepair(query) {
  return request({
    url: '/maintain/repair/list',
    method: 'get',
    params: query
  })
}

export function getRepair(id) {
  return request({
    url: '/maintain/repair/' + id,
    method: 'get'
  })
}

// 查询新的维修单
export function getNewRepair() {
  return request({
    url: '/maintain/repair/newList',
    method: 'get'
  })
}

// 查询正在维修的维修单
export function getRepairing() {
  return request({
    url: '/maintain/repair/repairingList',
    method: 'get'
  })
}

// 添加新的维修单
export function addRepair(data) {
  return request({
    url: '/maintain/repair',
    method: 'post',
    data: data
  })
}
// 修改维修单状态
export function updateStatus(data) {
  return request({
    url: '/maintain/repair/changeStatus',
    method: 'put',
    data: data
  })
}


// 修改维修单
export function updateRepair(data) {
  return request({
    url: '/maintain/repair',
    method: 'put',
    data: data
  })
}

// 删除检测单
export function delRepair(repairId) {
  return request({
    url: '/maintain/repair/' + repairId,
    method: 'delete'
  })
}
