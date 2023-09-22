//创建用户相关的小仓库
import { defineStore } from 'pinia'
import { Book } from '@/api/book/type.ts'
import { reqGetBookList } from '@/api/book/api.ts'

export const useBookStore = defineStore('Book', {
  state: () => {
    return {
      bookList: [] as Book[], // 书籍列表
      isShowBookInfoDrawer: false, // 是否显示书籍详情抽屉
      currentBookInfo: {} as Book, // 当前书籍详情
    }
  },
  actions: {
    // 获取书籍列表
    async getBookList() {
      reqGetBookList().then((res) => {
        this.bookList = res.data
      })
    },
  },
})
