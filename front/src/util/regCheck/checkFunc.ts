import { ruleItem, rules } from '@/util/regCheck/rule.ts'
import { ElNotification } from 'element-plus'
import { ResponseData } from '@/api/type.ts'

export const checkAllParamPatter = (params: object) => {
  // 遍历所有的参数,检查参数是否符合规则
  Reflect.ownKeys(params).forEach((key) => {
    if (!checkParamPattern(key as string, Reflect.get(params, key) as string)) {
      ElNotification.warning({
        title: '警告',
        message: '参数' + key.toString() + '不符合规则',
      })
      return false
    }
  })
  return true
}

export const checkParamPattern = (param: string, value: string) => {
  const isHasAttr = Reflect.has(rules, param)
  if (isHasAttr) {
    const reg = Reflect.get(rules, param) as ruleItem
    const result = reg.pattern.test(value)
    if (!result) {
      ElNotification.warning({
        title: '警告',
        message: '参数' + param + '不符合规则',
      })
      return false
    }
  }
  return true
}

export const checkResultCode = (result: ResponseData<any>) => {
  if (result.code === 200) {
    ElNotification({
      title: '成功',
      message: result.msg,
      type: 'success',
      duration: 2000,
    })
    return true
  } else {
    ElNotification({
      title: '错误',
      message: result.msg,
      type: 'error',
      duration: 2000,
    })
    return false
  }
}
