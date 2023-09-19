<template>
  <el-card v-if="chatRoomStore.currentFriend">
    <template #header>
      <span>{{ friendNickname }}</span>
    </template>
    <div id="scrollbar">
      <div v-for="(item, index) in chatList">
        <div v-if="showTime(index)" class="timeView">{{ item.time }}</div>

        <el-row>
          <el-col :span="12">
            <div v-if="item.sender !== user.code" class="chatListLeft">
              <div>
                {{ friend.nickName }}
                <span v-if="!item.isRead" style="color: red">*</span>
              </div>
              <br />
              <div>
                <div
                  :class="{ chatBorderRed: !item.isRead }"
                  class="messageSpan"
                >
                  {{ item.message }}
                </div>
              </div>
            </div>
          </el-col>
          <el-col :span="12">
            <div v-if="item.sender === user.code" class="chatListRight">
              <div style="text-align: right">Me</div>
              <br />
              <div class="messageSpan" style="float: right">
                {{ item.message }}
              </div>
            </div>
          </el-col>
        </el-row>
      </div>
    </div>

    <!--分割线-->
    <el-divider />

    <!--聊天输入框-->
    <el-input
      v-model="chatInput"
      :rows="4"
      placeholder="textarea"
      type="textarea"
      @focus="readMessage"
    ></el-input>

    <!--发送消息按钮-->
    <el-button style="float: right" type="primary" @click="sendMessage">
      发送
    </el-button>
  </el-card>
</template>

<script lang="ts" setup>
import { onMounted, reactive, ref, watch } from 'vue'
import { User } from '@/api/user/type.ts'
import { getObj } from '@/util/localStorage.ts'
import { ElMessage } from 'element-plus'
import { useChatRoomStore } from '@/store/chatRoomStore.ts'
import { Friend } from '@/api/chatRoom/friend/type.ts'
import { Message } from '@/api/chatRoom/message/type.ts'

//获取当前登录的用户数据
const chatRoomStore = useChatRoomStore()
const user = getObj<User>('user') as User
// 当前聊天的好友
let friend: Friend
// 聊天信息列表
let chatList = reactive<Message[]>([])
// 聊天输入框
const chatInput = ref()
// 当前聊天的好友昵称
let friendNickname = ref('')
// 监听当前聊天的好友，当切换好友时，重新获取聊天信息列表
watch(
  () => chatRoomStore.currentFriend,
  () => {
    friend = chatRoomStore.currentFriend as Friend
    friendNickname.value = friend.nickName
    chatList = reactive<Message[]>([])
    getMessageList()
  },
)
onMounted(async () => {
  if (chatRoomStore.websocket === null) {
    await chatRoomStore.initWebSocket()
  }
  // 对websocket的消息进行监听
  // @ts-ignore
  chatRoomStore.websocket.onmessage = (event) => {
    const message = JSON.parse(event.data)
    // 如果消息的发送者是当前聊天的好友，将消息添加到聊天信息列表中
    if (message.sender === friend.code) {
      chatList.push(message)
      scrollToBottom()
    }
    // 如果消息的发送者是00000000，则是系统发送的消息
    if (message.sender === '00000000' && message.message === '好友不在线') {
      ElMessage({
        message: message.message,
        type: 'info',
      })
    }
    // 如果消息发送者不是当前的好友，则提醒用户收到新的消息
  }
})

// 获取聊天信息列表
const getMessageList = async () => {
  if (!friend) return
  await chatRoomStore
    .getMessageList({
      sender: user.code,
      receiver: friend.code,
    })
    .then((res) => {
      chatList.push(...res.data)
    })
  // 获取消息列表后，将滚动条滚动到底部
  scrollToBottom()
}

// 发送消息
const sendMessage = async () => {
  if (!friend) return
  else {
    const message = {
      sender: user.code,
      receiver: friend.code,
      message: chatInput.value,
      time: new Date().toLocaleString(),
      isRead: 0,
    }
    await chatRoomStore.addMessage(message).then((res) => {
      chatList.push(res.data)
      chatInput.value = ''
      // @ts-ignore
      chatRoomStore.websocket.send(JSON.stringify(message))
    })
    readMessage()
    scrollToBottom()
  }
}

// 滚动条滚动到底部
const scrollToBottom = () => {
  const scrollbar = document.getElementById('scrollbar')
  if (scrollbar) {
    scrollbar.scrollTop = scrollbar.scrollHeight
  }
}

// 判断是否显示时间
const showTime = (index: number) => {
  // 如果是第一条消息，直接显示时间
  if (index === 0) return true
  // 如果不是第一条消息，判断当前消息与上一条消息的时间差是否大于4分钟，如果大于4分钟，显示时间
  else {
    let date1 = new Date(chatList[index - 1].time).getTime()
    let date2 = new Date(chatList[index].time).getTime()
    const time = (date2 - date1) / 1000 / 60
    return time > 4
  }
}

// 当点击聊天输入框或者聊天框时，将消息设置为已读
const readMessage = () => {
  chatList.forEach((item) => {
    if (item.receiver === user.code) item.isRead = 1
  })
  // 发送给后端请求，对redis中的所有消息进行已读操作
  // 将当前chatList发送给后端，在redis进行全部覆盖即可
  chatRoomStore
    .setMessageRead({
      sender: user.code,
      receiver: friend.code,
      messages: [],
    })
    .then((res) => {
      console.log(res)
    })
}
</script>

<style lang="scss" scoped>
.chatListRight {
  float: right;
}

.chatListLeft {
  display: block;
  text-align: left;
}

.chatListLeft,
.chatListRight {
  fontfamily: 'Microsoft YaHei';
}

.chatInput {
  width: 100vw;
  height: 50px;
  border: #10f9da 1px solid;
}

#scrollbar {
  height: 400px;
  overflow-y: scroll;
}

.timeView {
  text-align: center;
  color: grey;
  font-size: 13px;
}

.messageSpan {
  border-radius: 25px;
  font-family: Arial;
  font-size: 15px;
  line-height: 30px;
  font-weight: bold;
  border: 2px solid;
  border-radius: 10px;
  padding: 15px;
  height: auto;
  overflow: hidden;
  width: fit-content;
  margin-bottom: 10px;
}

.chatBorderRed {
  border: 2px solid red;
}
</style>
