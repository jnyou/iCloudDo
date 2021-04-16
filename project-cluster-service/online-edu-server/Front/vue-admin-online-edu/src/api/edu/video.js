import request from '@/utils/request'

const api_name = '/eduservice/video'

export default {

  saveVideoInfo(video) {
    return request({
      url: `${api_name}/addVideoInfo`,
      method: 'POST',
      data: video
    })
  },

  getVideoInfoById(videoId) {
    return request({
      url: `${api_name}/getVideoInfoById/${videoId}`,
      method: 'GET'
    })
  },

  updateVideoInfoById(video) {
    return request({
      url: `${api_name}/updateVideoInfo`,
      method: 'PUT',
      data: video
    })
  },
  // 删除小节
  removeById(videoId) {
    return request({
      url: `${api_name}/deleteVideoInfoById/${videoId}`,
      method: 'DELETE'
    })
  },
  // 删除阿里云视频
  removeVideoAliyun(videoSourceId){
    return request({
      url: `/eduvod/video/deleteVideoAliyun/${videoSourceId}`,
      method: 'DELETE'
    })
  }
}