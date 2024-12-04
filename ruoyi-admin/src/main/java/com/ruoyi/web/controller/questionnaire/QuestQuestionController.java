package com.ruoyi.web.controller.questionnaire;

import com.alibaba.excel.EasyExcel;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.excel.GeneralListener;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.QuestQuestion;
import com.ruoyi.system.domain.QuestQuestionFromExcel;
import com.ruoyi.system.service.IQuestQuestionService;
import com.ruoyi.web.controller.common.utils.FindFile;
import com.ruoyi.web.controller.common.utils.UploadFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Objects;

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

    @Value("${ruoyi.profile}")
    private String profile;

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

    @PostMapping( "/importQuestion")
    public AjaxResult add(MultipartFile file) throws Exception {
        if (file.isEmpty()){
            return AjaxResult.error("上传文件不能为空");
        }
        AjaxResult ajaxResult = UploadFile.uploadFileWithAbsolutePath(file);

        //      使用实现ReadListener的方式
//       建立一个通用的读取监听器，在该监听器读取到数据，并使用List保存，读取完成之后，使用getList()方法获得
        GeneralListener<QuestQuestionFromExcel> generalListener = new GeneralListener<>();
//        执行Excel的读取，.read()方法需要传入三个参数：1、文件。2、实体类。3、监听器。
        File file1 = new File(ajaxResult.get("uploadpath").toString());
        FileInputStream fileInputStream = new FileInputStream(file1);

        EasyExcel.read(fileInputStream,QuestQuestionFromExcel.class ,generalListener).sheet().doRead();
//        获得监听器读取的数据列表
        List<QuestQuestionFromExcel> courseInfoList = generalListener.getList();

        int i = questQuestionService.UpdateQuestQuestion(courseInfoList);
        return AjaxResult.success();
    }


    /**
     * 获取模板excel文件路径
     * @return
     * @throws Exception
     */
    @GetMapping("/getFilePath")
    public AjaxResult getFilePath() throws Exception {

        //获取当前文件所在的路径
        String localPath = Objects.requireNonNull(this.getClass().getResource("")).getPath();
        String filePath = profile + "/问卷问题录入表.xlsx";

        if (localPath != null) {
            return AjaxResult.success("找到上传的文件",filePath);
        } else {
            // 如果没有找到最新的日期目录
            return AjaxResult.error("No Excel files found in the latest directory.",null);
        }
    }
}
