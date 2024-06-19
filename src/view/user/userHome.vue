<!--
 * @Author: Jing
 * @Date: 2024-04-14 08:34:47
 * @LastEditTime: 2024-06-02 20:22:37
 * @Description: 用户主界面-首页
-->
<template>
    <div :class="$style.userHomeDiv">
        <h3>
            <!-- <mqttVue ref="mqttChild"> </mqttVue> -->
        </h3>
        <el-space
            wrap
            direction="horizontal"
            :fill="true"
            style="width: 100%"
            :fill-ratio="40"
        >
            <!-- 第一部分卡片 -->
            <el-card style="border-radius: 5px; height: 240px" shadow="hover">
                <div id="weather">
                    <el-row :gutter="30">
                        <el-col
                            :span="24"
                            :offset="0"
                            style="font-size: 25px"
                            >{{ userInfo.address }}</el-col
                        >
                    </el-row>
                    <el-row :gutter="30">
                        <el-col :span="3" :offset="1" style="font-size: 80px"
                            ><i :class="weather.icon"></i>
                        </el-col>

                        <el-col
                            :span="5"
                            :offset="1"
                            style="font-size: 50px; padding-top: 20px"
                            >{{ weather.temp }}</el-col
                        >
                        <el-col
                            :span="12"
                            :offset="1"
                            style="padding-top: 25px"
                        >
                            <el-row :gutter="20" style="margin-top: 10px">
                                <el-col
                                    :span="14"
                                    :offset="0"
                                    style="font-size: 18px; margin-bottom: 5px"
                                    >风速：{{ weather.windSpeed }}</el-col
                                >
                            </el-row>
                            <el-row :gutter="20">
                                <el-col
                                    :span="12"
                                    :offset="0"
                                    style="font-size: 18px"
                                    >湿度：{{ weather.humidity }}</el-col
                                >
                            </el-row>
                        </el-col>
                    </el-row>
                    <el-row :gutter="30">
                        <el-col :span="3" :offset="1" style="font-size: 20px">{{
                            weather.text
                        }}</el-col
                        ><el-col
                            :span="4"
                            :offset="0"
                            style="font-size: 20px"
                            >{{ week }}</el-col
                        >
                        <el-col :span="5" :offset="0" style="font-size: 20px">
                            {{ weather.windScale }}
                        </el-col>
                    </el-row>
                </div>
            </el-card>
            <!-- 第二部分卡片 -->
            <el-card
                shadow="hover"
                style="border-radius: 5px; width: 400px; height: 240px"
            >
                <el-row>
                    <el-col :span="12" :offset="2">
                        <ul style="margin-top: 10px">
                            <li>
                                <span
                                    style="font-size: 35px"
                                    :class="[$style.cardName, $style.textColor]"
                                >
                                    {{ userInfo.name }}
                                </span>
                            </li>
                            <li style="margin-top: 20px">
                                <span style="font-size: 20px">
                                    ----&nbsp;{{ userInfo.signature }}
                                </span>
                            </li>
                        </ul>
                    </el-col>
                    <el-col :span="8" :offset="2"
                        ><el-avatar
                            icon="el-icon-user-solid"
                            :size="163"
                            shape="circle"
                            :src="'/static/img/' + userInfo.imgPic"
                            fit="fill"
                        ></el-avatar
                    ></el-col>
                </el-row>

                <!-- card body -->
            </el-card>
            <!-- 环境监测卡片 -->
            <el-card class="box-card" style="border-radius: 5px" shadow="hover">
                <template #header>
                    <div class="card-header">
                        <span :class="[$style.cardName, $style.textColor]"
                            >环境监测</span
                        >
                    </div>
                </template>
                <p>
                    <el-row :gutter="20">
                        <!-- 温度 -->
                        <el-col :span="6" :offset="0">
                            <el-row :gutter="20">
                                <el-col :span="7" :offset="2"
                                    ><img
                                        src="@/assets/images/temp.png"
                                        style="width: 110px"
                                /></el-col>
                                <el-col :span="12" :offset="1"
                                    ><el-progress
                                        type="dashboard"
                                        :percentage="this.$store.state.wd"
                                        color="#f96401"
                                    >
                                        <template #default="{ percentage }">
                                            <span class="percentage-value">
                                                {{ percentage }}℃</span
                                            >
                                            <span class="percentage-label"
                                                >当前温度</span
                                            >
                                        </template>
                                    </el-progress></el-col
                                >
                            </el-row>
                        </el-col>
                        <!-- 湿度 -->
                        <el-col :span="6" :offset="0">
                            <el-row :gutter="20">
                                <el-col :span="7" :offset="0"
                                    ><img
                                        src="@/assets/images/humidity.png"
                                        style="width: 110px"
                                /></el-col>
                                <el-col :span="12" :offset="2"
                                    ><el-progress
                                        type="dashboard"
                                        :percentage="this.$store.state.sd"
                                        color="#018cf7"
                                    >
                                        <template #default="{ percentage }">
                                            <span class="percentage-value">
                                                {{ percentage
                                                }}<span :class="$style.danwei"
                                                    >%RH</span
                                                ></span
                                            >
                                            <span class="percentage-label"
                                                >当前湿度</span
                                            >
                                        </template>
                                    </el-progress></el-col
                                >
                            </el-row>
                        </el-col>
                        <!-- 土壤湿度 -->
                        <el-col :span="6" :offset="0">
                            <el-row :gutter="20">
                                <el-col :span="7" :offset="0"
                                    ><img
                                        src="@/assets/images/soilSd.png"
                                        style="width: 120px; margin-top: 10px"
                                /></el-col>
                                <el-col :span="12" :offset="3"
                                    ><el-progress
                                        type="dashboard"
                                        :percentage="this.$store.state.soilSd"
                                        color="green"
                                    >
                                        <template #default="{ percentage }">
                                            <span class="percentage-value">
                                                {{ percentage
                                                }}<span :class="$style.danwei"
                                                    >%</span
                                                ></span
                                            >
                                            <span class="percentage-label"
                                                >土壤湿度</span
                                            >
                                        </template>
                                    </el-progress></el-col
                                >
                            </el-row>
                        </el-col>
                        <!-- 光照值 -->
                        <el-col :span="6" :offset="0">
                            <el-row :gutter="20">
                                <el-col :span="7" :offset="0"
                                    ><img
                                        src="@/assets/images/lightLux.png"
                                        style="width: 110px"
                                /></el-col>
                                <el-col :span="11" :offset="3">
                                    <el-progress
                                        type="dashboard"
                                        :percentage="this.$store.state.gz / 2"
                                        color="#f9cb01"
                                    >
                                        <template #default="{ percentage }">
                                            <span class="percentage-value">
                                                {{ percentage * 2
                                                }}<span :class="$style.danwei"
                                                    >Lux</span
                                                ></span
                                            >
                                            <span class="percentage-label"
                                                >光照强度</span
                                            >
                                        </template>
                                    </el-progress></el-col
                                >
                            </el-row>
                        </el-col>
                    </el-row>
                </p>
            </el-card>
        </el-space>
        <!-- 按钮控制卡片 -->
        <el-space
            wrap
            direction="vertical"
            :fill="true"
            style="width: 100%; margin-top: 10px"
        >
            <el-card shadow="hover" :body-style="{ padding: '20px' }">
                <template #header>
                    <div class="card-header">
                        <span :class="[$style.cardName, $style.textColor]"
                            >用户控制</span
                        >
                    </div>
                </template>
                <!-- card body -->
                <el-row :gutter="20">
                    <el-col :span="7" :offset="1">
                        <el-row :gutter="20">
                            <el-col :span="7" :offset="0"
                                ><img
                                    style="width: 70px"
                                    src="@/assets/images/fan.png"
                                    alt=""
                            /></el-col>
                            <el-col :span="12" :offset="0">
                                <ul>
                                    <li
                                        style="
                                            font-size: 25px;
                                            margin-bottom: 5px;
                                        "
                                    >
                                        风扇开关
                                    </li>
                                    <li>
                                        <el-switch
                                            v-model="this.$store.state.pwm"
                                            class="ml-2"
                                            inline-prompt
                                            style="
                                                --el-switch-on-color: #13ce66;
                                                --el-switch-off-color: #ff4949;
                                            "
                                            active-text="开"
                                            inactive-text="关"
                                            size="large"
                                            @click="pwmControll"
                                        />
                                    </li>
                                </ul>
                            </el-col>
                        </el-row>
                    </el-col>
                    <el-col :span="8" :offset="0"
                        ><el-row :gutter="20">
                            <el-col :span="7" :offset="0"
                                ><img
                                    style="width: 70px"
                                    src="@/assets/images/water.png"
                                    alt=""
                            /></el-col>
                            <el-col :span="12" :offset="0">
                                <ul>
                                    <li
                                        style="
                                            font-size: 25px;
                                            margin-bottom: 5px;
                                        "
                                    >
                                        水阀开关
                                    </li>
                                    <li>
                                        <el-switch
                                            v-model="
                                                this.$store.state.waterSwitch
                                            "
                                            class="ml-2"
                                            inline-prompt
                                            style="
                                                --el-switch-on-color: #13ce66;
                                                --el-switch-off-color: #ff4949;
                                            "
                                            active-text="开"
                                            inactive-text="关"
                                            size="large"
                                            @click="waterControll"
                                        />
                                    </li>
                                </ul>
                            </el-col> </el-row
                    ></el-col>
                    <el-col :span="8" :offset="0"
                        ><el-row :gutter="20">
                            <el-col :span="7" :offset="0"
                                ><img
                                    style="width: 70px"
                                    src="@/assets/images/lightOff.png"
                                    v-if="
                                        this.$store.state.lightSwitch == false
                                    "
                                /><img
                                    style="width: 70px"
                                    src="@/assets/images/lightOn.png"
                                    v-else
                                />
                            </el-col>
                            <el-col :span="12" :offset="0">
                                <ul>
                                    <li
                                        style="
                                            font-size: 25px;
                                            margin-bottom: 5px;
                                        "
                                    >
                                        照明开关
                                    </li>
                                    <li>
                                        <el-switch
                                            v-model="
                                                this.$store.state.lightSwitch
                                            "
                                            class="ml-2"
                                            inline-prompt
                                            style="
                                                --el-switch-on-color: #13ce66;
                                                --el-switch-off-color: #ff4949;
                                                --el-switch-font-size: 20px;
                                            "
                                            active-text="开"
                                            inactive-text="关"
                                            size="large"
                                            @click="lightControll"
                                        />
                                    </li>
                                </ul>
                            </el-col> </el-row
                    ></el-col>
                </el-row>
            </el-card>
        </el-space>
    </div>
