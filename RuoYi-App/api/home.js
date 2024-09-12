import request from '@/utils/request'

// 查询公告列表, 获取最新一条公告 
export function getLastAnnouncement() {
	return request({
		url: '/system/notice/announcement',
		method: 'get',
	})
}

//获得通知列表(非公告)
export function listNotice() {
	return request({
		url: '/system/notice/notice',
		method: 'get',
	})
}

//获得通知列表(非公告)
export function getNotification(noticeId) {
	return request({
		url: '/system/notice/mp/' + noticeId,
		method: 'get',
	})
}

//获取首页轮播图
export function listBanner(query) {
  return request({
    url: '/system/banner/list',
    method: 'get',
	params: query
  })
}

//获得通研发页面文字内容
export function getRAndDNotification(noticeType) {
	return request({
		url: '/system/notice/rAndD/' + noticeType,
		method: 'get',
	})
}

