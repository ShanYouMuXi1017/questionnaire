package com.ruoyi.system.cache;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import com.ruoyi.system.config.WechatTemplateConfig;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;


import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Component
public class AccessTokenCache {

    @Autowired
    private WxMaService wxMaService;

    @Autowired
    private WechatTemplateConfig wechatTemplateConfig;
    //定义缓存的名字
    private static final String ACCESS_TOKEN_CACHE_NAME = "accessTokenCache";
    private static final String ACCESS_TOKEN_KEY = "accessToken";
    private static final String ACCESS_TOKEN_TIMESTAMP_KEY = "accessTokenTimestamp";
    private static final long EXPIRATION_TIME_IN_HOURS = 2;

    @Autowired
    private final CacheManager cacheManager;

    @Autowired
    public AccessTokenCache(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public void saveAccessToken(String accessToken) {
        Cache cache = cacheManager.getCache(ACCESS_TOKEN_CACHE_NAME);
        if (cache != null) {
            cache.put(ACCESS_TOKEN_KEY, accessToken);
            cache.put(ACCESS_TOKEN_TIMESTAMP_KEY, LocalDateTime.now());
        }
    }

    public String getAccessToken() {
        //根据名字获取到缓存
        Cache cache = cacheManager.getCache(ACCESS_TOKEN_CACHE_NAME);
        if (cache != null) {
            //拿到保存的accessToken
            Cache.ValueWrapper tokenWrapper = cache.get(ACCESS_TOKEN_KEY);
            Cache.ValueWrapper timestampWrapper = cache.get(ACCESS_TOKEN_TIMESTAMP_KEY);

            if (tokenWrapper != null && timestampWrapper != null) {
                LocalDateTime timestamp = (LocalDateTime) timestampWrapper.get();
                if (isTokenExpired(timestamp)) {
                    //如果accessToken超过两个小时就重新获取
                    String newAccessToken = getWxAccessToken();
                    saveAccessToken(newAccessToken);
                    return newAccessToken;
                } else {
                    return (String) tokenWrapper.get();
                }
            }
        }
        //accessToken为空就直接获取
        String newAccessToken = getWxAccessToken();
        saveAccessToken(newAccessToken);
        return newAccessToken;
    }

    private String getWxAccessToken(){

        String WxAccessToken = "";

        //开始获取 access token
        WxMaDefaultConfigImpl wxMaDefaultConfig1 = new WxMaDefaultConfigImpl();
        wxMaDefaultConfig1.setAppid(wechatTemplateConfig.getAppId());
        wxMaDefaultConfig1.setSecret(wechatTemplateConfig.getSecret());
        wxMaService.setWxMaConfig(wxMaDefaultConfig1);

        try {
            // 获取 access token
            WxAccessToken = wxMaService.getAccessToken();
            if (WxAccessToken != null) {
                System.out.println("Access token获取成功");
            } else {
                System.out.println("Failed to fetch access token.");
            }
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        return WxAccessToken;
    }

    private boolean isTokenExpired(LocalDateTime timestamp) {
        return ChronoUnit.HOURS.between(timestamp, LocalDateTime.now()) >= EXPIRATION_TIME_IN_HOURS;
    }

}

