import { createRouter, createWebHistory } from 'vue-router'
//创建路由
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
    //这个routes可以单独拿出去
    routes: [
        {
            path: '/',
            redirect: '/userLogin'
        },
        {
            path: '/userLogin',  //访问路径
            name: 'userLogin',//名称
            component: () => import('@/view/Login.vue') //引用组件
        },
        {
            path: '/register',
            name: 'register',
            // route level code-splitting
            // this generates a separate chunk (About.[hash].js) for this route
            // which is lazy-loaded when the route is visited.
            component: () => import('@/view/Register.vue') //引用方式2
        },
        {
            path: '/adminLogin',  //访问路径
            name: 'adminLogin',//名称
            component: () => import('@/view/AdminLogin.vue') //引用组件
        },
        {
            path: '/adminMain',
            name: 'adminMain',
            component: () => import('@/view/admin/adminMain.vue'),
            redirect:'/adminMain/adminHome',
            children: [{
                path: 'userManager',
                component: () => import('@/view/admin/userManager.vue')
            },  {
                path: 'adminHome',
                    component: () => import('@/view/admin/adminHome.vue')
                },{
                    path: 'flowerType',
                        component: () => import('@/view/admin/flowerType.vue')
                },
                {
                    path: 'addType',
                        component: () => import('@/view/admin/addType.vue')
                    }]
        
        },
        {
            path: '/userMain',
            name: 'userMain',
            component: () => import('@/view/user/userMain.vue'),
            redirect:'/userMain/userHome',
            children: [{
                path: 'userHome',
                component: () => import('@/view/user/userHome.vue')
            }, {
                path: 'updatePassword',
                component: () => import('@/view/user/updatePassword.vue')
                }, {
                    path: 'flowerManage',
                    component: () => import('@/view/user/flowerManage.vue')
                }, {
                    path: 'userInfo',
                    component: () => import('@/view/user/userInfo.vue')
                }, {
                    path: 'addFlower',
                    component: () => import('@/view/user/flowerAdd.vue')
                }, {
                    path: 'flowerDetail',
                    component: () => import('@/view/user/flowerDetail.vue')
                },
                {
                    path: 'trend',
                    component: () => import('@/view/user/trend.vue')
                }
            ]
        }
  ]
})
//导出
export default router