<!--
 * @Author: Jing
 * @Date: 2024-04-12 23:11:17
 * @LastEditTime: 2024-05-29 20:27:07
 * @Description: 管理员登录
-->

<template>
    <div class="container">
        <el-button
            id="adminBtn"
            type="primary"
            size="default"
            @click="userLogin"
            >用户登录</el-button
        >
        <el-container direction="vertical" id="myLogin_div">
            <el-header height="20%"> 管理员登录 </el-header>

            <el-main height="70%">
                <!-- Main content -->
                <el-form
                    :rules="rules"
                    :model="form"
                    label-width="auto"
                    id="myLogin_form"
                    ref="myForm"
                >
                    <el-row>
                        <el-col :span="30"
                            ><el-form-item label="用户名：" prop="admin.uname">
                                <input
                                    type="text"
                                    v-model="form.admin.uname"
                                    class="cin"
                                /> </el-form-item
                        ></el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="30">
                            <el-form-item label="密码：" prop="admin.uPwd">
                                <input
                                    type="password"
                                    v-model="form.admin.uPwd"
                                    class="cin"
                                /> </el-form-item
                        ></el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="100" direction="vertical">
                            <el-form-item>
                                <el-button
                                    class="login_btn"
                                    style="width: 180px; margin-left: 80px"
                                    type="primary"
                                    @click="adminLoginSubmit"
                                    >登录</el-button
                                >
                            </el-form-item></el-col
                        >
                    </el-row>
                </el-form>
            </el-main>
            <el-footer height="10%">
                <!-- Footer content -->
                <span>version 1.0 </span><span>2023/12</span>
            </el-footer>
        </el-container>
    </div>
</template>

<script>
import qs from 'qs'
export default {
    data() {
        return {
            form: {
                admin: {
                    uname: '',
                    uPwd: '',
                }

            },
            rules: {
                admin: {
                    uname: [{ required: true, message: "用户名不能为空", trigger: ['blur', 'change'] }],
                    uPwd: [{ required: true, message: "密码不能为空", trigger: ['blur', 'change'] }],
                }

            },
        }
    },
    methods: {
        /**
         * @return {*}
         * @Date: 2024-05-29 20:26:24
         * @description: 跳转用户登录界面
         */
        userLogin() {
            this.$router.push('userLogin')
        },
        /**
         * @return {*}
         * @Date: 2024-05-29 20:26:41
         * @description: 管理员用户登录校验
         */
        adminLoginSubmit() {
            this.$refs['myForm'].validate((valid) => {
                if (valid) {
                    console.log("校验通过， 可以保存了")
                    this.adminLogin();
                }
                else {
                    console.log("校验失败")
                }
            })
        },
        /**
         * @return String
         * @Date: 2024-05-29 20:27:10
         * @description: 管理员登录
         * @Tip: qs.stringify用于将对象 转成URL的形式，将数据以&进行拼接
         */
        adminLogin() {
            this.axios.post('api/login/adminLogin.do', qs.stringify(this.form.admin)).then((result) => {
                if (result.data === "登录成功") {
                    sessionStorage.setItem('admin', JSON.stringify(this.form.admin))//注意要先将数据转成字符串格式，在进行存值
                    this.$store.dispatch('setAdmin', this.form.admin)
                    this.$router.push('adminMain');
                } else {
                    alert(result.data)
                    this.form.admin.uname = '';
                    this.form.admin.uPwd = ''
                }
            }).catch((err) => {

            });
        }
    }
}
</script>
<style src="@/styles/login.css" scoped lang="less">
</style>
<style scoped>
.el-form-item {
    margin-top: 25px !important;
}
</style>