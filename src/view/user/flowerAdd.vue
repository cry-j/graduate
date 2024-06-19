<!--
 * @Author: Jing
 * @Date: 2024-04-18 21:45:18
 * @LastEditTime: 2024-06-02 19:30:22
 * @Description: 花卉添加入库页
-->

<template>
    <div class="addDiv">
        <p class="addtittle">添加</p>
        <el-divider />
        <el-form
            :model="form"
            label-width="auto"
            style="max-width: 600px"
            ref="addForm"
        >
            <el-form-item label="品种" prop="typeId">
                <el-select
                    v-model="form.typeId"
                    placeholder="please select your flowerType"
                >
                    <el-option
                        v-for="(item, index) of typeList"
                        :label="item.typeName"
                        :value="item.typeId"
                    />
                </el-select>
            </el-form-item>
            <el-form-item label="数量" prop="number">
                <el-input v-model="form.number" />
            </el-form-item>
            <el-form-item label="购买价" prop="purchase">
                <el-input v-model="form.purchase" />
            </el-form-item>
            <el-form-item label="销售价" prop="sale">
                <el-input v-model="form.sale" />
            </el-form-item>
            <el-form-item>
                <el-button
                    type="primary"
                    @click="onSubmit"
                    class="addBtn"
                    style="margin-left: 30%"
                    >添加</el-button
                >
                <el-button @click="clearInput('addForm')" class="addBtn"
                    >清空</el-button
                >
            </el-form-item>
        </el-form>
    </div>
</template>

<script>
import qs from 'qs'
export default {
    data() {
        return {
            form: {
                typeId: '',//typeId
                number: '',//数量
                purchase: '',//进价
                sale: ''//售价
            },
            userId: '',//用户id
            typeList: []
        }
    },
    /**
     * @return {*}
     * @Date: 2024-06-02 19:30:39
     * @description: 加载初始信息
     */    
    mounted() {
        this.userId = this.$store.state.userInfo.userId;
        this.axios.get('/api/user/getAllTypes.do').then((result) => {
            let rs = result.data.dataList;
            this.typeList = rs;
        }).catch((err) => {
            alert(err.message)
        });
    },
    methods: {
        clearInput(addForm) {
            this.$refs[addForm].resetFields()//重置表单数据
        },
        /**
         * @return {*}
         * @Date: 2024-06-02 19:31:00
         * @description: 添加入库
         */        
        onSubmit() {
            this.axios.post('/api/user/addFlower.do', {
                userId: this.userId,
                typeId: this.form.typeId,
                number: this.form.number,
                purchase: this.form.purchase,
                sale: this.form.sale
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
.addDiv {
    width: 60%;
    position: relative;
    left: 5%;
    top: 5%;
}
.addtittle {
    font-size: 30px;
}
:deep(.el-form-item__label) {
    font-size: 19px;
}
.addBtn {
    margin-left: 20px;
    font-size: 18px;
}
</style>