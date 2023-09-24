<template>
  <el-drawer
    v-model="showDrawer"
    :before-close="handleClose"
    direction="rtl"
    title="书籍详情"
  >
    <el-image
      style="width: 200px; height: 200px; margin-left: 75px"
      :src="bookInfo.picture"
      :zoom-rate="1.2"
      :preview-src-list="previewSrcList"
      fit="cover"
    ></el-image>
    <el-form>
      <el-form-item label="书名">
        <div>{{ bookInfo.title }}</div>
      </el-form-item>
      <el-form-item label="价格">
        <div>{{ bookInfo.price }}</div>
      </el-form-item>
      <el-form-item label="作者">
        <div>{{ bookInfo.author }}</div>
      </el-form-item>
      <el-form-item label="种类">
        <div>{{ bookInfo.bookType.type }}</div>
      </el-form-item>
      <el-form-item label="卖家">
        <div>{{ bookInfo.seller }}</div>
      </el-form-item>
      <el-form-item label="库存">
        <div>{{ bookInfo.account }}</div>
      </el-form-item>
      <el-form-item label="版本" v-if="bookInfo.version !== ''">
        <div>{{ bookInfo.version }}</div>
      </el-form-item>
      <el-form-item label="出版商" v-if="bookInfo.publisher !== ''">
        <div>{{ bookInfo.publisher }}</div>
      </el-form-item>
      <el-form-item label="印刷年份" v-if="bookInfo.printTime !== ''">
        <div>{{ bookInfo.printTime }}</div>
      </el-form-item>
    </el-form>

    <template #footer>
      <div style="flex: auto">
        <el-button :icon="ChatLineRound" type="primary">与卖家联系</el-button>
      </div>
    </template>
  </el-drawer>
</template>

<script lang="ts" setup>
import { useBookStore } from '@/store/bookStore.ts'
import { reactive, ref, watch } from 'vue'
import { Book } from '@/api/book/type.ts'
import { ChatLineRound } from '@element-plus/icons-vue'

let bookStore = useBookStore()
let showDrawer = ref(false)
let bookInfo = reactive({} as Book)
let previewSrcList = reactive([] as string[])
watch(
  () => bookStore.isShowBookInfoDrawer,
  (newValue) => {
    showDrawer.value = newValue
  },
)
watch(
  () => bookStore.currentBookInfo,
  (newValue) => {
    bookInfo.id = newValue.id
    bookInfo.title = newValue.title
    bookInfo.author = newValue.author
    bookInfo.price = newValue.price
    bookInfo.version = newValue.version
    bookInfo.bookType = newValue.bookType
    bookInfo.seller = newValue.seller
    bookInfo.account = newValue.account
    bookInfo.publisher = newValue.publisher
    bookInfo.printTime = newValue.printTime
    bookInfo.picture = newValue.picture
    previewSrcList = [newValue.picture]
  },
)

const handleClose = (done: () => void) => {
  bookStore.isShowBookInfoDrawer = false
  done()
}
</script>

<style lang="scss" scoped></style>
