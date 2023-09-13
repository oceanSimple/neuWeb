import { reactive } from 'vue'
import { rules } from '@/util/regCheck/rule.ts'
export interface selectOption {
  label: string
  value: string
}

export const campusArray: selectOption[] = [
  {
    label: '南校校区',
    value: '南校校区',
  },
  {
    label: '浑南校区',
    value: '浑南校区',
  },
]

export const dormitoryArray: selectOption[] = [
  {
    label: '1宿舍A区',
    value: '1宿舍A区',
  },
  {
    label: '1宿舍B区',
    value: '1宿舍B区',
  },
  {
    label: '2宿舍A区',
    value: '2宿舍A区',
  },
  {
    label: '2宿舍B区',
    value: '2宿舍B区',
  },
  {
    label: '3宿舍A区',
    value: '3宿舍A区',
  },
  {
    label: '3宿舍B区',
    value: '3宿舍B区',
  },
]

// 表单校验
export const rule = reactive({
  code: [
    { required: true, message: '请输入账号', trigger: 'blur' },
    {
      pattern: rules.code.pattern,
      message: rules.code.message,
      trigger: 'blur',
    },
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    {
      pattern: rules.password.pattern,
      message: rules.password.message,
      trigger: 'blur',
    },
  ],
  nickname: [
    { required: true, message: '请输入昵称', trigger: 'blur' },
    {
      pattern: rules.nickname.pattern,
      message: rules.nickname.message,
      trigger: 'blur',
    },
  ],
  email: [
    { required: true, message: '请输入邮箱', trigger: 'blur' },
    {
      pattern: rules.email.pattern,
      message: rules.email.message,
      trigger: 'blur',
    },
  ],
  password2: [{}, {}],
})
