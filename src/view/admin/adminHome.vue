<!--
 * @Author: Jing
 * @Date: 2024-04-14 19:15:25
 * @LastEditTime: 2024-06-02 20:13:28
 * @Description: admin首页
-->

<template>
    <div :class="$style.userHomeDiv">
        <el-space wrap direction="vertical" :fill="true" style="width: 100%">
            <!-- 第二部分卡片 -->
            <el-card shadow="hover" style="border-radius: 5px">
                <el-row>
                    <el-col :span="22" :offset="2">
                        <ul style="margin-top: 10px">
                            <li>
                                <span
                                    style="font-size: 35px"
                                    :class="[$style.cardName, $style.textColor]"
                                >
                                    欢迎您，管理员[{{ admin.uname }}]
                                </span>
                            </li>
                            <li style="margin-top: 30px">
                                <span style="font-size: 20px; margin-left: 10%">
                                    ----温暖的夏日，一杯香浓的咖啡，一份美味的冰淇淋，青春就是这么甜美。
                                </span>
                            </li>
                        </ul>
                    </el-col>
                </el-row>
            </el-card>
            <el-card shadow="hover">
                <el-calendar v-model="value" />
            </el-card>
        </el-space>
    </div>
</template>

<script>
// 引入和风天气图标
import 'qweather-icons/font/qweather-icons.css'
import { ElSpace } from 'element-plus';
import { ref } from 'vue'
export default {
    data() {
        return {
            admin: {},
            value: ''//用于储存当天日历信息
        }
    },
    created() {
        this.admin = this.$store.state.admin
        this.value = ref(new Date())
    },
    methods: {
    },
    beforeDestroy() {

    },
}
</script>

<style module>
.userHomeDiv {
    width: 100%;
    height: 100%;
    background-color: rgb(239, 239, 245);
}

.cardName {
    font-size: 30px;
}
.textColor {
    color: #333639;
}
.danwei {
    font-size: 28px;
}
.mySwitch {
    font-size: 20px !important;
}
</style>
<style scoped>
.percentage-value {
    display: block;
    margin-top: 10px;
    font-size: 28px;
}
.percentage-label {
    display: block;
    margin-top: 10px;
    font-size: 12px;
}
.demo-progress .el-progress--line {
    margin-bottom: 15px;
    max-width: 600px;
}
.demo-progress .el-progress--circle {
    margin-right: 15px;
}

:deep(.el-switch__core .el-switch__inner .is-text) {
    font-size: 16px;
}
</style>