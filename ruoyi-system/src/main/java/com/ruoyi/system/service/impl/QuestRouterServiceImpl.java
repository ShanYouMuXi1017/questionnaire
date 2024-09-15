package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.QuestRouter;
import com.ruoyi.system.mapper.QuestRouterMapper;
import com.ruoyi.system.service.IQuestRouterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 路线问卷Service业务层处理
 * 
 * @author MCL
 * @date 2024-09-14
 */
@Service
public class QuestRouterServiceImpl implements IQuestRouterService 
{
    @Autowired
    private QuestRouterMapper questRouterMapper;

    /**
     * 查询路线问卷
     * 
     * @param routerId 路线问卷主键
     * @return 路线问卷
     */
    @Override
    public QuestRouter selectQuestRouterByRouterId(Long routerId)
    {
        return questRouterMapper.selectQuestRouterByRouterId(routerId);
    }

    /**
     * 查询路线问卷列表
     * 
     * @param questRouter 路线问卷
     * @return 路线问卷
     */
    @Override
    public List<QuestRouter> selectQuestRouterList(QuestRouter questRouter)
    {
        return questRouterMapper.selectQuestRouterList(questRouter);
    }

    /**
     * 新增路线问卷
     * 
     * @param questRouter 路线问卷
     * @return 结果
     */
    @Override
    public int insertQuestRouter(QuestRouter questRouter)
    {
        return questRouterMapper.insertQuestRouter(questRouter);
    }

    /**
     * 修改路线问卷
     * 
     * @param questRouter 路线问卷
     * @return 结果
     */
    @Override
    public int updateQuestRouter(QuestRouter questRouter)
    {
        return questRouterMapper.updateQuestRouter(questRouter);
    }

    /**
     * 批量删除路线问卷
     * 
     * @param routerIds 需要删除的路线问卷主键
     * @return 结果
     */
    @Override
    public int deleteQuestRouterByRouterIds(Long[] routerIds)
    {
        return questRouterMapper.deleteQuestRouterByRouterIds(routerIds);
    }

    /**
     * 删除路线问卷信息
     * 
     * @param routerId 路线问卷主键
     * @return 结果
     */
    @Override
    public int deleteQuestRouterByRouterId(Long routerId)
    {
        return questRouterMapper.deleteQuestRouterByRouterId(routerId);
    }
}
