export interface Option {
  label: string
  name: string
  path: string
  icon: string
}

export const optionArray: Option[] = [
  {
    label: '收藏夹',
    name: 'favorite',
    path: '/favorite',
    icon: 'Star',
  },
  {
    label: '历史订单',
    name: 'historyOrder',
    path: '/historyOrder',
    icon: 'Tickets',
  },
  {
    label: '个人信息',
    name: 'info',
    path: '/info',
    icon: 'User',
  },
]