import request from '@/utils/request' // 引入axios，此处封装了axios的请求和响应的结果信息

// 讲师列表（条件分页查询）
export default {
    addCourseInfo(courseInfo){
        return request({
            // 请求地址
            // 拼接参数  两种方式  1、 url: '/table/list'+ current + "/" + limit  
            // 2、↓   使用飘的符号来处理字符串
            url: `/eduservice/course/addCourseInfo`, 
            method: 'POST',
            data: courseInfo
        })
    },
    // 查询所有讲师
    getTeacherList(){
        return request({
            // 请求地址
            // 拼接参数  两种方式  1、 url: '/table/list'+ current + "/" + limit  
            // 2、↓   使用飘的符号来处理字符串
            url: `/eduservice/teacher/findAll`, 
            method: 'GET'
        })
    },
    // 根据课程ID查询课程信息
    getCourseInfoByCourseId(courseId){
        return request({
            url: `/eduservice/course/queryCourseInfoByCourseId/${courseId}`, 
            method: 'GET'
        })
    },
    // 修改课程信息
    updateCourseInfo(courseInfo){
        return request({
            // 请求地址
            // 拼接参数  两种方式  1、 url: '/table/list'+ current + "/" + limit  
            // 2、↓   使用飘的符号来处理字符串
            url: `/eduservice/course/updateCourseInfo`, 
            method: 'POST',
            data: courseInfo
        })
    },

    // 查询课程发布信息
    queryCoursePublishInfo(courseId){
        return request({
            // 请求地址
            // 拼接参数  两种方式  1、 url: '/table/list'+ current + "/" + limit  
            // 2、↓   使用飘的符号来处理字符串
            url: `/eduservice/course/queryCoursePublishInfo/${courseId}`, 
            method: 'GET'
        })
    },
    // 课程的最终发布
    publishCourse(id){
        return request({
            // 请求地址
            // 拼接参数  两种方式  1、 url: '/table/list'+ current + "/" + limit  
            // 2、↓   使用飘的符号来处理字符串
            url: `/eduservice/course/publishCourse/${id}`, 
            method: 'POST'
        })
    },
    // 分页条件查询课程列表
    pageCondition(page,limit,searchObj){
        return request({
            // 请求地址
            // 拼接参数  两种方式  1、 url: '/table/list'+ current + "/" + limit  
            // 2、↓   使用飘的符号来处理字符串
            url: `/eduservice/course/pageCondition/${page}/${limit}`, 
            method: 'GET',
            params: searchObj
        })
    },
    // 删除课程相关的所有信息
    removeById(courseId){
        return request({
            // 请求地址
            // 拼接参数  两种方式  1、 url: '/table/list'+ current + "/" + limit  
            // 2、↓   使用飘的符号来处理字符串
            url: `/eduservice/course/deleteCourse/${courseId}`, 
            method: 'DELETE'
        })
    },
    
}
  