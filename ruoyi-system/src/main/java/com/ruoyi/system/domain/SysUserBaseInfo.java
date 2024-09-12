package com.ruoyi.system.domain;

import java.io.Serializable;

/**
 * 用于返回指定userId的用户基本信息, 方便进行数据整合和放置信息泄露
 */
public class SysUserBaseInfo implements Serializable {
    private Long userId;

    private String nickName;

    private String email;

    private String phonenumber;

    private String sex;

    private String avatar;

    public SysUserBaseInfo() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public SysUserBaseInfo(Long userId, String nickName, String email, String phonenumber, String sex, String avatar) {
        this.userId = userId;
        this.nickName = nickName;
        this.email = email;
        this.phonenumber = phonenumber;
        this.sex = sex;
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "SysUserBaseInfo{" +
                "userId=" + userId +
                ", nickName='" + nickName + '\'' +
                ", email='" + email + '\'' +
                ", phonenumber='" + phonenumber + '\'' +
                ", sex='" + sex + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}
