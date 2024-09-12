import request from '@/utils/request'



// 查询文档列表
export function listPreview(query) {
    return request({
        url: '/system/preview/getList',
        method: 'get',
        params: query
    })
}

// 获取域名地址
export function getBaseUrl() {
  return request({
    url: '/system/preview/getUrl',
    method: 'get'
  })
}

