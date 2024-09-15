package com.ruoyi.system.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConfigurationProperties(prefix = "wechat.template")
public class WechatTemplateConfig {
    //订阅消息的模板id
    private Map<String, String> templateId;
    //消息模板的参数
    private Map<String, String> param;
    //点击消息后跳转的页面
    private Map<String,String> page;

    @Value("${ruoyi.appId}")
    private String appId;

    @Value("${ruoyi.secret}")
    private String secret;


    public Map<String, String> getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Map<String, String> templateId) {
        this.templateId = templateId;
    }

    public Map<String, String> getParam() {
        return param;
    }

    public void setParam(Map<String, String> param) {
        this.param = param;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Map<String, String> getPage() {
        return page;
    }

    public void setPage(Map<String, String> page) {
        this.page = page;
    }
}
