export default [
    {
        path: '/signup',
        component: () => import('@/views/SignupPage.vue')
        // redirect: {path:"/signup"},
        // children: [
        //     {
        //         path: 'view',
        //         component: () => import('@/views/board/BoardView.vue'),
        //     },
        // ],
    },
];