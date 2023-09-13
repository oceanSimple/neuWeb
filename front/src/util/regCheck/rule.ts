export interface ruleItem {
  pattern: RegExp
  message: string
}

export interface rulesObj {
  code: ruleItem
  nickname: ruleItem
  email: ruleItem
  password: ruleItem
  emailVerifyCode: ruleItem
}

export const rules: rulesObj = {
  code: {
    pattern: /^[0-9]{8}$/,
    message: '账号必须是8位数字',
  },
  nickname: {
    pattern: /^[a-zA-Z0-9]{1,20}$/,
    message: '昵称必须是1-20个字母或数字',
  },
  email: {
    pattern: /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/,
    message: '邮箱格式不正确',
  },
  password: {
    pattern: /^[a-zA-Z0-9]{6,16}$/,
    message: '密码必须是6-16位字母或数字',
  },
  emailVerifyCode: {
    pattern: /^[0-9]{4}$/,
    message: '验证码必须是4位数字',
  },
}
