# 水平布局

1. el-row
   > 1. 排版的父组件
   > 2. 将每行划分为24个分栏
2. el-col
   > 1. 子组件，通过`span`属性设置占据的分栏数
   > 2. :xs="0"，代表当屏幕宽度小于600px时，不占据分栏
   > 3. :xs="24"，代表当屏幕宽度小于600px时，占据24个分栏
   ```html
     <el-col :span="12" :xs="0"></el-col>
     <el-col :span="12" :xs="24"></el-col>
   ```

# el-input

> 1. :prefix-icon="User"，代表前缀图标,去iconfont上找
> 2. show-password，代表显示密码（小眼睛图表）
> 3. type="password"，代表密码框

```html

<el-input
        v-model="loginForm.password"
        :prefix-icon="Lock"
        show-password
        type="password"
></el-input>
```

```typescript
import {Lock, User} from '@element-plus/icons-vue'
```

# el-button

> 1. :loading="loadingState"，代表加载状态,true为加载中，false为加载完成

```html

<el-form-item>
    <el-button
            :loading="loadingState"
            class="login_btn"
            size="default"
            type="primary"
            @click="login"
    >
        登录
    </el-button>
</el-form-item>
```

# Notification

> 用来作为消息提示框
> 
> 网址：https://element-plus.org/en-US/component/notification.html

基础使用：

1. title：标题
2. message：内容
3. type：类型，有success、warning、info、error四种类型
4. duration：持续时间，单位毫秒，0表示不自动关闭
5. offset：偏移量，单位像素，x表示水平方向，y表示垂直方向
6. onClose：关闭时的回调函数
7. onClick：点击时的回调函数
8. dangerouslyUseHTMLString：是否将 message 属性作为 HTML 片段处理

```javascript
import {ElNotification} from 'element-plus'

ElNotification({
  title: '登录成功',
  message: '欢迎回来',
  type: 'success',
})
```

# el-form表单校验

注意点：

1. el-form
   1. :model="loginForm"，代表表单数据
   2. :rules="rules"，代表表单验证规则
   3. ref="canCommit",代表表单的引用,用来判断表单是否验证通过
2. el-form-item
   1. prop="username"，代表表单数据的属性名

```html

<el-form :model="loginForm" :rules="rules" class="login_form" ref="canCommit">
    <el-form-item prop="username">
        <el-input
                v-model="loginForm.username"
                :prefix-icon="User"
        ></el-input>
    </el-form-item>
    <el-form-item prop="password">
        <el-input
                v-model="loginForm.password"
                :prefix-icon="Lock"
                show-password
                type="password"
        ></el-input>
    </el-form-item>
    <el-form-item>
        <el-button
                :loading="loadingState"
                class="login_btn"
                size="default"
                type="primary"
                @click="login"
        >
            登录
        </el-button>
    </el-form-item>
</el-form>
```

> ts代码

```typescript
// 表单验证规则
const rules = reactive({
  username: [
    {required: true, message: '请输入用户名', trigger: 'blur'},
    {
      pattern: /^[a-z0-9]{5,10}$/,
      message: '用户名必须是5-10个字母或数字',
      trigger: 'blur',
    },
  ],
  password: [
    {required: true, message: '请输入密码', trigger: 'blur'},
    {
      pattern: /^[a-z0-9]{3,}$/,
      message: '密码必须是3位以上的字母或数字',
      trigger: 'blur',
    },
  ],
})

const canCommit = ref() // 接收表单校验结果

// 在提交按钮中await
await canCommit.value.validate()
```

# 导航栏

## el-menu

> 最外层套一个el-menu
> 
> 1. collapse: 代表菜单折叠（图标放在title插槽外面！）
> 2. :default-active="activePath"，代表默认加载的路由，即就算默认页面为二级菜单，会帮你默认展开

```html

<el-menu
        :default-active="$route.path"
        background-color="#001529"
        collapse
        text-color="white"
>
```

```html

<el-menu-item
        v-if="!item.children && !item.meta.hidden"
        :index="item.path"
        @click="goRoute"
>
    <el-icon>
        <component :is="item.meta.icon"></component>
    </el-icon>
    <template #title>
        <span>{{ item.meta.title }}</span>
    </template>
</el-menu-item>
```

```typescript
// 打印当前路由路径
import {useRoute} from 'vue-router'

let $route = useRoute()
console.log($route.path)
```

## el-menu-item

