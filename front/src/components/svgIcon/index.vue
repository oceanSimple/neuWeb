<template>
  <div>
    <svg :style="{ width, height }">
      <use :fill="color" :xlink:href="prefix + name"></use>
    </svg>
  </div>
</template>

<script lang="ts" setup>
// 接收父组件传递的参数
defineProps({
  // xlink:href的前缀
  prefix: {
    type: String,
    default: '#icon-',
  },
  // 图标名称
  name: String,
  // 颜色
  color: {
    type: String,
    default: '',
  },
  // 大小
  width: {
    type: String,
    default: '50px',
  },
  height: {
    type: String,
    default: '50px',
  },
})
</script>

<style scoped></style>