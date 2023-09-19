export interface Friend {
  code: string // 好友code
  nickName: string // 好友昵称
  remark: string // 好友备注
  hasNewMessage: number // 是否有新消息
}

// 提交：添加好友
export interface AddFriendParam {
  code: string // 用户code
  friend: Friend // 好友信息
}

// 提交：删除好友
export interface DeleteFriendParam {
  userCode: string // 用户code
  index: number // 好友索引
}

// 提交：修改好友信息
export interface UpdateFriendParam {
  code: string // 用户code
  index: number // 好友索引
  friend: Friend // 好友信息
}

// 提交： 通过code添加好友
export interface AddFriendByCodeParam {
  userCode: string // 用户code
  friendCode: string // 好友code
}
