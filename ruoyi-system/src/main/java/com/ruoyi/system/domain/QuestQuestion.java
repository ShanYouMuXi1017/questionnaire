package com.ruoyi.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 问卷问题对象 quest_question
 * 
 * @author MCL
 * @date 2024-09-14
 */
public class QuestQuestion extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 问题ID */
    private Long issueId;


    /** 问题类型 */
    @Excel(name = "问题类型")
    private String problemType;

    /** 问题类型权重 */
    @Excel(name = "问题类型权重")
    private Long typeWeight;

    /** 问题 */
    @Excel(name = "问题")
    private String problem;

    /** 回答类型 */
    @Excel(name = "回答类型")
    private Long answerType;

    /** 评分 */
    @Excel(name = "评分")
    private Long grade;


    /** 回答选项 */
    @Excel(name = "回答选项")
    private String answerOptions;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createDate;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updateDate;

    public void setIssueId(Long issueId) 
    {
        this.issueId = issueId;
    }

    public Long getIssueId() 
    {
        return issueId;
    }

    public void setProblemType(String problemType) 
    {
        this.problemType = problemType;
    }

    public String getProblemType() 
    {
        return problemType;
    }
    public void setTypeWeight(Long typeWeight) 
    {
        this.typeWeight = typeWeight;
    }

    public Long getTypeWeight() 
    {
        return typeWeight;
    }
    public void setProblem(String problem) 
    {
        this.problem = problem;
    }

    public String getProblem() 
    {
        return problem;
    }
    public void setAnswerType(Long answerType) 
    {
        this.answerType = answerType;
    }

    public Long getAnswerType() 
    {
        return answerType;
    }
    public void setGrade(Long grade)
    {
        this.grade = grade;
    }

    public Long getGrade()
    {
        return grade;
    }
    public void setCreateDate(Date createDate) 
    {
        this.createDate = createDate;
    }

    public String getAnswerOptions() {return answerOptions; }
    public void setAnswerOptions(String answerOptions) {this.answerOptions = answerOptions; }

    public Date getCreateDate() 
    {
        return createDate;
    }
    public void setUpdateDate(Date updateDate) 
    {
        this.updateDate = updateDate;
    }

    public Date getUpdateDate() 
    {
        return updateDate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("issueId", getIssueId())
            .append("problemType", getProblemType())
            .append("typeWeight", getTypeWeight())
            .append("problem", getProblem())
            .append("answerType", getAnswerType())
            .append("answerOptions", getAnswerOptions())
            .append("grade", getGrade())
            .append("createDate", getCreateDate())
            .append("updateDate", getUpdateDate())
            .toString();
    }
}