1. 介绍设计思路
   1. 通过循环路由确认导航栏的各种信息
   2. 用v-if判断路由是否有子路由以及是否需要隐藏
   3. 如果没有子路由或只有一个子路由，那么就直接渲染el-menu-item
   4. 如果有多个子路由，那么就渲染el-submenu，然后递归调用自己！
2. 迭代路由组件来确定导航栏信息

```
<template v-for="item in menuList" :key="item.path">
```

3. 属性举例介绍
   > 1. v-if="!item.children && !item.meta.hidden"，代表没有子路由且没有隐藏
   > 2. :index="item.path"，代表路由的路径,同时也是导航栏的索引（必写！！！）
   > 3. @click="goRoute"，代表点击导航栏的回调函数
   > 4. <template #title>，代表导航栏的标题，插槽
   > 5. <el-icon>，代表图标
   > 6. <component :is="item.meta.icon">，代表图标的组件，item.meta.icon是路由的meta中的icon属性，这个属性是一个组件，所以需要用component标签来渲染
   ```html
   
   <el-menu-item
           v-if="!item.children && !item.meta.hidden"
           :index="item.path"
           @click="goRoute"
   >
       <template #title>
           <el-icon>
               <component :is="item.meta.icon"></component>
           </el-icon>
           <span>{{ item.meta.title }}</span>
       </template>
   </el-menu-item>
   ```
4. :index="item.path"

> 本属性用路由路径绑定，也方便后面获取该值进行路由跳转

5. @click="goRoute"

> 本属性用来绑定点击事件，点击导航栏时触发
> 
> 该组件自带回调参数，参数为el: menu-item instance
> 
> 所以可以通过参数获取到index属性，从而获取到路由路径，然后进行路由跳转

```
// html绑定事件
@click="goRoute

// ts代码，vc无需传递，直接使用即可
const goRoute = (vc: any) => {
console.log(vc)
try {
  $router.push(vc.index)
} catch (e) {
  console.log(e)
}
}
```

6. 递归调用

> 递归调用需要给组件命名，这需要用到vue2的写法

```
<script lang = "ts" >
import {useRouter} from 'vue-router'

export default {
  name: 'Menu-Demo',
  props: ['menuList'],
  setup(props) {
    const $router = useRouter()
    const goRoute = (vc: any) => {
      console.log(vc)
      try {
        $router.push(vc.index)
      } catch (e) {
        console.log(e)
      }
    }
    return {
      goRoute,
    }
  },
}
< /script>
```

> 当然也可以vue3写法

```
<script lang="ts" setup>
import { reactive } from 'vue'

const menuListProp = defineProps(['menuList'])
const menuList = reactive(menuListProp.menuList)
</script>

<script lang="ts">
export default {
  name: 'Menu-Demo',
}
</script>
```

7. 父传子的props数据传递

> vue2写法接收后可以直接在html中使用！
> 
> 接收后可以直接在html中使用！
> 
> 如果想要在setup中使用，直接放进setup的参数即可使用！

```typescript
export default {
  name: 'Menu-Demo',
  props: ['menuList'],
  setup() {
  },
}
```

> vue3写法

```typescript
import {reactive} from 'vue'

const menuListProp = defineProps(['menuList'])
const menuList = reactive(menuListProp.menuList)
```

# 导航栏的缩放

1. el-menu的附带属性
   > 当el-menu的collapse属性为true时，代表导航栏可以缩放
   ```
   :collapse="layoutSettingStore.fold"
   ```
2. 设计介绍
   > 1. 因为按钮是在menu组件的兄弟组件的子组件中，所以最方便的是将变量放在仓库中共享
   > 2. 通过一个fold的布尔变量，判断菜单是否收缩
   > 3. 同时，菜单虽然收缩了，但包含菜单组件的父组件div的大小没变，所以需要改变整个页面的布局
   > 4. 通过:class的方法，当需要伸缩的时候，用新的css对width进行修改，以达到伸缩的效果
   > 5. 同时使用transition: all 0.3s使页面的变化更加的平滑

# icon的使用

1. 在src/component/index.ts中引入所有的icon

```typescript
//引入项目中全部的全局组件
import SvgIcon from './svgIcon/index.vue'
//引入element-plus提供全部图标组件
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
//全局对象
const allGloablComponent: any = {SvgIcon}
//对外暴露插件对象
export default {
  //务必叫做install方法
  install(app: any) {
    //注册项目全部的全局组件
    Object.keys(allGloablComponent).forEach((key) => {
      //注册为全局组件
      app.component(key, allGloablComponent[key])
    })
    //将element-plus提供图标注册为全局组件
    for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
      app.component(key, component)
    }
  },
}
```

