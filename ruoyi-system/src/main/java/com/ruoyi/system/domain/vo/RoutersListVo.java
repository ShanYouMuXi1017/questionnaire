package com.ruoyi.system.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * @ Description： 小程序基本信息路线列表
 * @ Author： 程序员好冰
 * @ Date： 2024/11/20/10:50
 */
@Data
public class RoutersListVo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 问卷ID */
    private Long routerId;

    /** 路线名称 */
    @Excel(name = "路线名称")
    private String routeName;

    @Excel(name = "图片路径")
    private String imageUrl;

    /** 总分 */
    @Excel(name = "总分")
    private Long core;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createDate;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updateDate;

    /** 全长(千米) */
    private Double length;

    /** 爬升高度(米) */
    private Double elevation;

    /** 是否已经作答标识*/
    private int isAC;
}
