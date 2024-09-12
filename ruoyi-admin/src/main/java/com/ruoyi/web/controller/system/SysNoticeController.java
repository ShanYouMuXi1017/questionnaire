package com.ruoyi.web.controller.system;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.SysNotice;
import com.ruoyi.system.service.ISysNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 公告 信息操作处理
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/system/notice")
public class SysNoticeController extends BaseController {
    @Autowired
    private ISysNoticeService noticeService;

    /**
     * 获取通知公告列表
     */
    @PreAuthorize("@ss.hasPermi('system:notice:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysNotice notice) {
        startPage();
        List<SysNotice> list = noticeService.selectNoticeList(notice);
        return getDataTable(list);
    }

    /**
     * 根据通知公告编号获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:notice:query')")
    @GetMapping(value = "/{noticeId}")
    public AjaxResult getInfo(@PathVariable Long noticeId) {
        return success(noticeService.selectNoticeById(noticeId));
    }

    /**
     * 小程序获取最新一条公告
     */
    @GetMapping("/announcement")
    public AjaxResult getLastAnnouncement() {
        return success(noticeService.selectLastAnnouncement());
    }

    /**
     * 小程序获取所有通知(非公告)
     */
    @GetMapping("/notice")
    public AjaxResult getAllNotice() {
        return success(noticeService.selectAllNotice());
    }


    /**
     * 小程序根据通知公告编号获取详细信息
     */
    @GetMapping(value = "/mp/{noticeId}")
    public AjaxResult getInfoById(@PathVariable("noticeId") Long noticeId) {
        SysNotice notice = noticeService.selectNoticeById(noticeId);
        if (notice != null) {
            notice.setNoticeType(null);
            notice.setCreateBy(null);
            notice.setUpdateBy(null);
            notice.setUpdateTime(null);
            notice.setStatus(null);
            return success(notice);
        } else {
            SysNotice emptyNotice = new SysNotice();
            emptyNotice.setCreateTime(new Date());
            emptyNotice.setNoticeTitle("(空标题)");
            emptyNotice.setNoticeContent("没有找到通知内容 . . .");
            return success(emptyNotice);
        }
    }

    /**
     * 小程序根据通知公告编号获取详细信息
     */
    @GetMapping(value = "/rAndD/{noticeType}")
    public AjaxResult getInfoByType(@PathVariable("noticeType") String noticeType) {
        SysNotice notice = noticeService.selectNoticeByType(noticeType);
        if (notice != null) {
            notice.setNoticeType(null);
            notice.setCreateBy(null);
            notice.setUpdateBy(null);
            notice.setUpdateTime(null);
            notice.setStatus(null);
            return success(notice);
        } else {
            SysNotice emptyNotice = new SysNotice();
            emptyNotice.setCreateTime(new Date());
            emptyNotice.setNoticeTitle("(空标题)");
            emptyNotice.setNoticeContent("没有找到通知内容 . . .");
            return success(emptyNotice);
        }
    }

    /**
     * 新增通知公告
     */
    @PreAuthorize("@ss.hasPermi('system:notice:add')")
    @Log(title = "通知公告", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysNotice notice) {
        notice.setCreateBy(getUsername());
        return toAjax(noticeService.insertNotice(notice));
    }

    /**
     * 修改通知公告
     */
    @PreAuthorize("@ss.hasPermi('system:notice:edit')")
    @Log(title = "通知公告", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysNotice notice) {
        notice.setUpdateBy(getUsername());
        return toAjax(noticeService.updateNotice(notice));
    }

    /**
     * 删除通知公告
     */
    @PreAuthorize("@ss.hasPermi('system:notice:remove')")
    @Log(title = "通知公告", businessType = BusinessType.DELETE)
    @DeleteMapping("/{noticeIds}")
    public AjaxResult remove(@PathVariable Long[] noticeIds) {
        return toAjax(noticeService.deleteNoticeByIds(noticeIds));
    }

    @GetMapping("/getRAndDNoticeNum")
    public int getRAndDNoticeNum() {
        return noticeService.selectRAndDNoticeNum();
    }
}
