package com.ruoyi.web.controller.system;

import com.alibaba.druid.sql.visitor.functions.Char;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.file.MimeTypeUtils;
import com.ruoyi.system.domain.SysBanner;
import com.ruoyi.system.service.ISysBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * 轮播图Controller
 *
 * @date 2024-04-07
 */
@RestController
@RequestMapping("/system/banner")
public class SysBannerController extends BaseController {
    @Autowired
    private ISysBannerService sysBannerService;

    /**
     * 查询轮播图列表
     */
    @GetMapping("/list")
    public TableDataInfo list(SysBanner sysBanner) {
        startPage();
        System.out.println("list:"+sysBanner.toString());
        List<SysBanner> list = sysBannerService.selectSysBannerList(sysBanner);
        return getDataTable(list);
    }

    /**
     * 获取轮播图详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(sysBannerService.selectSysBannerById(id));
    }

    /**
     * 新增轮播图
     */
    @PreAuthorize("@ss.hasPermi('system:banner:operate')")
    @Log(title = "轮播图", businessType = BusinessType.INSERT)
    @PostMapping("/upload{type}")
    public AjaxResult add(@RequestParam("bannerFile") MultipartFile file,@PathVariable String type) {
        System.out.println("type:"+type);
        if (!file.isEmpty()) {
            try {
                String bannerPath = FileUploadUtils.upload(RuoYiConfig.getBannerPath(), file, MimeTypeUtils.IMAGE_EXTENSION);
                SysBanner banner = new SysBanner();
                banner.setCreator(getUserId());
                banner.setBanner(bannerPath);
                banner.setBannerType(type);
                banner.setUpdateTime(new Date());
                if (sysBannerService.insertSysBanner(banner) == 1) {
                    AjaxResult ajax = AjaxResult.success();
                    ajax.put("imgUrl", bannerPath);
                    return ajax;
                } else {
                    throw new Exception("banner图片路径数据" + bannerPath + "数据库写入失败!");
                }
            } catch (Exception e) {
                e.printStackTrace();
                return new AjaxResult(HttpStatus.ERROR, "服务器异常,上传失败!");
            }
        }
        return new AjaxResult(HttpStatus.ERROR, "格式异常, 上传失败");
    }

    /**
     * 修改轮播图
     */
    @PreAuthorize("@ss.hasPermi('system:banner:operate')")
    @Log(title = "轮播图", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysBanner sysBanner) {
        return toAjax(sysBannerService.updateSysBanner(sysBanner));
    }

    /**
     * 删除轮播图
     */
    @PreAuthorize("@ss.hasPermi('system:banner:operate')")
    @Log(title = "轮播图", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        try {
            for (Long id : ids) {
                String originBannerPath = sysBannerService.selectSysBannerById(id).getBanner().replaceAll("/profile", RuoYiConfig.getProfile());
                File originBanner = new File(originBannerPath);
                if (originBanner.exists())
                    originBanner.delete();
            }
        } catch (Exception ignore) {
        }
        return toAjax(sysBannerService.deleteSysBannerByIds(ids));
    }
}
