<!--
 * @Author: Jing
 * @Date: 2024-04-14 08:39:50
 * @LastEditTime: 2024-06-02 15:57:16
 * @Description: 花卉管理界面
-->

<template >
    <p style="font-size: 30px; padding-top: 20px; padding-left: 20px">
        我的花卉
    </p>
    <el-divider style="margin-top: 5px" />
    <el-table :data="tableData" class="myTable" table-layout="auto">
        <el-table-column
            fixed
            label="序号"
            type="index"
            :index="reIndex"
            width="80"
        />
        <el-table-column prop="id" label="花卉编号" width="120" />
        <el-table-column prop="type.typeName" label="名称" width="120" />
        <el-table-column prop="number" label="数量" width="120" />
        <el-table-column prop="purchase" label="进价(元/株)" width="120" />
        <el-table-column prop="sale" label="售价(元/株)" width="120" />
        <el-table-column prop="plantDate" label="种植日期" width="150" />
        <el-table-column fixed="right" label="操作" width="150">
            <template #default="scope">
                <el-button
                    link
                    type="primary"
                    size="large"
                    @click="deleteFlower(scope.$index, scope.row)"
                >
                    Delete
                </el-button>
                <el-button
                    link
                    type="primary"
                    size="large"
                    @click="editFlower(scope.$index, scope.row)"
                    >Edit</el-button
                >
                <el-button
                    link
                    type="primary"
                    size="large"
                    @click="tipBtn(scope.$index, scope.row)"
                    >Tip</el-button
                >
            </template>
        </el-table-column>
    </el-table>
    <!-- 分页 -->
    <el-pagination
        :page-size="10"
        v-model:current-page="pageNo"
        :pager-count="5"
        layout="prev, pager, next"
        v-model:total="total"
        @current-change="currentChange"
    />
    <!-- 编辑框 -->
    <el-dialog
        v-model="dialogTableVisible"
        :title="flowerDetail.type.typeName"
        width="600"
    >
        <el-form
            :model="flowerDetail"
            size="large"
            style="margin-left: 10%; margin-top: 10px"
        >
            <el-form-item label="花卉编号" label-width="20%">
                <el-input
                    v-model="flowerDetail.id"
                    autocomplete="off"
                    disabled
                />
            </el-form-item>
            <el-form-item label="数量" label-width="20%">
                <el-input v-model="flowerDetail.number" autocomplete="off" />
            </el-form-item>
            <el-form-item label="进价" label-width="20%">
                <el-input v-model="flowerDetail.purchase" autocomplete="off" />
            </el-form-item>
            <el-form-item label="预售价" label-width="20%">
                <el-input v-model="flowerDetail.sale" autocomplete="off" />
            </el-form-item>
            <el-form-item label="种植日期" label-width="20%">
                <el-date-picker
                    v-model="flowerDetail.plantDate"
                    type="date"
                    placeholder="Pick a day"
                    style="width: 100%"
                    class="myPager"
                />
            </el-form-item>
        </el-form>
        <div class="dialog-footer">
            <el-button @click="cancelBtn" style="margin-right: 20px"
                >取消
            </el-button>
            <el-button type="primary" @click="confirmBtn"> 修改</el-button>
        </div>
    </el-dialog>
    <!-- 趋势框 -->
    <el-dialog
        :title="flowerDetail.type.typeName"
        v-model="tipDialog"
        @close="tipCancelBtn"
        style="width: 80%; height: 80%; position: relative"
    >
        <!--这里是echarts绘图区域-->
        <h3 style="font-size: 30px; margin-left: 5%">{{ trendTittle }}</h3>
        <div
            id="mychart"
            style="
                width: 80%;
                height: 80%;
                position: absolute;
                margin-left: 10%;
            "
        ></div>
    </el-dialog>
</template>

<script>

import { ElMessage } from 'element-plus'
import * as echarts from "echarts";//引入的echarts库

