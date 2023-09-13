import {
  CheckEmailVerificationCode,
  CheckTokenParam,
  LoginUserParam,
  RegisterParams,
  UpdateUserParam,
  User,
} from '@/api/user/type.ts'
import { ResponseData } from '@/api/type.ts'
import request from '@/util/request.ts'

const baseUrl = 'user'
enum Api {
  LOGIN_URL = baseUrl + '/login',
  CHECK_TOKEN_URL = baseUrl + '/checkToken',
  REGISTER_URL = baseUrl + '/register',
  UPDATE_URL = baseUrl + '/update',
  CHECK_EMAIL_VERIFICATION_CODE_URL = baseUrl + '/checkEmailVerificationCode',
  GET_EMAIL_VERIFICATION_CODE_URL = '/mqProducer/temporaryInformation/emailVerificationCode',
}

// 用户登录
export const reqUserLogin = (data: LoginUserParam) =>
  request.post<any, ResponseData<User>>(Api.LOGIN_URL, data)
// token自动登录
export const reqUserCheckToken = (data: CheckTokenParam) =>
  request.post<any, ResponseData<string>>(Api.CHECK_TOKEN_URL, data)
// 用户注册
export const reqUserRegister = (data: RegisterParams) =>
  request.post<any, ResponseData<User>>(Api.REGISTER_URL, data)
// 修改
export const reqUserUpdate = (data: UpdateUserParam) =>
  request.post<any, ResponseData<User>>(Api.UPDATE_URL, data)
// 检查验证码
export const reqCheckEmailVerificationCode = (
  data: CheckEmailVerificationCode,
) =>
  request.post<any, ResponseData<string>>(
    Api.CHECK_EMAIL_VERIFICATION_CODE_URL,
    data,
  )
// 获取邮箱验证码
export const reqUserGetEmailVerificationCode = (email: string) =>
  request.get<any, ResponseData<string>>(
    Api.GET_EMAIL_VERIFICATION_CODE_URL + '/' + email,
  )
