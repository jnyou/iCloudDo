import request from '@/utils/request'
const api_name = '/eduvod/video'

export default {

  getPlayAuth(vid) {
    return request({
      url: `${api_name}/getPlayAuth/${vid}`,
      method: 'GET'
    })
  }

}