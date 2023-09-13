export interface SortItem {
  index: string
  label: string
  name: string
  icon: string
  children: SortItemChild[]
}

export interface SortItemChild {
  index: string
  name: string
  label: string
}

export const sortList: SortItem[] = [
  {
    index: '1',
    label: '书籍',
    name: 'book',
    icon: 'book',
    children: [
      {
        index: '1-1',
        name: 'textbook',
        label: '教科书',
      },
      {
        index: '1-2',
        name: 'professionalBook',
        label: '专业书籍',
      },
    ],
  },
  {
    index: '2',
    label: '电子产品',
    name: 'electronic',
    icon: 'laptop',
    children: [
      {
        index: '2-1',
        name: 'phone',
        label: '显示器',
      },
      {
        index: '2-2',
        name: 'computer',
        label: '电脑',
      },
    ],
  },
  {
    index: '3',
    label: '生活用品',
    name: 'life',
    icon: 'dailyNecessities',
    children: [
      {
        index: '3-1',
        name: 'clothes',
        label: '衣物',
      },
      {
        index: '3-2',
        name: 'misc',
        label: '杂物',
      },
    ],
  },
]