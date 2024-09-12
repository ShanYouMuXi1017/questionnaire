import request from '@/utils/request'

// 查询优惠券列表
export function listValue(query) {
  return request({
    url: '/system/coupon_value/list',
    method: 'get',
    params: query
  })
}

// 查询用户列表
export function showUsers0() {
  return request({
    url: '/system/coupon_value/list2',
    method: 'get',
  })
}

// 查询优惠券详细
export function getValue(couponId) {
  return request({
    url: '/system/coupon_value/' + couponId,
    method: 'get'
  })
}

// 新增优惠券
export function addValue(data) {
  return request({
    url: '/system/coupon_value',
    method: 'post',
    data: data
  })
}

// 修改优惠券
export function updateValue(data) {
  return request({
    url: '/system/coupon_value',
    method: 'put',
    data: data
  })
}

// 删除优惠券
export function delValue(couponId) {
  return request({
    url: '/system/coupon_value/' + couponId,
    method: 'delete'
  })
}
