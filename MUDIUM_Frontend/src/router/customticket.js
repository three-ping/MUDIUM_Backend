export default [
    {
      path: '/customticket',
      component: () => import('@/views/customticket/Customticket.vue'),
      },
    {
      path: '/customticket/:id',
      name: 'CustomTicketView',
      component: () => import('@/views/customticket/CustomTicketView.vue'),
    }
  ];
