<template>
  <div>
    <el-menu :ellipsis="false" mode="horizontal">
      <el-menu-item index="1">LOGO</el-menu-item>

      <SearchBox></SearchBox>

      <div class="flex-grow" />
      <el-menu-item index="4" @click="goChatRoom">
        <template #title>
          <el-icon>
            <component
              is="ChatLineRound"
              :class="{ messageIconColor: messageIconColor }"
            />
          </el-icon>
          <span>消息</span>
        </template>
      </el-menu-item>

      <el-sub-menu index="5">
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
import SearchBox from '@/view/homePage/main/util/SearchBox.vue'
import { optionArray } from '@/view/homePage/header/static.ts'
import router from '@/router'
import { onMounted, ref, watch } from 'vue'
import { useChatRoomStore } from '@/store/chatRoomStore.ts'
import { getObj } from '@/util/localStorage.ts'
import { User } from '@/api/user/type.ts'

// 获取chatRoomStore
const chatRoomStore = useChatRoomStore()
// 获取用户信息
const user = getObj<User>('user') as User
// message Icon的颜色控制
let messageIconColor = ref(false)

onMounted(async () => {
  // 初始化好友列表
  const friendList = await chatRoomStore.getFriendList(user.code)
  useChatRoomStore().friendList = friendList.data
  // 初始化hasNewMessage
  useChatRoomStore().friendList.forEach((item) => {
    if (item.hasNewMessage) {
      useChatRoomStore().hasNewMessage = true
      return
    }
  })
})

// 监听hasNewMessage的变化
watch(
  () => useChatRoomStore().hasNewMessage,
  (newVal) => {
    if (newVal) {
      messageIconColor.value = true
    } else {
      messageIconColor.value = false
    }
  },
)

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

.messageIconColor {
  color: #ff0000;
}
</style>
