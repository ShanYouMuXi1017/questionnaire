import request from '@/utils/request'

// 查询路线问卷列表
export function listRouter(query) {
  return request({
    url: '/questionnaire/router/list',
    method: 'get',
    params: query
  })
}

// 查询路线问卷详细
export function getRouter(routerId) {
  return request({
    url: '/questionnaire/router/' + routerId,
    method: 'get'
  })
}

// 新增路线问卷
export function addRouter(data) {
  return request({
    url: '/questionnaire/router',
    method: 'post',
    data: data
  })
}

// 修改路线问卷
export function updateRouter(data) {
  return request({
    url: '/questionnaire/router',
    method: 'put',
    data: data
  })
}

// 删除路线问卷
export function delRouter(routerId) {
  return request({
    url: '/questionnaire/router/' + routerId,
    method: 'delete'
  })
}
