<template>
  <el-autocomplete
    class="inline-input"
    style="width: 360px"
    v-model="queryContent"
    :validate-event="false"
    clearable
    :hide-loading="false"
    :debounce="100"
    @input="handleInputChange"
    :highlight-first-item="true"
    :fetch-suggestions="handleQuery"
    :placeholder="placeholder"
    suffix-icon="el-icon-user-solid"
    @select="handleSelect"
  ></el-autocomplete>
</template>

<script>
import { getUsersInfoLike } from '@/api/system/user'

export default {
  name: 'UserIDInput',

  model: {
    prop: 'value',
    event: 'updateUserId'
  },

  props: {
    /**
     * 输入框内的默认值(用于修改输入框的初始值)
     */
    defaultValue: {
      type: Number,
      default: null
    },

    /**
     * 查询最多提示的选项数
     */
    queryNum: {
      type: Number,
      default: 8
    },

    /**
     * 输入框提示信息
     */
    placeholder: {
      type: String,
      default: '输入ID/名称/电话以检索用户'
    },

    /**
     * 防抖延迟时间
     */
    delay: {
      type: Number,
      default: 500
    }

  },

  data() {
    return {
      queryContent: '',
      selectItem: '',
      timeDelay: null
    }
  },

  // watch: {
  //   value(newVal) {
  //     console.log('value is null')
  //     if (newVal === null || newVal === undefined) {
  //       this.queryContent = ''
  //       this.selectItem = ''
  //     }
  //   }
  // },

  methods: {
    handleInputChange() {
      this.$emit('updateUserId', null)
    },

    handleQuery(queryString, cb) {
      let queryItem = []
      //函数防抖
      this.timeDelay = null
      this.timeDelay = setTimeout(() => {
        if (queryString !== '' && queryString != null && queryString !== '1' && queryString !== this.selectItem) {
          getUsersInfoLike({ queryString: queryString, queryNum: this.queryNum }).then(res => {
            res.data.forEach(item => {
              queryItem.push({
                userId: item.userId,
                value: 'ID: ' + item.userId + ' & 名称: ' + item.nickName + ' & 电话: ' + item.phonenumber
              })
            })
          }).then(() => {
            if (queryItem.length === 0) {
              queryItem.push({
                value: '没有查找到数据...'
              })
            }
            cb(queryItem)
          })
        } else {
          cb(queryItem)
        }
      }, this.delay)
    },

    handleSelect(item) {
      if (item.value === '没有查找到数据...') {
        this.$emit('updateUserId', null)
        return
      } else {
        this.$emit('updateUserId', item.userId)
        this.selectItem = item.value
      }
    },

    reset() {
      this.selectItem = ''
      this.queryContent = ''
    }

  }
}

</script>

<style scoped>

</style>
