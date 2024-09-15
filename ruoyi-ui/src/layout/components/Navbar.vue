<template>
  <div class="navbar">
    <hamburger id="hamburger-container" :is-active="sidebar.opened" class="hamburger-container"
               @toggleClick="toggleSideBar"
    />

    <breadcrumb id="breadcrumb-container" class="breadcrumb-container" v-if="!topNav"/>
    <top-nav id="topmenu-container" class="topmenu-container" v-if="topNav"/>

    <div class="right-menu">
      <template v-if="device!=='mobile'">
        <search id="header-search" class="right-menu-item"/>

        <!--        <el-tooltip content="源码地址" effect="dark" placement="bottom">-->
        <!--          <ruo-yi-git id="ruoyi-git" class="right-menu-item hover-effect" />-->
        <!--        </el-tooltip>-->

        <!--                <el-tooltip content="文档地址" effect="dark" placement="bottom">-->
        <!--                  <ruo-yi-doc id="ruoyi-doc" class="right-menu-item hover-effect" />-->
        <!--                </el-tooltip>-->

        <el-tooltip effect="dark" placement="bottom">
          <div slot="content" style="text-align: center">设置微信小程序首页轮播图</div>
          <el-button type="text" icon="el-icon-picture-outline" style="font-weight: 1000; font-size: 24px"
                     class="right-menu-item" @click="handlerBannerSetting"
          ></el-button>
        </el-tooltip>

        <screenfull id="screenfull" class="right-menu-item hover-effect"/>

        <el-tooltip effect="dark" placement="bottom">
          <div slot="content" style="text-align: center">刷新本地缓存<br/>若列表的用户昵称、头像更新或显示不及时<br/>可点此按钮刷新本地缓存</div>
          <el-button type="text" icon="el-icon-refresh" style="font-weight: 1000; font-size: 24px"
                     class="right-menu-item" @click="refreshUsersInfo"
          ></el-button>
        </el-tooltip>

        <el-tooltip content="布局大小" effect="dark" placement="bottom">
          <size-select id="size-select" class="right-menu-item hover-effect"/>
        </el-tooltip>

      </template>

      <el-dropdown class="avatar-container right-menu-item hover-effect" trigger="click">
        <div class="avatar-wrapper">
          <img :src="avatar" class="user-avatar">
          <i class="el-icon-caret-bottom"/>
        </div>
        <el-dropdown-menu slot="dropdown">
          <router-link to="/user/profile">
            <el-dropdown-item>个人中心</el-dropdown-item>
          </router-link>
          <el-dropdown-item @click.native="setting = true">
            <span>布局设置</span>
          </el-dropdown-item>
          <el-dropdown-item divided @click.native="logout">
            <span>退出登录</span>
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
    <!--    轮播图设置-->
    <div>
      <el-dialog
        title="提示"
        :visible.sync="showBannerDialog"
        width="80%"

        @close="onBannerDialogClose"
      >
        <div v-loading="bannerDialogLoading || bannerDialogLoadingDis || bannerDialogLoadingDev">
          <el-card style="margin-bottom: 15px" shadow="hover">
            <p style="text-align: center; margin: 0"><strong>注意: 上传的单张图片超过500kb无法上传, 推荐使用jpg格式图片。每个页面的轮播图最多能上传六张</strong></p>
          </el-card>

          <el-card>
            <el-collapse  @change="handleChange" style="margin-top: 10px; " >
              <el-collapse-item title="首页轮播图设置">
                <el-upload
                  action="#"
                  list-type="picture-card"
                  name="bannerFile"
                  :auto-upload="true"
                  :on-change="bannerListChange"
                  :before-upload="beforeBannerUpload"
                  :file-list="fileList"
                  :limit="6"
                  accept=".jpeg,.png,.jpg,.bmp,.gif"
                  :on-preview="handleBannerPreview"
                  :before-remove="handleBannerBeforeRemove"
                  :on-remove="handleBannerRemove"
                >
                  <i class="el-icon-plus"></i>
                </el-upload>
              </el-collapse-item>

              <el-collapse-item title="分销轮播图设置" >
                <el-upload
                  action="#"
                  list-type="picture-card"
                  name="bannerFile"
                  :auto-upload="true"
                  :on-change="bannerListChangeDis"
                  :before-upload="beforeBannerUpload"
                  :file-list="fileListDis"
                  :limit="6"
                  accept=".jpeg,.png,.jpg,.bmp,.gif"
                  :on-preview="handleBannerPreview"
                  :before-remove="handleBannerBeforeRemove"
                  :on-remove="handleBannerRemove"
                >
                  <i class="el-icon-plus"></i>
                </el-upload>
              </el-collapse-item>

              <el-collapse-item title="研发页面轮播图设置" >
                <el-upload
                  action="#"
                  list-type="picture-card"
                  name="bannerFile"
                  :auto-upload="true"
                  :on-change="bannerListChangeDev"
                  :before-upload="beforeBannerUpload"
                  :file-list="fileListDev"
                  :limit="6"
                  accept=".jpeg,.png,.jpg,.bmp,.gif"
                  :on-preview="handleBannerPreview"
                  :before-remove="handleBannerBeforeRemove"
                  :on-remove="handleBannerRemove"
                >
                  <i class="el-icon-plus"></i>
                </el-upload>
              </el-collapse-item>

            </el-collapse>
          </el-card>


        </div>

        <span slot="footer" class="dialog-footer">
    <el-button type="primary" @click="confirmBannerDialog">关 闭</el-button>
      </span>

      </el-dialog>
      <!--   轮播图片上传预览-->
      <el-dialog :visible.sync="dialogVisible">
        <img width="100%" :src="dialogImageUrl" alt="">
      </el-dialog>
    </div>
    <!--轮播图设置元素结束-->

  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import Breadcrumb from '@/components/Breadcrumb'
