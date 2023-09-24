const homePageRoutes = [
  {
    path: '/homePage',
    name: 'HomePage',
    component: () => import('@/view/homePage/index.vue'),
    redirect: '/homePage/book',
    children: [
      {
        path: 'book',
        name: 'Book',
        component: () => import('@/view/homePage/main/bookPage/index.vue'),
      },
    ],
  },
  {
    path: '/chatRoom',
    name: 'ChatRoom',
    component: () => import('@/view/chatRoom/index.vue'),
  },
]

const loginRoutes = [
  {
    path: '/',
    redirect: '/login',
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/view/login/index.vue'),
  },

  {
    path: '/register',
    name: 'Register',
    component: () => import('@/view/register/index.vue'),
  },
  {
    path: '/findBackPsw',
    name: 'FindBackPsw',
    component: () => import('@/view/findBackPsw/index.vue'),
  },
]

const personalInformationRoutes = [
  {
    path: '/favorite',
    name: 'Favorite',
    component: () => import('@/view/personalInformation/favorite/index.vue'),
  },
  {
    path: '/historyOrder',
    name: 'HistoryOrder',
    component: () =>
      import('@/view/personalInformation/historyOrder/index.vue'),
  },
  {
    path: '/info',
    name: 'Info',
    component: () => import('@/view/personalInformation/info/index.vue'),
  },
  {
    path: '/sell',
    name: 'Sell',
    component: () => import('@/view/personalInformation/sell/index.vue'),
  },
]

export const route = Array.prototype.concat(
  loginRoutes,
  homePageRoutes,
  personalInformationRoutes,
)
