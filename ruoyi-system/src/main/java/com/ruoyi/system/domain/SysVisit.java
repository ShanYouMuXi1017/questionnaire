package com.ruoyi.system.domain;

import java.text.SimpleDateFormat;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 委托拜访对象 sys_visit
 *
 * @author ruoyi
 * @date 2024-03-31
 */
public class SysVisit extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 委托拜访id */
    private Long id;

    /** 后台创建此委托事件的人的id */
    @Excel(name = "后台创建此委托事件的人的id")
    private Long creatorId;

    /** 受委托业务员id */
    @Excel(name = "受委托业务员id")
    private Long visitorId;

    /** 被拜访的客户id */
    @Excel(name = "被拜访的客户id")
    private Long customerId;

    /** 状态0表正在拜访, 1表拜访完成, 2表超时完成, 3表拜访超时 */
    @Excel(name = "状态: 0表正在拜访, 1表拜访完成, 2表超时完成, 3表拜访超时")
    private Integer visitStatus;

    /** 委托拜访的签到距离 */
    @Excel(name = "委托拜访的签到距离")
    private Integer visitRange;

    /** 委托完成时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "委托完成时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date finishTime;

    /** 委托截止时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "委托截止时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    /** 是否被删除, 0表未删除, 1表已被删除 */
    private Integer isDelete;

    /** 本次委托拜访的备注信息 */
    @Excel(name = "本次委托拜访的备注信息")
    private String visitRemark;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setCreatorId(Long creatorId)
    {
        this.creatorId = creatorId;
    }

    public Long getCreatorId()
    {
        return creatorId;
    }
    public void setVisitorId(Long visitorId)
    {
        this.visitorId = visitorId;
    }

    public Long getVisitorId()
    {
        return visitorId;
    }
    public void setCustomerId(Long customerId)
    {
        this.customerId = customerId;
    }

    public Long getCustomerId()
    {
        return customerId;
    }
    public void setVisitStatus(Integer visitStatus)
    {
        this.visitStatus = visitStatus;
    }

    public Integer getVisitRange() {
        return visitRange;
    }

    public void setVisitRange(Integer visitRange) {
        this.visitRange = visitRange;
    }

    public Integer getVisitStatus()
    {
        return visitStatus;
    }
    public void setFinishTime(Date finishTime)
    {
        this.finishTime = finishTime;
    }

    public Date getFinishTime()
    {
        return finishTime;
    }
    public void setEndTime(Date endTime)
    {
        this.endTime = endTime;
    }

    public Date getEndTime()
    {
        return endTime;
    }
    public void setIsDelete(Integer isDelete)
    {
        this.isDelete = isDelete;
    }

    public Integer getIsDelete()
    {
        return isDelete;
    }
    public void setVisitRemark(String visitRemark)
    {
        this.visitRemark = visitRemark;
    }

    public String getVisitRemark()
    {
        return visitRemark;
    }

    SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public String getEndTimeForMate(){
        if(endTime == null){
            return  null;
        }
        return sdf.format(endTime);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("creatorId", getCreatorId())
            .append("visitorId", getVisitorId())
            .append("customerId", getCustomerId())
            .append("visitStatus", getVisitStatus())
            .append("visitRange", getVisitRange())
            .append("createTime", getCreateTime())
            .append("finishTime", getFinishTime())
            .append("endTime", getEndTime())
            .append("isDelete", getIsDelete())
            .append("visitRemark", getVisitRemark())
            .toString();
    }
}
