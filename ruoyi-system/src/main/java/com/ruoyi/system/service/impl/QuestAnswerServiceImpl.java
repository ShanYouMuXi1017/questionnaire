package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.QuestAnswerMapper;
import com.ruoyi.system.domain.QuestAnswer;
import com.ruoyi.system.service.IQuestAnswerService;

/**
 * 回答表Service业务层处理
 * 
 * @author MCL
 * @date 2024-09-14
 */
@Service
public class QuestAnswerServiceImpl implements IQuestAnswerService 
{
    @Autowired
    private QuestAnswerMapper questAnswerMapper;

    /**
     * 查询回答表
     * 
     * @param id 回答表主键
     * @return 回答表
     */
    @Override
    public QuestAnswer selectQuestAnswerById(Long id)
    {
        return questAnswerMapper.selectQuestAnswerById(id);
    }

    /**
     * 查询回答表列表
     * 
     * @param questAnswer 回答表
     * @return 回答表
     */
    @Override
    public List<QuestAnswer> selectQuestAnswerList(QuestAnswer questAnswer)
    {
        return questAnswerMapper.selectQuestAnswerList(questAnswer);
    }

    /**
     * 新增回答表
     * 
     * @param questAnswer 回答表
     * @return 结果
     */
    @Override
    public int insertQuestAnswer(QuestAnswer questAnswer)
    {
        return questAnswerMapper.insertQuestAnswer(questAnswer);
    }

    /**
     * 修改回答表
     * 
     * @param questAnswer 回答表
     * @return 结果
     */
    @Override
    public int updateQuestAnswer(QuestAnswer questAnswer)
    {
        return questAnswerMapper.updateQuestAnswer(questAnswer);
    }

    /**
     * 批量删除回答表
     * 
     * @param ids 需要删除的回答表主键
     * @return 结果
     */
    @Override
    public int deleteQuestAnswerByIds(Long[] ids)
    {
        return questAnswerMapper.deleteQuestAnswerByIds(ids);
    }

    /**
     * 删除回答表信息
     * 
     * @param id 回答表主键
     * @return 结果
     */
    @Override
    public int deleteQuestAnswerById(Long id)
    {
        return questAnswerMapper.deleteQuestAnswerById(id);
    }
}
