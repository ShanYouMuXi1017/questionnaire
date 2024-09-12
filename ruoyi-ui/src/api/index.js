import request from "@/utils/request";

export function getByMonth() {
  return request({
    url: '/echarts/getByMonth',
    method: 'get',
  })
}
export function getByYear() {
  return request({
    url: '/echarts/getByYear',
    method: 'get',
  })
}
export function getByPerMonth() {
  return request({
    url: '/echarts/getByPerMonth',
    method: 'get',
  })
}
//申请表中未处理、已同意、已拒绝的订单数
export function getStForRequests() {
  return request({
    url: '/echarts/getStForRequests',
    method: 'get',
  })
}

//检测表中未处理、已同意、已拒绝的订单数
export function getForInspect() {
  return request({
    url: '/echarts/getForInspect',
    method: 'get',
  })
}

//维修表中未处理、已同意、已拒绝的订单数
export function getForRepair() {
  return request({
    url: '/echarts/getForRepair',
    method: 'get',
  })
}

//交付表中已交付和未交付的订单数
export function getForDelivery() {
  return request({
    url: '/echarts/getForDelivery',
    method: 'get',
  })
}

//查询user表中代理商和非代理商的人数
export function getAgentNum() {
  return request({
    url: '/echarts/getAgentNum',
    method: 'get',
  })
}

//查询申请表中当天的订单
export function getBusiness() {
  return request({
    url: '/echarts/getWeakBusiness',
    method: 'get',
  })
}

//获取本月业务量
export function getBusMonth() {
  return request({
    url: '/echarts/getBusinessByMonth',
    method: 'get',
  })
}


//获取本月业务量
export function getBusYear() {
  return request({
    url: '/echarts/getBusinessByYear',
    method: 'get',
  })
}

//查询技术文档数量
export function getDocuCount() {
  return request({
    url: '/echarts/getDocumentCount',
    method: 'get',
  })
}
