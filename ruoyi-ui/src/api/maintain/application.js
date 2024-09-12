import request from '@/utils/request'

// 按条件查询所有申请单
export function selectApplication(query) {
  return request({
    url: '/maintain/application/select',
    method: 'get',
    params: query
  })
}
// 按ID查找申请单
export function getApplication(id) {
  return request({
    url: '/maintain/application/' + id,
    method: 'get'
  })
}

// 查询所有申请单
export function listApplication(query) {
  return request({
    url: '/maintain/application/list',
    method: 'get',
    params: query
  })
}

// 查询新的申请单
export function getNewApplication() {
  return request({
    url: '/maintain/application/newList',
    method: 'get'
  })
}

// 查询可以入库的申请单
export function rukuProduct() {
  return request({
    url: '/maintain/application/rukuList',
    method: 'get'
  })
}


// 添加新的申请单
export function addApplication(data) {
  return request({
    url: '/maintain/application',
    method: 'post',
    data: data
  })
}

// 修改申请单状态
export function updateStatus(data) {
  return request({
    url: '/maintain/application/changeStatus',
    method: 'put',
    data: data
  })
}

// 修改申请单
export function updateApplication(data) {
  return request({
    url: '/maintain/application',
    method: 'put',
    data: data
  })
}

// 删除申请单
export function delApplication(applicationId) {
  return request({
    url: '/maintain/application/' + applicationId,
    method: 'delete'
  })
}
