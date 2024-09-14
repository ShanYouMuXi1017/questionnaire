package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

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

    /** 问卷ID */
    @Excel(name = "问卷ID")
    private Long routerId;

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
    private Long garde;

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
    public void setRouterId(Long routerId) 
    {
        this.routerId = routerId;
    }

    public Long getRouterId() 
    {
        return routerId;
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
    public void setGarde(Long garde) 
    {
        this.garde = garde;
    }

    public Long getGarde() 
    {
        return garde;
    }
    public void setCreateDate(Date createDate) 
    {
        this.createDate = createDate;
    }

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
            .append("routerId", getRouterId())
            .append("problemType", getProblemType())
            .append("typeWeight", getTypeWeight())
            .append("problem", getProblem())
            .append("answerType", getAnswerType())
            .append("garde", getGarde())
            .append("createDate", getCreateDate())
            .append("updateDate", getUpdateDate())
            .toString();
    }
}
