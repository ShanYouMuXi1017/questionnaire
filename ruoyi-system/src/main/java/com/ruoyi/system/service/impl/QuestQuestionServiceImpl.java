package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.QuestQuestion;
import com.ruoyi.system.mapper.QuestQuestionMapper;
import com.ruoyi.system.service.IQuestQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 问卷问题Service业务层处理
 * 
 * @author MCL
 * @date 2024-09-14
 */
@Service
public class QuestQuestionServiceImpl implements IQuestQuestionService 
{
    @Autowired
    private QuestQuestionMapper questQuestionMapper;

    /**
     * 查询问卷问题
     * 
     * @param issueId 问卷问题主键
     * @return 问卷问题
     */
    @Override
    public QuestQuestion selectQuestQuestionByIssueId(Long issueId)
    {
        return questQuestionMapper.selectQuestQuestionByIssueId(issueId);
    }

    /**
     * 查询问卷问题列表
     * 
     * @param questQuestion 问卷问题
     * @return 问卷问题
     */
    @Override
    public List<QuestQuestion> selectQuestQuestionList(QuestQuestion questQuestion)
    {
        return questQuestionMapper.selectQuestQuestionList(questQuestion);
    }

    /**
     * 新增问卷问题
     * 
     * @param questQuestion 问卷问题
     * @return 结果
     */
    @Override
    public int insertQuestQuestion(QuestQuestion questQuestion)
    {
        return questQuestionMapper.insertQuestQuestion(questQuestion);
    }

    /**
     * 修改问卷问题
     * 
     * @param questQuestion 问卷问题
     * @return 结果
     */
    @Override
    public int updateQuestQuestion(QuestQuestion questQuestion)
    {
        return questQuestionMapper.updateQuestQuestion(questQuestion);
    }

    /**
     * 批量删除问卷问题
     * 
     * @param issueIds 需要删除的问卷问题主键
     * @return 结果
     */
    @Override
    public int deleteQuestQuestionByIssueIds(Long[] issueIds)
    {
        return questQuestionMapper.deleteQuestQuestionByIssueIds(issueIds);
    }

    /**
     * 删除问卷问题信息
     * 
     * @param issueId 问卷问题主键
     * @return 结果
     */
    @Override
    public int deleteQuestQuestionByIssueId(Long issueId)
    {
        return questQuestionMapper.deleteQuestQuestionByIssueId(issueId);
    }
}
