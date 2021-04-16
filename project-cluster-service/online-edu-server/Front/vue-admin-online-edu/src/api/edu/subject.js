import request from '@/utils/request' // 引入axios，此处封装了axios的请求和响应的结果信息

// 查询一级二级分类的树
export default {
    getSujectList(){
        return request({
            // 请求地址
            // 拼接参数  两种方式  1、 url: '/table/list'+ current + "/" + limit  
            // 2、↓   使用飘的符号来处理字符串
            url: `/eduservice/subject/subjectCategoryListTree`, 
            method: 'GET'
        })
    }
    
}
  