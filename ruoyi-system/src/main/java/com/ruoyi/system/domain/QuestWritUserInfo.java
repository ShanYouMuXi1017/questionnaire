package com.ruoyi.system.domain;

import lombok.Data;

@Data
public class QuestWritUserInfo {
    private Long userId;
    private String sex;
    private Integer ageDuan;
    private Integer ridingAge;

//    public String getGender() {
//        if ("0".equals(sex)) {
//            return "男";
//        } else if ("1".equals(sex)) {
//            return "女";
//        } else {
//            return "未知"; // 处理其他值
//        }
//    }
//
//    public void setSex(String sex) {
//        this.sex = sex;
//    }
}
