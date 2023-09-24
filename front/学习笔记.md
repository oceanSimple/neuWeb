# 创建项目

1. 初始化项目文件
   1. 安装pnpm（默认已安装npm）
      ```powershell
      npm install -g pnpm
      ```
   2. 初始化项目
      ```powershell
      pnpm create vite
      pnpm install
      pnpm run dev
      ```
   3. 解决main.ts报错
      > 在src/vite-env.d.ts中添加
      ```typescript
        /// <reference types="vite/client" />
        //解决ts文件引入vue文件出现红色警告问题
        declare module '*.vue' {
        import { defineComponent } from 'vue'
        const Component: ReturnType<typeof defineComponent>
        export default Component
        }
      ```

# 项目格式化配置

1. eslint安装
   1. 安装eslint
      ```powershell
      pnpm install -D eslint
      ```
   2. 初始化eslint配置文件
      ```powershell
      npx eslint --init
      ```
2. vue3插件安装
   ```powershell
   pnpm install -D eslint-plugin-import eslint-plugin-vue eslint-plugin-node eslint-plugin-prettier eslint-config-prettier eslint-plugin-node @babel/eslint-parser
   ```
3. 覆盖eslint配置文件
   ```javascript
   // @see https://eslint.bootcss.com/docs/rules/
   
   module.exports = {
       env: {
           browser: true,
           es2021: true,
           node: true,
           jest: true,
       },
       /* 指定如何解析语法 */
       parser: 'vue-eslint-parser',
       /** 优先级低于 parse 的语法解析配置 */
       parserOptions: {
           ecmaVersion: 'latest',
           sourceType: 'module',
           parser: '@typescript-eslint/parser',
           jsxPragma: 'React',
           ecmaFeatures: {
               jsx: true,
           },
       },
       /* 继承已有的规则 */
       extends: [
           'eslint:recommended',
           'plugin:vue/vue3-essential',
           'plugin:@typescript-eslint/recommended',
           'plugin:prettier/recommended',
       ],
       plugins: ['vue', '@typescript-eslint'],
       /*
        * "off" 或 0    ==>  关闭规则
        * "warn" 或 1   ==>  打开的规则作为警告（不影响代码执行）
        * "error" 或 2  ==>  规则作为一个错误（代码不能执行，界面报错）
        */
       rules: {
           // eslint（https://eslint.bootcss.com/docs/rules/）
           'no-var': 'error', // 要求使用 let 或 const 而不是 var
           'no-multiple-empty-lines': ['warn', { max: 1 }], // 不允许多个空行
           'no-console': process.env.NODE_ENV === 'production' ? 'error' : 'off',
           'no-debugger': process.env.NODE_ENV === 'production' ? 'error' : 'off',
           'no-unexpected-multiline': 'error', // 禁止空余的多行
           'no-useless-escape': 'off', // 禁止不必要的转义字符
   
           // typeScript (https://typescript-eslint.io/rules)
           '@typescript-eslint/no-unused-vars': 'error', // 禁止定义未使用的变量
           '@typescript-eslint/prefer-ts-expect-error': 'error', // 禁止使用 @ts-ignore
           '@typescript-eslint/no-explicit-any': 'off', // 禁止使用 any 类型
           '@typescript-eslint/no-non-null-assertion': 'off',
           '@typescript-eslint/no-namespace': 'off', // 禁止使用自定义 TypeScript 模块和命名空间。
           '@typescript-eslint/semi': 'off',
   
           // eslint-plugin-vue (https://eslint.vuejs.org/rules/)
           'vue/multi-word-component-names': 'off', // 要求组件名称始终为 “-” 链接的单词
           'vue/script-setup-uses-vars': 'error', // 防止<script setup>使用的变量<template>被标记为未使用
           'vue/no-mutating-props': 'off', // 不允许组件 prop的改变
           'vue/attribute-hyphenation': 'off', // 对模板中的自定义组件强制执行属性命名样式
       },
   }
   ```
4. eslintignore配置
   > 生成.eslintignore忽略文件
   ```
   dist
   node_modules
   ```
5. 添加运行脚本
   > 在package.json中添加
   ```
   "scripts": {
      "lint": "eslint src",
      "fix": "eslint src --fix",
   }
   ```
