/*
 * @Author: Jing
 * @Date: 2024-04-13 18:28:03
 * @LastEditTime: 2024-05-29 21:38:23
 * @Description: store 是一个集中管理数据的对象，使组件通信更方便，可以避免组件之间的混乱和重复代码，并实现更好的代码复用
 */
//tag:管理全局数据
import { createStore } from 'vuex'
// 类似 Redux 中的建立状态树

export default createStore({
  
    // 1、 存储所有全局数据
    state: {
        wd: 0,
        sd: 0,
        gz: 0,
        soilSd: 0,
        pwm: false,
        waterSwitch: false,
        lightSwitch: false,
        userInfo: {
            
        },
        admin: {
            
        },
        activeVue: ''
    },
 
    // 2、 需要通过计算获取state里的内容获取的数据
    // 只能读取不可修改
    getters: {
        getCurrentTemperature(state) {
            return state.wd
        },
        getCurrentHumidity(state) {
            return state.sd
        },
        getSoilMoisture(state) {
            return state.soilSd
        },
        getLightLux(state) {
            return state.gz
        },
        getPWM(state) {
            return state.pwm
        },
        getWaterSwitch(state) {
            return state.waterSwitch
        },
        getLightSwitch(state) {
            return state.lightSwitch
        },
        getUserInfo(state) {
            return state.userInfo
        },
        getAdmin(state) {
            return state.admin
        },
        getActiveVue(state) {
            return state.activeVue
        }
      
    },
 
    //  3、定义对state的各种操作
    // why不直接实现在mutation里需要写到action里?
    // mtations不能执行异步操作。aq:从云端获取信息-->(等待云端反馈)更新到state异步操作
    // 因此说:对于异步操作需要放到action里，简单的直接赋值操作可以直接放到mutation里
    mutations: {
        setCurrentTemperature(state, wd) {
            state.wd = wd
        },
        setCurrentHumidity(state, sd) {
            state.sd = sd
        },
        setSoilMoisture(state, soilSd) {
            state.soilSd = soilSd
        },
        setLightLux(state, gz) {
            state.gz = gz
        },
        setPwm(state, pwm) {
            state.pwm = pwm
        },
        setWaterSwitch(state, waterSwitch) {
            state.waterSwitch = waterSwitch
        },
        setLightSwitch(state, lightSwitch) {
            state.lightSwitch = lightSwitch
        },
        setUserInfo(state, userInfo) {
            state.userInfo = userInfo
        },
        setAdmin(state, admin) {
            state.admin = admin
        },
        setActive(state, activeVue) { 
            state.activeVue = activeVue
        }
  },

  // 3、定义对state的各种操作
  // actions无法直接修改state，需要在mutations里更新
  // mutation不支持异步，所以需要在action里写api到url
  actions: {
      // 比说action定义了更新state的操作
      // 但是不可对其直接修改
      // 所有的修改操作必须放到mutations里
      
      updateWD(context, wd) {
          context.commit('setCurrentTemperature',wd)
      },
      updateSD(context, sd) {
        context.commit('setCurrentHumidity',sd)
      },
      updateSoilSd(context, soilSd) {
        context.commit('setSoilMoisture',soilSd)
      },
      updateGZ(context, gz) {
        context.commit('setLightLux',gz)
      },
      controllPWM(context, pwm) { 
          context.commit('setPwm',pwm)
      },
      controllWater(context, waterSwitch) {
          context.commit('setWaterSwitch',waterSwitch)
      },
      controllLight(context, lightSwitch) {
          context.commit('setLightSwitch',lightSwitch)
      },
      setUserInfo(context, userInfo) { 
          context.commit('setUserInfo',userInfo)
      },
      setAdmin(context, admin) { 
        context.commit('setAdmin',admin)
      },
      setActive(context, active) { 
          context.commit('setActive',active)
      }
  },

  // state中信息过长时
  // 用来将state进行分割
  // 如下，将state树分割出一个user state。
  modules: {
  }
})

