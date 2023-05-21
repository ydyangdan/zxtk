import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import '@/assets/css/global.css'
import zhCn from 'element-plus/dist/locale/zh-cn.mjs'
import echarts from 'echarts'



// main.js:注册所有图标
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
var app = createApp(App)
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}
app.use(store).use(router).use(ElementPlus,{locale: zhCn,}).mount('#app');



