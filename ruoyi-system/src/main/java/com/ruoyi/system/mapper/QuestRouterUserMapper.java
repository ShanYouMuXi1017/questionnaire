package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.QuestRouterUser;

/**
 * 问卷用户关联表Mapper接口
 * 
 * @author MCL
 * @date 2024-09-15
 */
public interface QuestRouterUserMapper 
{
    /**
     * 查询问卷用户关联表
     * 
     * @param id 问卷用户关联表主键
     * @return 问卷用户关联表
     */
    public QuestRouterUser selectQuestRouterUserById(Long id);

    /**
     * 查询问卷用户关联表列表
     * 
     * @param questRouterUser 问卷用户关联表
     * @return 问卷用户关联表集合
     */
    public List<QuestRouterUser> selectQuestRouterUserList(QuestRouterUser questRouterUser);

    /**
     * 新增问卷用户关联表
     * 
     * @param questRouterUser 问卷用户关联表
     * @return 结果
     */
    public int insertQuestRouterUser(QuestRouterUser questRouterUser);

    /**
     * 修改问卷用户关联表
     * 
     * @param questRouterUser 问卷用户关联表
     * @return 结果
     */
    public int updateQuestRouterUser(QuestRouterUser questRouterUser);

    /**
     * 删除问卷用户关联表
     * 
     * @param id 问卷用户关联表主键
     * @return 结果
     */
    public int deleteQuestRouterUserById(Long id);

    /**
     * 批量删除问卷用户关联表
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteQuestRouterUserByIds(Long[] ids);
}
