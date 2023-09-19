import { getObj } from '@/util/localStorage.ts'
import { User } from '@/api/user/type.ts'

const baseURL = 'ws://localhost:8002/ws/'

export const websocketConnect = (): WebSocket => {
  const user = getObj<User>('user') as User
  const uri = baseURL + user.code
  const socket = new WebSocket(uri)
  socket.onopen = () => {
    console.log('连接成功')
  }
  socket.onclose = () => {
    console.log('连接关闭')
  }
  socket.onerror = () => {
    console.log('连接错误')
  }
  return socket
}
