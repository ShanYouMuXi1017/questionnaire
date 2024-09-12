import request from '@/utils/request'



// 按条件查询交付单
export function selectDelivery(query) {
  return request({
    url: '/maintain/delivery/select',
    method: 'get',
    params: query
  })
}


// 查询所有新的交付单
export function listNewDelivery() {
  return request({
    url: '/maintain/delivery/newList',
    method: 'get'
  })
}

// 查询所有交付单
export function listDelivery(query) {
  return request({
    url: '/maintain/delivery/list',
    method: 'get',
    params: query
  })
}

// 添加新的交付单
export function addDelivery(data) {
  return request({
    url: '/maintain/delivery',
    method: 'post',
    data: data
  })
}


// 修改维修单
export function updateDelivery(data) {
  return request({
    url: '/maintain/delivery',
    method: 'put',
    data: data
  })
}

// 删除检测单
export function delDelivery(deliveryId) {
  return request({
    url: '/maintain/delivery/' + deliveryId,
    method: 'delete'
  })
}
