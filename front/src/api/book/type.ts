export interface Book {
  id: number
  title: string
  author: string
  price: number
  address: string
  picture: string
  type: number
  account: number
  printTime: string
  publisher: string
  version: string
  seller: string
  bookType: BookType
}

export interface BookType {
  id: number
  type: string
}
