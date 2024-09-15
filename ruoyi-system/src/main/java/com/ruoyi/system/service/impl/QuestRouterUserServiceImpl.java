package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.QuestRouterUserMapper;
import com.ruoyi.system.domain.QuestRouterUser;
import com.ruoyi.system.service.IQuestRouterUserService;

/**
 * 问卷用户关联表Service业务层处理
 * 
 * @author MCL
 * @date 2024-09-15
 */
@Service
public class QuestRouterUserServiceImpl implements IQuestRouterUserService 
{
    @Autowired
    private QuestRouterUserMapper questRouterUserMapper;

    /**
     * 查询问卷用户关联表
     * 
     * @param id 问卷用户关联表主键
     * @return 问卷用户关联表
     */
    @Override
    public QuestRouterUser selectQuestRouterUserById(Long id)
    {
        return questRouterUserMapper.selectQuestRouterUserById(id);
    }

    /**
     * 查询问卷用户关联表列表
     * 
     * @param questRouterUser 问卷用户关联表
     * @return 问卷用户关联表
     */
    @Override
    public List<QuestRouterUser> selectQuestRouterUserList(QuestRouterUser questRouterUser)
    {
        return questRouterUserMapper.selectQuestRouterUserList(questRouterUser);
    }

    /**
     * 新增问卷用户关联表
     * 
     * @param questRouterUser 问卷用户关联表
     * @return 结果
     */
    @Override
    public int insertQuestRouterUser(QuestRouterUser questRouterUser)
    {
        return questRouterUserMapper.insertQuestRouterUser(questRouterUser);
    }

    /**
     * 修改问卷用户关联表
     * 
     * @param questRouterUser 问卷用户关联表
     * @return 结果
     */
    @Override
    public int updateQuestRouterUser(QuestRouterUser questRouterUser)
    {
        return questRouterUserMapper.updateQuestRouterUser(questRouterUser);
    }

    /**
     * 批量删除问卷用户关联表
     * 
     * @param ids 需要删除的问卷用户关联表主键
     * @return 结果
     */
    @Override
    public int deleteQuestRouterUserByIds(Long[] ids)
    {
        return questRouterUserMapper.deleteQuestRouterUserByIds(ids);
    }

    /**
     * 删除问卷用户关联表信息
     * 
     * @param id 问卷用户关联表主键
     * @return 结果
     */
    @Override
    public int deleteQuestRouterUserById(Long id)
    {
        return questRouterUserMapper.deleteQuestRouterUserById(id);
    }
}
