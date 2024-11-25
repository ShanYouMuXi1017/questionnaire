<template>
  <view>
    <!-- 顶部导航 -->
    <view id="top-box" class="cu-bar bg-white solid-bottom">
      <view class="action text-black">
        <!--判断当前这道小题是什么类型的-->
        <text>问题类型:{{ currentGroupType }}</text>
      </view>
      <view class="action">
        <button class="cu-btn bg-blue shadow" @tap="showCardModal" data-target="modalCard">答题卡</button>
      </view>
    </view>

    <!-- 答题卡弹窗 -->
    <view class="cu-modal" :class="modalCard === 'modalCard' ? 'show' : ''" @tap="hideCardModal">
      <view class="cu-dialog" @tap.stop>
        <scroll-view class="page padding" :scroll-y="true" :style="{'height': swiperHeight2}">
          <view class="cu-bar solid-bottom">
            <view class="action" style="font-size: 22px;font-weight: 700">
              <text class="cuIcon-title text-red"></text>
              答题卡
            </view>
          </view>
          <!-- 分组展示 -->
          <view v-for="(group, type) in groupedSubjects" :key="type">
            <view class="cu-bar bg-light-gray">
              <text>问题 {{ type }}</text>
            </view>
            <view class="grid col-5">
              <view
                  class="margin-tb-sm text-center"
                  v-for="(subject, index) in group"
                  :key="index"
              >
                <button
                    class="cu-btn round"
                    :class="[subject.answerResult === null ? 'line-grey' : 'bg-blue']"
                    @click="AppointedSubject(subject.issueId)"
                >
                  {{ index + 1 }}
                </button>
              </view>
            </view>
          </view>
        </scroll-view>
      </view>
    </view>


    <!-- 问题组 Swiper -->
    <swiper :current="groupIndex" class="swiper-box" @change="SwiperGroupChange" :style="{'height': swiperHeight}">
      <swiper-item v-for="(group, index) in groupedData" :key="index">
        <scroll-view
            scroll-y="true"
            style="height: 1300rpx;"
            id="scroll-container"
        >
          <!-- 当前组的题目 -->
          <view v-for="(subject, subjectIndex) in group.subjects" :key="subjectIndex"
                :id="'subject-' + index + '-' + subjectIndex">
            <view class="action question-description">
              <!-- 问题类型标签 -->
              <view :class="['tag', subject.answerType === 1 ? 'tag-single' : 'tag-question']">
                {{ subject.answerType === 1 ? "单选题" : "问答题" }}
              </view>
              <!-- 问题内容 -->
              <text class="problem-text">{{ subjectIndex + 1 }}. {{ subject.problem }}</text>
            </view>

            <!-- 单选题 -->
            <view v-if="subject.answerType === 1">
              <view class="slider-container">
                <slider
                    :max="subject.answerOptions2.length - 1"
                    :value="subject.answerOptions2.findIndex(option => option.id === subject.answerResult)"
                    step="1"
                    show-value
                    @change="handleSliderChange(subject, $event)"
                    active-color="#ff7e5f"
                    background-color="#e0e0e0"
                    block-size="20"
                ></slider>
                <view class="slider-labels">
                  <view
                      v-for="(option, index) in subject.answerOptions2"
                      :key="option.id"
                      class="slider-label"
                      :class="{ active: subject.answerResult === option.id }"
                  >
                    {{ option.id }}. {{ option.content }}
                  </view>
                </view>
              </view>
            </view>



            <!-- 问答题 -->
            <view v-else-if="subject.answerType === 0">
              <view class="cu-form-group solid margin">
                <textarea maxlength="-1" @blur="textInput(subject,$event)" v-model="subject.answerResult"
                          placeholder="请输入您的答案"></textarea>
              </view>
            </view>
          </view>
        </scroll-view>
      </swiper-item>
    </swiper>


    <!-- 底部导航 -->
    <view id="foot-box" class="cu-bar tabbar bg-white shadow foot enhanced-nav">
      <view
          class="nav-button"
          @click="MoveGroup(-1)"
          :class="{ disabled: groupIndex === 0 }"
      >
        <view class="icon">
          <text class="lg cuIcon-back" :class="groupIndex === 0 ? 'text-gray' : 'text-blue'"></text>
        </view>
        <view class="label" :class="groupIndex === 0 ? 'text-gray' : 'text-blue'">上一组</view>
      </view>

      <view
          class="nav-button"
          @click="handleNavigation"
      >
        <view class="icon">
          <text
              class="lg"
              :class="groupIndex === groupedData.length-1 ? 'cuIcon-check text-red' : 'cuIcon-right text-blue'"
          ></text>
        </view>
        <view class="label">
          {{ groupIndex === groupedData.length-1  ? '交卷' : '下一组' }}
        </view>
      </view>


    </view>
  </view>
