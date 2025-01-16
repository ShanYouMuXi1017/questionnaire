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
                    :class="[subject.answer === null ? 'line-grey' : 'bg-blue']"
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
      <swiper-item  @touchmove.stop v-for="(group, index) in groupedData" :key="index">
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

                <!-- 标签 -->
                <view class="slider-labels">
                  <view
                      v-for="(option, index) in subject.answerOptions2"
                      :key="option.id"
                      class="slider-label"
                      :class="{ active: isOptionHighlighted(subject, index) }"
                      :style="{ left: calculateDividerPosition(index, subject.answerOptions2.length) + '%' }"
                  >
                    {{ option.id }}. {{ option.content }}
                  </view>
                </view>
                  <!-- 滑动条 -->
                  <slider
                      :max="100"
                      :value="subject.answer || 0"
                      step="0.1"
                      show-value
                      @changing="handleSliderChanging(subject, $event)"
                      @change="handleSliderChange(subject, $event)"
                      active-color="#ff7e5f"
                      background-color="#e0e0e0"
                      block-size="20"
                      class="custom-slider"
                  ></slider>
                  <!-- 分段标记 -->
                  <!--<view class="slider-divider-container">-->
                  <!--  <view-->
                  <!--      v-for="(option, index) in subject.answerOptions2"-->
                  <!--      :key="option.id"-->
                  <!--      class="slider-divider"-->
                  <!--      :style="{ left: calculateDividerPosition(index, subject.answerOptions2.length) + '%' }"-->
                  <!--  ></view>-->
                  <!--</view>-->


              </view>
            </view>

            <!-- 问答题 -->
            <view v-else-if="subject.answerType === 0">
              <view class="cu-form-group solid margin">
                <textarea maxlength="-1" @blur="textInput(subject,$event)" v-model="subject.answer"
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
          {{ groupIndex === groupedData.length - 1 ? '交卷' : '下一组' }}
        </view>
      </view>


    </view>
  </view>
</template>

<script>
import {getQuestSheet, submitTo} from "api/system/user";

export default {
  data() {
    return {
      routerId: null,  // 存储传递的 route
      groupedData: [], // 按 problemType 分组的数据
      flattenedSubjects: [], // 扁平化后的所有题目，用于答题卡
      groupIndex: 0, // 当前组索引
      groupedSubjects: {}, // 按 problemType 分组的题目
      swiperHeight: "800px", // 滑屏高度
      swiperHeight2: "800px", // 答题卡高度
      modalCard: null, // 答题卡弹窗控制
      currentGroupType: "景观质量因素", // 当前组的问题类型
    };
  },
  created() {
    this.fetchQuestionSheet();
  },
  onLoad(options) {
    const routeStr = options.routerId;
    if (routeStr) {
      this.routerId = Number(routeStr);
    }
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
    handleSliderChanging(subject, event) {
      const sliderValue = event.detail.value; // 滑块实时位置
      subject.answer = sliderValue; // 实时更新滑块值，不锁定到选项
    },
    handleSliderChange(subject, event) {
      const sliderValue = event.detail.value; // 滑块最终位置
      subject.answer = sliderValue; // 保存滑块最终位置
    },
    isOptionHighlighted(subject, index) {
      const sliderValue = subject.answer; // 当前滑块值
      if (sliderValue === null || sliderValue === undefined) {
        return false; // 如果未选择任何选项，则不高亮
      }
      const totalOptions = subject.answerOptions2.length; // 选项总数
      const optionStart = (index / totalOptions) * 100; // 选项起始范围
      const optionEnd = ((index + 1) / totalOptions) * 100; // 选项结束范围
      return sliderValue >= optionStart && sliderValue < optionEnd; // 判断滑块是否处于选项范围内
    },
    calculateDividerPosition(index, totalOptions) {
      // 计算分割线和选项标签的位置，范围是 0% 到 100%
      return (index / (totalOptions - 1)) * 100;
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
        console.log(res);
        this.groupedData = res.data; // 按 problemType 分组的数据
        this.flattenedSubjects = this.groupedData.reduce(
            (acc, group) => acc.concat(group.subjects),
            []
        );
        // 初始化所有题目时，确保 `answer` 默认为 `null`
        this.flattenedSubjects.forEach(subject => {
          subject.answer = null; // 确保未选中任何值
        });
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
        return subject.answer !== null && subject.answer !== '';
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
      const userId = this.$store.state.user.userId; // 获取用户ID
      const routerId = this.routerId; // 获取路由ID
      const processedSubjects = this.flattenedSubjects.map(subject => {
        const isFillInTheBlank = subject.answerType === 0; // 判断是否为填空题

        if (isFillInTheBlank) {
          // 填空题处理
          return {
            issueId: subject.issueId, // 返回题目ID
            answer: subject.answer, // 验证并存储填空题答案
            answerResult: null // 填空题没有选项结果
          };
        } else {
          // 单选题处理
          const originalAnswer = subject.answer && !isNaN(Number(subject.answer)) ? Number(subject.answer) : 0; // 验证 answer 是否为数字
          const grade = subject.grade || 1; // 获取题目分值，默认为 1

          // 计算换算后的 answer
          const calculatedAnswer = Math.round((originalAnswer / 100) * grade * 100) / 100;

          // 根据 answer 比例计算对应选项内容
          let calculatedAnswerResult = null;
          if (subject.answerOptions2 && subject.answerOptions2.length > 0) {
            const totalOptions = subject.answerOptions2.length; // 获取选项数量
            const selectedIndex = Math.min(
                Math.floor((originalAnswer / 100) * totalOptions),
                totalOptions - 1
            ); // 根据 answer 计算选项索引
            calculatedAnswerResult = subject.answerOptions2[selectedIndex]?.content || null;
          }

          return {
            issueId: subject.issueId, // 返回题目ID
            answer: calculatedAnswer, // 换算后的 answer
            answerResult: calculatedAnswerResult // 计算出的 answerResult
          };
        }
      });
      // 提交数据
      submitTo(userId, routerId, processedSubjects)
          .then(res => {
            uni.showToast({
              title: '提交成功！',
              icon: 'success',
              duration: 2000
            });
            setTimeout(() => {
              uni.switchTab({
                url: '/pages/index'
              }).then(res=>{
                uni.showToast({
                  title: '请再打一条吧！',
                  icon: 'success',
                  duration: 2000
                });
              });
            }, 2000);
          })
          .catch(error => {
            console.error("提交失败：", error);
            uni.showToast({
              title: '提交失败，请重试',
              icon: 'none',
              duration: 2000
            });
          });
    }
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

