<template>
  <el-row>
    <el-col :span="20">
      <Message></Message>
    </el-col>
    <el-col :span="4" class="friend">
      <Friend></Friend>
    </el-col>
  </el-row>
</template>

<script setup lang="ts">
import Friend from '@/view/chatRoom/friend/index.vue'
import Message from '@/view/chatRoom/message/index.vue'
</script>

<style scoped lang="scss">
.friend {
  height: 400px;
  background-color: #e8e8e8;
}
</style>
