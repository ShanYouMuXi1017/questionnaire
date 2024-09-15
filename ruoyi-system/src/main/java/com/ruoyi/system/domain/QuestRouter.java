package com.ruoyi.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 路线问卷对象 quest_router
 * 
 * @author MCL
 * @date 2024-09-14
 */
public class QuestRouter extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 问卷ID */
    private Long routerId;

    /** 起始位置名称 */
    @Excel(name = "起始位置名称")
    private String beginName;

    /** 终点位置名称 */
    @Excel(name = "终点位置名称")
    private String endName;

    /** 状态 */
    @Excel(name = "状态")
    private Long status;

    /** 图片路径 */
    @Excel(name = "图片路径")
    private String imageUrl;

    /** 总分 */
    @Excel(name = "总分")
    private Long core;

    /** 删除标记 */
    private Long flag;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createDate;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updateDate;

    public void setRouterId(Long routerId) 
    {
        this.routerId = routerId;
    }

    public Long getRouterId() 
    {
        return routerId;
    }
    public void setBeginName(String beginName) 
    {
        this.beginName = beginName;
    }

    public String getBeginName() 
    {
        return beginName;
    }
    public void setEndName(String endName) 
    {
        this.endName = endName;
    }

    public String getEndName() 
    {
        return endName;
    }
    public void setStatus(Long status) 
    {
        this.status = status;
    }

    public Long getStatus() 
    {
        return status;
    }
    public void setImageUrl(String imageUrl) 
    {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() 
    {
        return imageUrl;
    }
    public void setCore(Long core) 
    {
        this.core = core;
    }

    public Long getCore() 
    {
        return core;
    }
    public void setFlag(Long flag) 
    {
        this.flag = flag;
    }

    public Long getFlag() 
    {
        return flag;
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
            .append("routerId", getRouterId())
            .append("beginName", getBeginName())
            .append("endName", getEndName())
            .append("status", getStatus())
            .append("imageUrl", getImageUrl())
            .append("core", getCore())
            .append("flag", getFlag())
            .append("createDate", getCreateDate())
            .append("updateDate", getUpdateDate())
            .toString();
    }
}
