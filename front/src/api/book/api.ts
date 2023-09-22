import request from '@/util/request.ts'
import { Book } from '@/api/book/type.ts'
import { ResponseData } from '@/api/type.ts'

const baseUrl = 'book'

enum Api {
  GET_BOOK_LIST_URL = baseUrl + '/getBookList',
}

export const reqGetBookList = () =>
  request.get<any, ResponseData<Book[]>>(Api.GET_BOOK_LIST_URL)
