package com.ruoyi.web.controller.system;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysMenu;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginBody;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.framework.web.service.SysLoginService;
import com.ruoyi.framework.web.service.SysPermissionService;
import com.ruoyi.system.service.ISysMenuService;
import com.ruoyi.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Set;

/**
 * 登录验证
 *
 * @author ruoyi
 */
@RestController
public class SysLoginController {
    @Autowired
    private SysLoginService loginService;

    @Autowired
    private ISysMenuService menuService;

    @Autowired
    private SysPermissionService permissionService;

    @Autowired
    ISysUserService userService;
    @Value("${ruoyi.appId}")
    public String appId;

    @Value("${ruoyi.secret}")
    public String secret;

    /**
     * 获取用户的OpenId
     *
     * @param jsCode 用户进入程序的jsCode
     * @return openId
     */
    @PostMapping("/openId")
    public String getOpenid(@RequestBody String jsCode) {
        String authUrl = "https://api.weixin.qq.com/sns/jscode2session?grant_type=authorization_code";
        authUrl += "&appid=" + appId + "&secret=" + secret + "&js_code=" + jsCode;
        String result = HttpUtil.get(authUrl);
        JSONObject jsonObject = JSONUtil.parseObj(result);
        String openId = jsonObject.getStr("openid");
        if (null == openId) {
            openId = "errorCode";
        } else {
            try {
                //检查用户是否存在(存在返回false), 不存在则先注册(新增该用户)
                SysUser user = new SysUser();
                user.setUserName(openId);
                if (userService.checkUserNameUnique(user)) {
                    user.setNickName("骑友");
                    user.setUserType("11");
                    user.setPassword(SecurityUtils.encryptPassword(simpleEncryption(openId)));
                    userService.insertUser(user);
                }
            } catch (Exception e) {
                e.printStackTrace();
                openId = "errorCode";
            }

        }
        return openId;
    }


    /**
     * 登录方法
     *
     * @param loginBody 登录信息
     * @return 结果
     */
    @PostMapping("/login")
    public AjaxResult login(@RequestBody LoginBody loginBody) {
        AjaxResult ajax = AjaxResult.success();
        // 生成令牌
        String token = loginService.login(loginBody.getUsername(), loginBody.getPassword(), loginBody.getCode(),
                loginBody.getUuid());
        ajax.put(Constants.TOKEN, token);
        return ajax;
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @GetMapping("getInfo")
    public AjaxResult getInfo() {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        // 角色集合
        Set<String> roles = permissionService.getRolePermission(user);
        // 权限集合
        Set<String> permissions = permissionService.getMenuPermission(user);
        AjaxResult ajax = AjaxResult.success();
        ajax.put("user", user);
        ajax.put("roles", roles);
        ajax.put("permissions", permissions);
        return ajax;
    }

    /**
     * 获取路由信息
     *
     * @return 路由信息
     */
    @GetMapping("getRouters")
    public AjaxResult getRouters() {
        Long userId = SecurityUtils.getUserId();
        List<SysMenu> menus = menuService.selectMenuTreeByUserId(userId);
        return AjaxResult.success(menuService.buildMenus(menus));
    }


    /**
     * 简单加密
     */
    private String simpleEncryption(String str) {
        StringBuilder temp = new StringBuilder("mp");
        StringBuilder result = new StringBuilder();
        String resultTemp = null;
        for (int indexTemp = str.length() - 1; indexTemp >= 0; indexTemp--) {
            if (indexTemp % 4 == 0) {
                temp.append(str.charAt(indexTemp));
            }
        }
        resultTemp = DigestUtils.md5DigestAsHex(temp.toString().getBytes(StandardCharsets.UTF_8));
        for (int index = resultTemp.length() - 1; index >= 0; index--) {
            if (index % 2 == 0) {
                result.append(resultTemp.charAt(index));
            }
        }
        return result.toString();
    }
}
