// 分销相关接口
import upload from '@/utils/upload'
import request from '@/utils/request'


//初始化优惠券首页数据
export function getCoupon(){
	return request({
		url:'/system/coupon_cost/getCoupon',
		method: 'get'
	})
}

//面值最大的券
export function getMaxCoupon(){
	return request({
		url:'/system/coupon_cost/getMaxCoupon',
		method: 'get'
	})
}


//处理优惠券确认领取问题
export function handleCoupon(){
	return request({
		url:'/system/coupon_cost/handleCoupon',
		method: 'post'
	})
}
