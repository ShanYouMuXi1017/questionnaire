package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.SysVisit;

/**
 * 委托拜访Mapper接口
 *
 * @author ruoyi
 * @date 2024-03-31
 */
public interface SysVisitMapper
{
    /**
     * 查询委托拜访
     *
     * @param id 委托拜访主键
     * @return 委托拜访
     */
    public SysVisit selectSysVisitById(Long id);

    /**
     * 查询委托拜访列表
     *
     * @param sysVisit 委托拜访
     * @return 委托拜访集合
     */
    public List<SysVisit> selectSysVisitList(SysVisit sysVisit);

    /**
     * 查询最新的一条委托拜访
     *
     * @param sysVisit 委托拜访
     * @return 委托拜访
     */
    public SysVisit selectLastSysVisit(SysVisit sysVisit);

    /**
     * 新增委托拜访
     *
     * @param sysVisit 委托拜访
     * @return 结果
     */
    public int insertSysVisit(SysVisit sysVisit);

    /**
     * 修改委托拜访
     *
     * @param sysVisit 委托拜访
     * @return 结果
     */
    public int updateSysVisit(SysVisit sysVisit);

    /**
     * 删除委托拜访
     *
     * @param id 委托拜访主键
     * @return 结果
     */
    public int deleteSysVisitById(Long id);

    /**
     * 批量删除委托拜访
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysVisitByIds(Long[] ids);

    /**
     * 批量移除委托拜访, 修改isDelete = 1
     *
     * @param ids 需要移除的数据主键集合
     * @return 结果
     */

    public int updateRemoveSysVisitByIds(Long[] ids);


    public int queryTaskById(Long id);

}
