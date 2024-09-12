package com.ruoyi.system.mapper;



import com.ruoyi.system.domain.SysFilePreview;
import org.apache.ibatis.annotations.MapKey;

import java.util.List;
import java.util.Map;

/**
 * previewMapper接口
 *
 * @author ruoyi
 * @date 2021-10-29
 */
public interface SysFilePreviewMapper
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
     * 删除preview
     *
     * @param id previewID
     * @return 结果
     */
    public int deleteBusFilePreviewById(Long id);

    /**
     * 批量删除preview
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteBusFilePreviewByIds(Long[] ids);

    /**
     * 批量删除preview
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public SysFilePreview selectBusFilePreviewByIds(Long ids);

    /**
     * 查询不同类型的文档数量
     * @return
     */
    @MapKey("documentType")
    List<Map<String, Object>> getDocumentCount();
}
