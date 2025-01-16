package com.ruoyi.system.domain.vo;

import lombok.Data;

/**
 * @ Description： 提交子项
 * @ Author： 程序员好冰
 * @ Date： 2024/12/19/17:14
 */
@Data
public class SubjectVo {
    private Long issueId; // 题目ID
    private Object answer; // 填空题为 String，单选题为 Double
    private String answerResult; // 单选题为选项内容，填空题为 null
}
