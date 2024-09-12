package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.SysBanner;

/**
 * 轮播图Mapper接口
 * 
 * @author ruoyi
 * @date 2024-04-07
 */
public interface SysBannerMapper 
{
    /**
     * 查询轮播图
     * 
     * @param id 轮播图主键
     * @return 轮播图
     */
    public SysBanner selectSysBannerById(Long id);

    /**
     * 查询轮播图列表
     * 
     * @param sysBanner 轮播图
     * @return 轮播图集合
     */
    public List<SysBanner> selectSysBannerList(SysBanner sysBanner);

    /**
     * 新增轮播图
     * 
     * @param sysBanner 轮播图
     * @return 结果
     */
    public int insertSysBanner(SysBanner sysBanner);

    /**
     * 修改轮播图
     * 
     * @param sysBanner 轮播图
     * @return 结果
     */
    public int updateSysBanner(SysBanner sysBanner);

    /**
     * 删除轮播图
     * 
     * @param id 轮播图主键
     * @return 结果
     */
    public int deleteSysBannerById(Long id);

    /**
     * 批量删除轮播图
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysBannerByIds(Long[] ids);
}
