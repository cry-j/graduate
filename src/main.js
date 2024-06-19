import './assets/global.css'

import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import VueAxios from 'vue-axios'
import axios from 'axios'
import store from './store/index.js'

//导入ElementPlus组件功能和样式
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
// 导入图标库
import * as ElementPlusIconsVue from '@element-plus/icons-vue'

const app = createApp(App)
app.use(VueAxios, axios)
app.use(ElementPlus)
app.use(store)
app.provide('axios', axios) 
//使用迭代循环注册图标
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
  }
//这句不写, 组件里无法注入全局axios, 也就无法使用
app.use(router)
app.mount('#app')
