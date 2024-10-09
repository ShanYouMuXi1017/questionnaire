import request from '@/utils/request'

// 查询回答表列表
export function sumEveryoneAnswerRouter(query) {
    return request({
      url: '/questionnaire/router/sumEveryoneAnswerRouter',
      method: 'get',
      params: query
    })
  }
  

  export function countRouter(query) {
    return request({
      url: '/questionnaire/router/countRouter',
      method: 'get',
      params: query
    })
  }
  
  export function avgEveryoneAnswerRouter(query) {
    return request({
      url: '/questionnaire/router/avgEveryoneAnswerRouter',
      method: 'get',
      params: query
    })
  }
  
  export function avgEveryAnswerRouter(query) {
    return request({
      url: '/questionnaire/router/avgEveryAnswerRouter',
      method: 'get',
      params: query
    })
  }
  
  export function avgAnswerRouter() {
    return request({
      url: '/questionnaire/router/avgAnswerRouter',
      method: 'get'
    })
  }
  
  export function selectEveryoneAnswerRouter(query) {
    return request({
      url: '/questionnaire/router/selectEveryoneAnswerRouter',
      method: 'get',
      params: query
    })
  }
  