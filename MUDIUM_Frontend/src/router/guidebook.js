export default [
    {
        path: '/guidebook',
        component: () => import('@/views/guidebook/GuidebookMain.vue'),
        redirect: {path:"guidebook/guidemain"},
        children: [
            {
                path: 'guidemain',
                component: () => import('@/components/guidebook/Recommended.vue'),
            },
            {
                path: 'terms',
                component: () => import('@/components/guidebook/Terms.vue'),
            },
            {
                path: 'etiquette',
                component: () => import('@/components/guidebook/Etiquette.vue'),
            },
        ],
    },
];