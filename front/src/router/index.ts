import { createRouter, createWebHashHistory } from 'vue-router'
import { route } from './routes'
// 创建路由器
const router = createRouter({
  // 路由模式
  history: createWebHashHistory(),
  // 路由地址
  routes: route,
  // 滚动行为
  scrollBehavior: () => ({
    top: 0,
    left: 0,
  }),
})

export default router