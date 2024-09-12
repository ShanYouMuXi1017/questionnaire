package com.ruoyi.quartz.task;

import com.ruoyi.common.utils.StringUtils;
import org.springframework.stereotype.Component;

/**
 * 定时任务调度测试
 *
 字符串（需要单引号''标识 如：ryTask.ryParams(’ry’)）
 布尔类型（需要true false标识 如：ryTask.ryParams(true)）
 长整型（需要L标识 如：ryTask.ryParams(2000L)）
 浮点型（需要D标识 如：ryTask.ryParams(316.50D)）
 整型（纯数字即可）
 *
 * @author ruoyi
 */
@Component("ryTask")
public class RyTask {
    public void ryMultipleParams(String s, Boolean b, Long l, Double d, Integer i) {
        System.out.println(StringUtils.format("执行多参方法： 字符串类型{}，布尔类型{}，长整型{}，浮点型{}，整形{}", s, b, l, d, i));
    }

    public void ryParams(String params) {
        System.out.println("执行有参方法：" + params);
    }

    public void ryNoParams() {
        System.out.println("执行无参方法");
    }
}
