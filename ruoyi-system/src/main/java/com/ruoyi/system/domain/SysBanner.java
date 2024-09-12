package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 轮播图对象 sys_banner
 *
 * @author ruoyi
 * @date 2024-04-07
 */
public class SysBanner extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 轮播图id */
    private Long id;

    /** 图片地址 */
    @Excel(name = "图片地址")
    private String banner;

    /** 轮播图创建或更新人 */
    @Excel(name = "轮播图创建或更新人")
    private Long creator;

    /** 轮播图类型 */
    @Excel(name = "轮播图类型")
    private String bannerType;


    public String getBannerString() {
        return bannerString;
    }

    public void setBannerString(String bannerString) {
        this.bannerString = bannerString;
    }

    /** 轮播图插文 */
    @Excel(name = "轮播图插文")
    private String bannerString;

    public SysBanner() {

    }


    public String getBannerType() {
        return bannerType;
    }

    public void setBannerType(String bannerType) {
        this.bannerType = bannerType;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setBanner(String banner)
    {
        this.banner = banner;
    }

    public String getBanner()
    {
        return banner;
    }
    public void setCreator(Long creator)
    {
        this.creator = creator;
    }

    public Long getCreator()
    {
        return creator;
    }

    @Override
    public String toString() {
        return "SysBanner{" +
                "id=" + id +
                ", banner='" + banner + '\'' +
                ", creator=" + creator +
                ", bannerType='" + bannerType + '\'' +
                ", bannerString='" + bannerString + '\'' +
                '}';
    }
}
