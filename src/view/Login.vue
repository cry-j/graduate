<!--
 * @Author: Jing
 * @Date: 2024-04-12 22:33:58
 * @LastEditTime: 2024-05-29 20:24:52
 * @Description: 用户登录界面
-->
<template>
    <div class="container">
        <el-button
            id="adminBtn"
            type="primary"
            size="default"
            @click="adminLogin"
            >管理员登录</el-button
        >
        <el-container direction="vertical" id="myLogin_div">
            <el-header height="20%"> 智能花卉养殖系统 </el-header>

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
                            ><el-form-item label="用户名：" prop="user.uname">
                                <input
                                    type="text"
                                    v-model="form.user.uname"
                                    class="cin"
                                /> </el-form-item
                        ></el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="30">
                            <el-form-item label="密码：" prop="user.uPwd">
                                <input
                                    type="password"
                                    v-model="form.user.uPwd"
                                    class="cin"
                                /> </el-form-item
                        ></el-col>
                    </el-row>
                    <el-row>
                        <el-form-item>
                            <el-col :span="100" direction="vertical">
                                <el-button
                                    class="login_btn"
                                    type="primary"
                                    @click="userLogin"
                                    >登录</el-button
                                >
                                <el-button
                                    class="login_btn"
                                    type="primary"
                                    size="default"
                                    @click="register"
                                    >注册</el-button
                                >
                            </el-col>
                        </el-form-item>
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
                user: {
                    uname: '',
                    uPwd: '',
                }

            },
            rules: {
                user: {
                    uname: [{ required: true, message: "用户名不能为空", trigger: ['blur', 'change'] }],
                    uPwd: [{ required: true, message: "密码不能为空", trigger: ['blur', 'change'] }],
                }

            },
        }
    },

    methods: {
        /**
         * @return {*}
         * @Date: 2024-05-29 20:16:26
         * @description: 注册登录
         */
        register() {
            this.$router.push('register');
        },
        /**
         * @Param: qs.stringify(this.form.user)
         * @Return: Result<User>
         * @Description:登录
         * @Tip: qs.stringify用于将对象 转成URL的形式，将数据以&进行拼接
         */
        userLoginSubmit() {
            this.axios.post('api/login/userLogin.do', qs.stringify(this.form.user)).then((result) => {
                let rs = result.data;
                console.log(rs)
                if (rs.status === 100) {
                    sessionStorage.setItem('userInfo', JSON.stringify(rs.data))//注意要先将数据转成字符串格式，在进行存值
                    this.$store.dispatch('setUserInfo', rs.data)
                    this.$router.push('userMain');
                } else {
                    alert(rs.message)
                    this.form.admin.uname = '';
                    this.form.admin.uPwd = ''
                }
            }).catch((err) => {

            });
        },
        /**
         * @return {*}
         * @Date: 2024-05-29 20:21:06
         * @description: 跳转管理员登录界面
         */
        adminLogin() {
            this.$router.push('adminLogin')
        },
        /**
         * @return {*}
         * @Date: 2024-05-29 20:21:06
         * @description: 用户登录校验，当满足所有rules才会返回true
         */
        userLogin() {
            this.$refs['myForm'].validate((valid) => {
                if (valid) {
                    console.log("校验通过， 可以保存了")
                    this.userLoginSubmit();
                }
                else {
                    console.log("校验失败")
                }
            })
        },
    }
}
</script>
<style src="@/styles/login.css" scoped lang="less">
</style>
<style scoped>
.el-form-item {
    margin-bottom: 0px;
    margin-top: 25px !important;
}
.el-main {
    padding: 0px;
}
.login_btn {
    margin-left: 65px;
    margin-right: 55px;
}
</style>
