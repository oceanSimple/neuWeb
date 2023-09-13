import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import path from 'path'
// 引入svg图标插件
import { createSvgIconsPlugin } from 'vite-plugin-svg-icons'

export default defineConfig({
  plugins: [
    vue(),
    // 注册svg图标插件
    createSvgIconsPlugin({
      // 配置需要自动导入的svg文件路径
      iconDirs: [path.resolve(process.cwd(), 'src/assets/icon')],
      // 配置symbolId格式
      symbolId: 'icon-[dir]-[name]',
    }),
  ],
  resolve: {
    // 相对路径别名配置，使用 @ 代替 src
    alias: {
      '@': path.resolve('./src'),
    },
  },
  // scss全局变量配置
  css: {
    preprocessorOptions: {
      scss: {
        javascriptEnabled: true,
        additionalData: '@import "./src/style/variable.scss";',
      },
    },
  },
  // 跨域配置
  server: {
    proxy: {
      '/api': {
        target: 'http://localhost:8000',
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api/, ''),
      },
    },
  },
})
