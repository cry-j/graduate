<!--
 * @Author: Jing
 * @Date: 2024-04-13 22:07:25
 * @LastEditTime: 2024-06-02 19:33:55
 * @Description: 用户管理页码
-->
<template>
    <p style="font-size: 30px; margin-left: 10px; padding-top: 20px">
        用户管理
    </p>
    <el-divider />
    <el-table
        :data="tableData"
        stripe
        table-layout="auto"
        style="width: 90%; margin-left: 5%"
    >
        <el-table-column
            fixed
            label="序号"
            type="index"
            :index="reIndex"
            width="80"
        />
        <el-table-column label="登录日期" prop="loginDate" width="150">
        </el-table-column>

        <el-table-column prop="userId" label="用户id" width="80" />
        <el-table-column prop="userName" label="用户名" width="120" />
        <el-table-column prop="name" label="昵称" width="120" />
        <el-table-column prop="sex" label="性别" width="80" />
        <el-table-column prop="age" label="年龄" width="120" />
        <el-table-column prop="tel" label="电话" width="150" />
        <el-table-column prop="address" label="地址" width="150" />
        <el-table-column label="状态" width="120">
            <template v-slot="scope">
                <el-tag type="danger" effect="dark" v-if="scope.row.state == 0"
                    >已封禁</el-tag
                >
                <el-tag type="success" effect="dark" v-if="scope.row.state == 1"
                    >正常</el-tag
                >
            </template>
        </el-table-column>
        <el-table-column label="Operations" fixed="right" width="160">
            <template #default="scope">
                <el-button
                    size="small"
                    @click="handleEdit(scope.$index, scope.row)"
                    v-if="scope.row.state == 1"
                >
                    封禁
                </el-button>
                <el-button
                    size="small"
                    @click="handleEdit(scope.$index, scope.row)"
                    v-if="scope.row.state == 0"
                >
                    解封
                </el-button>
                <el-button
                    size="small"
                    type="danger"
                    @click="handleDelete(scope.$index, scope.row)"
                >
                    删除
                </el-button>
            </template>
        </el-table-column>
    </el-table>
    <!-- 分页 -->
    <el-pagination
        :page-size="8"
        v-model:current-page="pageNo"
        :pager-count="5"
        layout="prev, pager, next"
        v-model:total="total"
        @current-change="currentChange"
        style="margin-left: 40%"
    />
</template>

<script>
import qs from 'qs'
export default {
    inject: ['reload'],//用于局部刷新
    data() {
        return {
            tableData: [

            ],
            pageNo: 1,
            total: 1
        }
    },
    /**
     * @return {*}
     * @Date: 2024-06-02 19:32:15
     * @description: 加载初始数据
     */    
    mounted() {
        this.axios.get('/api/admin/getAllUser.do?pageNo=1').then((result) => {
            let rs = result.data.page;

            this.pageNo = parseInt(rs.currentPage);
            this.total = parseInt(rs.total);
            this.tableData = rs.list
            console.log(this.tableData)
        }).catch((err) => {
            alert(err.message)
        });
    },
    methods: {
        /**
         * @param {*} index
         * @return {*}
         * @Date: 2024-06-02 19:32:26
         * @description: 获取序号
         */        
        reIndex(index) {
            return (this.pageNo - 1) * 8 + index + 1
        },
        /**
         * @param {*} pageNo
         * @return {*}
         * @Date: 2024-06-02 19:32:35
         * @description: 页码改变操作
         */        
        currentChange(pageNo) {
            this.axios.get(`/api/admin/getAllUser.do?pageNo=${pageNo}`).then((result) => {
                let rs = result.data.page;
                this.pageNo = parseInt(rs.currentPage);
                this.total = parseInt(rs.total);
                this.tableData = rs.list
            }).catch((err) => {
                alert(err.message)
            });
        },
        /**
         * @param {*} index
         * @param {*} row
         * @return {*}
         * @Date: 2024-06-02 19:32:52
         * @description: 封禁解封操作
         */        
        handleEdit(index, row) {
            this.axios.put('/api/admin/updateState.do', qs.stringify({
                state: row.state,
                userId: row.userId,
            })).then((result) => {

                if (result.status === 200) {
                    //局部刷新
                    this.refresh();
                    this.$message.success(result.data)
                }
            }).catch((err) => {
                this.$message.error("error")
            });
        },
        refresh() { //局部刷新
            this.reload();
        },
        /**
         * @param {*} index
         * @param {*} row
         * @return {*}
         * @Date: 2024-06-02 19:33:44
         * @description: 删除操作
         */        
        handleDelete(index, row) {
            let userId = row.userId;
            if (confirm("你确定要删除吗？")) {
                this.axios.delete(`/api/admin//deleteUser.do?userId=${userId}`).then((result) => {
                    if (result.status === 200) {
                        this.$message.success(result.data)
                        this.refresh()
                    } else {
                        this.$message.error("error")
                    }
                }).catch((err) => {

                });
            }
        }
    },
}
</script>

<style>
</style>