6. prettierrc.json
   > 安装prettier
   ```powershell
   pnpm install -D eslint-plugin-prettier prettier eslint-config-prettier
   ```
   > 创建.prettierrc.json文件
   > 
   > 1. "singleQuote": true, // 使用单引号
   > 2. "semi": false, // 结尾不使用分号
   > 3. "bracketSpacing": true, // 对象字面量的大括号间使用空格（默认true）
   > 4. "htmlWhitespaceSensitivity": "ignore", // 对HTML全局空白不敏感
   > 5. "endOfLine": "auto", // 结尾换行符
   > 6. "trailingComma": "all", // 多行时尽可能打印尾随逗号
   > 7. "tabWidth": 2 // tab缩进大小,默认为2
   ```json
   {
      "singleQuote": true,
      "semi": false,
      "bracketSpacing": true,
      "htmlWhitespaceSensitivity": "ignore",
      "endOfLine": "auto",
      "trailingComma": "all",
      "tabWidth": 2
   }
   ```
7. stylelint配置
   1. 安装stylelint
      ```powershell
      pnpm add sass sass-loader stylelint postcss postcss-scss postcss-html stylelint-config-prettier stylelint-config-recess-order stylelint-config-recommended-scss stylelint-config-standard stylelint-config-standard-vue stylelint-scss stylelint-order stylelint-config-standard-scss -D
      ```
   2. 配置文件
   ```javascript
   // @see https://stylelint.bootcss.com/
   
   module.exports = {
     extends: [
       'stylelint-config-standard', // 配置stylelint拓展插件
       'stylelint-config-html/vue', // 配置 vue 中 template 样式格式化
       'stylelint-config-standard-scss', // 配置stylelint scss插件
       'stylelint-config-recommended-vue/scss', // 配置 vue 中 scss 样式格式化
       'stylelint-config-recess-order', // 配置stylelint css属性书写顺序插件,
       'stylelint-config-prettier', // 配置stylelint和prettier兼容
     ],
     overrides: [
       {
         files: ['**/*.(scss|css|vue|html)'],
         customSyntax: 'postcss-scss',
       },
       {
         files: ['**/*.(html|vue)'],
         customSyntax: 'postcss-html',
       },
     ],
     ignoreFiles: [
       '**/*.js',
       '**/*.jsx',
       '**/*.tsx',
       '**/*.ts',
       '**/*.json',
       '**/*.md',
       '**/*.yaml',
     ],
     /**
      * null  => 关闭该规则
      * always => 必须
      */
     rules: {
       'value-keyword-case': null, // 在 css 中使用 v-bind，不报错
       'no-descending-specificity': null, // 禁止在具有较高优先级的选择器后出现被其覆盖的较低优先级的选择器
       'function-url-quotes': 'always', // 要求或禁止 URL 的引号 "always(必须加上引号)"|"never(没有引号)"
       'no-empty-source': null, // 关闭禁止空源码
       'selector-class-pattern': null, // 关闭强制选择器类名的格式
       'property-no-unknown': null, // 禁止未知的属性(true 为不允许)
       'block-opening-brace-space-before': 'always', //大括号之前必须有一个空格或不能有空白符
       'value-no-vendor-prefix': null, // 关闭 属性值前缀 --webkit-box
       'property-no-vendor-prefix': null, // 关闭 属性前缀 -webkit-mask
       'selector-pseudo-class-no-unknown': [
         // 不允许未知的选择器
         true,
         {
           ignorePseudoClasses: ['global', 'v-deep', 'deep'], // 忽略属性，修改element默认样式的时候能使用到
         },
       ],
     },
   }
   ```
   3. stylelintignore忽略文件
   ```
   /node_modules/*
   /dist/*
   /html/*
   /public/*
   ```
   4. 添加运行脚本
      > 在package.json中添加
   ```
   "scripts": {
     "lint:style": "stylelint src",
     "fix:style": "stylelint src --fix",
   }
   ```
8. husky配置
   > 安装husky
   ```powershell
    pnpm install -D husky
   ```

# 下载项目依赖

## 引入element-plus依赖

1. 安装element-plus
   ```powershell
   pnpm i element-plus
   ```
2. 引入element-plus
   ```typescript
   // 引入element-plus组件和样式
   import ElementPlus from 'element-plus'
   import 'element-plus/dist/index.css'
   //@ts-expect-error忽略当前文件ts类型的检测否则有红色提示(打包会失败)
   import zhCn from 'element-plus/dist/locale/zh-cn.mjs'
   
   app.use(ElementPlus, {
     locale: zhCn,
   })
   ```
