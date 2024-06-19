<!--
 * @Author: Jing
 * @Date: 2024-04-12 22:33:58
 * @LastEditTime: 2024-05-29 20:47:54
 * @Description: 注册界面
-->
<template>
    <div class="container">
        <el-container direction="vertical" id="myRegister_div">
            <el-header height="20%">
                <el-icon :size="20" id="back" @click="back"
                    ><DArrowLeft
                /></el-icon>
                注册界面
            </el-header>
            <el-main height="70%">
                <!-- Main content -->
                <el-form
                    :model="form"
                    ref="form"
                    :rules="rules"
                    label-width="auto"
                    label-position="left"
                    :inline="false"
                >
                    <el-form-item label="用户名" prop="uname">
                        <input
                            type="text"
                            class="cin"
                            v-model="form.uname"
                            @blur="checkName"
                        />
                    </el-form-item>
                    <el-form-item label="密码" prop="pwd">
                        <input type="password" class="cin" v-model="form.pwd" />
                    </el-form-item>
                    <el-form-item label="确认密码" prop="rePwd">
                        <input
                            type="password"
                            class="cin"
                            v-model="form.rePwd"
                        />
                    </el-form-item>
                    <el-form-item label="验证码" prop="yan">
                        <el-row>
                            <el-col :span="12"
                                ><input
                                    type="text"
                                    class="cin"
                                    id="yanzheng"
                                    v-model="form.yan"
                            /></el-col>
                            <el-col :span="9"
                                ><span id="verify">{{ verify }}</span></el-col
                            >
                            <el-col :span="3"
                                ><el-icon
                                    :size="20"
                                    style="margin-top: 5px; cursor: pointer"
                                    @click="update"
                                    ><Refresh /></el-icon
                            ></el-col>
                        </el-row>
                    </el-form-item>
                    <el-form-item>
                        <el-button
                            type="primary"
                            class="login_btn"
                            id="regeisterBtn"
                            @click="regester"
                            >注册</el-button
                        >
                    </el-form-item>
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
import qs from 'qs';
import { ElMessage } from 'element-plus'
//注册成功弹出框
const resucess = () => {
    ElMessage({
        message: '注册成功',
        type: 'success',
    })
}
export default {
    data() {
        return {
            form: {
                uname: '',
                pwd: '',
                rePwd: '',
                yan: '',
            },
            verify: '',
            rules: {
                uname: [{ required: true, message: "用户名不能为空", trigger: ['blur', 'change'] }],
                pwd: [{ required: true, message: "密码不能为空", trigger: ['blur', 'change'] }],
                rePwd: [{ required: true, message: "密码不能为空", trigger: ['blur', 'change'] }],
                yan: [{ required: true, message: "验证码不能为空", trigger: ['blur', 'change'] }]
            }

        }
    },
    // 设置网页加载自动载入update
    created: function () {
        this.update();
    },
    methods: {
        /**
         * @return {*}
         * @Date: 2024-05-29 20:35:25
         * @description: 随机生成验证码
         */
        update() {
            let num = [
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
            ]
            let str = ''
            for (let index = 0; index < 4; index++) {
                str += num[Math.floor(Math.random() * num.length)]
            }
            this.verify = str
            console.log(this.verify)
        },
        /**
         * @return Result<User>
         * @Date: 2024-05-29 20:42:13
         * @description: 验证密码是否一致、验证是否正确（不忽略大小写），成功注册自动登录
         */
        yanzheng() {
            if (this.form.pwd != this.form.rePwd) {
                alert("密码不一致")
            } else if (this.form.yan != this.verify) {
                alert("验证码不正确")
                this.update()
                this.form.yan = ''
            }
            else {
                this.axios.post('api/login/register.do', qs.stringify({
                    uname: this.form.uname,
                    uPwd: this.form.pwd
                })
                ).then((result) => {
                    let rs = result.data;
                    console.log(rs)
                    if (rs.status === 100) {
                        resucess();
                        sessionStorage.setItem('userInfo', JSON.stringify(rs.data))//注意要先将数据转成字符串格式，在进行存值
                        //将user存储至store
                        this.$store.dispatch('setUserInfo', rs.data)
                        setTimeout(() => {
                            this.$router.push('userMain');
                        }, 2000)

                    }
                }).catch((err) => {

                });

            }
        },
        //返回登录界面
        back() {
            this.$router.push('userLogin')
        },
        /**
         * @return {*}
         * @Date: 2024-05-29 20:47:36
         * @description: 信息输入校验
         */
        regester() {
            this.$refs['form'].validate((valid) => {
                if (valid) {
                    console.log("校验通过， 可以保存了")
                    this.yanzheng()
                }
                else {
                    console.log("校验失败")
                }
            })
        },
        /**
         * @return {*}
         * @Date: 2024-05-29 20:48:23
         * @description: 输入用户名失去焦点自动触发，判断用户名是否存在
         */
        checkName() {
            this.axios.get('api/login/checkName.do?uname=' + this.form.uname).then((result) => {
                if (result.data === false) {
                    alert("用户名已存在")
                    this.form.uname = ''
                }
            })
        }
    },

}
</script>

<style src="@/styles/login.css" scoped>
</style>
<style src="@/styles/register.css" scoped>
</style>
<style scoped>
.el-form-item {
    margin-top: 25px !important;
    padding-left: 10px;
}
</style>