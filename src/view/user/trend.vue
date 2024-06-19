<!--
 * @Author: Jing
 * @Date: 2024-04-22 19:51:24
 * @LastEditTime: 2024-06-02 15:09:45
 * @Description: 适宜温度推荐图，此部分已废用
-->
<template>
    <div class="echart" id="mychart" style="width: 100%; height: 100%"></div>
</template>
  
  <script>
import * as echarts from "echarts";

export default {
    data() {
        return {
            myChart: {},
            xData: [], //横坐标
            min: [], //最低温度
            max: [], //最高温度
            currentTemperature: [],//当前温度
            myChartStyle: { float: "left", width: "100%", height: "100%" } //图表样式
        };
    },
    mounted() {
        this.$nextTick(() => {
            this.renderEchats()
        })
        this.axios.get('/api/user/getTrend.do?typeId=1').then((result) => {
            let rs = result.data.dataList;
            rs.forEach((item) => {
                this.xData.push(item.month)
                this.min.push(item.min)
                this.max.push(item.max)
            })
            this.initEcharts();

        }).catch((err) => {
            alert(err.message)
        });

    },
    methods: {
        //当 鼠标移到线条上时触发

        initEcharts() {
            let month = new Date().getMonth(); //获取当前时间的月份
            let currentTemperature = this.$store.state.wd;
            const option = {

                tooltip: {
                    trigger: 'axis',
                    show: true,
                    transitionDuration: 0.4,//动画过渡时间
                    axisPointer: {
                        type: 'cross',
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
                    // formatter: (params) => {
                    //     var res = "";
                    //     for (var i = 0; i < params.length; i++) {
                    //         var series = params[i];
                    //         if (series.seriesName == window.selectSeries) {
                    //             if (series.data.length > 0) {
                    //                 res = series.axisValue + "月" + "<br/>" + series.marker + series.seriesName + ":" + series.data[1] + "℃" + "<br/>";
                    //             } else {
                    //                 res = series.axisValue + "月" + "<br/>" + series.marker + series.seriesName + ":" + series.data + "℃" + "<br/>";
                    //             }
                    //             break;
                    //         }
                    //     }
                    //     return res;

                    // }
                },
                toolbox: {
                    feature: {
                        dataView: { show: true, readOnly: false },
                        magicType: { show: true, type: ['line', 'scatter'] },
                        restore: { show: true },
                        saveAsImage: { show: true }
                    }
                },
                xAxis: {
                    data: this.xData,

                },
                legend: { // 图例
                    data: ["最低温度", "最高温度", "当前温度"],
                    bottom: "0%"
                },
                yAxis: {
                    type: 'value',
                    name: '温度',
                    min: 0,
                },
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
            chart = echarts.init(document.getElementById("mychart"));
            chart.setOption(option);
            //随着屏幕大小调节图表
            // //当 鼠标移到线条上时触发
            // chart.on('mousemove', function (params) {
            //     window.selectSeries = params.seriesName;
            // });
            // //当 鼠标移出线条时触发 ，如果不处理这个，鼠标移到空白上还有tooltip显示。
            // chart.on('mouseout', function (params) {
            //     window.selectSeries = '';
            // });
            this.myChart = chart;
            window.addEventListener("resize", this.onResize, false);

        },

    },
    beforeUnmount() {
        this.myChart.dispose(); // 销毁echarts图表对象
    },

    // watch: {
    //     // 观察option的变化
    //     option: {
    //         handler(newVal, oldVal) {
    //             if (this.myChart) {
    //                 if (newVal) {
    //                     // 这块加上true
    //                     this.myChart.setOption(newVal, true)
    //                 } else {
    //                     // 这块加上true
    //                     this.myChart.setOption(oldVal, true)
    //                 }
    //             } else {
    //                 this.initEcharts()
    //             }
    //         }
    //     }
    // }
}
  </script>
  
  