3. 下载图标库
   ```powershell
   pnpm i @element-plus/icons-vue
   ```

## src别名@

1. vite.config.ts
   ```typescript
   import { defineConfig } from 'vite'
   import vue from '@vitejs/plugin-vue'
   import path from 'path'
   // https://vitejs.dev/config/
   export default defineConfig({
     plugins: [vue()],
     resolve: {
       alias: {
         '@': path.resolve(__dirname, './src'),
       },
     },
   })
   ```
2. tsconfig.json
   ```json
    // src的别名
    // 解析非相对模块的基地址，默认是当前目录
    "baseUrl": "./",
    //路径映射，相对于baseUrl
    "paths": {
      "@/*": [
        "src/*"
      ]
    },
   ```

## 环境变量的配置

1. 在根目录下生成.env文件
   ```
   .env.development
   .env.production
   .env.test
   ```
2. 配置文件（按顺序写！）
   ```
   # 变量必须以 VITE_ 为前缀才能暴露给外部读取
   NODE_ENV = 'development'
   VITE_APP_TITLE = '硅谷甄选运营平台'
   VITE_APP_BASE_API = '/dev-api'
   ```
   ```
   NODE_ENV = 'production'
   VITE_APP_TITLE = '硅谷甄选运营平台'
   VITE_APP_BASE_API = '/prod-api'
   ```
   ```
   # 变量必须以 VITE_ 为前缀才能暴露给外部读取
   NODE_ENV = 'test'
   VITE_APP_TITLE = '硅谷甄选运营平台'
   VITE_APP_BASE_API = '/test-api'
   ```
3. 配置运行命令：package.json
   ```
    "scripts": {
       "dev": "vite --open",
       "build:test": "vue-tsc && vite build --mode test",
       "build:pro": "vue-tsc && vite build --mode production",
       "preview": "vite preview"
     },
   ```
   
   通过import.meta.env获取环境变量:
console.log(import.meta.env)

## SVG图标配置

1. 安装SVG依赖插件
   ```powershell
   pnpm install vite-plugin-svg-icons -D
   ```
2. 配置vite.config.ts
   ```typescript
   import { defineConfig } from 'vite'
   import vue from '@vitejs/plugin-vue'
   import path from 'path'
   // 引入svg需要的插件
   import { createSvgIconsPlugin } from 'vite-plugin-svg-icons'
   
   export default defineConfig({
    plugins: [
      vue(),
      createSvgIconsPlugin({
        // Specify the icon folder to be cached
        iconDirs: [path.resolve(process.cwd(), 'src/assets/icons')],
        // Specify symbolId format
        symbolId: 'icon-[dir]-[name]',
      }),
    ],
    resolve: {
      alias: {
        '@': path.resolve(__dirname, './src'),
      },
    },
   })
   ```
3. main.ts中引入svg图标
   ```typescript
   // 引入svg图标
   import 'virtual:svg-icons-register'
   ```
4. svg图标使用
   > 在src/assets/icons目录下创建vue.svg文件
   ```html
   <svg>
     <use xlink:href="#icon-vue"></use>
   </svg>
   ```
5. 自定义模板
   > 创建src/components/svgIcon/index.vue
   ```vue
   <template>
     <div>
       <svg :style="{ width, height }">
         <use :fill="color" :xlink:href="prefix + name"></use>
       </svg>
     </div>
   </template>
   
   <script lang="ts" setup>
   // 接收父组件传递的参数
   defineProps({
     // xlink:href的前缀
     prefix: {
       type: String,
       default: '#icon-',
     },
     // 图标名称
     name: String,
     // 颜色
     color: {
       type: String,
       default: '',
     },
     // 大小
     width: {
       type: String,
       default: '50px',
     },
     height: {
       type: String,
       default: '50px',
     },
   })
   </script>
   
   <style scoped></style>
   ```
6. 创建src/components/index.ts
   > 用来批量全局注册组件
   ```typescript
   // 对外暴露插件对象
   import SvgIcon from './SvgIcon/index.vue'
   import type { App, Component } from 'vue'
   
   const components: { [name: string]: Component } = { SvgIcon }
   
   export default {
     install(app: App) {
       Object.keys(components).forEach((key: string) => {
         app.component(key, components[key])
       })
     },
   }
   ```
7. main.ts

```typescript
   import globalComponent from './components/index' // 引入全局组件
app.use(globalComponent) // 注册全局组件
```

