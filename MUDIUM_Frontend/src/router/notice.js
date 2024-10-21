export default [
    {
        path: '/notice',
        component: () => import('@/views/notice/NoticeRoot.vue'),
        redirect: {path:"/notice/view"},
        children: [
            {
                path: 'view',
                component: () => import('@/views/notice/NoticeView.vue'),
            },
            {
                path: 'view/:id',  // :id는 동적 라우트 매개변수
                name: 'NoticeDetailView',
                component: () => import('@/views/notice/NoticeDetailView.vue')    
            },
            {
                path: 'create',  
                name: 'NoticeCreate',
                component: () => import('@/views/notice/NoticeCreate.vue')    
            },
            {
                path: 'view/no-args',  
                name: 'BoardNoArgs',
                component: () => import('@/views/notice/NoticeNoArgs.vue')    
            },
            {
                path: 'edit/:id',  
                name: 'NoticeUpdate',
                component: () => import('@/views/notice/NoticeUpdate.vue')    
            }
        ]
    }
];
