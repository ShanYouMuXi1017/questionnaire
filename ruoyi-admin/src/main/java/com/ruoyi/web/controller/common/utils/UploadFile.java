package com.ruoyi.web.controller.common.utils;

import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.file.FileUploadUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public class UploadFile {
    public static AjaxResult uploadFileWithAbsolutePath(MultipartFile file) throws Exception
    {
        try
        {
            // 上传文件路径
            String filePath = RuoYiConfig.getUploadPath();
            // 上传并返回新文件名称
            String fileName = FileUploadUtils.uploadWithAbsolutePath(filePath, file);
            AjaxResult ajax = AjaxResult.success();
            ajax.put("uploadpath", filePath+ File.separator+fileName);
            return ajax;
        }
        catch (Exception e)
        {
            return AjaxResult.error(e.getMessage());
        }
    }
}