8. 使用
   ```
    <SvgIcon color="pink" name="home"></SvgIcon>
   ```

## 集成scss

1. 在src/styles目录下创建一个index.scss文件，当然项目中需要用到清除默认样式，因此在index.scss引入reset.scss
   ```
   @import "./reset.scss";
   ```
2. 在同级目录引用创建reset.scss
   ```
   *,
   *:after,
   *:before {
     box-sizing: border-box;
   
     outline: none;
   }
   
   html,
   body,
   div,
   span,
   applet,
   object,
   iframe,
   h1,
   h2,
   h3,
   h4,
   h5,
   h6,
   p,
   blockquote,
   pre,
   a,
   abbr,
   acronym,
   address,
   big,
   cite,
   code,
   del,
   dfn,
   em,
   img,
   ins,
   kbd,
   q,
   s,
   samp,
   small,
   strike,
   strong,
   sub,
   sup,
   tt,
   var,
   b,
   u,
   i,
   center,
   dl,
   dt,
   dd,
   ol,
   ul,
   li,
   fieldset,
   form,
   label,
   legend,
   table,
   caption,
   tbody,
   tfoot,
   thead,
   tr,
   th,
   td,
   article,
   aside,
   canvas,
   details,
   embed,
   figure,
   figcaption,
   footer,
   header,
   hgroup,
   menu,
   nav,
   output,
   ruby,
   section,
   summary,
   time,
   mark,
   audio,
   video {
     font: inherit;
     font-size: 100%;
   
     margin: 0;
     padding: 0;
   
     vertical-align: baseline;
   
     border: 0;
   }
   
   article,
   aside,
   details,
   figcaption,
   figure,
   footer,
   header,
   hgroup,
   menu,
   nav,
   section {
     display: block;
   }
   
   body {
     line-height: 1;
   }
   
   ol,
   ul {
     list-style: none;
   }
   
   blockquote,
   q {
     quotes: none;
   
     &:before,
     &:after {
       content: '';
       content: none;
     }
   }
   
   sub,
   sup {
     font-size: 75%;
     line-height: 0;
   
     position: relative;
   
     vertical-align: baseline;
   }
   
   sup {
     top: -.5em;
   }
   
   sub {
     bottom: -.25em;
   }
   
   table {
     border-spacing: 0;
     border-collapse: collapse;
   }
   
   input,
   textarea,
   button {
     font-family: inhert;
     font-size: inherit;
   
     color: inherit;
   }
   
   select {
     text-indent: .01px;
     text-overflow: '';
   
     border: 0;
     border-radius: 0;
   
     -webkit-appearance: none;
     -moz-appearance: none;
   }
   
   select::-ms-expand {
     display: none;
   }
   
   code,
   pre {
     font-family: monospace, monospace;
     font-size: 1em;
   }
   ```
3. 在同级目录创建variable.scss
   > 全局变量
   ```scss
   //项目提供scss全局变量
   //定义项目主题颜色
   $color: green;
   //左侧的菜单的宽度
   $base-menu-width: 260px;
   //左侧菜单的背景颜色
   $base-menu-background: #001529;
   $base-menu-min-width: 50px;
   
   // 顶部导航的高度
   $base-tabbar-height: 50px;
   
   //左侧菜单logo高度设置
   $base-menu-logo-height: 50px;
   
   //左侧菜单logo右侧文字大小
   $base-logo-title-fontSize: 20px;
   ```
4. 在vite.config.ts文件配置
   ```typescript
   export default defineConfig((config) => {
       css: {
         preprocessorOptions: {
           scss: {
             javascriptEnabled: true,
             additionalData: '@import "./src/styles/variable.scss";',
           },
         },
       },
       }
   }
   ```
5. 在main.ts引入
   ```typescript
   // 引入模板的全局样式
   import '@/styles/index.scss'
   ```

## mock

1. 安装依赖
   > 注意：将package.json中的版本3.0->2.9.6
   ```powershell
   pnpm install -D vite-plugin-mock mockjs
   ```