</template>

<script>
import {getQuestSheet} from "../../api/system/user";

export default {
  data() {
    return {
      groupedData: [], // 按 problemType 分组的数据
      flattenedSubjects: [], // 扁平化后的所有题目，用于答题卡
      groupIndex: 0, // 当前组索引
      groupedSubjects: {}, // 按 problemType 分组的题目
      swiperHeight: "800px", // 滑屏高度
      swiperHeight2: "800px", // 答题卡高度
      modalCard: null, // 答题卡弹窗控制
      currentGroupType: "1", // 当前组的问题类型
    };
  },
  created() {
    this.fetchQuestionSheet();
  },
  onReady() {
    var tempHeight = 800;
    var _me = this;
    uni.getSystemInfo({
      success: function (res) {
        tempHeight = res.windowHeight;
        uni.createSelectorQuery().select("#top-box").fields({
          size: true,
          scrollOffset: true
        }, (data) => {
          tempHeight -= data.height;
          uni.createSelectorQuery().select("#foot-box").fields({
            size: true,
            scrollOffset: true
          }, (data) => {
            tempHeight -= data.height;
            _me.swiperHeight2 = tempHeight + 'px';
          }).exec();

        }).exec();
      }
    });

  },
  methods: {
    handleSliderChange(subject, event) {
      const selectedIndex = event.detail.value;
      subject.answerResult = subject.answerOptions2[selectedIndex].id;
    },
    toggleRadioSelection(subject, optionId) {
      if (subject.answerResult === optionId) {
        // 如果当前选中的选项再次点击，取消选择
        subject.answerResult = null;
      } else {
        // 否则选择新的选项
        subject.answerResult = optionId;
      }
    },
    handleNavigation() {
      if (this.groupIndex === this.groupedData.length - 1) {
        this.submitPaper(); // 最后一组时，交卷
      } else {
        this.MoveGroup(1); // 非最后一组时，跳到下一组
      }
    },
    async fetchQuestionSheet() {
      try {
        const res = await getQuestSheet(); // 获取题目数据
        this.groupedData = res.data; // 按 problemType 分组的数据
        this.flattenedSubjects = this.groupedData.reduce(
            (acc, group) => acc.concat(group.subjects),
            []
        );

        // 根据 problemType 对题目进行分组
        this.groupedSubjects = this.flattenedSubjects.reduce((acc, subject) => {
          if (!acc[subject.problemType]) {
            acc[subject.problemType] = [];
          }
          acc[subject.problemType].push(subject);
          return acc;
        }, {});
      } catch (error) {
        console.error("Failed to fetch question sheet:", error);
      }
    },
    showCardModal(e) {
      this.modalCard = e.currentTarget.dataset.target;
    },
    hideCardModal() {
      this.modalCard = null;
    },
    SwiperGroupChange(e) {
      this.groupIndex = e.detail.current;
      this.currentGroupType = this.groupedData[this.groupIndex].problemType;

      // 使用 $nextTick 确保页面更新后滚动到顶部
      this.scrollTop = 0;
      this.$nextTick(() => {
        this.scrollTop = 1; // 触发滚动重置
        this.scrollTop = 0;
      });
    },
    RadioboxChange(subject, event) {
      subject.answerResult = event.detail.value;
    },
    MoveGroup(direction) {
      const newIndex = this.groupIndex + direction;
      if (newIndex >= 0 && newIndex < this.groupedData.length) {
        this.groupIndex = newIndex;
        this.currentGroupType = this.groupedData[this.groupIndex].problemType;

        // 滚动到顶部
        this.scrollToElement(`scroll-container`);
      }
    },
    textInput(subject, event) { //填空题
      subject.answer = event.detail.value;
    },
    scrollToElement(id) {
      // 小程序 API，滚动到指定位置
      this.$nextTick(() => {
        const query = uni.createSelectorQuery();
        query.select(`#${id}`).boundingClientRect();
        query.selectViewport().scrollOffset();
        query.exec((res) => {
          if (res && res[0]) {
            const top = res[0].top;
            wx.pageScrollTo({
              scrollTop: top,
              duration: 300,
            });
          }
        });
      });
    },
    AppointedSubject(issueId) {
      // 根据 issueId 定位到对应的问题组和小题
      for (let i = 0; i < this.groupedData.length; i++) {
        const group = this.groupedData[i];
        const index = group.subjects.findIndex((s) => s.issueId === issueId);
        if (index !== -1) {
          this.groupIndex = i;
          this.currentGroupType = group.problemType;

          // 滚动到指定题目
          const targetId = `subject-${i}-${index}`;
          this.scrollToElement(targetId);
          break;
        }
      }
      this.modalCard = null;
    },
    submitPaper() {
      console.log(this.groupedData.length);
      console.log(this.groupIndex);

      // 检查是否有未完成的问题
      const isComplete = this.flattenedSubjects.every((subject) => {
        return subject.answerResult !== null && subject.answerResult !== '';
      });

      if (!isComplete) {
        // 如果有未完成的问题，提示用户
        uni.showToast({
          title: '请完成所有问题再提交',
          icon: 'none',
          duration: 2000,
        });
        return;
      }

      // 如果所有问题都完成，显示确认框
      uni.showModal({
        title: '确认交卷',
        content: '是否确认提交试卷？提交后不可修改。',
        success: (res) => {
          if (res.confirm) {
            this.handleSubmit();
          }
        },
      });
    },
    handleSubmit() {
      // 提交逻辑，例如调用 API
      console.log('提交试卷');
      uni.showToast({
        title: '提交成功',
        icon: 'success',
        duration: 2000,
      });
    },
  }
};
</script>


