package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.QuestAnswer;
import com.ruoyi.system.domain.QuestParam.AnswerParam;
import com.ruoyi.system.domain.QuestWritExcel;
import com.ruoyi.system.mapper.QuestAnswerMapper;
import com.ruoyi.system.service.IQuestAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    /**
     * 根据路线id查询已经回答问卷的用户id
     * @param routerId
     * @return
     */
    @Override
    public List<Integer> selectUserId(Long routerId) {
        return questAnswerMapper.selectUserId(routerId);
    }

    /**
     * 根据userId和routerId查询回答的问卷结果
     * @param answerParam
     * @return
     */
    @Override
    public List<QuestWritExcel> selectQuestAnswerListExcel(AnswerParam answerParam) {
        if(answerParam == null|| answerParam.getUserId() == null || answerParam.getRouterId() == null){
            throw new RuntimeException("查询问卷结果时，userId和routerId不能为空");
        }
        return questAnswerMapper.getQuestAnswerListByUserIdAndRouterId(answerParam);
    }
}
