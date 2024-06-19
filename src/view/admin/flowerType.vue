<!--
 * @Author: Jing
 * @Date: 2024-04-13 23:50:30
 * @LastEditTime: 2024-06-02 20:11:11
 * @Description: 花卉类别管理界面
-->

<template>
    <p class="tittle">
        花卉类别
        <el-button
            type="primary"
            size="default"
            @click="addType"
            style="float: right; margin-right: 5%"
            >添加类别</el-button
        >
    </p>
    <el-divider style="margin-top: 10px" />
    <div class="myTable" style="padding-left: 30px; padding-right: 30px">
        <el-table
            :data="typeList"
            style="font-size: 20px"
            width="100%"
            max-height="450px"
            table-layout="auto"
            :row-style="{ height: '50px' }"
        >
            <el-table-column
                fixed
                label="序号"
                type="index"
                :index="1"
                width="80"
            />
            <el-table-column prop="typeName" label="类别名称" width="150" />
            <el-table-column prop="family" label="属" width="200" />
            <el-table-column
                prop="temperature"
                label="适宜温度"
                width="280"
                show-overflow-tooltip
            />
            <el-table-column
                prop="sunshine"
                label="日照"
                width="280"
                show-overflow-tooltip
            />

            <el-table-column
                prop="florescence"
                label="花期"
                width="280"
                show-overflow-tooltip
            />
            <el-table-column
                prop="origin"
                label="原产地"
                width="280"
                show-overflow-tooltip
            />
            <el-table-column fixed="right" label="操作" width="80">
                <template #default="scope">
                    <el-button
                        link
                        type="primary"
                        size="large"
                        @click="flowerDetail(scope.$index, scope.row)"
                    >
                        Detail
                    </el-button>
                </template>
            </el-table-column>
        </el-table>
        <el-dialog v-model="DialogVisible" :title="type.typeName" width="900">
            <el-divider style="margin-top: 10px" />
            <el-form
                :model="type"
                ref="form"
                label-width="80px"
                :inline="false"
            >
                <el-row :gutter="20">
                    <el-col :span="14" :offset="0">
                        <el-form-item label="科属">
                            <el-input v-model="type.family"></el-input>
                        </el-form-item>
                        <el-form-item label="原产地">
                            <el-input v-model="type.origin"></el-input>
                        </el-form-item>
                        <el-form-item label="花果期">
                            <el-input v-model="type.florescence"></el-input>
                        </el-form-item>
                        <el-form-item label="日照">
                            <el-input v-model="type.sunshine"></el-input>
                        </el-form-item>
                        <el-form-item label="温度">
                            <el-input v-model="type.temperature"></el-input>
                        </el-form-item>
                        <el-form-item label="土壤">
                            <el-input v-model="type.soil"></el-input>
                        </el-form-item>
                        <el-form-item label="水分">
                            <el-input v-model="type.water"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="10" :offset="0"
                        ><el-image
                            :src="
                                'https://bkimg.cdn.bcebos.com/pic/' +
                                type.flowerPic
                            "
                            fit="scale-down"
                            :min-scale="0.7"
                            :max-scale="2"
                            ><template #placeholder>
                                <div class="image-slot">
                                    Loading<span class="dot">...</span>
                                </div>
                            </template></el-image
                        ></el-col
                    >
                </el-row>
                <el-divider style="margin-bottom: 5px" />
                <p style="font-size: 25px; margin-bottom: 10px">描述</p>
                <el-row :gutter="20">
                    <el-col :span="23" :offset="0">
                        <el-input
                            v-model="type.description"
                            type="textarea"
                            style="height: 150px"
                        ></el-input>
                    </el-col>
                </el-row>
                <el-form-item>
                    <el-button
                        type="primary"
                        @click="updateType"
                        style="margin-right: 20px"
                        >修改</el-button
                    >
                    <el-button @click="DialogVisible = false">取消</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>
    </div>
</template>

<script>
const baseUrl = "https://bkimg.cdn.bcebos.com/pic/"
export default {
    data() {
        return {
            typeList: [],
            type: {

            },
            DialogVisible: false,//控制修改页面显示

        }
    },
    /**
     * @return {*}
     * @Date: 2024-06-02 19:36:24
     * @description: 加载初始数据
     */    
    mounted() {
        this.axios.get('/api/user/getAllTypes.do').then((result) => {
            let rs = result.data.dataList;
            this.typeList = rs;
        }).catch((err) => {
            alert(err.message)
        });
    },
    methods: {
        /**
         * @param {*} index
         * @param {*} row
         * @return {*}
         * @Date: 2024-06-02 19:36:41
         * @description:花卉类别详情展示 
         */        
        flowerDetail(index, row) {
            this.type = row
            console.log(this.type)
            this.DialogVisible = true
        },
        /**
         * @return {*}
         * @Date: 2024-06-02 20:04:23
         * @description: 修改花卉类别
         */        
        updateType() {
            this.axios.put('/api/admin/updateType.do', this.type
            ).then((result) => {
                let rs = result.data;
                if (rs.status === 100) {
                    this.$message.success(rs.data)
                    this.form = {}
                } else {
                    this.$message.error(rs.data)
                }
            }).catch((err) => {
                this.$message.error(err)
            });
            this.DialogVisible = false
        },
        addType() {
            this.$store.dispatch('setActive', '/adminMain/addType')
            this.$router.push('addType')
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
:deep(.el-form-item__label) {
    font-size: 18px;
}
ul li {
    font-size: 20px;
    line-height: 30px;
}
ul > li span:nth-child(1) {
    color: rgb(255, 134, 13);
}
</style>