// 用户类数据类型
export interface User {
  id: string
  code: string
  password: string
  nickname: string
  campus: string
  dormitory: string
  isDelete: string
  gmtCreate: string
  gmtModified: string
  email: string
  phone: string
  token: string
}

// 提交：用户登录
export interface LoginUserParam {
  code: string
  password: string
}

// 提交：token自动登录
export interface CheckTokenParam {
  code: string
  token: string
}

// 提交：通过邮箱获取验证码
export interface CheckEmailVerificationCode {
  email: string
  emailVerifyCode: string
}

// 提交：用户更新
export interface UpdateUserParam {
  code: string
  target: string
  data: string
}

// 提交：用户注册
export interface RegisterParams {
  code: string
  password: string
  nickname: string
  email: string
  campus: string
  dormitory: string
}
