<template>
  <div class="container">
    <el-card>
      <template #header>
        <el-icon @click="toLogin">
          <component is="Back"></component>
        </el-icon>
        <span style="font-size: 15px" @click="toLogin">返回</span>
        <span style="margin-left: 35%; font-size: 20px">个人信息</span>
      </template>
      <el-form :model="updateParams" label-width="25%">
        <el-form-item label="账号" prop="code">
          <el-input
            v-model="updateParams.code"
            disabled
            placeholder="code"
            prefix-icon="User"
          ></el-input>
        </el-form-item>

        <el-form-item label="昵称" prop="nickname">
          <el-input
            v-model="updateParams.nickname"
            placeholder="nickname"
            prefix-icon="Brush"
          >
            <template #append>
              <el-button @click="update('nickname', updateParams.nickname)">
                修改昵称
              </el-button>
            </template>
          </el-input>
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
            v-model="updateParams.email"
            disabled
            placeholder="email"
            prefix-icon="Message"
          >
            <template #append>
              <el-button @click="updateEmail">修改邮箱</el-button>
            </template>
          </el-input>
        </el-form-item>

        <el-button
          plain
          size="small"
          style="float: right"
          type="info"
          @click="updatePassword"
        >
          修改密码
        </el-button>

        <!--        <button class="submitButton" @click="submit">提交</button>-->
      </el-form>
    </el-card>
  </div>
  <!--修改密码-->
  <DialogPassword></DialogPassword>
  <!--修改邮箱-->
  <DialogEmail></DialogEmail>
</template>

<script lang="ts" setup>
import { reactive, ref, watch } from 'vue'
import { campusArray, dormitoryArray } from '@/view/register/static'
import { UpdateUserParam, User } from '@/api/user/type'
import router from '@/router'
import { getObj } from '@/util/localStorage.ts'
import { useUserStore } from '@/store/userStore.ts'
import DialogPassword from '@/view/personalInformation/info/updatePsw/index.vue'
import DialogEmail from '@/view/personalInformation/info/updateEmail/index.vue'

let userStore = useUserStore()
// 从localStorage中获取用户信息
const user = getObj<User>('user') as User
const campus = ref(user.campus) // 校区
const dormitory = ref(user.dormitory) // 宿舍楼
// 邮箱隐藏
const encryptedMailbox = user.email.replace(/(.{2}).*(.{2}@.*)/, '$1****$2')

// 修改参数
const updateParams = reactive({
  code: user.code,
  nickname: user.nickname,
  email: encryptedMailbox,
})
// 下拉框更改触发更新
watch(
  () => campus.value,
  (val) => {
    update('campus', val)
  },
)
watch(
  () => dormitory.value,
  (val) => {
    update('dormitory', val)
  },
)

const update = async (target: string, data: string) => {
  const updateParam: UpdateUserParam = {
    code: updateParams.code,
    target,
    data,
  }
  await userStore.userUpdate(updateParam)
}

const updatePassword = async () => {
  userStore.dialogPasswordVisible = true
  // 获取验证码
  await userStore.userGetVerifyCodeByEmail(user.email)
}

const updateEmail = async () => {
  userStore.dialogEmailVisible = true
  // 获取验证码
  await userStore.userGetVerifyCodeByEmail(user.email)
}

const toLogin = () => {
  router.push('/homePage')
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
  margin-top: 30px;
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
