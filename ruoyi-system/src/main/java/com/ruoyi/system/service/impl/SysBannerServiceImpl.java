package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.SysBannerMapper;
import com.ruoyi.system.domain.SysBanner;
import com.ruoyi.system.service.ISysBannerService;

/**
 * 轮播图Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-04-07
 */
@Service
public class SysBannerServiceImpl implements ISysBannerService 
{
    @Autowired
    private SysBannerMapper sysBannerMapper;

    /**
     * 查询轮播图
     * 
     * @param id 轮播图主键
     * @return 轮播图
     */
    @Override
    public SysBanner selectSysBannerById(Long id)
    {
        return sysBannerMapper.selectSysBannerById(id);
    }

    /**
     * 查询轮播图列表
     * 
     * @param sysBanner 轮播图
     * @return 轮播图
     */
    @Override
    public List<SysBanner> selectSysBannerList(SysBanner sysBanner)
    {
        return sysBannerMapper.selectSysBannerList(sysBanner);
    }

    /**
     * 新增轮播图
     * 
     * @param sysBanner 轮播图
     * @return 结果
     */
    @Override
    public int insertSysBanner(SysBanner sysBanner)
    {
        return sysBannerMapper.insertSysBanner(sysBanner);
    }

    /**
     * 修改轮播图
     * 
     * @param sysBanner 轮播图
     * @return 结果
     */
    @Override
    public int updateSysBanner(SysBanner sysBanner)
    {
        sysBanner.setUpdateTime(DateUtils.getNowDate());
        return sysBannerMapper.updateSysBanner(sysBanner);
    }

    /**
     * 批量删除轮播图
     * 
     * @param ids 需要删除的轮播图主键
     * @return 结果
     */
    @Override
    public int deleteSysBannerByIds(Long[] ids)
    {
        return sysBannerMapper.deleteSysBannerByIds(ids);
    }

    /**
     * 删除轮播图信息
     * 
     * @param id 轮播图主键
     * @return 结果
     */
    @Override
    public int deleteSysBannerById(Long id)
    {
        return sysBannerMapper.deleteSysBannerById(id);
    }
}
