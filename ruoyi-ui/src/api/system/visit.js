import request from '@/utils/request'

// 查询委托拜访列表
export function listVisit(query) {
  return request({
    url: '/system/visit/list',
    method: 'get',
    params: query
  })
}

// 查询委托拜访详细
export function getVisit(id) {
  return request({
    url: '/system/visit/' + id,
    method: 'get'
  })
}

// 新增委托拜访
export function addVisit(data) {
  return request({
    url: '/system/visit',
    method: 'post',
    data: data
  })
}

// 修改委托拜访
export function updateVisit(data) {
  return request({
    url: '/system/visit',
    method: 'put',
    data: data
  })
}

// 删除委托拜访
export function delVisit(id) {
  return request({
    url: '/system/visit/' + id,
    method: 'delete'
  })
}
