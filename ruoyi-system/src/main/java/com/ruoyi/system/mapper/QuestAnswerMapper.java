package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.QuestAnswer;

import java.util.List;

/**
 * 回答表Mapper接口
 * 
 * @author MCL
 * @date 2024-09-14
 */
public interface QuestAnswerMapper 
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
     * 删除回答表
     * 
     * @param id 回答表主键
     * @return 结果
     */
    public int deleteQuestAnswerById(Long id);

    /**
     * 批量删除回答表
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteQuestAnswerByIds(Long[] ids);
}