//成功弹出框
const resucess = () => {
    ElMessage({
        message: "修改成功",
        type: 'success',
    })
}
export default {

    inject: ['reload'],//局部刷新，接收来自Main的reload数据

    data() {
        return {
            tableData: [

            ],//储存花卉数据
            pageNo: 1,//当前页码
            total: 1,//总页数
            userId: '',//储存当前用户id
            dialogTableVisible: false,//控制编辑页的显示隐藏
            tipDialog: false,//控制温度趋势图的显示隐藏
            flowerDetail: {
                type: {

                }
            },//用于存储花卉详情信息
            /**
             * *以下用于趋势图绘制
             */
            trendTittle: '',//图标题
            myChart: null,//图表对象
            xData: [], //横坐标
            min: [], //最低温度
            max: [], //最高温度
            currentTemperature: [],//当前温度
            myChartStyle: { float: "left", width: "70%", height: "70%" } //图表样式
        }
    },
    methods: {
        /**
         * @param {*} index
         * @param {*} row
         * @return Result<String>
         * @Date: 2024-06-02 15:21:16
         * @description: 删除花卉操作
         */        
        deleteFlower(index, row) {
            let id = row.id;
            if (confirm("你确定要删除吗？")) {
                this.axios.delete(`/api/user/deleteFlower.do?id=${id}`).then((result) => {
                    let rs = result.data;
                    if (rs.status === 100) {
                        this.$message.success("删除成功")
                        this.refresh()
                    } else {
                        this.$message.error("error")
                    }
                }).catch((err) => {

                });
            }
        },
        /**
         * @param {*} pageNo
         * @return {*}
         * @Date: 2024-06-02 15:22:19
         * @description: 页码改变时的操作
         */        
        currentChange(pageNo) {
            this.axios.get(`/api/user/getAllFlower.do?userId=${this.userId}&pageNo=${pageNo}`).then((result) => {
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
         * @return {*}
         * @Date: 2024-06-02 15:24:07
         * @description: 序号连续标号操作
         */        
        reIndex(index) {
            return (this.pageNo - 1) * 10 + index + 1
        },
        /**
         * @param {*} index
         * @param {*} row
         * @return {*}
         * @Date: 2024-06-02 15:24:28
         * @description: 获取花卉详情信息操作
         */        
        editFlower(index, row) {
            this.dialogTableVisible = true;
            this.axios.get(`/api/user/FlowerDetail.do?id=${row.id}`).then((result) => {
                let rs = result.data.data;
                this.flowerDetail = rs;
            }).catch((err) => {
                alert(err.message)
            });
        },
        /**
         * @param {*} index
         * @param {*} row
         * @return {*}
         * @Date: 2024-06-02 15:25:24
         * @description: 获取趋势图信息
         */
        //tag: 实现智能推荐图绘制   
        tipBtn(index, row) {
            this.trendTittle = row.type.typeName;
            this.tipDialog = true;
            console.log(row.type.typeId)
            //趋势图
            this.axios.get(`/api/user/getTrend.do?typeId=${row.type.typeId}`).then((result) => {
                let rs = result.data.dataList;
                if (rs != null) {
                    //获取图表信息数据
                    rs.forEach((item) => {
                        this.xData.push(item.month)
                        this.min.push(item.min)
                        this.max.push(item.max)
                    })
                    //绘制
                    this.initEcharts();
                } else {
                    this.tipDialog = false;
                    alert("无数据")
                }


            }).catch((err) => {
                alert(err.message)
            });
        },
        /**
         * @return {*}
         * @Date: 2024-06-02 15:30:38
         * @description: 修改花卉信息
         */        
        confirmBtn() {
            this.axios.put('/api/user/updateFlower.do', this.flowerDetail).then((result) => {
                let rs = result.data;

                if (rs.status === 100) {
                    //局部刷新调用
                    this.refresh();
                    resucess();
                }
            }).catch((err) => {
                alert(err.message)
            });
            this.dialogTableVisible = false;
        },
        /**
         * @return {*}
         * @Date: 2024-06-02 15:31:02
         * @description: 关闭编辑框操作
         */        
        cancelBtn() {
            this.dialogTableVisible = false;
        },
        /**
         * @return {*}
         * @Date: 2024-06-02 15:31:20
         * @description: 调用来自父组件的reload，实现局部刷新
         */        
        refresh() { 
            this.reload();
        },
        /**
         * @return {*}
         * @Date: 2024-06-02 15:31:44
         * @description: 关闭趋势框操作，销毁
         */        
        tipCancelBtn() {
            this.tipDialog = false;
            this.myChart.dispose(); // 销毁echarts图表对象
            this.xData = [], //横坐标
                this.min = [], //最低温度
                this.max = []//最高温度
        },
        /**
         * @return {*}
         * @Date: 2024-06-02 15:32:30
         * @description: 绘制图
         */        
        initEcharts() {
            let month = new Date().getMonth(); //获取当前时间的月份
            let currentTemperature = this.$store.state.wd;//获取当前环境温度
            /**设置图表样式、数据等 */
            const option = {
                //提示标签的一些样式
                tooltip: {
                    trigger: 'axis',// 提示组件触发类型
                    show: true,// 是否显示提示组件          
                    transitionDuration: 0.4,//动画过渡时间
                    //设置指示器样式
                    axisPointer: {
                        type: 'cross',//十字准星指示器
                        crossStyle: {
                            color: '#999'
                        },
                        label: {
                            backgroundColor: '#6a7985',
                            // formatter: function (params) {
                            //     if (params.seriesData.length === 0) {
                            //         window.mouseCurValue = params.value;
                            //     }
                            // },
                        }

                    },
                    //设置标签显示格式
                    formatter: (params) => {
                        var res = "";
                        res += params[1].axisValue + "月" + "<br/>"
                        for (var i = 0; i < params.length; i++) {
                            var series = params[i];
                            if (series.data.length > 0) {
                                res += series.marker + series.seriesName + ":" + series.data[1] + "℃" + "<br/>";
                            } else {
                                res += series.marker + series.seriesName + ":" + series.data + "℃" + "<br/>";
                            }
                        }
                        return res;

                    },
                    
                },
                //图表工具栏
                toolbox: {
                    feature: {
                        dataView: { show: true, readOnly: false },//数据视图，将图表简单以表格形式展示
                        magicType: { show: true, type: ['line', 'scatter'] },//切换图形类型
                        restore: { show: true },//还原
                        saveAsImage: { show: true }//保存 Ehcarts 为图片
                    }
                },
                //X轴设置
                xAxis: {
                    data: this.xData,
                },
                //图例设置
                legend: { 
                    data: ["最低温度", "最高温度", "当前温度"],
                    bottom: "0%"
                },
                //y轴设置
                yAxis: {
                    type: 'value',
                    name: '温度',
                    min: 0,
                },
                //数据显示形式设置
                series: [
                    {
                        name: "最低温度",
                        data: this.min,
                        type: "line", // 类型设置为折线图
                        smooth: true,

                    },
                    {
                        name: "最高温度",
                        data: this.max,
                        type: "line", // 类型设置为折线图
                        smooth: true,//平滑

                    }, {
                        name: "当前温度",
                        data: [[month, currentTemperature]],
                        type: "scatter", // 类型设置为点

                    }

                ],

            };
            let chart;
            //初始化绘制
            chart = echarts.init(document.getElementById("mychart"));
            chart.setOption(option);//setOption()绘制图表
            // 随着屏幕大小调节图表
            // //当 鼠标移到线条上时触发
            // chart.on('mousemove', function (params) {
            //     window.selectSeries = params.seriesName;
            // });
            // //当 鼠标移出线条时触发 ，如果不处理这个，鼠标移到空白上还有tooltip显示。
            // chart.on('mouseout', function (params) {
            //     window.selectSeries = '';
            // });
            this.myChart = chart;
            // window.addEventListener("resize", this.onResize, false);//添加监听事件

        },

    },
    /**
     * @return {*}
     * @Date: 2024-06-02 15:57:03
     * @description: 加载初始数据
     */    
    mounted() {
        this.userId = this.$store.state.userInfo.userId;
        this.axios.get(`/api/user/getAllFlower.do?userId=${this.userId}&pageNo=1`).then((result) => {
            let rs = result.data.page;
            this.pageNo = parseInt(rs.currentPage);
            this.total = parseInt(rs.total);
            this.tableData = rs.list
        }).catch((err) => {
            alert(err.message)
        });

    },
    beforeUnmount() {
        if (this.myChart != null) {
            this.myChart.dispose(); // 销毁echarts图表对象
            this.myChart = null
        }

    },
}
</script>

<style lang="scss" scoped>
.el-dialog__title {
    font-size: 25px;
}
.el-form-item__label {
    font-size: 19px;
}
.dialog-footer {
    margin-left: 30%;
}
.el-form-item {
    margin-bottom: 20px;
    width: 90%;
}
.myTable {
    font-size: 18px;
    padding-left: 10%;
    padding-top: 10px;
}
.el-pager li {
    font-size: 18px;
}
.el-pagination {
    margin-left: 35%;
}
</style>