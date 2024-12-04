package com.ruoyi.system.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @ Description： 小程序端 问卷表单模型
 * @ Author： 程序员好冰
 * @ Date： 2024/11/21/09:51
 */
@Data
public class QuestionSheetVo {
    /**
     * 问题ID
     */
    private Long issueId;

    /**
     * 路线ID
     */
    private Long routerId;

    /**
     * 问题类型
     */
    private String problemType;


    /**
     * 问题
     */
    private String problem;

    /**
     * 回答类型
     */
    private Integer answerType;

    /**
     * 评分
     */
    private Double grade;


    /**
     * 回答选项
     */
    private String answerOptions;

    private List<Map<String, String>> answerOptions2;

    private int answerOptionsLength;

    /**
     * 用户Id
     */
    private Long userId;

    /**
     * 评分
     */
    private String answer;

    /**
     * 回答
     */
    private String answerResult;


}
