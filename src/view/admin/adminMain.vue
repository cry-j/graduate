<!--
 * @Author: Jing
 * @Date: 2024-04-13 21:10:54
 * @LastEditTime: 2024-06-02 20:10:05
 * @Description: Admin的框架
-->

<template>
    <div class="common-layout container">
        <el-container>
            <!-- 头部 -->
            <el-header class="headerLine">
                <el-menu
                    class="el-menu-demo user-menu"
                    mode="horizontal"
                    :ellipsis="false"
                >
                    <el-menu-item index="0" class="permissions">
                        <img
                            src="@/assets/images/admin.png"
                            style="width: 250px; margin-left: 30px"
                        />
                    </el-menu-item>
                    <el-divider
                        direction="vertical"
                        style="
                            height: 100%;
                            width: 2px;
                            color: rgb(239 239 245);
                        "
                    />
                    <div class="flex-grow" />
                    <el-menu-item index="1" class="user-menu">{{
                        times
                    }}</el-menu-item>
                    <el-sub-menu index="2">
                        <template #title
                            ><span style="font-size: 18px; margin-right: 20px"
                                >超级管理员：{{
                                    this.$store.state.admin.uname
                                }}</span
                            ></template
                        >
                        <el-menu-item index="2" @click="quit">
                            <span style="margin-left: 35px"
                                ><el-icon><Back /></el-icon>退出</span
                            ></el-menu-item
                        >
                    </el-sub-menu>
                </el-menu>
            </el-header>
            <!-- 主体 -->
            <el-container>
                <el-aside width="200px" class="asideLine">
                    <el-menu
                        :default-active="activeVue"
                        class="el-menu-vertical-demo"
                        style="padding-top: 10px"
                        @select="handSelect"
                        router
                    >
                        <el-menu-item
                            v-for="(item, index) of menulist"
                            :key="item.id"
                            :index="item.url"
                        >
                            <span style="font-size: 20px; padding-left: 20px">{{
                                item.name
                            }}</span>
                        </el-menu-item>
                    </el-menu>
                </el-aside>
                <!-- 侧边导航 -->

                <el-main
                    ><div id="contentMain">
                        <router-view v-if="isActive"></router-view></div
                ></el-main>
            </el-container>
        </el-container>
    </div>
</template>

<script>
//异步组件导入方式
import { defineAsyncComponent } from 'vue';

const adminHome = defineAsyncComponent(() => import('@/view/admin/adminMain.vue'));
export default {
    provide() {
        return {
            reload: this.reload//用于局部刷新
        }
    },
    components: {
        adminHome,//未使用

    },
    data() {
        return {
            admin: '',//储存admin信息
            times: '',//格式化之后的当前时间
            activeVue: '/adminMain/adminHome',//当前激活页
            isActive: true,//用于局部刷新
            menulist: [{
                id: 1,
                name: '首页',
                url: '/adminMain/adminHome'
            }, {
                id: 2,
                name: '用户管理',
                url: '/adminMain/userManager'
            },
            {
                id: 3,
                name: '花卉类别管理',
                url: '/adminMain/flowerType'
            },
            {
                id: 4,
                name: '添加花卉类别',
                url: '/adminMain/addType'
            },]
        }
    },
    created() {
        this.getTimes();
    },
    /**
     * @return {*}
     * @Date: 2024-06-02 20:08:48
     * @description: 初始化数据信息
     */    
    mounted() {
        this.admin = this.$store.state.admin;
        if (this.$store.state.activeVue != '') {
            this.activeVue = this.$store.state.activeVue
        }
    }, methods: {
        /**
         * @return {*}
         * @Date: 2024-06-02 20:09:54
         * @description: 退出操作
         */        
        quit() {
            if (confirm('你确定退出吗？')) {
                sessionStorage.removeItem('admin')
                this.$store.dispatch('setActive', '')
                this.$router.push('/adminLogin')
            }
        },
        /**
         * @return {*}
         * @Date: 2024-06-02 20:10:06
         * @description: 用于局部刷新
         */        
        reload() {
            this.isActive = false;
            this.$nextTick(() => {
                this.isActive = true;
            })
        },
        /**
         * @return {*}
         * @Date: 2024-06-02 20:10:22
         * @description: 时钟
         */        
        getTimesInterval: function () {
            let _this = this;
            let year = new Date().getFullYear(); //获取当前时间的年份
            let month = new Date().getMonth() + 1; //获取当前时间的月份
            let day = new Date().getDate(); //获取当前时间的天数
            let hours = new Date().getHours(); //获取当前时间的小时
            let minutes = new Date().getMinutes(); //获取当前时间的分数
            let seconds = new Date().getSeconds(); //获取当前时间的秒数
            //当小于 10 的是时候，在前面加 0
            if (hours < 10) {
                hours = "0" + hours;
            }
            if (minutes < 10) {
                minutes = "0" + minutes;
            }
            if (seconds < 10) {
                seconds = "0" + seconds;
            }
            //拼接格式化当前时间
            _this.times = year + "年" + month + "月" + day + "日" + " " + hours + ":" + minutes + ":" + seconds;
        },
        //获取当前时间
        getTimes() {
            setInterval(this.getTimesInterval, 1000);
        },
        /**
         * @param {*} key
         * @param {*} keyPath
         * @return {*}
         * @Date: 2024-06-02 20:10:45
         * @description: 激活页切换
         */        
        handSelect(key, keyPath) {
            this.$store.dispatch('setActive', key)
            console.log("选择：", key, keyPath);
        }
    },
    beforeDestroy() {
        if (this.times) {
            clearInterval(this.getTimesInterval);
        }
    },
    /**
     * @return {*}
     * @Date: 2024-05-30 20:34:44
     * @description: 监听，实现同步更改激活当前Vue
     */  
    watch: {
        "$store.state.activeVue": {
            handler: function (newVal, oldVal) {
                this.activeVue = newVal
                console.log("++++++++" + newVal, oldVal);
            }
        }
    },
}
</script>

<style src="@/assets/main.css" scoped>
</style>
<style lang="scss">
.el-menu-item {
    font-size: 17px;
}
.el-menu--collapse .el-menu .el-submenu,
.el-menu--popup {
    min-width: 180px;
}
.el-menu-item {
    padding-left: 30px;
}
.el-sub-menu .el-sub-menu__icon-arrow {
    margin-right: 20px;
}
</style>