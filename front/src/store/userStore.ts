//创建用户相关的小仓库
import { defineStore } from 'pinia'
import {
  CheckEmailVerificationCode,
  CheckTokenParam,
  LoginUserParam,
  RegisterParams,
  UpdateUserParam,
  User,
} from '@/api/user/type'
import { getObj, setObj } from '@/util/localStorage'
import {
  reqCheckEmailVerificationCode,
  reqUserCheckToken,
  reqUserGetEmailVerificationCode,
  reqUserLogin,
  reqUserRegister,
  reqUserUpdate,
} from '@/api/user/api.ts'
import {
  checkAllParamPatter,
  checkParamPattern,
  checkResultCode,
} from '@/util/regCheck/checkFunc.ts'
import { ElNotification } from 'element-plus'

export const useUserStore = defineStore('User', {
  state: () => {
    return {
      user: getObj<User>('user') as User,
      dialogPasswordVisible: false,
      dialogEmailVisible: false,
    }
  },
  actions: {
    // 用户登录
    async userLogin(loginParam: LoginUserParam) {
      // 检查参数是否符合规则
      if (!checkAllParamPatter(loginParam)) return false
      const result = await reqUserLogin(loginParam)
      const flag = checkResultCode(result)
      if (flag) {
        // 更新userStore.user和localStorage中的user
        this.user = result.data
        setObj<User>('user', this.user)
      }
      return flag
    },
    // token登录
    async userCheckToken() {
      // 判断userStore.user是否为存在
      if (this.user === null) {
        return false
      }
      // 如果本地保存了user信息，就自动登录
      const param: CheckTokenParam = {
        token: this.user.token,
        code: this.user.code,
      }
      const result = await reqUserCheckToken(param)
      if (result.code === 200) {
        ElNotification.success('登录成功')
        return true
      }
      return false
    },
    // 获取邮箱验证码
    async userGetVerifyCodeByEmail(email: string) {
      // 检查参数是否符合规则
      if (!checkParamPattern('email', email)) return false
      const result = await reqUserGetEmailVerificationCode(email)
      return checkResultCode(result)
    },
    // 检查邮箱验证码
    async userCheckVerifyCode(param: CheckEmailVerificationCode) {
      // 检查参数是否符合规则
      if (!checkAllParamPatter(param)) return false
      const result = await reqCheckEmailVerificationCode(param)
      return checkResultCode(result)
    },
    // 用户注册
    async userRegister(param: RegisterParams) {
      // 检查参数是否符合规则
      if (!checkAllParamPatter(param)) return false
      const result = await reqUserRegister(param)
      const flag = checkResultCode(result)
      if (flag) {
        // 更新userStore.user和localStorage中的user
        this.user = result.data
        setObj<User>('user', this.user)
      }
      return flag
    },
    // 用户修改
    async userUpdate(param: UpdateUserParam) {
      // 检查参数是否符合规则
      if (!checkParamPattern(param.target, param.data)) return false
      const result = await reqUserUpdate(param)
      const flag = checkResultCode(result)
      if (flag) {
        // 更新userStore.user和localStorage中的user
        this.user = result.data
        setObj<User>('user', this.user)
      }
      return flag
    },
  },
})
