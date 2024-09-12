package com.ruoyi.system.domain;


import java.io.Serializable;

/**
 * 此类仅用于向后台传递用户的基本信息
 */
public class SysUsersInfo implements Serializable {
    private Long userId;
    private String nickName;
    private String avatar;

    private String phonenumber;

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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }
}
