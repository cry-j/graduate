<!--
 * @Author: Jing
 * @Date: 2024-04-14 22:41:37
 * @LastEditTime: 2024-05-30 21:36:12
 * @Description: 个人信息页
-->
<template>
    <el-skeleton
        style="
            width: 100%;
            height: 100%;
            background-color: rgb(239, 239, 245);
            --el-skeleton-circle-size: 200px;
        "
    >
        <template #template>
            <el-space
                :fill="true"
                wrap
                style="width: 100%"
                direction="vertical"
            >
                <el-card shadow="hover" :body-style="{ padding: '20px' }">
                    <el-row :gutter="20">
                        <el-col :span="4" :offset="0">
                            <el-avatar
                                :size="150"
                                fit="fill"
                                style="background-color: transparent"
                            >
                                <img :src="'/static/img/' + userInfo.imgPic" />
                            </el-avatar>
                        </el-col>
                        <el-col :span="14" :offset="0"
                            ><h3 style="font-size: 38px; margin-top: 25px">
                                {{ userInfo.name }}
                            </h3>
                            <p style="margin-top: 15px; font-size: 18px">
                                {{ userInfo.signature }}
                            </p>
                        </el-col>
                        <el-col :span="2" :offset="0">
                            <el-upload
                                class="upload-demo"
                                ref="upload"
                                accept="image/jpeg,image/gif,image/png"
                                :http-request="picUpload"
                                :before-upload="uploadPreview"
                                :on-change="handleChangePic"
                                multiple
                                :file-list="fileList"
                                :show-file-list="false"
                                :auto-upload="true"
                            >
                                <el-button
                                    style="font-size: 18px; margin-top: 80px"
                                    size="large"
                                    type="success"
                                    >修改头像</el-button
                                >
                            </el-upload>
                        </el-col>
                        <el-col :span="2" :offset="1">
                            <el-button
                                style="font-size: 18px; margin-top: 80px"
                                size="large"
                                type="primary"
                                @click="changePwd"
                                >修改密码</el-button
                            ></el-col
                        >
                    </el-row>
                </el-card>
                <el-card shadow="hover" :body-style="{ padding: '30px' }">
                    <template #header>
                        <div class="card-header">
                            <h3 style="font-size: 25px">个人信息</h3>
                        </div>
                    </template>
                    <el-form
                        :model="userInfo"
                        label-width="auto"
                        style="max-width: 600px"
                        size="large"
                    >
                        <el-form-item label="用户昵称：">
                            <el-input
                                v-model="userInfo.name"
                                style="font-size: 17px"
                            />
                        </el-form-item>
                        <el-form-item label="用户名：">
                            <el-input
                                v-model="userInfo.userName"
                                style="font-size: 17px"
                                disabled
                            />
                        </el-form-item>
                        <el-form-item label="性别：">
                            <el-input
                                v-model="userInfo.sex"
                                style="font-size: 17px"
                            />
                        </el-form-item>
                        <el-form-item label="年龄：">
                            <el-input
                                v-model="userInfo.age"
                                style="font-size: 17px"
                            />
                        </el-form-item>
                        <el-form-item label="地址：">
                            <el-input
                                v-model="userInfo.address"
                                style="font-size: 17px"
                            />
                        </el-form-item>
                        <el-form-item label="电话：">
                            <el-input
                                v-model="userInfo.tel"
                                style="font-size: 17px"
                            />
                        </el-form-item>
                        <el-form-item label="个性签名：">
                            <el-input
                                v-model="userInfo.signature"
                                type="textarea"
                                style="font-size: 17px"
                            />
                        </el-form-item>
                    </el-form>
                    <el-button
                        type="primary"
                        style="
                            font-size: 20px;
                            margin-left: 160px;
                            margin-right: 20px;
                        "
                        @click="updateUserInfo"
                        >修改</el-button
                    >
                    <el-button style="font-size: 20px" @click="resetData"
                        >取消</el-button
                    >
                </el-card>
            </el-space>
        </template>
    </el-skeleton>
</template>

