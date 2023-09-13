import axios from 'axios'
import { ElMessage } from 'element-plus'

const request = axios.create({
  baseURL: '/api', // 基础路径,仅作标识符，后配置跨域代理
  timeout: 5000,
})

// 请求拦截器
request.interceptors.request.use((config) => {
  return config
})

// 第三步：响应拦截器
request.interceptors.response.use(
  (response) => {
    // response 响应数据
    return response.data
  },
  (error) => {
    // error 错误信息
    let message = ''
    const status = error.response.status
    switch (status) {
      case 400:
        message = '请求错误'
        break
      case 401:
        message = '未授权的访问'
        break
      case 403:
        message = '禁止访问'
        break
      case 404:
        message = '资源不存在，请求地址错误'
        break
      case 500:
        message = '服务器内部错误'
        break
      default:
        message = '网络错误'
        break
    }
    return ElMessage({
      type: 'error',
      message,
    })
    return Promise.reject(error)
  },
)
export default request