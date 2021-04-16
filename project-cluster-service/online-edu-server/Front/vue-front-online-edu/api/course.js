import request from '@/utils/request'
export default {
   // 查询课程分页列表
  getPageList(page, size, searchObj) {
    return request({
      url: `/eduservice/course/front/getFrontCourseListByPage/${page}/${size}`,
      method: 'POST',
      data: searchObj
    })
  },
  // 获取课程分类列表
  getNestedTreeList() {
    return request({
      url: `/eduservice/subject/subjectCategoryListTree`,
      method: 'GET'
    })
  },
  getById(courseId) {
    return request({
        url: `/eduservice/course/front/${courseId}`,
        method: 'GET'
    })
  }
}