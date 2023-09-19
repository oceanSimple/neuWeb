import {
  GetMessageParam,
  Message,
  SetMessageReadParam,
} from '@/api/chatRoom/message/type.ts'
import request from '@/util/request.ts'

const baseUrl = 'chatRoom/message'

enum Api {
  ADD_MESSAGE = '/add',
  GET_MESSAGE = '/getMessage',
  SET_MESSAGE_READ = '/setMessageIsRead',
}

export const reqAddMessage = (data: Message) =>
  request.post<Message>(baseUrl + Api.ADD_MESSAGE, data)
export const reqGetMessage = (data: GetMessageParam) =>
  request.post<Message[]>(baseUrl + Api.GET_MESSAGE, data)
export const reqSetMessageRead = (data: SetMessageReadParam) =>
  request.post<String>(baseUrl + Api.SET_MESSAGE_READ, data)
