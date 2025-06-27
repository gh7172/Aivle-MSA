import { createRouter, createWebHashHistory } from 'vue-router';

const router = createRouter({
  history: createWebHashHistory(),
  routes: [
    {
      path: '/',
      component: () => import('../components/pages/Index.vue'),
    },
    {
      path: '/작가',
      component: () => import('../components/ui/작가Grid.vue'),
    },
    {
      path: '/집필',
      component: () => import('../components/ui/집필Grid.vue'),
    },
    {
      path: '/도서',
      component: () => import('../components/ui/도서Grid.vue'),
    },
    {
      path: '/subscribes',
      component: () => import('../components/ui/SubscribeGrid.vue'),
    },
    {
      path: '/유저',
      component: () => import('../components/ui/유저Grid.vue'),
    },
    {
      path: '/points',
      component: () => import('../components/ui/PointGrid.vue'),
    },
    {
      path: '/집필요청',
      component: () => import('../components/ui/집필요청Grid.vue'),
    },
    {
      path: '/pays',
      component: () => import('../components/ui/PayGrid.vue'),
    },
    {
      path: '/userInfos',
      component: () => import('../components/UserInfoView.vue'),
    },
  ],
})

export default router;