import TopNav from '@/components/TopNav'
import Hamburger from '@/components/Hamburger'
import Screenfull from '@/components/Screenfull'
import SizeSelect from '@/components/SizeSelect'
import Search from '@/components/HeaderSearch'
import RuoYiGit from '@/components/RuoYi/Git'
import RuoYiDoc from '@/components/RuoYi/Doc'
import { delBanner, listBanner, updateBanner, uploadBanner } from '@/api/system/home/banner'

export default {
  components: {
    Breadcrumb,
    TopNav,
    Hamburger,
    Screenfull,
    SizeSelect,
    Search,
    RuoYiGit,
    RuoYiDoc
  },
  computed: {
    ...mapGetters([
      'sidebar',
      'avatar',
      'device'
    ]),
    setting: {
      get() {
        return this.$store.state.settings.showSettings
      },
      set(val) {
        this.$store.dispatch('settings/changeSetting', {
          key: 'showSettings',
          value: val
        })
      }
    },
    topNav: {
      get() {
        return this.$store.state.settings.topNav
      }
    }
  },
  data() {
    return {
      //显示banner变量
      bannerUploadUrl: '/system/banner',
      showBannerDialog: false,
      dialogImageUrl: '',
      dialogVisible: false,
      //banner对话框的loading
      bannerDialogLoading: false,
      bannerDialogLoadingDis: false,
      bannerDialogLoadingDev: false,

      //banner图片列表
      fileList: [],
      //分销轮播图
      fileListDis: [],
      //研发页面轮播图
      fileListDev:[],
      //上传的轮播图的类型 1 为首页轮播图 0为分销轮播图 2为研发页面轮播图
      // 查询参数
      queryParams: {
        bannerType:1,
      },
      queryParamsDis: {
        bannerType:0,
      },
      queryParamsDev: {
        bannerType:2,
      },
    }
  },
  methods: {
    //-----------------------------------------------------------
    //弹出轮播图设置, 展示已有的轮播图
    handlerBannerSetting() {
      this.showBannerDialog = true
      this.loadBannerList()
      this.loadBannerListDis()
      this.loadBannerListDev()
    },
    //关闭banner对话框时
    onBannerDialogClose() {
      this.fileList = []
      this.fileListDis = []
      this.fileListDev = []
    },

    // 加载bannerList
    loadBannerList() {
      this.bannerDialogLoading = true
      this.fileList = []
      setTimeout(() => {
        listBanner(this.queryParams).then(response => {
          if (response.code == 200) {
            this.fileList = response.rows
            this.fileList.forEach(item => {
              item.url = process.env.VUE_APP_BASE_API + item.banner
            })
          }
          this.bannerDialogLoading = false
        }).catch(error => {
          console.error(error)
          this.bannerDialogLoading = false
        })
      }, 800)
    },
    //分销页面轮播图处理
    loadBannerListDis() {
      this.bannerDialogLoadingDis = true
      this.fileListDis = []
      setTimeout(() => {
        listBanner(this.queryParamsDis).then(response => {
          if (response.code == 200) {
            this.fileListDis = response.rows
            this.fileListDis.forEach(item => {
              item.url = process.env.VUE_APP_BASE_API + item.banner
            })
          }
          this.bannerDialogLoadingDis = false
        }).catch(error => {
          console.error(error)
          this.bannerDialogLoadingDis = false
        })
      }, 800)
    },
    //研发页面轮播图处理
    loadBannerListDev() {
      this.bannerDialogLoadingDev = true
      this.fileListDev = []
      setTimeout(() => {
        listBanner(this.queryParamsDev).then(response => {
          if (response.code == 200) {
            this.fileListDev = response.rows
            this.fileListDev.forEach(item => {
              item.url = process.env.VUE_APP_BASE_API + item.banner
            })
          }
          this.bannerDialogLoadingDev = false
        }).catch(error => {
          console.error(error)
          this.bannerDialogLoadingDev = false
        })
      }, 800)
    },

    //确认
    confirmBannerDialog() {
      this.showBannerDialog = false
    },

    //阻止文件自动上传
    beforeBannerUpload(file) {
      return false
    },

    //banner图片上传接口
    uploadBannerFile(bannerFile) {
      let formData = new FormData()
      formData.append('bannerFile', bannerFile, 'bannerFile')
      uploadBanner(formData,1).then(response => {
        this.fileList.push({
          url: process.env.VUE_APP_BASE_API + response.imgUrl
        })
      }).catch(error => {
        this.$message.error('服务异常, 上传失败!')
      })
    },

    //上传分销banner体魄
    uploadBannerFileDis(bannerFile) {
      let formData = new FormData()
      formData.append('bannerFile', bannerFile, 'bannerFile')
      uploadBanner(formData,0).then(response => {
        this.fileListDis.push({
          url: process.env.VUE_APP_BASE_API + response.imgUrl
        })
      }).catch(error => {
        this.$message.error('服务异常, 上传失败!')
      })
    },
    //上传研发banner
    uploadBannerFileDev(bannerFile) {
      let formData = new FormData()
      formData.append('bannerFile', bannerFile, 'bannerFile')
      uploadBanner(formData,2).then(response => {
        this.fileListDev.push({
          url: process.env.VUE_APP_BASE_API + response.imgUrl
        })
      }).catch(error => {
        this.$message.error('服务异常, 上传失败!')
      })
    },

    //文件状态改变时, 仅显示文件选中添加时触发(此处阻止了自动上传)
    bannerListChange(file, fileList) {
      //文件校验
      if (
        file.raw.type === 'image/jpg' ||
        file.raw.type === 'image/png' ||
        file.raw.type === 'image/jpeg' ||
        file.raw.type === 'image/bmp' ||
        file.raw.type === 'image/gif'
      ) {
        if (file.raw.size > 500 * 1024) {
          this.$message.error(
            '文件大于500KB无法上传!'
          )
        } else {
          //上传图片
            this.uploadBannerFile(file.raw)
        }
      } else {
        this.$message.error(
          '不是.jpeg .png .jpg .bmp .gif文件, 请上传正确的图片类型'
        )
      }
    },
    bannerListChangeDis(file, fileList) {
      //文件校验
      if (
        file.raw.type === 'image/jpg' ||
        file.raw.type === 'image/png' ||
        file.raw.type === 'image/jpeg' ||
        file.raw.type === 'image/bmp' ||
        file.raw.type === 'image/gif'
      ) {
        if (file.raw.size > 500 * 1024) {
          this.$message.error(
            '文件大于500KB无法上传!'
          )
        } else {
          //上传图片
          this.uploadBannerFileDis(file.raw)
        }
      } else {
        this.$message.error(
          '不是.jpeg .png .jpg .bmp .gif文件, 请上传正确的图片类型'
        )
      }
    },
    //研发页面轮播图
    bannerListChangeDev(file, fileList) {
      //文件校验
      if (
        file.raw.type === 'image/jpg' ||
        file.raw.type === 'image/png' ||
        file.raw.type === 'image/jpeg' ||
        file.raw.type === 'image/bmp' ||
        file.raw.type === 'image/gif'
      ) {
        if (file.raw.size > 500 * 1024) {
          this.$message.error(
            '文件大于500KB无法上传!'
          )
        } else {
          //上传图片
          this.uploadBannerFileDev(file.raw)
        }
      } else {
        this.$message.error(
          '不是.jpeg .png .jpg .bmp .gif文件, 请上传正确的图片类型'
        )
      }
    },

    //删除前置校验
    handleBannerBeforeRemove(file, fileList) {
      if (file.id != null && file.id != undefined && file.id != '') {
        delBanner(file.id).then(response => {
          if (response.code == 200) {
            this.$message.success('移除成功!')
          } else {
            this.$message.warning('服务异常!')
          }
          this.loadBannerList()
          this.loadBannerListDis()
          this.loadBannerListDev()
        }).catch(error => {
          this.$message.error('服务异常, 删除失败!')
          this.loadBannerList()
          this.loadBannerListDis()
          this.loadBannerListDev()
        })
      }
    },

    //处理移除列表文件
    handleBannerRemove(file, fileList) {
      //默认为空, 不做处理
    },

    //预览图片按钮
    handleBannerPreview(file) {
      this.dialogImageUrl = file.url
      this.dialogVisible = true
    },

    //------------------------轮播图设置结束----------------------------------

    //刷新本地缓存
    refreshUsersInfo() {
      this.$message({
        message: '缓存刷新中...',
        type: 'warning'
      })
      this.$store.dispatch('GetUserInfoList').then(() => {
        this.$message({
          message: '缓存刷新成功, 页面将自动刷新!',
          type: 'success'
        })
        setTimeout(() => {
          window.location.reload()
        }, 1000)
      }).catch(err => {
        console.log(err)
      })
    },
    toggleSideBar() {
      this.$store.dispatch('app/toggleSideBar')
    },
    // 折叠面板
    handleChange(activeNames) {
      this.activeNames = activeNames;
    },
    async logout() {
      this.$confirm('确定注销并退出系统吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$store.dispatch('LogOut').then(() => {
          // location.href = '/index'
          location.href = '/admin'
        })
      }).catch(() => {
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.navbar {
  height: 50px;
  overflow: hidden;
  position: relative;
  background: #fff;
  box-shadow: 0 1px 4px rgba(0, 21, 41, .08);

  .hamburger-container {
    line-height: 46px;
    height: 100%;
    float: left;
    cursor: pointer;
    transition: background .3s;
    -webkit-tap-highlight-color: transparent;

    &:hover {
      background: rgba(0, 0, 0, .025)
    }
  }

  .breadcrumb-container {
    float: left;
  }

  .topmenu-container {
    position: absolute;
    left: 50px;
  }

  .errLog-container {
    display: inline-block;
    vertical-align: top;
  }

  .right-menu {
    float: right;
    height: 100%;
    line-height: 50px;

    &:focus {
      outline: none;
    }

    .right-menu-item {
      display: inline-block;
      padding: 0 8px;
      height: 100%;
      font-size: 18px;
      color: #5a5e66;
      vertical-align: text-bottom;

      &.hover-effect {
        cursor: pointer;
        transition: background .3s;

        &:hover {
          background: rgba(0, 0, 0, .025)
        }
      }
    }

    .avatar-container {
      margin-right: 30px;

      .avatar-wrapper {
        margin-top: 5px;
        position: relative;

        .user-avatar {
          cursor: pointer;
          width: 40px;
          height: 40px;
          border-radius: 10px;
        }

        .el-icon-caret-bottom {
          cursor: pointer;
          position: absolute;
          right: -20px;
          top: 25px;
          font-size: 12px;
        }
      }
    }
  }
}
</style>
