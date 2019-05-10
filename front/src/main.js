import Vue from 'vue'
import App from './App.vue'
import Antd from 'ant-design-vue'
import 'ant-design-vue/dist/antd.css'
import 'bootstrap'
import "bootstrap/dist/css/bootstrap.css"

import 'bootstrap/js/dist/util'
import 'bootstrap/js/dist/alert'

Vue.use(Antd)

Vue.config.productionTip = false


new Vue({
    render: h => h(App),
}).$mount('#app')