2. 在vite.config.ts中配置
   > 改为箭头函数格式！
   ```typescript
   import { defineConfig } from 'vite'
   import vue from '@vitejs/plugin-vue'
   import path from 'path'
   //引入svg需要用到插件
   import { createSvgIconsPlugin } from 'vite-plugin-svg-icons'
   //mock插件提供方法
   import { viteMockServe } from 'vite-plugin-mock'
   
   export default defineConfig(({ command }) => {
     return {
       plugins: [
         vue(),
         createSvgIconsPlugin({
           iconDirs: [path.resolve(process.cwd(), 'src/assets/icons')],
           symbolId: 'icon-[dir]-[name]',
         }),
         viteMockServe({
           localEnabled: command === 'serve', //保证开发阶段可以使用mock接口
         }),
       ],
       resolve: {
         alias: {
           '@': path.resolve('./src'), // 相对路径别名配置，使用 @ 代替 src
         },
       },
       //scss全局变量一个配置
       css: {
         preprocessorOptions: {
           scss: {
             javascriptEnabled: true,
             additionalData: '@import "./src/styles/variable.scss";',
           },
         },
       },
     }
   })
   ```
3. 创建project/mock/user.ts
   ```typescript
   //用户信息数据
   function createUserList() {
     return [
       {
         userId: 1,
         avatar:
           'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif',
         username: 'admin',
         password: '111111',
         desc: '平台管理员',
         roles: ['平台管理员'],
         buttons: ['cuser.detail'],
         routes: ['home'],
         token: 'Admin Token',
       },
       {
         userId: 2,
         avatar:
           'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif',
         username: 'system',
         password: '111111',
         desc: '系统管理员',
         roles: ['系统管理员'],
         buttons: ['cuser.detail', 'cuser.user'],
         routes: ['home'],
         token: 'System Token',
       },
     ]
   }
   
   export default [
     // 用户登录接口
     {
       url: '/api/user/login', //请求地址
       method: 'post', //请求方式
       response: ({ body }) => {
         //获取请求体携带过来的用户名与密码
         const { username, password } = body
         //调用获取用户信息函数,用于判断是否有此用户
         const checkUser = createUserList().find(
           (item) => item.username === username && item.password === password,
         )
         //没有用户返回失败信息
         if (!checkUser) {
           return { code: 201, data: { message: '账号或者密码不正确' } }
         }
         //如果有返回成功信息
         const { token } = checkUser
         return { code: 200, data: { token } }
       },
     },
     // 获取用户信息
     {
       url: '/api/user/info',
       method: 'get',
       response: (request) => {
         //获取请求头携带token
         const token = request.headers.token
         //查看用户信息是否包含有次token用户
         const checkUser = createUserList().find((item) => item.token === token)
         //没有返回失败的信息
         if (!checkUser) {
           return { code: 201, data: { message: '获取用户信息失败' } }
         }
         //如果有返回成功信息
         return { code: 200, data: { checkUser } }
       },
     },
   ]
   ```
4. 引入axios后进行测试
   ```typescript
   import { createApp } from 'vue'
   
   import App from '@/App.vue'
   // 引入element-plus组件和样式
   import ElementPlus from 'element-plus'
   import 'element-plus/dist/index.css'
   //@ts-expect-error忽略当前文件ts类型的检测否则有红色提示(打包会失败)
   import zhCn from 'element-plus/dist/locale/zh-cn.mjs'
   import 'virtual:svg-icons-register' // 引入svg图标
   import globalComponent from './components/index' // 引入全局组件
   // 引入模板的全局样式
   import '@/styles/index.scss'
   // 引入axios
   import axios from 'axios'
   
   axios({
     url: '/api/user/login',
     method: 'post',
     data: {
       username: 'admin',
       password: '111111',
     },
   })
   const app = createApp(App)
   app.use(ElementPlus, {
     locale: zhCn,
   })
   
   app.use(globalComponent)
   
   app.mount('#app')
   
   ```

# axios二次封装

1. 安装axios
   ```powershell
    pnpm install axios
   ```
2. src/utils/request.ts
   ```typescript
   // 进行axios的二次封装
   import axios from 'axios'
   import { ElMessage } from 'element-plus'
   // 第一步：利用axios.create创建一个axios实例
   const request = axios.create({
     baseURL: import.meta.env.VITE_BASE_API, // 基础路径,通过环境变量获取
     timeout: 5000, // 超时时间
   })
   // 第二步：请求拦截器
   request.interceptors.request.use((config) => {
     // config 本次请求的配置对象，可以修改
     return config
   })
   // 第三步：响应拦截器
   request.interceptors.response.use(
     (response) => {
       // response 响应数据
       return response.data
     },
     (error) => {
       // error 错误信息
       let message = ''
       const status = error.response.status
       switch (status) {
         case 400:
           message = '请求错误'
           break
         case 401:
           message = '未授权的访问'
           break
         case 403:
           message = '禁止访问'
           break
         case 404:
           message = '资源不存在，请求地址错误'
           break
         case 500:
           message = '服务器内部错误'
           break
         default:
           message = '网络错误'
           break
       }
       return ElMessage({
         type: 'error',
         message,
       })
   
       return Promise.reject(error)
     },
   )
   
   // 第四步：导出request
   export default request
   
   ```
