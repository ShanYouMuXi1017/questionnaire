package com.ruoyi.system.domain;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

@Data
public class QuestQuestionFromExcel extends BaseEntity {
    @ExcelProperty(value = "id",index = 0)
    private Integer issueId;

    @ExcelProperty(value = "准则层",index = 1)
    private String questionType;

    @ExcelIgnore
    private String questionWeight;

    @ExcelIgnore
    private String professorQuestion;

    @ExcelProperty(value = "问题准则层",index = 4)
    private String problemType;

    @ExcelProperty(value = "权重值（%）",index = 5)
    private Double typeWeight;

    @ExcelProperty(value = "满分值",index = 6)
    private Double grade;

    @ExcelProperty(value = "问题类型（1代表打分题；0代表填空题）",index = 7)
    private Integer answerType;

    @ExcelProperty(value = "问卷语句",index = 8)
    private String problem;

    @ExcelProperty(value = "选项",index = 9)
    private String answerOptions;

    @ExcelIgnore
    private Integer answerScore;

    @ExcelIgnore
    private Integer answerSumScore;

}