</template>

<script>
import 'qweather-icons/font/qweather-icons.css'// 引入和风天气图标
import * as mqtt from 'mqtt/dist/mqtt.min' // 引入mqtt模块
// import mqttVue from '@/components/mqtt.vue';
import { ElSpace } from 'element-plus';
export default {
    data() {
        return {
            userInfo: '',//存储用户信息
            weather: { //存储天气信息
                city: '',
                temp: "",
                text: "",
                icon: '',
                windScale: '',
                windSpeed: '',
                humidity: ''
            },
            week: '',//星期
            // pwm: this.$store.state.pwm,
            // lightSwitch: this.$store.state.pwm,
            // waterSwitch: this.$store.state.pwm

            //连接mqtt
            client: null,
            options: {
                connectTimeout: 4000, //超时时间
                reconnectPeriod: 8000, // 重连时间间隔
                clientId:
                    'k0zk2iUU9Kl.Controll|securemode=2,signmethod=hmacsha256,timestamp=1711439923591|', // id
                username: 'Controll&k0zk2iUU9Kl', // 用户名
                password:
                    '31fcd070a1c95b31ab0374a14df8e9a3fa04eec13a1a3122d7f4f32f3280e7c6', // 密码
                cleanSession: false,
                keepAlive: 60 //心跳值，心跳值太大可能会连接不成功，这个参考文档
            },
            subscription: {//订阅地址
                topic: ' /k0zk2iUU9Kl/ESP8266/user/get',
                qos: 0
            },
            recvData: '', // 接收的消息
            wd: '',//温度
            sd: '',//湿度
            gz: '',//光照
            soilSd: '',//土壤湿度
            publication: {//发布消息地址
                topic: '/k0zk2iUU9Kl/Controll/user/Controller_Topic',
                qos: 2,//确保用户收到且只会收到一次
                payload: '{"id":1712629120963,"params":{"WorkSwitch":"1"},"version":"1.0"}'
            },
        }
    },
    components: {

    },
    created() {
        this.userInfo = this.$store.state.userInfo//加载用户信息
        this.weather.city = this.userInfo.address//获取地址
    },
    mounted() {
        this.searchWeather()
        this.getdataTime();
        this.doConnected();
    },
    methods: {
        /**
         * @return {*}
         * @Date: 2024-05-30 21:43:40
         * @description: 获取实时天气
         */        
        searchWeather: async function () {//注意：这里有 async 用来完成异步操作
            //由于调用api是异步操作
            //在请求的时候需要用async+await让它同步，否则数据不好取出
            //如果没有await返回的是一个Promise 对象，我学术短浅，暂时没学到，不会取

            let key = "4b1057f0ebc74f5b92537b778a03c998"//引号中放入前面保存的key

            //方法二：新的API
            //获取城市的ID
            let httpUrl = `https://geoapi.qweather.com/v2/city/lookup?location=${this.weather.city}&key=${key}`
            let res = await fetch(httpUrl)
            let result = await res.json()
            let id = result.location[0].id
            //根据城市id获取具体的天气
            let httpUrl1 = `https://devapi.qweather.com/v7/weather/now?location=${id}&key=${key}`
            let res1 = await fetch(httpUrl1)
            let result1 = await res1.json()
            let weather = result1.now
            this.weather.temp = weather.temp + "℃"
            this.weather.text = weather.text
            this.weather.icon = "qi-" + weather.icon
            this.weather.windScale = weather.windDir + weather.windScale + "级"
            this.weather.windSpeed = weather.windSpeed + "公里/小时"
            this.weather.humidity = weather.humidity + "%"
        },
        /**
         * @return {*}
         * @Date: 2024-05-30 21:43:59
         * @description: 获取星期
         */        
        getdataTime() {
            let wk = new Date().getDay()
            let weeks = ['星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六']
            this.week = weeks[wk]
        },
        //tag：连接阿里云，MQTT 
        doConnected() {
            console.log('正在连接...')
            try {
                /**获取连接 */
                this.client = mqtt.connect('ws://k0zk2iUU9Kl.iot-as-mqtt.cn-shanghai.aliyuncs.com:443', this.options)
            } catch (error) {
                console.log('mqtt连接失败: ', error)
            }
            /**连接成功开始订阅 */
            this.client.on('connect', (e) => {
                console.log('连接成功')
                this.doSubscribe() // 订阅主题
            })
            /**接收消息处理 */
            this.client.on('message', (topic, message) => {
                console.log('收到来自', topic, '的消息', message.toString())
                this.recvData = message.toString()
                //tag:获取环境监测信息
                if (this.recvData.includes('CurrentTemperature')) {
                    this.wd = JSON.parse(message).items.CurrentTemperature.value
                    this.$store.dispatch('updateWD', this.wd)
                }
                if (this.recvData.includes('CurrentHumidity')) {
                    this.sd = JSON.parse(message).items.CurrentHumidity.value
                    this.$store.dispatch('updateSD', this.sd)
                }
                if (this.recvData.includes('SoilMoisture')) {
                    this.soilSd = JSON.parse(message).items.SoilMoisture.value
                    this.$store.dispatch('updateSoilSd', this.soilSd)
                }
                if (this.recvData.includes('LightLux')) {
                    this.gz = JSON.parse(message).items.LightLux.value
                    this.$store.dispatch('updateGZ', this.gz)
                }

                console.log("++++++++++" + this.$store.state.pwm)
                if (this.recvData.includes("WorkSwitch")) {
                    let pwm = JSON.parse(message).items.WorkSwitch.value
                    this.$store.dispatch('controllPWM', Boolean(pwm))
                }
                if (this.recvData.includes("WaterSwitch")) {
                    let water = JSON.parse(message).items.WaterSwitch.value
                    this.$store.dispatch('controllWater', Boolean(water))
                }
                if (this.recvData.includes("LightSwitch")) {
                    let light = JSON.parse(message).items.LightSwitch.value
                    this.$store.dispatch('controllLight', Boolean(light))
                }
                // this.waterSwitch = JSON.parse(message).items.WaterSwitch.value
                // this.lightSwitch = JSON.parse(message).items.LightSwitch.value
                // this.$store.dispatch('controllPWM', Boolean(this.pwm))
                // this.$store.dispatch('controllWater', Boolean(this.waterSwitch))
                // this.$store.dispatch('controllLight', Boolean(this.lightSwitch))
                //this.$emit('sd', this.sd)
            })
            // 连接错误处理
            this.client.on('error', (error) => {
                console.log('连接出错: ', error)
            })
            // 重新连接处理
            this.client.on('reconnect', () => {
                console.log('重新连接...')
            })
        },
        doDisconnected() {
            try {
                this.doUnSubscribe()
                this.client.end()
                console.log('断开连接')
            } catch (error) {
                console.log('断开连接失败: ', error.toString())
            }
        },
        /**
         * @return {*}
         * @Date: 2024-05-30 21:49:12
         * @description: 消息订阅
         */        
        doSubscribe() {
            const { topic, qos } = this.subscription
            this.client.subscribe(topic, qos, (error) => {
                if (!error) {
                    console.log('订阅成功')
                } else {
                    console.log('订阅失败')
                }
            })
        },
        doUnSubscribe() {
            const { topic } = this.subscription
            this.client.unsubscribe(topic, error => {
                if (error) {
                    console.log('取消订阅失败', error)
                }
            })
        },
        /**
         * @return {*}
         * @Date: 2024-05-30 21:49:55
         * @description: 发布订阅
         */        
        doPublish() {
            const { topic, qos, payload } = this.publication
            console.log('+++++++++++++++++++++++++++发布成功: ')
            this.client.publish(topic, payload, qos, error => {
                if (error) {
                    console.log('发布失败', error)
                }
            })
        },
        //tag:控制设备
        //风扇控制
        pwmControll() {
            if (this.$store.state.pwm) {
                this.publication.payload = '{"id":1712629120963,"params":{"WorkSwitch":"1"},"version":"1.0"}'
                console.log('+++++++++++++true ')
            } else {
                this.publication.payload = '{"id":1712629120963,"params":{"WorkSwitch":"0"},"version":"1.0"}'
            }
            this.doPublish()
        },
        //补光灯控制
        lightControll() {
            if (this.$store.state.lightSwitch) {
                this.publication.payload = '{"id":1712629120963,"params":{"LightSwitch":"1"},"version":"1.0"}'
            } else {
                this.publication.payload = '{"id":1712629120963,"params":{"LightSwitch":"0"},"version":"1.0"}'
            }
            this.doPublish()
        },
        //水阀控制
        waterControll() {
            if (this.$store.state.waterSwitch) {
                this.publication.payload = '{"id":1712629120963,"params":{"WaterSwitch":"1"},"version":"1.0"}'
            } else {
                this.publication.payload = '{"id":1712629120963,"params":{"WaterSwitch":"0"},"version":"1.0"}'
            }
            this.doPublish()
        }
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