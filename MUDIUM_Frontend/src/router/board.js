export default [
    {
        path: '/board',
        component: () => import('@/views/board/BoardRoot.vue'),
        redirect: {path:"/board/view"},
        children: [
            {
                path: 'view',
                component: () => import('@/views/board/BoardView.vue'),
            },
            {
                path: 'view/:id',  // :id는 동적 라우트 매개변수
                name: 'BoardDetailView',
                component: () => import('@/views/board/BoardDetailView.vue')    
            },
            {
                path: 'create',  
                name: 'BoardCreate',
                component: () => import('@/views/board/BoardCreate.vue')    
            },
            {
                path: 'view/no-args',  
                name: 'BoardNoArgs',
                component: () => import('@/views/board/BoardNoArgs.vue')    
            },
            {
                path: 'edit/:id',  
                name: 'BoardUpdate',
                component: () => import('@/views/board/BoardUpdate.vue')    
            }
        ],
    },
];
