import request from '@/utils/request'

// 查询优惠券明细列表
export function listCoupon_cost(query) {
  return request({
    url: '/system/coupon_cost/list',
    method: 'get',
    params: query
  })
}

// 查询优惠券明细详细
export function getCoupon_cost(costId) {
  return request({
    url: '/system/coupon_cost/' + costId,
    method: 'get'
  })
}

// 新增优惠券明细
export function addCoupon_cost(data) {
  return request({
    url: '/system/coupon_cost',
    method: 'post',
    data: data
  })
}

// 修改优惠券明细
export function updateCoupon_cost(data) {
  return request({
    url: '/system/coupon_cost',
    method: 'put',
    data: data
  })
}

// 删除优惠券明细
export function delCoupon_cost(costId) {
  return request({
    url: '/system/coupon_cost/' + costId,
    method: 'delete'
  })
}
