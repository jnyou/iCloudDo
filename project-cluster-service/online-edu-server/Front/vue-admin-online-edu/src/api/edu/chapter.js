import request from '@/utils/request' // 引入axios，此处封装了axios的请求和响应的结果信息

export default {
    // 根据课程ID查询课程大纲列表
    getChapterVedioByCourseId(courseId){
        return request({
            // 请求地址
            // 拼接参数  两种方式  1、 url: '/table/list'+ current + "/" + limit  
            // 2、↓   使用飘的符号来处理字符串
            url: `/eduservice/chapter/getChapterVideo/${courseId}`, 
            method: 'GET'
        })
    },
    // 根据ID查询章节信息
    getChapterByChapterId(chapterId){
        return request({
            // 请求地址
            // 拼接参数  两种方式  1、 url: '/table/list'+ current + "/" + limit  
            // 2、↓   使用飘的符号来处理字符串
            url: `/eduservice/chapter/getChapterInfo/${chapterId}`, 
            method: 'GET'
        })
    },
    // 添加章节
    addChapterInfo(chapter){
        return request({
            // 请求地址
            // 拼接参数  两种方式  1、 url: '/table/list'+ current + "/" + limit  
            // 2、↓   使用飘的符号来处理字符串
            url: `/eduservice/chapter/addChapterInfo`, 
            method: 'POST',
            data : chapter
        })
    },
    // 修改章节
    updateChapterInfo(chapter){
        return request({
            // 请求地址
            // 拼接参数  两种方式  1、 url: '/table/list'+ current + "/" + limit  
            // 2、↓   使用飘的符号来处理字符串
            url: `/eduservice/chapter/updateChapterInfo`, 
            method: 'POST',
            data : chapter
        })
    },
    // 删除章节信息
    deleteChapterInfo(chapterId){
        return request({
            // 请求地址
            // 拼接参数  两种方式  1、 url: '/table/list'+ current + "/" + limit  
            // 2、↓   使用飘的符号来处理字符串
            url: `/eduservice/chapter/deleteChapterInfo/${chapterId}`, 
            method: 'DELETE'
        })
    }

    
}
  