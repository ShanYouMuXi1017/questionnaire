import { getUsersInfo, listUser } from '@/api/system/user'
import localForage from 'localforage'

/**
 * 在此处存储了权限范围内的所有用户的信息,
 * 即用户管理页面的用户列表信息(登录者需要有system:user:list)权限
 *
 * 已暴露给getter.js, 可以便捷使用:
 *    (建议使用)获取所有用户列表的基本信息(userId, nickName, avatar, phonenumber), 用于列表显示和ID输入提示框
 *    => this.$store.getters.usersInfo
 *
 *    获得所有用户的所有信息(不安全, 弃用)
 *    => this.$store.getters.users
 */

const users = {
  state: {
    userList: [],
    usersInfoList: []
  },

  mutations: {
    SET_USERS: (state, userList) => {
      state.userList = userList
    },
    SET_USERS_INFO: (state, userInfoList) => {
      state.usersInfoList = userInfoList
    }
  },

  actions: {
    // 获取全部用户全部信息(容易泄露用户隐私, 不要使用此方法)
    GetUserList({ commit }) {
      return new Promise((resolve, reject) => {
        listUser().then(response => {
          if (response.code === 200 && response.rows.length !== 0) {
            let base_api = process.env.VUE_APP_BASE_API
            response.rows.forEach((user) => {
              user.avatar = base_api + user.avatar
            })
            commit('SET_USERS', response.rows)
            let usersInfoList = []
            response.rows.forEach((user) => {
              usersInfoList.push({
                userId: user.userId,
                // userName: user.userName,
                nickName: user.nickName,
                avatar: user.avatar
                // phonenumber: user.phonenumber,
                // email: user.email
              })
            })
            commit('SET_USERS_INFO', usersInfoList)
            localStorage.setItem('USERS_INFO', JSON.stringify(usersInfoList))
          }
          reject()
        }).catch(error => {
          console.error(error)
          resolve()
        })
      })
    },

    //获取用户基本信息并缓存到浏览器
    GetUserInfoList({ commit }) {
      return new Promise((resolve, reject) => {
        getUsersInfo().then(response => {
          let usersInfo = []
          if (response != null && response.length !== 0) {
            let base_api = process.env.VUE_APP_BASE_API
            response.forEach((user) => {
              if (!(user.avatar == '' || user.avatar == '<empty string>' || user.avatar == null)) {
                user.avatar = base_api + user.avatar
                usersInfo.push({
                  userId: user.userId,
                  nickName: user.nickName,
                  avatar: user.avatar,
                  // phonenumber: user.phonenumber
                })
              } else {
                usersInfo.push({
                  userId: user.userId,
                  nickName: user.nickName,
                  // phonenumber: user.phonenumber
                })
              }
            })

            localForage.setItem('usersInfo', usersInfo).catch((err) => {
              reject(err)
              return
            })

            // window.localStorage.clear()
            // commit('SET_USERS_INFO', usersInfo)
            // window.localStorage.setItem('USERS_INFO', JSON.stringify(usersInfo))
          }
          // resolve(window.localStorage.getItem('USERS_INFO'))
          localForage.getItem('usersInfo').then((res) => {
            resolve(res)
          }).catch((err) => {
            reject(err)
          })
        }).catch(err => {
          console.error(err)
          reject(err)
        })
      })
    }

  }
}

export default users
