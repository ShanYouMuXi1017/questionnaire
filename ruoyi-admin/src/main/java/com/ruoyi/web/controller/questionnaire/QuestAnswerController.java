package com.ruoyi.web.controller.questionnaire;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.QuestAnswer;
import com.ruoyi.system.service.IQuestAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 回答表Controller
 * 
 * @author MCL
 * @date 2024-09-14
 */
@RestController
@RequestMapping("/questionnaire/answer")
public class QuestAnswerController extends BaseController
{
    @Autowired
    private IQuestAnswerService questAnswerService;

    /**
     * 查询回答表列表
     */
    @PreAuthorize("@ss.hasPermi('questionnaire:answer:list')")
    @GetMapping("/list")
    public TableDataInfo list(QuestAnswer questAnswer)
    {
        startPage();
        List<QuestAnswer> list = questAnswerService.selectQuestAnswerList(questAnswer);
        return getDataTable(list);
    }

    /**
     * 导出回答表列表
     */
    @PreAuthorize("@ss.hasPermi('questionnaire:answer:export')")
    @Log(title = "回答表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, QuestAnswer questAnswer)
    {
        List<QuestAnswer> list = questAnswerService.selectQuestAnswerList(questAnswer);
        ExcelUtil<QuestAnswer> util = new ExcelUtil<QuestAnswer>(QuestAnswer.class);
        util.exportExcel(response, list, "回答表数据");
    }

    /**
     * 获取回答表详细信息
     */
    @PreAuthorize("@ss.hasPermi('questionnaire:answer:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(questAnswerService.selectQuestAnswerById(id));
    }

    /**
     * 新增回答表
     */
    @PreAuthorize("@ss.hasPermi('questionnaire:answer:add')")
    @Log(title = "回答表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody QuestAnswer questAnswer)
    {
        return toAjax(questAnswerService.insertQuestAnswer(questAnswer));
    }

    /**
     * 修改回答表
     */
    @PreAuthorize("@ss.hasPermi('questionnaire:answer:edit')")
    @Log(title = "回答表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody QuestAnswer questAnswer)
    {
        return toAjax(questAnswerService.updateQuestAnswer(questAnswer));
    }

    /**
     * 删除回答表
     */
    @PreAuthorize("@ss.hasPermi('questionnaire:answer:remove')")
    @Log(title = "回答表", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(questAnswerService.deleteQuestAnswerByIds(ids));
    }
}
