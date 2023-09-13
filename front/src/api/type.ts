// 返回的通用数据类型（与后端通用返回类型对应）
export interface ResponseData<T> {
  code: number
  msg: string
  map: Map<string, any>
  data: T
}