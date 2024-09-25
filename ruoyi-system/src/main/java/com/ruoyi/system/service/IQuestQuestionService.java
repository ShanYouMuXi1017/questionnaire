package com.ruoyi.system.service;

import com.ruoyi.system.domain.QuestQuestion;

import java.util.List;

/**
 * 问卷问题Service接口
 * 
 * @author MCL
 * @date 2024-09-14
 */
public interface IQuestQuestionService 
{
    /**
     * 查询问卷问题
     * 
     * @param issueId 问卷问题主键
     * @return 问卷问题
     */
    public QuestQuestion selectQuestQuestionByIssueId(Long issueId);

    /**
     * 查询问卷问题列表
     * 
     * @param questQuestion 问卷问题
     * @return 问卷问题集合
     */
    public List<QuestQuestion> selectQuestQuestionList(QuestQuestion questQuestion);

    /**
     * 新增问卷问题
     * 
     * @param questQuestion 问卷问题
     * @return 结果
     */
    public int insertQuestQuestion(QuestQuestion questQuestion);

    /**
     * 修改问卷问题
     * 
     * @param questQuestion 问卷问题
     * @return 结果
     */
    public int updateQuestQuestion(QuestQuestion questQuestion);

    /**
     * 批量删除问卷问题
     * 
     * @param issueIds 需要删除的问卷问题主键集合
     * @return 结果
     */
    public int deleteQuestQuestionByIssueIds(Long[] issueIds);

    /**
     * 删除问卷问题信息
     * 
     * @param issueId 问卷问题主键
     * @return 结果
     */
    public int deleteQuestQuestionByIssueId(Long issueId);
}
