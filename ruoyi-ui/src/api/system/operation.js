import request from '@/utils/request'

// 查询积分提现明细列表
export function listOperation(query) {
  return request({
    url: '/system/operation/list',
    method: 'get',
    params: query
  })
}

// 查询积分提现明细详细
export function getOperation(userId) {
  return request({
    url: '/system/operation/' + userId,
    method: 'get'
  })
}

// 新增积分提现明细
export function addOperation(data) {
  return request({
    url: '/system/operation',
    method: 'post',
    data: data
  })
}

// // 修改积分提现明细
// export function updateOperation(data) {
//   return request({
//     url: '/system/operation',
//     method: 'put',
//     data: data
//   })
// }
//
// // 删除积分提现明细
// export function delOperation(id) {
//   return request({
//     url: '/system/operation/' + id,
//     method: 'delete'
//   })
// }
