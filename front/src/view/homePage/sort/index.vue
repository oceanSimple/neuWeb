<template>
  <div class="title">分类</div>
  <el-menu background-color="white" collapse>
    <el-sub-menu v-for="item in sortList" :index="item.index">
      <template #title>
        <SvgIcon :name="item.icon" height="40px" width="40px"></SvgIcon>
      </template>

      <el-menu-item-group :title="item.label">
        <el-menu-item
          v-for="i in item.children"
          :index="i.index"
          :param="i.name"
        >
          {{ i.label }}
        </el-menu-item>
      </el-menu-item-group>
    </el-sub-menu>
  </el-menu>
</template>

<script lang="ts" setup>
import { sortList } from '@/view/homePage/sort/static.ts'
</script>

<style lang="scss" scoped>
.title {
  text-align: center;
  font-size: 30px;
  margin-bottom: 20px;
  margin-top: 15px;
  font-family: '华文行楷';
}
</style>