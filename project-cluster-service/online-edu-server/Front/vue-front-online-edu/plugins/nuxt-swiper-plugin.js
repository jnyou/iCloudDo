import Vue from 'vue'
import VueAwesomeSwiper from 'vue-awesome-swiper/dist/ssr' // 轮播图组件
import VueQriously from 'vue-qriously' // 微信二维码支付组件
import ElementUI from 'element-ui' //element-ui的全部组件
import 'element-ui/lib/theme-chalk/index.css'//element-ui的css
Vue.use(ElementUI) //使用elementUI
Vue.use(VueQriously) // 使用微信二维码支付
Vue.use(VueAwesomeSwiper) // 使用轮播图