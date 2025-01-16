package com.ruoyi.web.controller.questionnaire;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.system.domain.QuestAnswer;
import com.ruoyi.system.domain.vo.QuestAnswerSheetVo;
import com.ruoyi.web.controller.tool.excel.WritExcel;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import com.ruoyi.system.domain.QuestRouter;
import com.ruoyi.system.service.IQuestRouterService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 路线问卷Controller
 *
 * @author MCL
 * @date 2024-09-21
 */
@Slf4j
@RestController
@RequestMapping("/questionnaire/router")
public class QuestRouterController extends BaseController
{
    @Autowired
    private IQuestRouterService questRouterService;

    @Autowired
    private WritExcel writExcel;

    /**
     * 查询路线问卷列表
     */
    @PreAuthorize("@ss.hasPermi('questionnaire:router:list')")
    @GetMapping("/list")
    public TableDataInfo list(QuestRouter questRouter)
    {
        startPage();
        List<QuestRouter> list = questRouterService.selectQuestRouterList(questRouter);
        return getDataTable(list);
    }

    /**
     * 导出路线问卷列表
     */
    @PreAuthorize("@ss.hasPermi('questionnaire:router:export')")
    @Log(title = "路线问卷", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, QuestRouter questRouter)
    {
        List<QuestRouter> list = questRouterService.selectQuestRouterList(questRouter);
        ExcelUtil<QuestRouter> util = new ExcelUtil<QuestRouter>(QuestRouter.class);
        util.exportExcel(response, list, "路线问卷数据");
    }

    /**
     * 获取路线问卷详细信息
     */
    @PreAuthorize("@ss.hasPermi('questionnaire:router:query')")
    @GetMapping(value = "/{routerId}")
    public AjaxResult getInfo(@PathVariable("routerId") Long routerId)
    {
        return success(questRouterService.selectQuestRouterByRouterId(routerId));
    }

    /**
     * 新增路线问卷
     */
    @PreAuthorize("@ss.hasPermi('questionnaire:router:add')")
    @Log(title = "路线问卷", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody QuestRouter questRouter)
    {
        return toAjax(questRouterService.insertQuestRouter(questRouter));
    }

    /**
     * 修改路线问卷
     */
    @PreAuthorize("@ss.hasPermi('questionnaire:router:edit')")
    @Log(title = "路线问卷", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody QuestRouter questRouter)
    {
        return toAjax(questRouterService.updateQuestRouter(questRouter));
    }

    /**
     * 删除路线问卷
     */
    @PreAuthorize("@ss.hasPermi('questionnaire:router:remove')")
    @Log(title = "路线问卷", businessType = BusinessType.DELETE)
	@DeleteMapping("/{routerIds}")
    public AjaxResult remove(@PathVariable Long[] routerIds)
    {
        return toAjax(questRouterService.deleteQuestRouterByRouterIds(routerIds));
    }

    /**
     * 计算每份每人答卷的总分
     */
    @GetMapping("/sumEveryoneAnswerRouter")
    public TableDataInfo sumEveryoneAnswerRouter(QuestAnswerSheetVo questAnswerSheet)
    {
        List<QuestAnswerSheetVo> list = questRouterService.sumEveryoneAnswerRouter(questAnswerSheet);
        return getDataTable(list);
    }

    /**
    * 计算每份问卷的答卷数
     */
    @GetMapping("/countRouter")
    public TableDataInfo countRouter(QuestAnswerSheetVo questAnswerSheet)
    {
        List<QuestAnswerSheetVo> list = questRouterService.countRouter(questAnswerSheet);
        return getDataTable(list);
    }

    /**
     * 计算每份答卷的平均分
     */
    @GetMapping("/avgEveryoneAnswerRouter")
    public TableDataInfo avgEveryoneAnswerRouter(QuestAnswerSheetVo questAnswerSheet)
    {
        List<QuestAnswerSheetVo> list = questRouterService.avgEveryoneAnswerRouter(questAnswerSheet);
        return getDataTable(list);
    }

    /**
     * 计算每份答卷里的小题的平均分
     */
    @GetMapping("/avgEveryAnswerRouter")
    public TableDataInfo avgEveryAnswerRouter(QuestAnswerSheetVo questAnswerSheet)
    {
        List<QuestAnswerSheetVo> list = questRouterService.avgEveryAnswerRouter(questAnswerSheet);
        return getDataTable(list);
    }

    /**
     * 计算总的平均分
     */
    @GetMapping("/avgAnswerRouter")
    public QuestAnswerSheetVo avgAnswerRouter()
    {
        QuestAnswerSheetVo questAnswerSheet = questRouterService.avgAnswerRouter();
        return questAnswerSheet;
    }

    /**
     * 展示每份每人的答卷
     */
    @GetMapping("/selectEveryoneAnswerRouter")
    public TableDataInfo selectEveryoneAnswerRouter(QuestAnswerSheetVo questAnswerSheet)
    {
        List<QuestAnswerSheetVo> list = questRouterService.selectEveryoneAnswerRouter(questAnswerSheet);
        return getDataTable(list);
    }

    /**
     * 展示答题详情
     */
    @GetMapping("/listAnswerDetails")
    public TableDataInfo listAnswerDetails(QuestAnswerSheetVo questAnswerSheet)
    {
        startPage();
        List<QuestAnswerSheetVo> list = questRouterService.listAnswerDetails(questAnswerSheet);
        return getDataTable(list);
    }

    /**
     * 导出回答表列表
     */
    @Log(title = "答卷表", businessType = BusinessType.EXPORT)
    @PostMapping("/answerDetailsExport")
    public void export(HttpServletResponse response, QuestAnswerSheetVo questAnswerSheet)
    {
        List<QuestAnswerSheetVo> list = questRouterService.listAnswerDetails(questAnswerSheet);
        ExcelUtil<QuestAnswerSheetVo> util = new ExcelUtil<QuestAnswerSheetVo>(QuestAnswerSheetVo.class);
        util.exportExcel(response, list, "答卷表数据");
    }


    /**
     * 下载路线问卷汇总表
     */
    @GetMapping("/downloadExcel/{routerId}")
    public void  selectEveryoneAnswerRouter(HttpServletResponse response,@PathVariable("routerId") Long routerId ) throws IOException {

        QuestRouter router = questRouterService.selectQuestRouterByRouterId(routerId);
        String fileName = router.getRouteName() + "_路线用户问卷汇总表.xlsx";

        XSSFWorkbook workbook = null;

        try {
            // 生成 Excel 工作簿
            workbook = writExcel.writeAnswerToExcel(routerId);

            // 设置响应头，指示这是一个 Excel 文件
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=\""+fileName+"\""); // 设置文件名

            // 写入文件到输出流
            workbook.write(response.getOutputStream());
            response.flushBuffer(); // 刷新响应流

        } catch (Exception e) {
            log.error("用户问卷写入excel失败", e);
        } finally {
            if (workbook != null) {
                workbook.close();
            }
        }


    }

}
