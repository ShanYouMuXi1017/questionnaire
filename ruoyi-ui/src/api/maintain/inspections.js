import request from '@/utils/request'

// 按条件查询所有产品
export function selectInspection(query) {
  return request({
    url: '/maintain/inspections/select',
    method: 'get',
    params: query
  })
}

export function getInspection(id) {
  return request({
    url: '/maintain/inspections/' + id,
    method: 'get'
  })
}

// 查询已检测的检测单
export function listInspections(query) {
  return request({
    url: '/maintain/inspections/list',
    method: 'get',
    params: query
  })
}

// 查询新的检测单
export function getNewInspections() {
  return request({
    url: '/maintain/inspections/newList',
    method: 'get'
  })
}

// 添加新的检测单
export function addInspections(data) {
  return request({
    url: '/maintain/inspections',
    method: 'post',
    data: data
  })
}
// 修改检测单状态
export function updateStatus(data) {
  return request({
    url: '/maintain/inspections/changeStatus',
    method: 'put',
    data: data
  })
}


// 修改检测单
export function updateInspection(data) {
  return request({
    url: '/maintain/inspections',
    method: 'put',
    data: data
  })
}
// 删除检测单
export function delInspection(inspectionId) {
  return request({
    url: '/maintain/inspections/' + inspectionId,
    method: 'delete'
  })
}
