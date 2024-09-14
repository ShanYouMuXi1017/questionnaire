import request from '@/utils/request'

// 查询回答表列表
export function listAnswer(query) {
  return request({
    url: '/questionnaire/answer/list',
    method: 'get',
    params: query
  })
}

// 查询回答表详细
export function getAnswer(id) {
  return request({
    url: '/questionnaire/answer/' + id,
    method: 'get'
  })
}

// 新增回答表
export function addAnswer(data) {
  return request({
    url: '/questionnaire/answer',
    method: 'post',
    data: data
  })
}

// 修改回答表
export function updateAnswer(data) {
  return request({
    url: '/questionnaire/answer',
    method: 'put',
    data: data
  })
}

// 删除回答表
export function delAnswer(id) {
  return request({
    url: '/questionnaire/answer/' + id,
    method: 'delete'
  })
}
