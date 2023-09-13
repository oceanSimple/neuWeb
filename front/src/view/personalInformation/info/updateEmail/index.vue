<template>
  <!--修改邮箱-->
  <el-drawer
    v-model="userStore.dialogEmailVisible"
    :before-close="handleClose"
    title="修改邮箱"
  >
    <el-form ref="updateEmail" :model="params" :rules="rule" label-width="25%">
      <el-form-item label="旧邮箱">
        <el-input v-model="params.oldEmail" disabled type="text"></el-input>
      </el-form-item>

      <el-form-item label="旧验证码" prop="oldCode">
        <el-input v-model="params.oldCode" type="text"></el-input>
      </el-form-item>

      <el-form-item label="新邮箱" prop="newEmail">
        <el-input v-model="params.newEmail" type="text"></el-input>
      </el-form-item>

      <el-form-item>
        <el-button
          size="small"
          style="margin-left: 70%"
          type="info"
          @click="getNewCode"
        >
          发送验证码
        </el-button>
      </el-form-item>

      <el-form-item label="新验证码" prop="newCode">
        <el-input v-model="params.newCode" type="text"></el-input>
      </el-form-item>
    </el-form>
    <el-button style="margin-left: 50%" type="info" @click="update">
      修改
    </el-button>
  </el-drawer>
</template>

<script lang="ts" setup>
import { useUserStore } from '@/store/userStore.ts'
import { ElMessage, ElMessageBox } from 'element-plus'
import { reactive, ref } from 'vue'
import { getObj } from '@/util/localStorage.ts'
import { User } from '@/api/user/type'
import { rules } from '@/util/regCheck/rule.ts'

let userStore = useUserStore()
const updateEmail = ref() // 校验邮箱表单
const user = getObj<User>('user') as User

// 修改表单变量
const params = reactive({
  oldEmail: user.email.replace(/(.{2}).*(.{2}@.*)/, '$1****$2'),
  oldCode: '',
  newEmail: '',
  newCode: '',
})

// 表单校验规则
const rule = {
  oldCode: [
    { required: true, message: '请输入旧验证码', trigger: 'blur' },
    {
      pattern: rules.emailVerifyCode.pattern,
      message: rules.emailVerifyCode.message,
      trigger: 'blur',
    },
  ],
  newEmail: [
    { required: true, message: '请输入新邮箱', trigger: 'blur' },
    {
      pattern: rules.email.pattern,
      message: rules.email.message,
      trigger: 'blur',
    },
  ],
  newCode: [
    { required: true, message: '请输入新验证码', trigger: 'blur' },
    {
      pattern: rules.emailVerifyCode.pattern,
      message: rules.emailVerifyCode.message,
      trigger: 'blur',
    },
  ],
}

const getNewCode = () => {
  userStore.userGetVerifyCodeByEmail(params.newEmail)
}

const update = async () => {
  await updateEmail.value.validate()

  const oldCodeFlag = await userStore.userCheckVerifyCode({
    email: user.email,
    emailVerifyCode: params.oldCode,
  })
  if (!oldCodeFlag) return

  const newCodeFlag = await userStore.userCheckVerifyCode({
    email: params.newEmail,
    emailVerifyCode: params.newCode,
  })
  if (!newCodeFlag) return

  await userStore.userUpdate({
    code: user.code,
    target: 'email',
    data: params.newEmail,
  })
  // 清空表单，关闭弹窗
  updateEmail.value.resetFields()
  userStore.dialogEmailVisible = false
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
      updateEmail.value.resetFields()
    })
    .catch(() => {})
}
</script>

<style lang="scss" scoped></style>
