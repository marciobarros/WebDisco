import Vue from 'vue'
import Axios from 'axios'
import './plugins/vuetify'
import App from './App.vue'

Vue.config.productionTip = false
Axios.defaults.baseURL = 'http://127.0.0.1:8080'

new Vue({
  render: h => h(App)
}).$mount('#app')
