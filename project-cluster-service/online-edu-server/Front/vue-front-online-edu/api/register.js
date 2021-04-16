import request from '@/utils/request'

export default {
  //根据手机号码发送短信
  getMobile(mobile) {
    return request({
      url: `/edusms/sms/send/${mobile}`,
      method: 'GET'
    })
  },
  //用户注册
  submitRegister(registerVo) {
    return request({
      url: `/ucenterservice/ucenter/register`,
      method: 'POST',
      data: registerVo
    })
  }
}