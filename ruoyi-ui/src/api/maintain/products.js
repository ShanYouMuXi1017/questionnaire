import request from '@/utils/request'

// 按条件查询所有产品
export function selectProduct(query) {
  return request({
    url: '/maintain/products/select',
    method: 'get',
    params: query
  })
}

export function getProduct(id) {
  return request({
    url: '/maintain/products/' + id,
    method: 'get'
  })
}


// 查询所有产品
export function listProduct(query) {
  return request({
    url: '/maintain/products/list',
    method: 'get',
    params: query
  })
}


// 新增产品
export function addProduct(data) {
  return request({
    url: '/maintain/products/',
    method: 'post',
    data: data
  })
}

// 修改产品
export function updateProduct(data) {
  return request({
    url: '/maintain/products/',
    method: 'put',
    data: data
  })
}

// 删除产品
export function delProduct(id) {
  return request({
    url: '/maintain/products/' + id,
    method: 'delete'
  })
}

