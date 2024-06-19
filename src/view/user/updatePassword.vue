<!--
 * @Author: Jing
 * @Date: 2024-04-14 08:39:31
 * @LastEditTime: 2024-06-02 15:08:19
 * @Description: 更新密码界面
-->

<template>
    <el-card
        shadow="never"
        :body-style="{ padding: '20px' }"
        style="border: none"
    >
        <template #header>
            <div class="card-header">
                <span class="cardName">密码修改</span>
            </div>
        </template>
        <!-- card body -->
        <p class="text item">
            <el-form
                :model="form"
                ref="form"
                :rules="rules"
                label-width="30%"
                :inline="false"
                style="width: 40%; margin-top: 20px"
            >
                <el-form-item label="用户名：">
                    <el-input
                        v-model="form.userInfo.userName"
                        disabled
                    ></el-input>
                </el-form-item>
                <el-form-item label="原密码：" prop="oldPwd">
                    <el-input v-model="form.oldPwd" type="password"></el-input>
                </el-form-item>
                <el-form-item label="新密码：" prop="newPwd">
                    <el-input v-model="form.newPwd" type="password"></el-input>
                </el-form-item>
                <div style="margin-left: 30%; padding-top: 30px">
                    <el-button
                        type="primary"
                        @click="onSubmit"
                        style="margin-right: 10px"
                        >立即修改</el-button
                    >
                    <el-button @click="reset">取消</el-button>
                </div>
            </el-form>
        </p>
    </el-card>
</template>

<script>
import qs from 'qs'
import { ElMessage } from 'element-plus'
//弹出框
const resucess = () => {
    ElMessage({
        message: '修改成功',
        type: 'success',
    })
}
export default {
    data() {
        return {
            form: {
                userInfo: {},//储存用户信息
                oldPwd: '',//旧密码
                newPwd: ''//新密码
            },
            rules: {
                oldPwd: [{ required: true, message: "密码不能为空", trigger: ['blur',] }],
                newPwd: [{ required: true, message: "密码不能为空", trigger: ['blur',] }],
            }
        }
    },
    mounted() {
        //获取用户信息
        this.form.userInfo = this.$store.state.userInfo;
    },
    methods: {
        /**
         * @return {*}
         * @Date: 2024-06-02 14:55:56
         * @description: 密码修改提交
         */        
        onSubmit() {
            this.$refs['form'].validate((valid) => {
                if (valid) {
                    console.log("校验通过， 可以保存了")
                    if (this.form.userInfo.userPwd != this.form.oldPwd) {
                        alert("原密码不正确")
                    } else {
                        this.axios.post('/api/user/updatePwd.do', qs.stringify({
                            userId: this.form.userInfo.userId,
                            password: this.form.newPwd
                        })).then((result) => {
                            let rs = result.data;
                            if (rs.status === 100) {
                                resucess();
                                sessionStorage.setItem('userInfo', JSON.stringify(rs.data))//注意要先将数据转成字符串格式，在进行存值
                                this.$store.dispatch('setUserInfo', rs.data)
                                this.reset()
                            } else {
                                alert(rs.data);
                            }
                        }).catch((err) => {

                        });

                    }
                }
                else {
                    console.log("校验失败")
                }
            })
        },
        reset() {
            this.form.oldPwd = '';
            this.form.newPwd = '';
        }
    },
}
</script>

<style scoped>
.cardName {
    font-size: 30px;
}
:deep(.el-form-item__label) {
    font-size: 19px;
}
</style>