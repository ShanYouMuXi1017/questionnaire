import request from '@/utils/request'

// 查询积分参数列表
export function listValue(query) {
  return request({
    url: '/system/value/list',
    method: 'get',
    params: query
  })
}

// 查询积分参数详细
export function getValue(pointId) {
  return request({
    url: '/system/value/' + pointId,
    method: 'get'
  })
}

// 新增积分参数
export function addValue(data) {
  return request({
    url: '/system/value',
    method: 'post',
    data: data
  })
}

// 修改积分参数
export function updateValue(data) {
  return request({
    url: '/system/value',
    method: 'put',
    data: data
  })
}

// 删除积分参数
export function delValue(pointId) {
  return request({
    url: '/system/value/' + pointId,
    method: 'delete'
  })
}
