<template>
  <!--修改密码-->
  <el-drawer
    v-model="userStore.dialogPasswordVisible"
    :before-close="handleClose"
    title="修改密码"
  >
    <el-form
      ref="updatePsw"
      :model="dialogPsw"
      :rules="dialogPswRules"
      label-width="25%"
    >
      <el-form-item label="密码" prop="password">
        <el-input v-model="dialogPsw.password" type="password"></el-input>
      </el-form-item>
      <el-form-item label="确认密码" prop="confirmPassword">
        <el-input
          v-model="dialogPsw.confirmPassword"
          type="password"
        ></el-input>
      </el-form-item>
      <el-form-item label="验证码" prop="verifyCode">
        <el-input v-model="dialogPsw.verifyCode" type="text"></el-input>
      </el-form-item>
    </el-form>
    <el-button circle style="margin-left: 50%" @click="updatePassword">
      修改
    </el-button>
  </el-drawer>
</template>

<script lang="ts" setup>
// 修改密码
import { reactive, ref } from 'vue'
import { useUserStore } from '@/store/userStore.ts'
import { ElMessage, ElMessageBox } from 'element-plus'
import { rules } from '@/util/regCheck/rule.ts'
import { User } from '@/api/user/type'
import { getObj } from '@/util/localStorage.ts'
import { reqCheckEmailVerificationCode } from '@/api/user/api.ts'

let userStore = useUserStore()

const updatePsw = ref() // 校验密码表单
const dialogPsw = reactive({
  password: '',
  confirmPassword: '',
  verifyCode: '',
})
const checkConfirmPassword = () => {
  if (dialogPsw.password !== dialogPsw.confirmPassword) {
    return false
  } else {
    return true
  }
}
const dialogPswRules = reactive({
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    {
      pattern: rules['password'].pattern,
      message: rules['password'].message,
      trigger: 'blur',
    },
  ],
  confirmPassword: [
    { required: true, message: '请再次输入密码', trigger: 'blur' },
    {
      validator: checkConfirmPassword,
      message: '长度在 6 到 20 个字符',
      trigger: 'blur',
    },
  ],
  verifyCode: [
    { required: true, message: '请输入验证码', trigger: 'blur' },
    {
      pattern: rules.emailVerifyCode.pattern,
      message: rules.emailVerifyCode.message,
      trigger: 'blur',
    },
  ],
})
const updatePassword = async () => {
  await updatePsw.value.validate()

  const user = getObj<User>('user') as User
  const result = await reqCheckEmailVerificationCode({
    emailVerifyCode: dialogPsw.verifyCode,
    email: user.email,
  })
  if (result.code !== 200) {
    return
  }
  await userStore.userUpdate({
    code: user.code,
    target: 'password',
    data: dialogPsw.password,
  })
  updatePsw.value.resetFields()
  userStore.dialogPasswordVisible = false
}

const handleClose = (done: () => void) => {
  ElMessageBox.confirm('放弃修改？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning',
  })
    .then(() => {
      done()
      ElMessage({
        type: 'info',
        message: '取消修改',
      })
      updatePsw.value.resetFields()
    })
    .catch(() => {})
}
</script>

<style lang="scss" scoped></style>
