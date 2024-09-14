package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.QuestAnswer;

/**
 * 回答表Service接口
 * 
 * @author MCL
 * @date 2024-09-14
 */
public interface IQuestAnswerService 
{
    /**
     * 查询回答表
     * 
     * @param id 回答表主键
     * @return 回答表
     */
    public QuestAnswer selectQuestAnswerById(Long id);

    /**
     * 查询回答表列表
     * 
     * @param questAnswer 回答表
     * @return 回答表集合
     */
    public List<QuestAnswer> selectQuestAnswerList(QuestAnswer questAnswer);

    /**
     * 新增回答表
     * 
     * @param questAnswer 回答表
     * @return 结果
     */
    public int insertQuestAnswer(QuestAnswer questAnswer);

    /**
     * 修改回答表
     * 
     * @param questAnswer 回答表
     * @return 结果
     */
    public int updateQuestAnswer(QuestAnswer questAnswer);

    /**
     * 批量删除回答表
     * 
     * @param ids 需要删除的回答表主键集合
     * @return 结果
     */
    public int deleteQuestAnswerByIds(Long[] ids);

    /**
     * 删除回答表信息
     * 
     * @param id 回答表主键
     * @return 结果
     */
    public int deleteQuestAnswerById(Long id);
}
