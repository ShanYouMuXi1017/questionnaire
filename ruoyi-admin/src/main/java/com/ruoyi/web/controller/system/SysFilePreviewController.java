package com.ruoyi.web.controller.system;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.pdf.FilePdfUtils;
import com.ruoyi.framework.config.ServerConfig;
import com.ruoyi.system.domain.SysFilePreview;
import com.ruoyi.system.service.ISysFilePreviewService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * previewController
 *
 * @author ruoyi
 * @date 2021-10-29
 */
@RestController
@RequestMapping("/system/preview")
public class SysFilePreviewController extends BaseController
{
    @Autowired
    private ISysFilePreviewService busFilePreviewService;


    @Value("${url.httpUrl}")
    private String httpUrl;

    /**
     * 查询preview列表
     */
    @GetMapping("/list")
    public TableDataInfo list(SysFilePreview sysFilePreview)
    {
        startPage();
        List<SysFilePreview> list = busFilePreviewService.selectBusFilePreviewList(sysFilePreview);
        return getDataTable(list);
    }

    /*
     * 查询结果返回到小程序
     * */
    @GetMapping("/getList")
    public AjaxResult getList(SysFilePreview sysFilePreview)
    {
        return AjaxResult.success(busFilePreviewService.selectBusFilePreviewList(sysFilePreview));
    }


