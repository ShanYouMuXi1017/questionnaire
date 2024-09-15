package com.ruoyi.web.controller.questionnaire;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.QuestQuestion;
import com.ruoyi.system.service.IQuestQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 问卷问题Controller
 * 
 * @author MCL
 * @date 2024-09-14
 */
@RestController
@RequestMapping("/questionnaire/question")
public class QuestQuestionController extends BaseController
{
    @Autowired
    private IQuestQuestionService questQuestionService;

    /**
     * 查询问卷问题列表
     */
    @PreAuthorize("@ss.hasPermi('questionnaire:question:list')")
    @GetMapping("/list")
    public TableDataInfo list(QuestQuestion questQuestion)
    {
        startPage();
        List<QuestQuestion> list = questQuestionService.selectQuestQuestionList(questQuestion);
        return getDataTable(list);
    }

    /**
     * 导出问卷问题列表
     */
    @PreAuthorize("@ss.hasPermi('questionnaire:question:export')")
    @Log(title = "问卷问题", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, QuestQuestion questQuestion)
    {
        List<QuestQuestion> list = questQuestionService.selectQuestQuestionList(questQuestion);
        ExcelUtil<QuestQuestion> util = new ExcelUtil<QuestQuestion>(QuestQuestion.class);
        util.exportExcel(response, list, "问卷问题数据");
    }

    /**
     * 获取问卷问题详细信息
     */
    @PreAuthorize("@ss.hasPermi('questionnaire:question:query')")
    @GetMapping(value = "/{issueId}")
    public AjaxResult getInfo(@PathVariable("issueId") Long issueId)
    {
        return success(questQuestionService.selectQuestQuestionByIssueId(issueId));
    }

    /**
     * 新增问卷问题
     */
    @PreAuthorize("@ss.hasPermi('questionnaire:question:add')")
    @Log(title = "问卷问题", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody QuestQuestion questQuestion)
    {
        return toAjax(questQuestionService.insertQuestQuestion(questQuestion));
    }

    /**
     * 修改问卷问题
     */
    @PreAuthorize("@ss.hasPermi('questionnaire:question:edit')")
    @Log(title = "问卷问题", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody QuestQuestion questQuestion)
    {
        return toAjax(questQuestionService.updateQuestQuestion(questQuestion));
    }

    /**
     * 删除问卷问题
     */
    @PreAuthorize("@ss.hasPermi('questionnaire:question:remove')")
    @Log(title = "问卷问题", businessType = BusinessType.DELETE)
	@DeleteMapping("/{issueIds}")
    public AjaxResult remove(@PathVariable Long[] issueIds)
    {
        return toAjax(questQuestionService.deleteQuestQuestionByIssueIds(issueIds));
    }
}
