import request from '@/utils/request' // 引入axios，此处封装了axios的请求和响应的结果信息

// 讲师列表（条件分页查询）
export default {
    getTeacherListByPage(current,limit,teacherQuery){
        return request({
            // 请求地址
            // 拼接参数  两种方式  1、 url: '/table/list'+ current + "/" + limit  
            // 2、↓   使用飘的符号来处理字符串
            url: `/eduservice/teacher/pageCondition/${current}/${limit}`, 
            method: 'POST',
            // teacherQuery 为条件对象，后端使用@RequestBody获取数据，需要传递json数据格式
            // data 表示将对象转换json数据进行传递到接口中
            data: teacherQuery
        })
    },
    deleteTeacherById(id){
        return request({
            url: `/eduservice/teacher/${id}`, 
            method: 'DELETE'
        })
    },
    addTeacher(teacher){
        return request({
            url: `/eduservice/teacher/addTeacher`, 
            method: 'POST',
            data: teacher
        })
    },
    getTeacherInfoById(id){
        return request({
            url: `/eduservice/teacher/${id}`, 
            method: 'GET'
        })
    },
    updateTeacher(teacher){
        return request({
            url: `/eduservice/teacher/updateTeacher`, 
            method: 'POST',
            data: teacher
        })
    }
}

// export function getList(params) {}
  