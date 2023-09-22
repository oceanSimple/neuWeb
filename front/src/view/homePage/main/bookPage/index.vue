<template>
  <el-row>
    <el-col v-for="item in bookList" :span="7" class="card">
      <el-card :body-style="{ padding: '0px' }" class="card">
        <div style="display: flex">
          <div style="width: 50%">
            <img
              alt="picture"
              class="image"
              src="https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png"
            />
          </div>
          <div style="width: 50%">
            <div class="title">{{ item.title }}</div>
            <div class="author">{{ item.author }}</div>
            <el-tag size="small" style="margin-left: 55px">
              {{ item.address }}
            </el-tag>
            <span style="display: flex; margin-top: 10px; margin-left: 15px">
              <SvgIcon
                height="30px"
                name="rmb"
                width="30px"
                style="margin-left: 20px"
              ></SvgIcon>
              <span class="price">{{ item.price }}</span>
            </span>

            <div style="margin-top: 10px; display: flex">
              <el-button
                :icon="ShoppingCart"
                size="small"
                type="info"
                style="margin-left: 45px"
              >
                购买
              </el-button>
              <el-tooltip content="了解详情" effect="dark" placement="right">
                <SvgIcon
                  height="30px"
                  name="arrow-down"
                  width="30px"
                  @click="showDrawer(item)"
                  style="margin-left: 25px"
                ></SvgIcon>
              </el-tooltip>
            </div>
          </div>
        </div>
      </el-card>
    </el-col>
  </el-row>
  <InfoDraw></InfoDraw>
</template>

<script lang="ts" setup>
import { onMounted, reactive, watch } from 'vue'
import { useBookStore } from '@/store/bookStore.ts'
import { Book } from '@/api/book/type.ts'
import InfoDraw from '@/view/homePage/main/bookPage/InfoDraw.vue'
import { ShoppingCart } from '@element-plus/icons-vue'

let bookStore = useBookStore()

let bookList = reactive([] as Book[])

onMounted(async () => {
  await bookStore.getBookList()
})

watch(
  () => bookStore.bookList,
  (newVal) => {
    bookList.push(...newVal)
  },
)

const showDrawer = (book: Book) => {
  bookStore.isShowBookInfoDrawer = true
  bookStore.currentBookInfo = book
}
</script>

<style lang="scss" scoped>
.card {
  margin: 10px;
}

.title {
  height: 40px;
  font-size: 20px;
  text-align: center;
  margin-bottom: 5px;
}

.price {
  height: 40px;
  font-size: 25px;
  text-align: center;
  color: red;
}

.bottom {
  margin-top: 13px;
  line-height: 12px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.author {
  height: 20px;
  font-size: 10px;
  text-align: center;
}

.button {
  padding: 0;
  min-height: auto;
}

.image {
  width: 100%;
  display: block;
  margin-bottom: 5px;
}
</style>
