package com.ruoyi.system.service.impl;

import java.util.List;

import com.ruoyi.system.domain.vo.QuestAnswerSheetVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.QuestRouterMapper;
import com.ruoyi.system.domain.QuestRouter;
import com.ruoyi.system.service.IQuestRouterService;

/**
 * 路线问卷Service业务层处理
 * 
 * @author MCL
 * @date 2024-09-21
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

    /**
     * 计算每份每人答卷的总分
     * @param questAnswerSheet 答卷数据展示
     * @return 计算每份每人答卷的总分
     */
    @Override
    public List<QuestAnswerSheetVo> sumEveryoneAnswerRouter(QuestAnswerSheetVo questAnswerSheet)
    {
        return questRouterMapper.sumEveryoneAnswerRouter(questAnswerSheet);
    }

    /**
     * 计算每份问卷的答卷数
     * @param questAnswerSheet 答卷数据展示
     * @return 路线问卷
     */
    @Override
    public List<QuestAnswerSheetVo> countRouter(QuestAnswerSheetVo questAnswerSheet)
    {
        return questRouterMapper.countRouter(questAnswerSheet);
    }

    /**
     * 计算每份答卷的平均分
     *
     * @param questAnswerSheet 答卷数据展示
     * @return 路线问卷
     */
    @Override
    public List<QuestAnswerSheetVo> avgEveryoneAnswerRouter(QuestAnswerSheetVo questAnswerSheet)
    {
        return questRouterMapper.avgEveryoneAnswerRouter(questAnswerSheet);
    }

    /**
     * 计算每份答卷里的小题的平均分
     *
     * @param questAnswerSheet 答卷数据展示
     * @return 路线问卷
     */
    @Override
    public List<QuestAnswerSheetVo> avgEveryAnswerRouter(QuestAnswerSheetVo questAnswerSheet)
    {
        return questRouterMapper.avgEveryAnswerRouter(questAnswerSheet);
    }

    /**
     * 计算总的平均分
     *
     * @return 路线问卷
     */
    @Override
    public QuestAnswerSheetVo avgAnswerRouter()
    {
        return questRouterMapper.avgAnswerRouter();
    }

    /**
     * 展示每份每人的答卷
     *
     * @param questAnswerSheet 答卷数据展示
     * @return 路线问卷
     */
    @Override
    public List<QuestAnswerSheetVo> selectEveryoneAnswerRouter(QuestAnswerSheetVo questAnswerSheet)
    {
        return questRouterMapper.selectEveryoneAnswerRouter(questAnswerSheet);
    }

    /**
     * 展示答题详情
     *
     * @param questAnswerSheet 展示答题详情
     * @return 展示答题详情
     */
    @Override
    public List<QuestAnswerSheetVo> listAnswerDetails(QuestAnswerSheetVo questAnswerSheet)
    {
        return questRouterMapper.listAnswerDetails(questAnswerSheet);
    }
}
