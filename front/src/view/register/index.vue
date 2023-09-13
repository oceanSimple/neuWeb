<template>
  <div class="container">
    <el-card>
      <template #header>
        <el-icon @click="toLogin">
          <component is="Back"></component>
        </el-icon>
        <span style="font-size: 15px" @click="toLogin">返回</span>
        <span style="margin-left: 35%; font-size: 20px">注册</span>
      </template>
      <el-form
        ref="canCommit"
        :model="registerParams"
        :rules="rules"
        label-width="25%"
      >
        <el-form-item label="账号" prop="code">
          <el-input
            v-model="registerParams.code"
            placeholder="code"
            prefix-icon="User"
          ></el-input>
        </el-form-item>

        <el-form-item label="昵称" prop="nickname">
          <el-input
            v-model="registerParams.nickname"
            placeholder="nickname"
            prefix-icon="Brush"
          ></el-input>
        </el-form-item>

        <el-form-item label="密码" prop="password">
          <el-input
            v-model="registerParams.password"
            placeholder="password"
            prefix-icon="Lock"
            show-password
            type="password"
          ></el-input>
        </el-form-item>

        <el-form-item label="确认密码" prop="password2">
          <el-input
            v-model="registerParams.password2"
            placeholder="confirm password"
            prefix-icon="Lock"
            show-password
            type="password"
          ></el-input>
        </el-form-item>

        <el-form-item label="校区">
          <el-select v-model="campus" size="default">
            <el-option
              v-for="item in campusArray"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            ></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="宿舍楼">
          <el-select v-model="dormitory" size="default">
            <el-option
              v-for="item in dormitoryArray"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            ></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="邮箱" prop="email">
          <el-input
            v-model="registerParams.email"
            placeholder="email"
            prefix-icon="Message"
          ></el-input>
        </el-form-item>

        <el-button
          plain
          size="small"
          style="float: right"
          type="info"
          @click="getVerificationCode"
        >
          发送验证码
        </el-button>

        <div style="height: 5vh"></div>

        <el-form-item label="验证码" prop="verificationCode">
          <el-input
            v-model="registerParams.verificationCode"
            placeholder="verification code"
          ></el-input>
        </el-form-item>

        <button class="submitButton" @click="submit">提交</button>
      </el-form>
    </el-card>
  </div>
</template>

<script lang="ts" setup>
// 注册参数
import { reactive, ref } from 'vue'
import { useUserStore } from '@/store/userStore.ts'
import { campusArray, dormitoryArray, rule } from '@/view/register/static.ts'
import { rules } from '@/util/regCheck/rule.ts'
import { RegisterParams } from '@/api/user/type.ts'
import router from '@/router'
import { checkParamPattern } from '@/util/regCheck/checkFunc.ts'

let userStore = useUserStore()

const canCommit = ref() // 表单校验

// 表单
const campus = ref(campusArray[0].value) // 校区
const dormitory = ref(dormitoryArray[0].value) // 宿舍楼
const registerParams = reactive({
  code: '',
  password: '',
  password2: '',
  nickname: '',
  email: '',
  verificationCode: '',
})

// 表单校验
// 确认密码校验
// @ts-ignore
const checkConfirmPassword = (rule, value, callback) => {
  if (value === registerParams.password) {
    return callback()
  } else {
    return callback(new Error('两次输入密码不一致'))
  }
}
rule.password2 = [
  { required: true, message: '请再次输入密码', trigger: 'blur' },
  { validator: checkConfirmPassword, trigger: 'blur' },
]

// 获取验证码
const getVerificationCode = async () => {
  if (checkParamPattern('email', registerParams.email)) {
    await userStore.userGetVerifyCodeByEmail(registerParams.email)
  }
}

// 提交注册
const submit = async () => {
  // 检查表单
  await canCommit.value.validate()
  // 检查验证码
  const codeResult = await userStore.userCheckVerifyCode({
    emailVerifyCode: registerParams.verificationCode,
    email: registerParams.email,
  })
  if (!codeResult) return // 如果验证码错误，结束提交操作

  // 提交注册
  const registerData: RegisterParams = {
    code: registerParams.code,
    password: registerParams.password,
    nickname: registerParams.nickname,
    email: registerParams.email,
    campus: campus.value,
    dormitory: dormitory.value,
  }
  const registerResult = await userStore.userRegister(registerData)
  if (registerResult) {
    await router.push('/homePage')
  }
}

const toLogin = async () => {
  await router.push('/login')
}
</script>

<style lang="scss" scoped>
.container {
  width: 40vw;
  margin-left: 30%;
  margin-top: 10vh;
}

.submitButton {
  margin-left: 40%;
  color: #090909;
  padding: 0.7em 1.7em;
  font-size: 12px;
  border-radius: 0.5em;
  background: #e8e8e8;
  border: 1px solid #e8e8e8;
  transition: all 0.3s;
  box-shadow:
    6px 6px 12px #c5c5c5,
    -6px -6px 12px #ffffff;
}

.submitButton:hover {
  border: 1px solid white;
}

.submitButton:active {
  box-shadow:
    4px 4px 12px #c5c5c5,
    -4px -4px 12px #ffffff;
}
</style>
