import request from '@/utils/request'

// 查询积分包列表
export function listPackage(query) {
  return request({
    url: '/system/package/list',
    method: 'get',
    params: query
  })
}

// // 查询积分包详细
// export function getPackage(id) {
//   return request({
//     url: '/system/package/' + id,
//     method: 'get'
//   })
// }
//
// // 新增积分包
// export function addPackage(data) {
//   return request({
//     url: '/system/package',
//     method: 'post',
//     data: data
//   })
// }

// 修改积分包
export function updatePackage(data) {
  return request({
    url: '/system/package',
    method: 'put',
    data: data
  })
}

// 删除积分包
export function delPackage(id) {
  return request({
    url: '/system/package/' + id,
    method: 'delete'
  })
}
