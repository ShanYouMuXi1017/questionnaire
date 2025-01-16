package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.QuestRouter;
import com.ruoyi.system.domain.vo.QuestAnswerSheetVo;

/**
 * 路线问卷Service接口
 * 
 * @author MCL
 * @date 2024-09-21
 */
public interface IQuestRouterService 
{
    /**
     * 查询路线问卷
     * 
     * @param routerId 路线问卷主键
     * @return 路线问卷
     */
    public QuestRouter selectQuestRouterByRouterId(Long routerId);

    /**
     * 查询路线问卷列表
     * 
     * @param questRouter 路线问卷
     * @return 路线问卷集合
     */
    public List<QuestRouter> selectQuestRouterList(QuestRouter questRouter);

    /**
     * 新增路线问卷
     * 
     * @param questRouter 路线问卷
     * @return 结果
     */
    public int insertQuestRouter(QuestRouter questRouter);

    /**
     * 修改路线问卷
     * 
     * @param questRouter 路线问卷
     * @return 结果
     */
    public int updateQuestRouter(QuestRouter questRouter);

    /**
     * 批量删除路线问卷
     * 
     * @param routerIds 需要删除的路线问卷主键集合
     * @return 结果
     */
    public int deleteQuestRouterByRouterIds(Long[] routerIds);

    /**
     * 删除路线问卷信息
     * 
     * @param routerId 路线问卷主键
     * @return 结果
     */
    public int deleteQuestRouterByRouterId(Long routerId);

    /**
     * 计算每份每人答卷的总分
     *
     * @param questAnswerSheet 答卷数据展示
     * @return 路线问卷集合
     */
    public List<QuestAnswerSheetVo> sumEveryoneAnswerRouter(QuestAnswerSheetVo questAnswerSheet);


    /**
     * 计算每份问卷的答卷数
     *
     * @param questAnswerSheet 答卷数据展示
     * @return 路线问卷集合
     */
    public List<QuestAnswerSheetVo> countRouter(QuestAnswerSheetVo questAnswerSheet);

    /**
     * 计算每份答卷的平均分
     *
     * @param questAnswerSheet 答卷数据展示
     * @return 路线问卷集合
     */
    public List<QuestAnswerSheetVo> avgEveryoneAnswerRouter(QuestAnswerSheetVo questAnswerSheet);

    /**
     * 计算每份答卷里的小题的平均分
     *
     * @param questAnswerSheet 答卷数据展示
     * @return 路线问卷集合
     */
    public List<QuestAnswerSheetVo> avgEveryAnswerRouter(QuestAnswerSheetVo questAnswerSheet);

    /**
     * 计算总的平均分
     *
     * @return 路线问卷集合
     */
    public QuestAnswerSheetVo avgAnswerRouter();

    /**
     * 展示每份每人的答卷
     *
     * @param questAnswerSheet 答卷数据展示
     * @return 路线问卷集合
     */
    public List<QuestAnswerSheetVo> selectEveryoneAnswerRouter(QuestAnswerSheetVo questAnswerSheet);

    /**
     * 展示答题详情
     *
     * @param questAnswerSheet 展示答题详情
     * @return 展示答题详情
     */
    public List<QuestAnswerSheetVo> listAnswerDetails(QuestAnswerSheetVo questAnswerSheet);

}
