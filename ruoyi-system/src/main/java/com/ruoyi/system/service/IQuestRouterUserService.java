package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.QuestRouterUser;

/**
 * 问卷用户关联表Service接口
 * 
 * @author MCL
 * @date 2024-09-15
 */
public interface IQuestRouterUserService 
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
     * 批量删除问卷用户关联表
     * 
     * @param ids 需要删除的问卷用户关联表主键集合
     * @return 结果
     */
    public int deleteQuestRouterUserByIds(Long[] ids);

    /**
     * 删除问卷用户关联表信息
     * 
     * @param id 问卷用户关联表主键
     * @return 结果
     */
    public int deleteQuestRouterUserById(Long id);
}
