import request from '@/utils/request'
import data from "@/views/system/dict/data.vue";


// 查询preview列表
export function listPreview(query) {
  return request({
    url: '/system/preview/list',
    method: 'get',
    params: query
  })
}

// 查询preview详细
export function getPreview(id) {
  return request({
    url: '/system/preview/' + id,
    method: 'get'
  })
}

// 新增preview
export function addPreview(data) {
  return request({
    url: '/system/preview',
    method: 'post',
    data: data
  })
}

// 修改preview
export function updatePreview(editData) {
  return request({
    url: '/system/preview',
    method: 'put',
    data: editData
  })
}

// 删除preview
export function delPreview(id) {
  return request({
    url: '/system/preview/' + id,
    method: 'delete'
  })
}

// 获取域名地址
export function getBaseUrl() {
  return request({
    url: '/system/preview/getUrl',
    method: 'get'
  })
}