2. 使用

```html

<el-icon>
    <component :is="item.meta.icon"></component>
</el-icon>
```

# 路由守卫

> 创建src/permission.ts，并在main.ts中引入
to: Route: 即将要进入的目标 路由对象
from: Route: 当前导航正要离开的路由
next: Function: 一定要调用该方法来 resolve 这个钩子。执行效果依赖 next 方法的调用参数。

```typescript
// permission.ts
// 路由鉴权，路由安全访问
import router from '@/router'
// 全局守卫：任意路由切换都会出发的钩子
// 全局前置守卫
router.beforeEach(async (to: any, from: any, next: any) => {
})
router.afterEach((to: any, from: any) => {
})
```

```typescript
// main.ts
//引入路由鉴权
import './permission'
```

## 路由过渡动画

1. 安装nprogress
   ```powershell
   pnpm i nprogress
   ```
2. 引入nprogress
   > 如果想修改nprogress的样式，可以在node_modules/nprogress/nprogress.css中修改
   ```typescript
   import nprogress from 'nprogress'
   // 引入样式
   import 'nprogress/nprogress.css'
   // 关闭右上角的旋转图标
   nprogress.configure({ showSpinner: false })
   // 路由前置守卫使用
   nprogress.start()
    // 路由后置守卫使用
   nprogress.done()
   ```

# 路由切换的过渡动画

> src/layout/main/index.vue

```vue

<template>
  <!-- 路由组件出口的位置 -->
  <router-view v-slot="{ Component }">
    <transition name="fade">
      <!-- 渲染layout一级路由组件的子路由 -->
      <component :is="Component"/>
    </transition>
  </router-view>
</template>

<script lang="ts" setup></script>

<style scoped>
.fade-enter-from {
  opacity: 0;
  transform: scale(0);
}

.fade-enter-active {
  transition: all 0.3s;
}

.fade-enter-to {
  opacity: 1;
  transform: scale(1);
}
</style>

```

# 面包屑的使用

> 核心思路，根据当前路由的meta.title显示面包屑

1. 路由的使用
   ```typescript
   import {useRoute} from 'vue-router'
   let $route = useRoute()
   ```
   > v-for="(item, index) in $route.matched"
   1. 该变量会返回当前路由的所有信息，包括父路由
   2. 比如当前路由为：/home/system/user，那么该变量会返回：/home、/home/system、/home/system/user
2. 面包屑组件的使用
   > 1. separator-icon="ArrowRight"，代表分隔符为右箭头
   > 2. v-show="item.meta.title"，代表只有meta中有title属性的路由才会显示
   > 3. :to="item.path"，代表路由跳转(附带功能，点击即跳转)
   ```html
   <el-breadcrumb separator-icon="ArrowRight">
       <el-breadcrumb-item
               v-for="(item, index) in $route.matched"
               v-show="item.meta.title"
               :key="index"
               :to="item.path"
       >
           <el-icon v-if="item.meta.icon" style="margin: 0px 5px">
               <component :is="item.meta.icon"/>
           </el-icon>
           <span>{{ item.meta.title }}</span>
       </el-breadcrumb-item>
   </el-breadcrumb>
   ```

# 主页面数据部分刷新

> 需求分析：点击刷新按钮，仅刷新main部分，而不是刷新整个页面
> 
> 思路与导航伸缩类似，将是否刷新的变量放在仓库中共享
> 
> 刷新的逻辑是将组件注销并重新挂载

1. 布尔值flag代表是否显示main组件，改变flag即可实现组件的注销与挂载
2. 通过watch监听仓库中的refresh变量，当refresh变量改变时，执行刷新逻辑
3. 因为vue组件的挂载注销是异步操作，所以需要使用nextTick方法，将flag的值改变放在nextTick中，以保证flag的值改变时，组件已经挂载完毕
4. nextTick：使用 nextTick 方法的常见场景是在修改Vue实例的数据后，立即读取更新后的DOM状态或与更新后的DOM进行交互。

```typescript
import useLayOutSettingStore from '@/store/setting'
import {nextTick, ref, watch} from 'vue'

let layoutSettingStore = useLayOutSettingStore()
let flag = ref(true)
// 监听refresh的变化
watch(
  () => layoutSettingStore.refresh,
  () => {
    // 刷新页面
    flag.value = false
    nextTick(() => {
      flag.value = true
    })
  },
)
```

