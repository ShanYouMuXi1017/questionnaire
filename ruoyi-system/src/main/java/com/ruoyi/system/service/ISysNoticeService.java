package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.SysNotice;

/**
 * 公告 服务层
 *
 * @author ruoyi
 */
public interface ISysNoticeService
{
    /**
     * 查询公告信息
     *
     * @param noticeId 公告ID
     * @return 公告信息
     */
    public SysNotice selectNoticeById(Long noticeId);

    /**
     * 查询公告信息
     *
     * @param noticeType 公告类型
     * @return 返回公告到小程序
     */
    public SysNotice selectNoticeByType(String noticeType);

    /**
     * 小程序获取最新一条公告
     *
     * @return 公告信息
     */
    public SysNotice selectLastAnnouncement();

    /**
     * 小程序查询所有通知(非公告)
     *
     * @return 通知集合
     */
    public List<SysNotice> selectAllNotice();

    /**
     * 查询公告列表
     *
     * @param notice 公告信息
     * @return 公告集合
     */
    public List<SysNotice> selectNoticeList(SysNotice notice);

    /**
     * 查询小程序研发页面文字通知数量
     *
     * @return 研发页面文字通知数量
     */
    public int selectRAndDNoticeNum();

    /**
     * 新增公告
     *
     * @param notice 公告信息
     * @return 结果
     */
    public int insertNotice(SysNotice notice);

    /**
     * 修改公告
     *
     * @param notice 公告信息
     * @return 结果
     */
    public int updateNotice(SysNotice notice);

    /**
     * 删除公告信息
     *
     * @param noticeId 公告ID
     * @return 结果
     */
    public int deleteNoticeById(Long noticeId);

    /**
     * 批量删除公告信息
     *
     * @param noticeIds 需要删除的公告ID
     * @return 结果
     */
    public int deleteNoticeByIds(Long[] noticeIds);
}
