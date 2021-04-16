import request from '@/utils/request'

const api_name = '/eduservice/teacher/front'
export default {
  // 分页获取讲师列表
  getPageList(page, limit) {   
    return request({
      url: `${api_name}/getTeacherFrontPageList/${page}/${limit}`,
      method: 'GET'
    })
  },
  // 根据讲师ID查询讲师信息
  getById(teacherId) {
    return request({
        url: `${api_name}/${teacherId}`,
        method: 'GET'
    })
}
}