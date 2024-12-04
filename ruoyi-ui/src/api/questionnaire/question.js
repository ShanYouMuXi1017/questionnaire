import request from '@/utils/request'

// 查询问卷问题列表
export function listQuestion(query) {
  return request({
    url: '/questionnaire/question/list',
    method: 'get',
    params: query
  })
}

// 查询问卷问题详细
export function getQuestion(issueId) {
  return request({
    url: '/questionnaire/question/' + issueId,
    method: 'get'
  })
}

// 新增问卷问题
export function addQuestion(data) {
  return request({
    url: '/questionnaire/question',
    method: 'post',
    data: data
  })
}

// 修改问卷问题
export function updateQuestion(data) {
  return request({
    url: '/questionnaire/question',
    method: 'put',
    data: data
  })
}

// 删除问卷问题
export function delQuestion(issueId) {
  return request({
    url: '/questionnaire/question/' + issueId,
    method: 'delete'
  })
}

// 查询上传的excel路径
export function getFilePath() {
  return request({
    url: '/questionnaire/question/getFilePath/',
    method: 'get',
  })
}
