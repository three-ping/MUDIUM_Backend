export default [
    {
        path: '/secret-review',
        component: () => import('@/views/secret-review/SecretReviewRoot.vue'),
        redirect: {path:"/secret-review/view"},        // 이 부분 나중에 바꾸기!!!!!
        children: [
            {
                path: 'view',
                component: () => import('@/views/secret-review/SecretReviewView.vue'),
            },
            {
                path: 'view/:musicalId/:secretReviewId',  // :id는 동적 라우트 매개변수
                name: 'SecretReviewDetailView',
                component: () => import('@/views/secret-review/SecretReviewDetailView.vue')    
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