<style>
page {
  background-color: #FFFFFF;
}

.cu-form-group {
  justify-content: flex-start;
}

.cu-form-group .title {
  padding-left: 30px;
  padding-right: 0;
}

.cu-bar-title {
  min-height: 50px;
}

.cu-list.menu > .cu-item-error {
  justify-content: flex-start;
}


.cu-form-group + .cu-form-group {
  border-top: none;
}

.enhanced-nav {
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 60px; /* 更宽松的高度 */
  padding: 0 20px;
  background: linear-gradient(90deg, #f8f9fa, #ffffff); /* 添加背景渐变效果 */
  box-shadow: 0 -2px 5px rgba(0, 0, 0, 0.1); /* 提升层次感 */
  border-top: 1px solid #e0e0e0;
}

.nav-button {
  flex: 1;
  text-align: center;
  padding: 10px 0;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  border-radius: 10px; /* 按钮更圆滑 */
  transition: all 0.3s ease;
}

.nav-button.disabled {
  pointer-events: none; /* 禁用点击 */
}

.nav-button:not(.disabled):hover {
//background-color: #e9f5ff; /* 鼠标悬停背景 */
}

.icon {
  font-size: 24px;
  margin-bottom: 4px;
}

.label {
  font-size: 14px;
}

.text-blue {
  color: #007bff; /* 活跃状态的蓝色 */
}

.text-gray {
  color: #c0c0c0; /* 禁用状态的灰色 */
}

.question-description {
  display: flex;
  align-items: center;
  padding-left: 6px;
  margin-bottom: 10px; /* 给问题之间增加一些间距 */
}

.tag {
  padding: 4px 8px;
  border-radius: 12px;
  font-size: 12px;
  font-weight: bold;
  color: #fff;
  text-align: center;
  margin-right: 8px; /* 标签与问题文本之间的间距 */
}

.tag-single {
  background-color: #007bff; /* 蓝色标识单选题 */
}

.tag-question {
  background-color: #28a745; /* 绿色标识问答题 */
}

.problem-text {
  font-size: 14px;
  line-height: 1.6;
  color: #333; /* 深灰色的文字颜色 */
  word-break: break-word; /* 允许长文本换行 */
}


.submit-container {
  position: fixed;
  bottom: 20px;
  left: 50%;
  transform: translateX(-50%);
  z-index: 1000;
}

.slider-container {
  padding: 10px 0;
}

.slider-labels {
  display: flex;
  justify-content: space-between;
  margin-top: 10px;
}

.slider-label {
  font-size: 14px;
  color: #888;
  text-align: center;
  flex: 1;
  transition: color 0.3s;
}

.slider-label.active {
  color: #ff7e5f;
  font-weight: bold;
}


</style>