> 其他的组件是过渡动画的内容

```html

<template>
    <!-- 路由组件出口的位置 -->
    <router-view v-slot="{ Component }">
        <transition name="fade">
            <!-- 渲染layout一级路由组件的子路由 -->
            <component :is="Component" v-if="flag"/>
        </transition>
    </router-view>
</template>
```

# 全屏模式

> 使用原生dom

```typescript
const fullScreen = () => {
  let full = document.fullscreenElement
  if (!full) {
    document.documentElement.requestFullscreen()
  } else {
    document.exitFullscreen()
  }
}
```

# 表格table

## el-table

> 1. :data="trademarkArr"，代表表格的数据
> 2. border，代表表格有边框

```html

<el-table :data="trademarkArr" border style="margin: 10px 0px">
```

## el-table-column

### 基础使用

> 1. label="序号"，代表表头的标题
> 2. type="index"，代表表头的类型，index代表序号,selection代表复选框,expand代表展开
> 3. width="80"，代表表头的宽度
> 4. align="center"，代表表头的对齐方式
> 5. prop="id"，代表表格数据的属性名

```html

<el-table-column
        align="center"
        label="序号"
        type="index"
        width="80"
></el-table-column>
```

### 作用域插槽

> 1. 作用域插槽的作用是可以在表格中插入自定义的内容
> 2. 作用域插槽的参数为row，代表当前行的数据
> 3. 作用域插槽的内容需要放在template标签中

```html

<el-table-column align="center" label="品牌名称">
    <template #default="{ row }">
        <pre> {{ row.tmName }} </pre>
    </template>
</el-table-column>
```

### 插入图片

```html

<el-table-column label="品牌LOGO">
    <template #default="{ row }">
        <img
                :src="row.logoUrl"
                alt="加载失败"
                style="width: 100px; height: 100px; border-radius: 50%"
        />
    </template>
</el-table-column>
```

### 插入按钮

> 注意，row.id是数据的id，而不是显示的序号！！！

```html

<el-table-column label="品牌操作">
    <template #default="{ row }">
        <el-button
                icon="Edit"
                size="small"
                type="primary"
                @click="updateTrademark(row)"
        ></el-button>
        <el-popconfirm
                :title="`您确定要删除${row.tmName}?`"
                icon="Delete"
                width="250px"
                @confirm="removeTradeMark(row.id)"
        >
            <template #reference>
                <el-button icon="Delete" size="small" type="primary"></el-button>
            </template>
        </el-popconfirm>
    </template>
</el-table-column>
```

# el-pagination分页插件

> 1. v-model:current-page="pageNo"，代表当前页数
> 2. v-model:page-size="pageSize"，代表每页显示的条数
> 3. :background="background"，代表是否为按钮添加背景颜色，默认false
> 4. :page-sizes="pageSizes"，代表每页显示的条数的选择器的选项设置
> 5. :small="small"，代表是否使用小型分页样式，默认false
> 6. :total="400"，代表总条数
> 7. 调整各个组件元素的顺序, ->是将后面组件顶到最右侧
> 8. layout="prev, pager, next, jumper,-> ,total, sizes"，代表分页的布局.
> 9. @size-change="sizeChange"，代表每页显示的条数改变时的回调函数，即选择sizes组件
> 10. @current-change="getHasTrademark"，代表当前页数改变时的回调函数，即current-page

```html

<div class="demo-pagination-block">
    <el-pagination
            v-model:current-page="pageNo"
            v-model:page-size="pageSize"
            :background="background"
            :page-sizes="[3, 5, 7, 9]"
            :small="small"
            :total="400"
            layout="prev, pager, next, jumper,-> ,total, sizes"
            @size-change="sizeChange"
            @current-change="getHasTrademark"
    />
</div>
```

# el-popconfirm确认框

> 1. :title="`您确定要删除${row.tmName}?`"，代表确认框的标题
> 2. icon="Delete"，代表确认框的图标
> 3. @confirm="removeTradeMark(row.id)"，代表确认框的回调函数
> 4. <template #reference>，代表确认框的触发按钮

```html

<el-popconfirm
        :title="`您确定要删除${row.tmName}?`"
        icon="Delete"
        width="250px"
        @confirm="removeTradeMark(row.id)"
>
    <template #reference>
        <el-button icon="Delete" size="small" type="primary"></el-button>
    </template>
</el-popconfirm>
```

