import request from '@/utils/request'
export default {
  // 查询前两条banner图
  getBannerList() {
    return request({
      url: `/cmsservice/frontbanner/getAllBanner`,
      method: 'GET'
    })
  }
}