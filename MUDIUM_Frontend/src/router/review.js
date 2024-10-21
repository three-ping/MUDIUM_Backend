export default [
    {
        path: '/review',
        component: () => import('@/views/review/ReviewRoot.vue'),
        redirect: {path:"/review/view"},        // 이 부분 나중에 바꾸기!!!!!
        children: [
            {
                path: 'view/:musicalId',
                component: () => import('@/views/review/ReviewView.vue'),
            },
            {
                path: 'view/:musicalId/:reviewId',  // :id는 동적 라우트 매개변수
                name: 'ReviewDetailView',
                component: () => import('@/views/review/ReviewDetailView.vue')    
            },
            // {
            //     path: 'create',  
            //     name: 'BoardCreate',
            //     component: () => import('@/views/board/BoardCreate.vue')    
            // },
            // {
            //     path: 'view/no-args',  
            //     name: 'BoardNoArgs',
            //     component: () => import('@/views/board/BoardNoArgs.vue')    
            // },
            // {
            //     path: 'edit/:id',  
            //     name: 'BoardUpdate',
            //     component: () => import('@/views/board/BoardUpdate.vue')    
            // }
        ],
    },
];
