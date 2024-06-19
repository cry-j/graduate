<!--
 * @Author: Jing
 * @Date: 2024-04-13 21:19:03
 * @LastEditTime: 2024-06-02 15:14:42
 * @Description: 用户的框架
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
                        <img src="@/assets/images/user.png" />
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
                                >欢迎您：{{
                                    this.$store.state.userInfo.name
                                }}</span
                            ></template
                        >
                        <el-menu-item index="2-1" @click="updateUserInfo">
                            <span style="margin-left: 25px"
                                ><el-icon><setting /></el-icon
                                >{{ userHome.name }}</span
                            >
                        </el-menu-item>
                        <el-menu-item index="2" @click="quit">
                            <span style="margin-left: 45px"
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
                        <el-sub-menu index="3">
                            <template #title style="text-align: center">
                                <span
                                    style="font-size: 20px; padding-left: 20px"
                                    >花卉管理</span
                                >
                            </template>
                            <el-menu-item
                                v-for="(item, subIndex) in menuSub"
                                :index="item.url"
                                :key="item.id"
                                style="font-size: 18px; margin-left: 10px"
                            >
                                {{ item.name }}
                            </el-menu-item>
                        </el-sub-menu>
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
import { defineAsyncComponent } from 'vue'

export default {
    provide() {
        return {
            reload: this.reload//用于局部刷新
        }
    },
    data() {
        return {
            userInfo: '',//储存用户信息
            times: '',//格式化之后的当前时间
            isActive: true,//用于局部刷新
            userHome: {
                name: '个人信息',
                url: '/userMain'
            },
            activeVue: '/userMain/userHome',//储存激活页
            menulist: [
                {
                    id: 1,
                    name: '首页',
                    url: '/userMain/userHome'
                },
                {
                    id: 2,
                    name: '修改密码',
                    url: '/userMain/updatePassword'
                },

            ],
            menuSub: [
                {
                    id: '3-1',
                    name: '我的',
                    url: '/userMain/flowerManage'
                },
                {
                    id: '3-2',
                    name: '添加入库',
                    url: '/userMain/addFlower'
                },
                {
                    id: '3-3',
                    name: '花卉资料',
                    url: '/userMain/flowerDetail'
                }
            ]
        }
    },
    created() {
        this.getTimes();
    },
    /**
     * @return {*}
     * @Date: 2024-05-30 20:26:35
     * @description: 加载时读取当前用户信息和上一次激活的页面
     */
    mounted() {
        this.userInfo = this.$store.state.userInfo;
        if (this.$store.state.activeVue != '') {
            this.activeVue = this.$store.state.activeVue
        }

    },
    methods: {
        //获取当前选择的页面url
        handSelect(url) {
            this.tabVue = url
        },
        //获取当前时间
        getTimes() {
            setInterval(this.getTimesInterval, 1000);
        },
        //tag:实现局部刷新,父组件
        //控制组件显示隐藏，使组件进行重绘达到刷新效果
        reload() {
            this.isActive = false;
            this.$nextTick(() => {
                this.isActive = true;
            })
        },
        /**
         * @return _this.times
         * @Date: 2024-05-30 20:31:46
         * @description: 获取当前系统时间
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
        /**
         * @return {*}
         * @Date: 2024-05-30 20:32:35
         * @description: 跳转个人信息页
         */
        updateUserInfo() {
            this.$store.dispatch('setActive', '/userMain/userInfo')
            this.$router.push('userInfo')
        },
        quit() {
            if (confirm('你确定退出吗？')) {
                sessionStorage.removeItem('userInfo')
                this.$store.dispatch('setUserInfo', '')
                this.$store.dispatch('setActive', '')
                this.$router.push('/userLogin')
            }
        },
        /**
         * @param {*} key
         * @param {*} keyPath
         * @return {*}
         * @Date: 2024-05-30 20:33:10
         * @description: 记录点击激活的页面
         */        
        handSelect(key, keyPath) {
            // sessionStorage.setItem('activeVue', key)
            this.$store.dispatch('setActive', key)
            console.log("选择：", key, keyPath);
        },

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

    beforeDestroy() {
        if (this.times) {
            clearInterval(this.getTimesInterval);
        }
    },
}
</script>

<style src="@/assets/main.css" scoped>
</style>
<style lang="scss" scoped>
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

