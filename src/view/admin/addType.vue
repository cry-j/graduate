<!--
 * @Author: Jing
 * @Date: 2024-04-13 23:50:21
 * @LastEditTime: 2024-06-02 20:16:30
 * @Description: 添加类别页面
-->

<template>
    <p class="tittle">添加类别</p>
    <el-divider style="margin-top: 10px" />
    <el-form
        :model="form"
        ref="addType"
        label-width="80px"
        :inline="false"
        style="padding-left: 10%"
    >
        <el-row :gutter="20">
            <el-col :span="8" :offset="0">
                <el-form-item label="类别名称" prop="typeName">
                    <el-input
                        v-model="form.typeName"
                        @blur="showPic"
                    ></el-input>
                </el-form-item>
                <el-form-item label="属名" prop="family">
                    <el-input v-model="form.family"></el-input>
                </el-form-item>
                <el-form-item label="原产地" prop="origin">
                    <el-input v-model="form.origin"></el-input>
                </el-form-item>
                <el-form-item label="花期" prop="florescence">
                    <el-input v-model="form.florescence"></el-input>
                </el-form-item>
                <el-form-item label="习性" prop="sunshine">
                    <el-input v-model="form.sunshine"></el-input>
                </el-form-item>
                <el-form-item label="适宜温度" prop="temperature">
                    <el-input v-model="form.temperature"></el-input>
                </el-form-item>
                <el-form-item label="土质" prop="soil">
                    <el-input v-model="form.soil"></el-input>
                </el-form-item>
                <el-form-item label="水分" prop="water">
                    <el-input v-model="form.water"></el-input>
                </el-form-item>
                <el-form-item label="描述" prop="description">
                    <el-input
                        type="textarea"
                        v-model="form.description"
                    ></el-input> </el-form-item
            ></el-col>
            <el-col :span="12" :offset="1">
                <el-skeleton style="width: 240px" :loading="loading" animated>
                    <template #template>
                        <el-skeleton-item
                            variant="image"
                            style="width: 240px; height: 240px"
                        />
                    </template>
                    <template #default>
                        <el-card
                            style="width: 240px"
                            :body-style="{
                                padding: '2px'
                            }"
                        >
                            <img
                                :src="picUrl"
                                class="image"
                                style="width: 240px; height: 240px"
                            />
                        </el-card>
                    </template>
                </el-skeleton>
            </el-col>
        </el-row>

        <el-form-item>
            <el-button type="primary" @click="onSubmit">添加</el-button>
            <el-button @click="clearInput('addType')">取消</el-button>
        </el-form-item>
    </el-form>
</template>

<script>
export default {
    data() {
        return {
            form: {
                typeName: '',
                family: '',
                origin: '',
                florescence: '',
                sunshine: '',
                temperature: '',
                soil: '',
                water: '',
                flowerPic: '',
                description: ''
            },
            msg: '',
            picUrl: '',
            loading: true//骨架加载显示
        }
    },
    methods: {
        /**
         * @return {*}
         * @Date: 2024-06-02 20:15:13
         * @description: 获取图片
         */        
        showPic() {
            this.loading = false
            console.log(this.form.typeName);
            this.axios.get('/myPicApi/' + this.form.typeName).then((result) => {
                this.msg = result.data.data
                this.picUrl = result.data.data.cover
                let url = JSON.stringify(this.picUrl)
                this.form.flowerPic = url.slice(url.lastIndexOf('/pic/') + 5)
                console.log('++++++++++++++++++' + this.form.flowerPic);
            }).catch((err) => {

            });
        },
        clearInput(addType) {
            this.$refs[addType].resetFields()//重置表单数据
        },
        /**
         * @return {*}
         * @Date: 2024-06-02 20:16:17
         * @description: 添加操作
         */        
        onSubmit() {
            this.axios.post('/api/admin/addType.do', {
                typeName: this.form.typeName,
                family: this.form.family,
                origin: this.form.origin,
                florescence: this.form.florescence,
                sunshine: this.form.sunshine,
                temperature: this.form.temperature,
                soil: this.form.soil,
                water: this.form.water,
                flowerPic: this.form.flowerPic,
                description: this.form.description
            }
            ).then((result) => {
                let rs = result.data;
                if (rs.status === 100) {
                    this.$message.success(rs.data)
                    this.form = {}
                } else {
                    this.$message.error(rs.data)
                }

            }).catch((err) => {

            });
        }
    },
}
</script>

<style scoped>
.tittle {
    font-size: 30px;
    margin-left: 20px;
    padding-top: 20px;
}
.myTable {
    /* width: 90%; */
}
:deep(.el-dialog__title) {
    font-size: 30px;
    margin-left: 20px;
}
ul li {
    font-size: 20px;
    line-height: 30px;
}
ul > li span:nth-child(1) {
    color: rgb(255, 134, 13);
}
</style>