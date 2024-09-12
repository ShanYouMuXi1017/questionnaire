package com.ruoyi.system.service;


import com.ruoyi.system.domain.SysFilePreview;

import java.util.List;
import java.util.Map;

/**
 * previewService接口
 *
 * @author ruoyi
 * @date 2021-10-29
 */
public interface ISysFilePreviewService
{
    /**
     * 查询preview
     *
     * @param id previewID
     * @return preview
     */
    public SysFilePreview selectBusFilePreviewById(Long id);

    /**
     * 查询preview列表
     *
     * @param sysFilePreview preview
     * @return preview集合
     */
    public List<SysFilePreview> selectBusFilePreviewList(SysFilePreview sysFilePreview);

    /**
     * 新增preview
     *
     * @param sysFilePreview preview
     * @return 结果
     */
    public int insertBusFilePreview(SysFilePreview sysFilePreview);

    /**
     * 修改preview
     *
     * @param sysFilePreview preview
     * @return 结果
     */
    public int updateBusFilePreview(SysFilePreview sysFilePreview);

    /**
     * 批量删除preview
     *
     * @param ids 需要删除的previewID
     * @return 结果
     */
    public int deleteBusFilePreviewByIds(Long[] ids);

    /**
     * 删除preview信息
     *
     * @param id previewID
     * @return 结果
     */
    public int deleteBusFilePreviewById(Long id);

    /**
     * 批量查询preview的上传地址和pdf地址
     *
     * @param ids 需要查询的previewID
     * @return 结果
     */
    public SysFilePreview selectBusFilePreviewByIds(Long ids);

    /**
     * 查询不同类型的文档数量
     * @return
     */
    List<Map<String, Object>> getDocumentCount();
}