# el-dialog对话框

> 1. v-model="dialogFormVisible"，代表对话框的显示与隐藏
> 2. :title="trademarkParams.id ? '修改品牌' : '添加品牌'"，代表对话框的标题
> 3. template #footer，代表对话框的底部

```html

<el-dialog
        v-model="dialogFormVisible"
        :title="trademarkParams.id ? '修改品牌' : '添加品牌'"
>
    <el-form
            ref="formRef"
            :model="trademarkParams"
            :rules="rules"
            style="width: 80%"
    >
        <el-form-item label="品牌名称" label-width="100px" prop="tmName">
            <el-input
                    v-model="trademarkParams.tmName"
                    placeholder="请您输入品牌名称"
            ></el-input>
        </el-form-item>
    </el-form>
    <!-- 具名插槽:footer -->
    <template #footer>
        <el-button size="default" type="primary" @click="cancel">取消</el-button>
        <el-button size="default" type="primary" @click="confirm">确定</el-button>
    </template>
</el-dialog>
```

# el-upload上传组件

> 1. :before-upload="beforeAvatarUpload"，代表上传前的回调函数,该函数返回false则不会上传,一般用于将图片先上传到后端服务器
> 2. :on-success="handleAvatarSuccess"，代表上传成功的回调函数，用来获取后端返回的图片地址，进行回显
> 3. :show-file-list="false"，代表是否显示上传成功的文件列表
> 4. action="/api/admin/product/fileUpload"，代表图片上传路径,代理服务器发送这次post请求

```html
<!-- upload组件属性:action图片上传路径书写/api,代理服务器不发送这次post请求  -->
<el-upload
        :before-upload="beforeAvatarUpload"
        :on-success="handleAvatarSuccess"
        :show-file-list="false"
        action="/api/admin/product/fileUpload"
        class="avatar-uploader"
>
    <img
            v-if="trademarkParams.logoUrl"
            :src="trademarkParams.logoUrl"
            class="avatar"
    />
    <el-icon v-else class="avatar-uploader-icon">
        <Plus/>
    </el-icon>
</el-upload>
```

```typescript
//上传图片组件->上传图片之前触发的钩子函数
const beforeAvatarUpload: UploadProps['beforeUpload'] = (rawFile) => {
  //钩子是在图片上传成功之前触发,上传文件之前可以约束文件类型与大小
  //要求:上传文件格式png|jpg|gif 4M
  if (
    rawFile.type == 'image/png' ||
    rawFile.type == 'image/jpeg' ||
    rawFile.type == 'image/gif'
  ) {
    if (rawFile.size / 1024 / 1024 < 4) {
      return true
    } else {
      ElMessage({
        type: 'error',
        message: '上传文件大小小于4M',
      })
      return false
    }
  } else {
    ElMessage({
      type: 'error',
      message: '上传文件格式务必PNG|JPG|GIF',
    })
    return false
  }
}
//图片上传成功钩子
const handleAvatarSuccess: UploadProps['onSuccess'] = (response) => {
  //response:即为当前这次上传图片post请求服务器返回的数据
  //收集上传图片的地址,添加一个新的品牌的时候带给服务器
  trademarkParams.logoUrl = response.data
  //图片上传成功,清除掉对应图片校验结果
  formRef.value.clearValidate('logoUrl')
}
```

# 下拉选项框

## el-select

> 1. v-model="categoryStore.c1Id"，代表选中的值
> 2. :disabled="scene === 1"，代表是否禁用
> 3. @change="categoryStore.getC2"，代表选中值改变时的回调函数

```html

<el-select
        v-model="categoryStore.c1Id"
        :disabled="scene === 1"
        @change="categoryStore.getC2"
>
```

## el-option

> 1. v-for="c1 in categoryStore.c1Arr"，代表循环的数组
> 2. :key="c1.id"，代表循环的key
> 3. :label="c1.name"，代表显示的内容
> 4. :value="c1.id"，代表选中的值

```html

<el-option
        v-for="c1 in categoryStore.c1Arr"
        :key="c1.id"
        :label="c1.name"
        :value="c1.id"
></el-option>
```

# 拷贝

深拷贝

```typescript
// 实现深拷贝
Object.assign(attrParams, JSON.parse(JSON.stringify(row)))
```

# pinia仓库数据的重置

```typescript
categoryStore.$reset()
```
