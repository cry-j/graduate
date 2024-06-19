<template>
    <!-- <div>
        <button @click="doConnected">连接</button>
        <button @click="doPublish">
            <p v-if="pwm == 0">开</p>
            <p v-if="pwm == 1">关</p>
        </button>
        <button @click="doDisconnected">断开连接</button>
        <p>收到的消息: {{ recvData }}</p>
    </div>
    <div>
        <ul>
            <li>温度:{{ wd }}</li>
            <li>湿度:{{ sd }}</li>
        </ul>
    </div> -->
    <!-- <userMainVue :wd="wd" :sd="sd" /> -->
</template>
   
  <script>
import * as mqtt from 'mqtt/dist/mqtt.min' // 引入mqtt模块
// import userMainVue from '@/view/user/userMain.vue'
export default {
    created() {
        this.doConnected();
    },
    // components: {
    //     userMainVue
    // },
    data() {
        return {
            client: null,
            options: {
                connectTimeout: 4000, //超时时间
                clientId:
                    'k0zk2iUU9Kl.Controll|securemode=2,signmethod=hmacsha256,timestamp=1711439923591|', // id
                username: 'Controll&k0zk2iUU9Kl', // 用户名
                password:
                    '31fcd070a1c95b31ab0374a14df8e9a3fa04eec13a1a3122d7f4f32f3280e7c6', // 密码
                cleanSession: false,
                keepAlive: 60 //心跳值，心跳值太大可能会连接不成功，这个参考文档
            },
            subscription: {
                topic: ' /k0zk2iUU9Kl/ESP8266/user/get',
                qos: 0
            },
            recvData: '', // 接收的消息
            wd: '',
            sd: '',
            gz: '',
            publication: {
                topic: '/k0zk2iUU9Kl/Controll/user/Controller_Topic',
                qos: 0,
                payload: '{"id":1712629120963,"params":{"WorkSwitch":"1"},"version":"1.0"}'
            },
        }
    },
    methods: {
        doConnected() {
            console.log('正在连接...')
            try {
                this.client = mqtt.connect('ws://k0zk2iUU9Kl.iot-as-mqtt.cn-shanghai.aliyuncs.com:443', this.options)
            } catch (error) {
                console.log('mqtt连接失败: ', error)
            }
            this.client.on('connect', (e) => {
                console.log('连接成功')
                this.doSubscribe() // 订阅主题
            })
            // 接收消息处理
            this.client.on('message', (topic, message) => {
                console.log('收到来自', topic, '的消息', message.toString())
                this.recvData = message.toString()
                this.wd = JSON.parse(message).items.CurrentTemperature.value
                this.sd = JSON.parse(message).items.CurrentHumidity.value
                this.gz = JSON.parse(message).items.LightLux.value
                // this.pwm = JSON.parse(message).items.WorkSwitch.value
                // this.waterSwitch = JSON.parse(message).items.WaterSwitch.value
                // this.lightSwitch = JSON.parse(message).items.LightSwitch.value
                this.$store.dispatch('updateWD', this.wd)
                this.$store.dispatch('updateSD', this.sd)
                this.$store.dispatch('updateGZ', this.gz)

                // this.$store.dispatch('controllPWM', Boolean(this.pwm))
                // this.$store.dispatch('controllWater', Boolean(this.waterSwitch))
                // this.$store.dispatch('controllLight', Boolean(this.lightSwitch))
                console.log("++++++++++" + this.$store.state.pwm)
                // this.$emit('data', this.recvData)
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
        doPublish() {
            const { topic, qos, payload } = this.publication
            console.log('+++++++++++++++++++++++++++发布成功: ')
            this.client.publish(topic, payload, qos, error => {
                if (error) {
                    console.log('发布失败', error)
                }
            })
        },
        pwmControll() {
            if (this.$store.state.pwm) {
                this.publication.payload = '{"id":1712629120963,"params":{"WorkSwitch":"1"},"version":"1.0"}'
                console.log('+++++++++++++true ')
            } else {
                this.publication.payload = '{"id":1712629120963,"params":{"WorkSwitch":"0"},"version":"1.0"}'
            }


        },
        lightControll() {
            if (this.$store.state.lightSwitch) {
                this.publication.payload = '{"id":1712629120963,"params":{"LightSwitch":"1"},"version":"1.0"}'
            } else {
                this.publication.payload = '{"id":1712629120963,"params":{"LightSwitch":"0"},"version":"1.0"}'
            }

        },
        waterControll() {
            if (this.$store.state.waterSwitch) {
                this.publication.payload = '{"id":1712629120963,"params":{"WaterSwitch":"1"},"version":"1.0"}'
            } else {
                this.publication.payload = '{"id":1712629120963,"params":{"WaterSwitch":"0"},"version":"1.0"}'
            }
        }
    }
}
  </script>