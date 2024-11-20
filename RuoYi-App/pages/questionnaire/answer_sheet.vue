<template>
  <view>
    <view id="top-box" class="cu-bar bg-white solid-bottom">
      <view class="action text-black">
        <!--判断当前这道小题是什么类型的-->
        <text v-if="currentType===1">单选题</text>
        <text v-else-if="currentType===0">问答题</text>
      </view>
      <view class="action">
        <!--点击展示答题卡-->
        <button class="cu-btn bg-blue shadow" @tap="showCardModal" data-target="modalCard">答题卡</button>
      </view>
    </view>
    <view class="cu-modal" :class="modalCard==='modalCard'?'show':''" @tap="hideCardModal">
      <!--答题卡卡片-->
      <view class="cu-dialog" @tap.stop>
        <scroll-view class="page padding" :scroll-y=true :style="{'height':swiperHeight}">
          <view class="cu-bar solid-bottom">
            <view class="action">
              <text class="cuIcon-title text-red"></text>
              答题卡
            </view>
          </view>
          <view class="grid col-5 ">
            <view class="margin-tb-sm text-center" v-for="(subject,index) in subjectList" :key="index">
              <button class="cu-btn round" :class="[subject.userAnswer.length===0?'line-grey':'bg-blue']"
                      @click="AppointedSubject(index)">{{ index + 1 }}
              </button>
            </view>
          </view>
        </scroll-view>
      </view>
    </view>

    <form>
      <swiper :current="subjectIndex" class="swiper-box" @change="SwiperChange" :style="{'height':swiperHeight}">
        <swiper-item v-for="(subject,index) in subjectList">

          <!--问题题目-->
          <view v-if="index-subjectIndex>=-1&&index-subjectIndex<=1">
            <view class="cu-bar bg-white solid-bottom">
              <view class="action text-black">
                <text class="cuIcon-title text-blue"></text>
                {{ index + 1 + '.' }} {{ subject.title }}
              </view>
            </view>
            <view>

              <!--单选-->
              <radio-group class="block" @change="RadioboxChange" v-if="subject.type===1">
                <view class="cu-form-group" v-for="option in subject.optionList">
                  <radio :value="option.id" :checked="subject.userAnswer.indexOf(option.id) > -1"></radio>
                  <view class="title text-black">{{ option.id }}.{{ option.content }}</view>
                </view>
              </radio-group>

              <!--问答题-->
              <view v-else-if="subject.type===0">
                <view class="cu-bar cu-bar-title bg-white margin-top">
                  <view class="action  text-black">
                    答：
                  </view>
                </view>
                <view class="cu-form-group solid margin">
                  <textarea maxlength="-1" @blur="textInput" v-model="subject.userAnswer"
                            placeholder="多行文本输入框"></textarea>
                </view>
              </view>
            </view>
          </view>
        </swiper-item>
      </swiper>
    </form>

    <!--底部切换上下题的按钮-->
    <view id="foot-box" class="cu-bar tabbar bg-white shadow foot">
      <view class="action" @click="MoveSubject(-1)">
        <view class="cuIcon-cu-image">
          <text class="lg cuIcon-back text-gray"></text>
        </view>
        <view class="text-gray">上一题</view>
      </view>
      <view class="action" @click="MoveSubject(1)">
        <view class="cuIcon-cu-image">
          <text class="lg text-gray cuIcon-right"></text>
        </view>
        <view class="text-gray">下一题</view>
      </view>
    </view>
  </view>
</template>

<script>
export default {
  data() {
    return {
      currentType: 0, //当前题型
      subjectIndex: 0,//跳转索引
      autoRadioNext: true,//判断题、单项题，自动移下一题
      swiperHeight: '800px',//
      title: '骑行路线问卷调查',
      subjectList: [
        {
          "title": "电流分有？",
          "type": 1,
          "optionList": [{"id": "A", "content": "直流"}, {"id": "B", "content": "交流"}, {
            "id": "C",
            "content": "直流和交流"
          }],
          "answer": "C",
          "userAnswer": "",
          "explain": "科技学依据"
        },
        {
          "title": "什么美国要限制华为？",
          "type": 0,
          "optionList": [{"id": "", "content": ""}],
          "answer": "",
          "userAnswer": "",
          "explain": "问答题没有选项，无法做答，且不参与计分"
        },
      ],
      modalCard: null,//显示答题卡
    }
  },
  onReady() {
    var tempHeight = 800;
    var _me = this;
    uni.getSystemInfo({
      success: function (res) {
        tempHeight = res.windowHeight;
        console.log("屏幕可用高度 " + tempHeight);

        uni.createSelectorQuery().select("#top-box").fields({
          size: true,
          scrollOffset: true
        }, (data) => {
          tempHeight -= data.height;
          console.log("减掉顶部后的高度 " + tempHeight);

          uni.createSelectorQuery().select("#foot-box").fields({
            size: true,
            scrollOffset: true
          }, (data) => {
            tempHeight -= data.height;
            console.log("减掉底部后的高度 " + tempHeight);
            _me.swiperHeight = tempHeight + 'px';
            console.log("滑屏最后高度 " + _me.swiperHeight);
          }).exec();

        }).exec();
      }
    });

  },
  onLoad() {

    this.currentType = this.subjectList[0].type;
    uni.setNavigationBarTitle({
      title: this.title
    });

    //添加用户显示答案字段
    for (var i = 0; i < this.subjectList.length; i++) {
      this.$set(this.subjectList[i], "showAnswer", false);
    }

  },
  methods: {
    showCardModal: function (e) {
      this.modalCard = e.currentTarget.dataset.target
    },
    hideCardModal: function (e) {
      this.modalCard = null
    },
    SwiperChange: function (e) { //滑动事件
      let index = e.target.current;
      if (index !== undefined) {
        this.subjectIndex = index;
        this.currentType = this.subjectList[index].type;
      }
    },
    RadioboxChange: function (e) { //单选选中

      var items = this.subjectList[this.subjectIndex].optionList;
      var values = e.detail.value;
      this.subjectList[this.subjectIndex].userAnswer = values;
      if (this.autoRadioNext && this.subjectIndex < this.subjectList.length - 1) {
        this.subjectIndex += 1;
      }
      ;

    },
    textInput: function (e) { //填空题
      this.subjectList[this.subjectIndex].userAnswer = e.detail.value;
    },

    MoveSubject: function (e) { //上一题、下一题
      if (e === -1 && this.subjectIndex !== 0) {
        this.subjectIndex -= 1;
      }
      if (e === 1 && this.subjectIndex < this.subjectList.length - 1) {
        this.subjectIndex += 1;
      }
    },

    AppointedSubject: function (e) { //题卡指定
      this.modalCard = null;
      this.subjectIndex = e;
    },
  }
}
</script>

<style>

page {
  background-color: #FFFFFF;
}

.cu-form-group {
  justify-content: flex-start
}

.cu-form-group .title {
  padding-left: 30upx;
  padding-right: 0upx;
}

.cu-form-group + .cu-form-group {
  border-top: none;
}

.cu-bar-title {
  min-height: 50upx;
}

.cu-list.menu > .cu-item-error {
  justify-content: flex-start;
}
</style>
