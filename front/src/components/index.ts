import * as ElementPlusIconVue from '@element-plus/icons-vue'
// @ts-ignore
import SvgIcon from './svgIcon/index.vue'
import { Component } from 'vue' // svg component
const components: { [name: string]: Component } = { SvgIcon }
export default {
  install: (app: any) => {
    // 注册自定义全局组件
    Object.keys(components).forEach((key) => {
      app.component(key, components[key])
    })
    //将element-plus提供图标注册为全局组件
    for (const [key, component] of Object.entries(ElementPlusIconVue)) {
      app.component(key, component)
    }
  },
}