3. 统一管理api接口
   > src/api/user/index.ts
   ```typescript
   // 统一管理用户相关的api接口
   import request from '@/utils/request'
   import type {
     loginForm,
     loginResponseData,
     userResponseData,
   } from '@/api/user/type'
   
   enum Api {
     LOGIN_URL = '/user/login',
     USERINFO_URL = '/user/info',
   }
   
   // 对外暴露请求函数
   export const reqLogin = (data: loginForm) =>
     request.post<any, loginResponseData>(Api.LOGIN_URL, data)
   export const reqUserInfo = () =>
     request.get<any, userResponseData>(Api.USERINFO_URL)
   
   ```
   > src/api/user/type.ts
   ```typescript
   // 登录接口的ts类型
   export interface loginForm {
     username: string
     password: string
   }
   
   // 登录接口返回的数据类型
   interface dataType {
     token: string
   }
   
   export interface loginResponseData {
     code: number
     data: dataType
   }
   
   // 定义服务器返回的用户信息的类型
   interface user {
     checkUser: {
       userID: number
       avatar: string
       username: string
       password: string
       desc: string
       roles: string[]
       buttons: string[]
       routes: string[]
       token: string
     }
   }
   
   export interface userResponseData {
     code: number
     data: user
   }
   
   ```

# 路由搭建

1. 安装vue-router
   ```powershell
   pnpm install vue-router@4
   ```
2. src/router/index.ts
   ```typescript
   import { createRouter, createWebHashHistory } from 'vue-router'
   import { constantRoutes } from './routes'
   // 创建路由器
   const router = createRouter({
     // 路由模式
     history: createWebHashHistory(),
     // 路由地址
     routes: constantRoutes,
     // 滚动行为
     scrollBehavior: () => ({
       top: 0,
       left: 0,
     }),
   })
   
   export default router
   ```
3. src/router/routes.ts
   ```typescript
   export const constantRoutes = [
     {
       path: '/login',
       component: () => import('@/views/login/index.vue'),
       name: 'login',
     },
     {
       path: '/',
       component: () => import('@/views/home/index.vue'),
       name: 'layout',
     },
     {
       path: '/404',
       component: () => import('@/views/404/index.vue'),
       name: '404',
     },
     {
       // 匹配所有路径
       path: '/:pathMatch(.*)*',
       redirect: '/404',
       name: 'Any',
     },
   ]
   
   ```
4. main.ts引入
   ```typescript
   import router from '@/router/index' // 引入路由
   app.use(router)
   ```

# pinia仓库

> 相当于vuex

1. 安装pinia.
   ```powershell
   pnpm install pinia
   ```
2. 创建src/store/index.ts
   ```typescript
   // 大仓库
   import { createPinia } from 'pinia'
   
   const pinia = createPinia()
   // 入口文件需要引入仓库
   export default pinia
   ```
3. 小仓库src/store/modules/user.ts
   ```typescript
   //创建用户相关的小仓库
   import { defineStore } from 'pinia'
   // 引入接口
   import { reqLogin } from '@/api/user'
   import { loginForm, loginResponseData } from '@/api/user/type'
   import { UserState } from '@/store/modules/type/type'
   import { GET_TOKEN, SET_TOKEN } from '@/utils/token'
   //创建用户小仓库
   const useUserStore = defineStore('User', {
     //小仓库存储数据地方
     state: (): UserState => {
       return {
         token: GET_TOKEN() || '',
       }
     },
     //异步逻辑的地方
     actions: {
       //登录
       async userLogin(data: loginForm) {
         const result: loginResponseData = await reqLogin(data)
         // 登录成功，code为200
         if (result.code == 200) {
           this.token = result.data.token as string
           // 本地持久化存储
           SET_TOKEN(this.token)
           // 保证当前async函数返回的是一个成功的promise
           return 'ok'
         } else {
           // 登录失败
           return Promise.reject(new Error(result.data.message))
         }
       },
     },
     getters: {},
   })
   //对外暴露获取小仓库方法
   export default useUserStore
   
   ```
