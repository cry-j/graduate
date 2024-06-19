<template>
    <router-view></router-view>
    <!-- <mqtt-vue></mqtt-vue> -->
    <!-- <uploadPicVue></uploadPicVue> -->
    <!-- <test-vue /> -->
</template>
<script>
import mqttVue from './components/mqtt.vue'
import loginVue from './view/Login.vue'
import testVue from './view/test.vue'

export default {
    data() {
        return {
        }
    },
    components: {
        loginVue,
        mqttVue,
        testVue
    },
    //TAG:防止刷新页面丢失
    created() {
        /**
         * @param {*} sessionStorage
         * @return {*}
         * @Date: 2024-05-29 20:53:26
         * @description: 在页面加载时读取sessionStorage里的状态信息，防止刷新页面导致信息情况
        */

        if (sessionStorage.getItem('store')) {
            this.$store.replaceState(
                Object.assign(
                    {},
                    this.$store.state,
                    JSON.parse(sessionStorage.getItem('store'))
                )
            )
        }

        // 在页面刷新时将vuex里的信息保存到sessionStorage里
        // beforeunload事件在页面刷新时先触发
        window.addEventListener('beforeunload', () => {
            sessionStorage.setItem('store', JSON.stringify(this.$store.state))
        })
    },
}
</script>
<style>
</style>