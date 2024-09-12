import request from '@/utils/request'

// 查询分销参数 列表
export function listParam(query) {
  return request({
    url: '/system/param/list',
    method: 'get',
    params: query
  })
}

// 查询分销参数 详细
export function getParam(paramId) {
  return request({
    url: '/system/param/' + paramId,
    method: 'get'
  })
}

// 新增分销参数
export function addParam(data) {
  return request({
    url: '/system/param',
    method: 'post',
    data: data
  })
}

// 修改分销参数
export function updateParam(data) {
  return request({
    url: '/system/param',
    method: 'put',
    data: data
  })
}

// 删除分销参数
export function delParam(paramId) {
  return request({
    url: '/system/param/' + paramId,
    method: 'delete'
  })
}
