<template>
  <div>
    <el-menu :ellipsis="false" mode="horizontal">
      <el-menu-item index="1">LOGO</el-menu-item>
      <div class="flex-grow" />
      <el-menu-item index="2" @click="goChatRoom">
        <template #title>
          <el-icon>
            <component is="ChatLineRound" />
          </el-icon>
          <span>消息</span>
        </template>
      </el-menu-item>
      <el-sub-menu index="3">
        <template #title>
          <el-icon>
            <component is="House" />
          </el-icon>
          <span>我的</span>
        </template>
        <el-menu-item
          v-for="item in optionArray"
          :key="item.name"
          :index="item.path"
          @click="goRoute"
        >
          <el-icon>
            <component :is="item.icon" />
          </el-icon>
          {{ item.label }}
        </el-menu-item>
        <el-menu-item index="3-4" @click="logout">
          <el-icon>
            <component is="Back" style="color: red" />
          </el-icon>
          <span class="backTip">退出登录</span>
        </el-menu-item>
      </el-sub-menu>
    </el-menu>
  </div>
</template>

<script lang="ts" setup>
import { optionArray } from '@/view/homePage/header/static.ts'
import router from '@/router'

const goRoute = (vc: any) => {
  router.push(vc.index)
}

const logout = () => {
  router.push('/login')
  localStorage.removeItem('user')
  localStorage.removeItem('token')
}

const goChatRoom = () => {
  router.push('/chatRoom')
}
</script>

<style lang="scss" scoped>
.flex-grow {
  flex-grow: 1;
}

.backTip {
  color: #ff0000;
}
</style>
