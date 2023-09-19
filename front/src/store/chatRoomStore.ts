//创建用户相关的小仓库
import { defineStore } from 'pinia'
import {
  reqAddFriend,
  reqAddFriendByCode,
  reqGetFriendList,
  reqUnshiftFriendToTop,
} from '@/api/chatRoom/friend/api.ts'
import { AddFriendParam, Friend } from '@/api/chatRoom/friend/type.ts'
import {
  GetMessageParam,
  Message,
  SetMessageReadParam,
} from '@/api/chatRoom/message/type.ts'
import {
  reqAddMessage,
  reqGetMessage,
  reqSetMessageRead,
} from '@/api/chatRoom/message/api.ts'
import { websocketConnect } from '@/util/websocket.ts'
import { useUserStore } from '@/store/userStore.ts'

export const useChatRoomStore = defineStore('ChatRoom', {
  state: () => {
    return {
      currentFriend: {
        // 当前聊天的好友
        code: '00000000',
        nickName: '系统',
        remark: '系统客服',
        hasNewMessage: 0,
      },
      websocket: null as WebSocket | null, // websocket对象
      friendList: [] as Friend[], // 好友列表
      hasNewMessage: false, // 控制homePage页面的消息按钮，true为红色，告诉用户有新的未读消息
    }
  },
  actions: {
    // 初始化websocket
    async initWebSocket() {
      this.websocket = websocketConnect()
    },
    // 非chatRoom页面的websocket消息处理
    initWebSocketMessage() {
      // @ts-ignore
      this.websocket.onmessage = (e) => {
        // hasNewMessage置为true
        this.hasNewMessage = true
        // 把发送消息的好友在好友列表中置顶
        const msg = JSON.parse(e.data) as Message
        this.formatFriendList(msg.sender)
      }
    },
    // 判断新消息的发送方是否是已有的好友，格式化好友列表
    async formatFriendList(code: string) {
      let index = -1
      for (let i = 0; i < this.friendList.length; i++) {
        if (this.friendList[i].code === code) {
          // 如果是已有的好友，记录好友的索引
          index = i
          break
        }
      }
      if (index !== -1) {
        // 如果是已有的好友，把好友置顶
        // 把好友复制一份置顶
        this.friendList.unshift(this.friendList[index])
        // 删除原来的好友
        this.friendList.splice(index + 1, 1)
        // 更新redis内容
        await reqUnshiftFriendToTop({
          userCode: useUserStore().user.code,
          index: index,
        })
      } else {
        // 如果是新好友
        await reqAddFriendByCode({
          userCode: useUserStore().user.code,
          friendCode: '',
        })
      }
    },
    // 获取好友列表
    async getFriendList(code: string) {
      return await reqGetFriendList(code)
    },
    // 添加好友
    async addFriend(data: AddFriendParam) {
      return await reqAddFriend(data)
    },
    // 获取消息列表
    async getMessageList(data: GetMessageParam) {
      return await reqGetMessage(data)
    },
    // 添加消息
    async addMessage(data: Message) {
      return await reqAddMessage(data)
    },
    // 设置消息已读
    async setMessageRead(data: SetMessageReadParam) {
      return await reqSetMessageRead(data)
    },
  },
})
