package com.ruoyi.system.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.QuestRouterUser;
import com.ruoyi.system.service.IQuestRouterUserService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 问卷用户关联表Controller
 * 
 * @author MCL
 * @date 2024-09-15
 */
@RestController
@RequestMapping("/questionnaire/user")
public class QuestRouterUserController extends BaseController
{
    @Autowired
    private IQuestRouterUserService questRouterUserService;

    /**
     * 查询问卷用户关联表列表
     */
    @PreAuthorize("@ss.hasPermi('questionnaire:user:list')")
    @GetMapping("/list")
    public TableDataInfo list(QuestRouterUser questRouterUser)
    {
        startPage();
        List<QuestRouterUser> list = questRouterUserService.selectQuestRouterUserList(questRouterUser);
        return getDataTable(list);
    }

    /**
     * 导出问卷用户关联表列表
     */
    @PreAuthorize("@ss.hasPermi('questionnaire:user:export')")
    @Log(title = "问卷用户关联表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, QuestRouterUser questRouterUser)
    {
        List<QuestRouterUser> list = questRouterUserService.selectQuestRouterUserList(questRouterUser);
        ExcelUtil<QuestRouterUser> util = new ExcelUtil<QuestRouterUser>(QuestRouterUser.class);
        util.exportExcel(response, list, "问卷用户关联表数据");
    }

    /**
     * 获取问卷用户关联表详细信息
     */
    @PreAuthorize("@ss.hasPermi('questionnaire:user:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(questRouterUserService.selectQuestRouterUserById(id));
    }

    /**
     * 新增问卷用户关联表
     */
    @PreAuthorize("@ss.hasPermi('questionnaire:user:add')")
    @Log(title = "问卷用户关联表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody QuestRouterUser questRouterUser)
    {
        return toAjax(questRouterUserService.insertQuestRouterUser(questRouterUser));
    }

    /**
     * 修改问卷用户关联表
     */
    @PreAuthorize("@ss.hasPermi('questionnaire:user:edit')")
    @Log(title = "问卷用户关联表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody QuestRouterUser questRouterUser)
    {
        return toAjax(questRouterUserService.updateQuestRouterUser(questRouterUser));
    }

    /**
     * 删除问卷用户关联表
     */
    @PreAuthorize("@ss.hasPermi('questionnaire:user:remove')")
    @Log(title = "问卷用户关联表", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(questRouterUserService.deleteQuestRouterUserByIds(ids));
    }
}
