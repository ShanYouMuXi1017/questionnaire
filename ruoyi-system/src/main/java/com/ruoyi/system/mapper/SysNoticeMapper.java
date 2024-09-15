package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.SysNotice;

/**
 * 通知公告表 数据层
 *
 * @author ruoyi
 */
public interface SysNoticeMapper
{
    /**
     * 查询公告信息
     *
     * @param noticeId 公告ID
     * @return 公告信息
     */
    public SysNotice selectNoticeById(Long noticeId);

    /**
     * 查询公告信息返回到小程序研发页面
     *
     * @param noticeType 公告ID
     * @return 公告信息
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
     * 查询小程序研发页面文字公告数量
     *
     * @return 公告集合
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
     * 批量删除公告
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