<script>
import qs from 'qs'
import { ElMessage } from 'element-plus'
//修改成功弹出框
const resucess = () => {
    ElMessage({
        message: '修改成功',
        type: 'success',
    })
}
export default {
    data() {
        return {
            userInfo: {//储存用户信息

            },
            //updateFlag: 'disabled',
            //msg: ''
            fileList: [],//文件上传列表，这里只上传头像用
        }
    },
    //获取个人信息
    mounted() {
        this.userInfo = JSON.parse(sessionStorage.getItem('userInfo'))
    },
    methods: {
        /**
         * @return {*}
         * @Date: 2024-05-30 21:05:57
         * @description: 重置数据
         */        
        resetData() {
            this.userInfo = JSON.parse(sessionStorage.getItem('userInfo'))
        },
        /**
         * @return {*}
         * @Date: 2024-05-30 21:08:54
         * @description: 更新用户信息
         */        
        updateUserInfo() {
            console.log(qs.stringify(this.userInfo))
            if (confirm('你确定更改吗？')) {
                this.axios.post('/api/user/updateInfo.do', this.userInfo).then((result) => {
                    let rs = result.data;
                    console.log(rs)
                    if (rs.status === 100) {
                        sessionStorage.setItem('userInfo', JSON.stringify(rs.data))//注意要先将数据转成字符串格式，在进行存值
                        this.$store.dispatch('setUserInfo', rs.data)
                        resucess()
                    } else {
                        alert(rs.message)
                        this.resetData()
                    }
                }).catch((err) => {

                });
            }
        },
        /**
         * @param {*} f
         * @return {*}
         * @Date: 2024-05-30 21:18:28
         * @description: 更换头像并回显
         */        
        picUpload(f) {
            let params = new FormData()
            //注意在这里一个坑f.file
            params.append("file", f.file);
            params.append("userId", this.userInfo.userId);
            console.log(params);
            this.axios({
                method: 'post',
                url: '/api/user/upload.do', //此处为往后台发送请求的地址
                data: params,
                headers: {
                    "Content-Type": "multipart/form-data"
                },
            }).then(rs => {
                let resp = rs.data
                console.log(resp)
                if (resp.status === 100) {
                    sessionStorage.setItem('userInfo', JSON.stringify(resp.data))//注意要先将数据转成字符串格式，在进行存值
                    this.userInfo = resp.data
                    this.$store.dispatch('setUserInfo', resp.data)
                    resucess();
                } else {
                    alert(rs.message)
                    this.resetData()
                }

            })
        },
        /**
         * @param {*} file
         * @return {*}
         * @Date: 2024-05-30 21:17:25
         * @description: 图片上传前对图片大小限制
         */        
        uploadPreview: function (file) {
            var _this = this;
            return new Promise(function (resolve, reject) {
                var reader = new FileReader();
                reader.onload = function (event) {
                    var image = new Image();
                    image.onload = function () {
                        var width = this.width;
                        var height = this.height;
                        if (width > 1024) {
                            _this.$alert('图片宽度尺寸必须在1024之内！', '提示', { confirmButtonText: '确定' });
                            return false;
                            // reject();
                        }
                        else if (height > 1024) {
                            _this.$alert('图片高度尺寸必须在1024之内！', '提示', { confirmButtonText: '确定' });
                            return false;
                            // reject();
                        }
                        resolve();
                    };
                    image.src = event.target.result;
                }
                reader.readAsDataURL(file);
            });
        },
        /**
         * @param {*} files
         * @param {*} fileList
         * @return {*}
         * @Date: 2024-05-30 21:19:20
         * @description: 限制列表数量，大于1时自动覆盖
         */        
        handleChangePic(files, fileList) {
            console.log(fileList)
            if (fileList.length > 1) {
                fileList.splice(0, 1);
            }
        },
        /**
         * @return {*}
         * @Date: 2024-05-30 21:36:03
         * @description: 更改密码
         */        
        changePwd() {
            this.$store.dispatch('setActive', '/userMain/updatePassword')
            this.$router.push('updatePassword')

        }
    },
}
</script>

<style scoped>
:deep(.el-form-item__label) {
    font-size: 19px;
}
:deep(.avatar-uploader .el-upload) {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
}
:deep(.avatar-uploader .el-upload:hover) {
    border-color: #409eff;
}
:deep(.avatar-uploader-icon) {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
}
:deep(.avatar) {
    width: 178px;
    height: 178px;
    display: block;
}
</style>