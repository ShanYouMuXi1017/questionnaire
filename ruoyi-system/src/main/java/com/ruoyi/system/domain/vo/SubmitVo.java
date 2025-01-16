package com.ruoyi.system.domain.vo;

import lombok.Data;

import java.util.List;

/**
 * @ Description： 提交逻辑的VO
 * @ Author： 程序员好冰
 * @ Date： 2024/12/19/16:39
 */
@Data
public class SubmitVo {
    private static final long serialVersionUID = 1L;
    private Long userId; // 用户ID
    private Long routerId; // 路由ID
    private List<SubjectVo> subjects; // 题目列表
}
