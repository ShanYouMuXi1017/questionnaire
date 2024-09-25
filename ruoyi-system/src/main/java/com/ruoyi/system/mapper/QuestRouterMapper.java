package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.QuestRouter;

/**
 * 路线问卷Mapper接口
 * 
 * @author MCL
 * @date 2024-09-21
 */
public interface QuestRouterMapper 
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
     * 删除路线问卷
     * 
     * @param routerId 路线问卷主键
     * @return 结果
     */
    public int deleteQuestRouterByRouterId(Long routerId);

    /**
     * 批量删除路线问卷
     * 
     * @param routerIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteQuestRouterByRouterIds(Long[] routerIds);
}
