package com.ruoyi.system.service.impl;


import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.domain.SysFilePreview;
import com.ruoyi.system.mapper.SysFilePreviewMapper;
import com.ruoyi.system.service.ISysFilePreviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * previewService业务层处理
 *
 * @author ruoyi
 * @date 2021-10-29
 */
@Service
public class SysFilePreviewServiceImpl implements ISysFilePreviewService
{
    @Autowired
    private SysFilePreviewMapper sysFilePreviewMapper;

    /**
     * 查询preview
     *
     * @param id previewID
     * @return preview
     */
    @Override
    public SysFilePreview selectBusFilePreviewById(Long id)
    {
        return sysFilePreviewMapper.selectBusFilePreviewById(id);
    }

    /**
     * 查询preview列表
     *
     * @param sysFilePreview preview
     * @return preview
     */
    @Override
    public List<SysFilePreview> selectBusFilePreviewList(SysFilePreview sysFilePreview)
    {
        return sysFilePreviewMapper.selectBusFilePreviewList(sysFilePreview);
    }

    /**
     * 新增preview
     *
     * @param sysFilePreview preview
     * @return 结果
     */
    @Override
    public int insertBusFilePreview(SysFilePreview sysFilePreview)
    {
        sysFilePreview.setCreateTime(DateUtils.getNowDate());
        sysFilePreview.setCreateBy(String.valueOf(SecurityUtils.getUsername()));// 创建人
        return sysFilePreviewMapper.insertBusFilePreview(sysFilePreview);
    }

    /**
     * 修改preview
     *
     * @param sysFilePreview preview
     * @return 结果
     */
    @Override
    public int updateBusFilePreview(SysFilePreview sysFilePreview)
    {
        sysFilePreview.setUpdateTime(DateUtils.getNowDate());
        sysFilePreview.setUpdateBy(String.valueOf(SecurityUtils.getUsername()));// 创建人
        return sysFilePreviewMapper.updateBusFilePreview(sysFilePreview);
    }

    /**
     * 批量删除preview
     *
     * @param ids 需要删除的previewID
     * @return 结果
     */
    @Override
    public int deleteBusFilePreviewByIds(Long[] ids)
    {
        return sysFilePreviewMapper.deleteBusFilePreviewByIds(ids);
    }

    /**
     * 删除preview信息
     *
     * @param id previewID
     * @return 结果
     */
    @Override
    public int deleteBusFilePreviewById(Long id)
    {
        return sysFilePreviewMapper.deleteBusFilePreviewById(id);
    }

    @Override
    public SysFilePreview selectBusFilePreviewByIds(Long ids) {
        return sysFilePreviewMapper.selectBusFilePreviewByIds(ids);
    }

    /**
     * 查询不同类型的文档数量
     * @return
     */
    @Override
    public List<Map<String, Object>> getDocumentCount() {
        return sysFilePreviewMapper.getDocumentCount();
    }
}
