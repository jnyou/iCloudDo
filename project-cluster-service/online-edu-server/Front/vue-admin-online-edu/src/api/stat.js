import request from '@/utils/request'

const api_name = '/staservice/stat'
export default {

    // 生成统计数据
    createStatistics(date) {
        return request({
            url: `${api_name}/${date}`,
            method: 'GET'
        })
    },
    // 获取统计数据
    showChart(searchObj) {
        return request({
            url: `${api_name}/show-chart/${searchObj.begin}/${searchObj.end}/${searchObj.type}`,
            method: 'GET'
        })
    }
}