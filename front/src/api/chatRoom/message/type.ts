export interface Message {
  sender: string // 发送者
  receiver: string // 接收者
  message: string // 消息内容
  time: string // 时间
  isRead: number // 是否已读
}

// 提交：获取消息列表
export interface GetMessageParam {
  sender: string // 发送者
  receiver: string // 接收者
}

// 提交：设置消息已读
export interface SetMessageReadParam {
  sender: string // 发送者
  receiver: string // 接收者
  messages: Message[] // 消息列表
}