    /**
     * 获取preview详细信息
     */

    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(busFilePreviewService.selectBusFilePreviewById(id));
    }

    /**
     * 新增preview
     */

    @Log(title = "preview", businessType = BusinessType.INSERT)
    @PreAuthorize("@ss.hasPermi('system:preview:add')")
    @PostMapping
    public AjaxResult add(@RequestBody SysFilePreview sysFilePreview) throws IOException, URISyntaxException {
        if (StringUtils.isNull(sysFilePreview.getFilename())) {
            AjaxResult.error("缺少文件名称");
        }
        if (StringUtils.isNull(sysFilePreview.getUploadpath())) {
            AjaxResult.error("缺少上传文件路径");
        }

        String substringAfter = StringUtils.substringAfterLast(sysFilePreview.getUploadpath(), ".");
        String upName = StringUtils.substringAfterLast(sysFilePreview.getUploadpath(), "/");
        sysFilePreview.setUploadfilename(upName);
        sysFilePreview.setFiletype(substringAfter); //类型
        if ("pdf".equals(substringAfter)){
            FilePdfUtils.copyFile(sysFilePreview.getUploadpath(), RuoYiConfig.getProfile());
            String pdfName = StringUtils.substringAfterLast(sysFilePreview.getUploadpath(), "/");
            sysFilePreview.setPdfpath(RuoYiConfig.getProfile()+ "/" + pdfName);
//            String previewUrl = serverConfig.getUrl()+ Constants.RESOURCE_PREFIX+File.separator+pdfName;
            String previewUrl = "";
            sysFilePreview.setPreviewUrl(previewUrl);
            sysFilePreview.setPdfname(pdfName);
            return  toAjax(busFilePreviewService.insertBusFilePreview(sysFilePreview));
        }

        File file = new File(sysFilePreview.getUploadpath());
        FileInputStream fileInputStream = new FileInputStream(file);
        String htmFileName = FilePdfUtils.file2pdf(fileInputStream, substringAfter,RuoYiConfig.getProfile());
        if(htmFileName.equals("文件转换出错，请检查OpenOffice服务是否启动。")){
            return new AjaxResult(HttpStatus.ERROR, "文件转换出错，请检查OpenOffice服务是否启动。");
        }
        String pdfPath = RuoYiConfig.getProfile()+ "/" + htmFileName;
        sysFilePreview.setPdfpath(pdfPath);
        String pdfName = StringUtils.substringAfterLast(pdfPath, "/");
        sysFilePreview.setPdfname(pdfName);
//        String previewUrl = serverConfig.getUrl()+ Constants.RESOURCE_PREFIX+File.separator+htmFileName;
        String previewUrl = "";
        sysFilePreview.setPreviewUrl(previewUrl);
        return toAjax(busFilePreviewService.insertBusFilePreview(sysFilePreview));
    }

    @GetMapping("/getUrl")
    public String getUrl(){
        return httpUrl;
    }


    /**
     * 修改preview
     */
    @Log(title = "preview", businessType = BusinessType.UPDATE)
    @PreAuthorize("@ss.hasPermi('system:preview:edit')")
    @PutMapping()
    public AjaxResult edit(@RequestBody SysFilePreview[] editSysFilePreview) throws IOException {
        SysFilePreview sysFilePreview =  editSysFilePreview[0];//获取上传后的文件
        SysFilePreview oldSysFilePreview = editSysFilePreview[1];//获取上传前的文件

        if (StringUtils.isNull(sysFilePreview.getFilename())) {
            AjaxResult.error("缺少文件名称");
        }
        if (StringUtils.isNull(sysFilePreview.getUploadpath())) {
            AjaxResult.error("缺少上传文件路径");
        }

        String substringAfter = StringUtils.substringAfterLast(sysFilePreview.getUploadpath(), ".");
        String upName = StringUtils.substringAfterLast(sysFilePreview.getUploadpath(), "/");
        sysFilePreview.setUploadfilename(upName);
        sysFilePreview.setFiletype(substringAfter); //类型
        if ("pdf".equals(substringAfter)){

            FilePdfUtils.copyFile(sysFilePreview.getUploadpath(), RuoYiConfig.getProfile());
            String pdfName = StringUtils.substringAfterLast(sysFilePreview.getUploadpath(), "/");
            sysFilePreview.setPdfpath(RuoYiConfig.getProfile()+ "/" + pdfName);
//            String previewUrl = serverConfig.getUrl()+ Constants.RESOURCE_PREFIX+File.separator+pdfName;
            String previewUrl = "";
            sysFilePreview.setPreviewUrl(previewUrl);
            sysFilePreview.setPdfname(pdfName);

            if(!sysFilePreview.getUploadpath().equals(oldSysFilePreview.getUploadpath())){
                //删除之前上传的文件
                File file1 = new File(oldSysFilePreview.getUploadpath());
                File pdfFile = new File(oldSysFilePreview.getPdfpath());
                file1.delete();
                pdfFile.delete();
            }
            return  toAjax(busFilePreviewService.updateBusFilePreview(sysFilePreview));
        }

        File file = new File(sysFilePreview.getUploadpath());
        FileInputStream fileInputStream = new FileInputStream(file);
        String htmFileName = FilePdfUtils.file2pdf(fileInputStream, substringAfter,RuoYiConfig.getProfile());
        if(htmFileName.equals("文件转换出错，请检查OpenOffice服务是否启动。")){
            return new AjaxResult(HttpStatus.ERROR, "文件转换出错，请检查OpenOffice服务是否启动。");
        }
        String pdfPath = RuoYiConfig.getProfile()+ "/" + htmFileName;
        sysFilePreview.setPdfpath(pdfPath);
        String pdfName = StringUtils.substringAfterLast(pdfPath, "/");
        sysFilePreview.setPdfname(pdfName);
//        String previewUrl = serverConfig.getUrl()+ Constants.RESOURCE_PREFIX+File.separator+htmFileName;
        String previewUrl="";
        sysFilePreview.setPreviewUrl(previewUrl);

        if (!sysFilePreview.getUploadpath().equals(oldSysFilePreview.getUploadpath())){
            //删除之前上传的文件
            File file1 = new File(oldSysFilePreview.getUploadpath());
            File pdfFile = new File(oldSysFilePreview.getPdfpath());
            file1.delete();
            pdfFile.delete();
        }
        return toAjax(busFilePreviewService.updateBusFilePreview(sysFilePreview));
    }

    /**
     * 删除preview
     */
    @Log(title = "preview", businessType = BusinessType.DELETE)
    @PreAuthorize("@ss.hasPermi('system:preview:remove')")
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        SysFilePreview[] sysFilePreview = new SysFilePreview[10000];
        File[] files = new File[10000];
        File[] pdfFiles = new File[10000];
        for(int i =0;i < ids.length; i++){
            sysFilePreview[i] = busFilePreviewService.selectBusFilePreviewByIds(ids[i]);
            files[i] = new File(sysFilePreview[i].getUploadpath());
            pdfFiles[i] = new File(sysFilePreview[i].getPdfpath());
            files[i].delete();
            pdfFiles[i].delete();
        }
        return toAjax(busFilePreviewService.deleteBusFilePreviewByIds(ids));
    }


    @GetMapping("/pdf")
    @ApiOperation(value = "预览")
    public void prePDF(Long id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        SysFilePreview sysFilePreview = busFilePreviewService.selectBusFilePreviewById(id);
        if (StringUtils.isNotNull(sysFilePreview) && StringUtils.isNotNull(sysFilePreview.getPdfpath())) {
            File file = new File(sysFilePreview.getPdfpath());
            if (file.exists()) {
                byte[] data = null;
                try {
                    FileInputStream input = new FileInputStream(file);
                    data = new byte[input.available()];
                    input.read(data);
                    response.getOutputStream().write(data);
                    input.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (!file.exists()){
                SysFilePreview filePreview = new SysFilePreview();
                filePreview.setId(id);
                filePreview.setRemark("文件不存在");
                busFilePreviewService.updateBusFilePreview(filePreview);
            }
        }
    }

}
