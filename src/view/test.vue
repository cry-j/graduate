<!--
 * @Author: Jing
 * @Date: 2024-05-05 15:25:19
 * @LastEditTime: 2024-05-30 21:02:29
 * @Description: 
-->
<template>
    <h3>测试界面</h3>
    <input type="text" v-model="name" />
    <input type="button" value="查找" @click="findPic" />
    <p>{{ msg }}</p>
    <img :src="picUrl" alt="" />\   
    
</template>

<script>

export default {
    data() {
        return {
            name: '',
            msg: '',
            picUrl: ''
        }
    },
    methods: {
        findPic() {
            console.log(this.name);
            this.axios.get('/myPicApi/' + this.name).then((result) => {
                this.msg = result.data.data
                this.picUrl = result.data.data.cover
                console.log(this.picUrl);
            }).catch((err) => {

            });
        }
    },
}
</script>

<style>
</style>