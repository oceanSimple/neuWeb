import {
  AddFriendByCodeParam,
  AddFriendParam,
  DeleteFriendParam,
  Friend,
  UpdateFriendParam,
} from '@/api/chatRoom/friend/type.ts'
import { ResponseData } from '@/api/type.ts'
import request from '@/util/request.ts'

const baseUrl = 'chatRoom/friend'

enum Api {
  UNSHIFT_FRIEND_TO_TOP = baseUrl + '/unshiftFriendToTop',
  ADD_FRIEND_BY_CODE = baseUrl + '/addFriendByCode',
}

// 获取好友列表
export const reqGetFriendList = (code: string) =>
  request.get<any, ResponseData<Friend[]>>(baseUrl + '/' + code)
// 添加好友
export const reqAddFriend = (data: AddFriendParam) =>
  request.post<any, ResponseData<string>>(baseUrl, data)
// 删除好友
export const reqDeleteFriend = (data: DeleteFriendParam) =>
  request.delete<any, ResponseData<string>>(baseUrl, { data })
// 修改好友信息
export const reqUpdateFriend = (data: UpdateFriendParam) =>
  request.put<any, ResponseData<string>>(baseUrl, data)
// 将好友置顶，并设置消息未读
export const reqUnshiftFriendToTop = (data: DeleteFriendParam) =>
  request.post<any, ResponseData<string>>(Api.UNSHIFT_FRIEND_TO_TOP, data)
// 通过code添加好友
export const reqAddFriendByCode = (data: AddFriendByCodeParam) =>
  request.post<any, ResponseData<string>>(Api.ADD_FRIEND_BY_CODE, data)
