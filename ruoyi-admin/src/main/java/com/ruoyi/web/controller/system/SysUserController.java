package com.ruoyi.web.controller.system;

import com.mysql.cj.x.protobuf.Mysqlx;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.QuestAnswer;
import com.ruoyi.system.domain.QuestRouterUser;
import com.ruoyi.system.domain.SysUsersInfo;
import com.ruoyi.system.domain.vo.*;
import com.ruoyi.system.service.*;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 用户信息
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/system/user")
public class SysUserController extends BaseController {
    @Autowired
    private ISysUserService userService;

    @Autowired
    private IQuestAnswerService questAnswerService;

    @Autowired
    private ISysRoleService roleService;

    @Autowired
    private ISysDeptService deptService;

    @Autowired
    private ISysPostService postService;

    @Autowired
    private IQuestRouterUserService questRouterUserService;


    /**
     * 获取用户列表
     */
    @PreAuthorize("@ss.hasPermi('system:user:phone')")
    @GetMapping("/phone")
    public TableDataInfo getUserPhone(String phone) {
        SysUser user = new SysUser();
        user.setPhonenumber(phone);
        List<SysUser> user2 = userService.selectUserList(user);
        return getDataTable(user2);
    }

    /**
     * 根据字符串模糊查询用户(用户取代ID输入框)
     * queryString (like userId, nickName, phonenumber)
     */
    @PutMapping("/query")
    public AjaxResult insertAuthRole(String queryString, Integer queryNum) {
        return success(userService.selectUsersInfoLike(queryString, queryNum));
    }


    /**
     * 获取用户列表
     */
    @PreAuthorize("@ss.hasPermi('system:user:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysUser user) {
        startPage();
        List<SysUser> list = userService.selectUserList(user);
        return getDataTable(list);
    }

    /**
     * 查询所有用户的基本信息(userId, nickName, avatar)
     *
     * @return 数据集合
     */
    @GetMapping("usersInfo")
    public List<SysUsersInfo> usersInfo() {
        //能登录后台的用户才能查询到
        LoginUser loginUser = getLoginUser();
        List<SysUsersInfo> usersInfo = null;
        if (userService.selectUserType(loginUser.getUserId()).equals("00")) {
            usersInfo = userService.selectUsersInfo();
        } else {
            usersInfo = new ArrayList<>();
        }
        return usersInfo;
    }


    @Log(title = "用户管理", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('system:user:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysUser user) {
        List<SysUser> list = userService.selectUserList(user);
        ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
        util.exportExcel(response, list, "用户数据");
    }

    @Log(title = "用户管理", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('system:user:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception {
        ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
        List<SysUser> userList = util.importExcel(file.getInputStream());
        String operName = getUsername();
        String message = userService.importUser(userList, updateSupport, operName);
        return success(message);
    }

    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) {
        ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
        util.importTemplateExcel(response, "用户数据");
    }

    /**
     * 根据用户编号获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:user:query')")
    @GetMapping(value = {"/", "/{userId}"})
    public AjaxResult getInfo(@PathVariable(value = "userId", required = false) Long userId) {
        userService.checkUserDataScope(userId);
        AjaxResult ajax = AjaxResult.success();
        List<SysRole> roles = roleService.selectRoleAll();
        ajax.put("roles", SysUser.isAdmin(userId) ? roles : roles.stream().filter(r -> !r.isAdmin()).collect(Collectors.toList()));
        ajax.put("posts", postService.selectPostAll());
        if (StringUtils.isNotNull(userId)) {
            SysUser sysUser = userService.selectUserById(userId);
            if (sysUser == null) {
                return AjaxResult.error("用户不存在，请先创建ID为" + userId + "的用户");
            }
            ajax.put(AjaxResult.DATA_TAG, sysUser);
            ajax.put("postIds", postService.selectPostListByUserId(userId));
            ajax.put("roleIds", sysUser.getRoles().stream().map(SysRole::getRoleId).collect(Collectors.toList()));
        }
        return ajax;
    }


    /**
     * 新增用户
     */
    @PreAuthorize("@ss.hasPermi('system:user:add')")
    @Log(title = "用户管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysUser user) {
        if (!userService.checkUserNameUnique(user)) {
            return error("新增用户'" + user.getUserName() + "'失败，登录账号已存在");
        } else if (StringUtils.isNotEmpty(user.getPhonenumber()) && !userService.checkPhoneUnique(user)) {
            return error("新增用户'" + user.getUserName() + "'失败，手机号码已存在");
        } else if (StringUtils.isNotEmpty(user.getEmail()) && !userService.checkEmailUnique(user)) {
            return error("新增用户'" + user.getUserName() + "'失败，邮箱账号已存在");
        }
        user.setCreateBy(getUsername());
        user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
        return toAjax(userService.insertUser(user));
    }

    /**
     * 录入客户信息
     */
    @PreAuthorize("@ss.hasPermi('system:user:add')")
    @Log(title = "mp录入客户基本信息", businessType = BusinessType.INSERT)
    @PostMapping("/mp")
    public AjaxResult addCustomer(@Validated @RequestBody SysUser user) {
        if (!userService.checkUserNameUnique(user)) {
            return error("失败，登录账号已存在");
        } else if (StringUtils.isNotEmpty(user.getPhonenumber()) && !userService.checkPhoneUnique(user)) {
            return error("失败，手机号码已存在");
        } else if (StringUtils.isNotEmpty(user.getEmail()) && !userService.checkEmailUnique(user)) {
            return error("失败，邮箱账号已存在");
        }
        user.setCreateBy(getUsername());
        user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));

        if (0 < userService.insertUser(user)) {
            return success(userService.selectUserByUserName(user.getUserName()));
        }
        return error("数据请求失败");
    }

    /**
     * 修改用户
     */
    @PreAuthorize("@ss.hasPermi('system:user:edit')")
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysUser user) {
        userService.checkUserAllowed(user);
        userService.checkUserDataScope(user.getUserId());
        if (!userService.checkUserNameUnique(user)) {
            return error("修改用户'" + user.getUserName() + "'失败，登录账号已存在");
        } else if (StringUtils.isNotEmpty(user.getPhonenumber()) && !userService.checkPhoneUnique(user)) {
            return error("修改用户'" + user.getUserName() + "'失败，手机号码已存在");
        } else if (StringUtils.isNotEmpty(user.getEmail()) && !userService.checkEmailUnique(user)) {
            return error("修改用户'" + user.getUserName() + "'失败，邮箱账号已存在");
        }
        user.setUpdateBy(getUsername());
        return toAjax(userService.updateUser(user));
    }

    /**
     * 删除用户
     */
    @PreAuthorize("@ss.hasPermi('system:user:remove')")
    @Log(title = "用户管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{userIds}")
    public AjaxResult remove(@PathVariable Long[] userIds) {
        if (ArrayUtils.contains(userIds, getUserId())) {
            return error("当前用户不能删除");
        }
        return toAjax(userService.deleteUserByIds(userIds));
    }

    /**
     * 重置密码
     */
    @PreAuthorize("@ss.hasPermi('system:user:resetPwd')")
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PutMapping("/resetPwd")
    public AjaxResult resetPwd(@RequestBody SysUser user) {
        userService.checkUserAllowed(user);
        userService.checkUserDataScope(user.getUserId());
        if (userService.selectUserType(user.getUserId()).equals("11"))
            throw new ServiceException("该用户账户密码由微信授权, 无法重置!");
        user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
        user.setUpdateBy(getUsername());
        return toAjax(userService.resetPwd(user));
    }

    /**
     * 状态修改
     */
    @PreAuthorize("@ss.hasPermi('system:user:edit')")
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    public AjaxResult changeStatus(@RequestBody SysUser user) {
        userService.checkUserAllowed(user);
        userService.checkUserDataScope(user.getUserId());
        user.setUpdateBy(getUsername());
        return toAjax(userService.updateUserStatus(user));
    }

    /**
     * 根据用户编号获取授权角色
     */
    @PreAuthorize("@ss.hasPermi('system:user:query')")
    @GetMapping("/authRole/{userId}")
    public AjaxResult authRole(@PathVariable("userId") Long userId) {
        AjaxResult ajax = AjaxResult.success();
        SysUser user = userService.selectUserById(userId);
        List<SysRole> roles = roleService.selectRolesByUserId(userId);
        ajax.put("user", user);
        ajax.put("roles", SysUser.isAdmin(userId) ? roles : roles.stream().filter(r -> !r.isAdmin()).collect(Collectors.toList()));
        return ajax;
    }

    /**
     * 用户授权角色
     */
    @PreAuthorize("@ss.hasPermi('system:user:edit')")
    @Log(title = "用户管理", businessType = BusinessType.GRANT)
    @PutMapping("/authRole")
    public AjaxResult insertAuthRole(Long userId, Long[] roleIds) {
        userService.checkUserDataScope(userId);
        userService.insertUserAuth(userId, roleIds);
        return success();
    }

    /**
     * 获取部门树列表
     */
    @PreAuthorize("@ss.hasPermi('system:user:list')")
    @GetMapping("/deptTree")
    public AjaxResult deptTree(SysDept dept) {
        return success(deptService.selectDeptTreeList(dept));
    }


    /**
     * 小程序判断用户是否填写基本信息
     */
    @GetMapping("/basic/{userId}")
    public AjaxResult basic(@PathVariable("userId") Long userId) {
        SysUser sysUser = userService.selectUserById2(userId);
        if (sysUser.getSex() == null || sysUser.getAgeDuan() == null || sysUser.getRidingAge() == null || sysUser.getPreferredRouters() == null) {
            return success(-1);//没有填写
        }
        return success(1);//填写了
    }


    /**
     * 小程序端 用户填写基本信息接口
     *
     * @param userBasicInfoVo
     * @return
     */
    @PutMapping("/basic/update")
    public AjaxResult fillBasic(@RequestBody UserBasicInfoVo userBasicInfoVo) {
        SysUser sysUser = userService.selectUserById(userBasicInfoVo.getUserId());
        sysUser.setSex(userBasicInfoVo.getSex());
        sysUser.setAgeDuan(userBasicInfoVo.getAge_duan());
        sysUser.setRidingAge(userBasicInfoVo.getRiding_age());
        sysUser.setPreferredRouters(userBasicInfoVo.getPreferred_routers());
        return toAjax(userService.updateUser(sysUser));
    }

    /**
     * 小程序端 查询路线列表接口
     * @return
     */
    @GetMapping("/basic/list")
    public AjaxResult routersList() {
        LoginUser loginUser = getLoginUser();
        QuestRouterUser q = new QuestRouterUser();
        q.setUserId(loginUser.getUserId());
        List<QuestRouterUser> questRouterUsers = questRouterUserService.selectQuestRouterUserList(q);
        Set<Integer> answeredRouterIds = new HashSet<>();
        if (questRouterUsers != null && !questRouterUsers.isEmpty()) {
            answeredRouterIds = questRouterUsers.stream()
                    .map(qru -> qru.getRouterId().intValue())
                    .collect(Collectors.toSet());
        }
        List<RoutersListVo> routersListVos = userService.getRoutersList();
        for (RoutersListVo routersListVo : routersListVos) {
            if (answeredRouterIds.contains(routersListVo.getRouterId().intValue())) {
                routersListVo.setIsAC(1);
            } else {
                routersListVo.setIsAC(0);
            }
        }
        routersListVos.sort(Comparator.comparingInt(RoutersListVo::getIsAC));
        return success(routersListVos);
    }


    /**
     * 小程序端 找到一份问卷表单
     *
     * @return
     */
    @GetMapping("/quest/sheet")
    public AjaxResult getQuestSheet() {
        // 获取原始数据
        List<QuestionSheetVo> questionSheetVos = userService.getQuestSheet();

        // 处理选项并分组
        Map<String, List<QuestionSheetVo>> groupedData = new LinkedHashMap<>();
        for (QuestionSheetVo q : questionSheetVos) {
            // 处理选项
            if (q.getAnswerOptions() != null && !q.getAnswerOptions().isEmpty()) {
                List<String> options = Arrays.asList(q.getAnswerOptions().split("、"));
                List<Map<String, String>> optionList = new ArrayList<>();
                for (int i = 0; i < options.size(); i++) {
                    Map<String, String> optionMap = new LinkedHashMap<>();
                    optionMap.put("id", String.valueOf(i + 1));  // 选项的ID
                    optionMap.put("content", options.get(i));   // 选项的内容
                    optionList.add(optionMap);
                }
                q.setAnswerOptions2(optionList);
            } else {
                q.setAnswerOptions2(new ArrayList<>());
            }
            q.setAnswerOptionsLength(q.getAnswerOptions2().size());

            // 按 problemType 分组
            groupedData.computeIfAbsent(q.getProblemType(), k -> new ArrayList<>()).add(q);
        }

        // 将分组后的数据转换为符合需求的结构
        List<Map<String, Object>> result = new ArrayList<>();
        for (Map.Entry<String, List<QuestionSheetVo>> entry : groupedData.entrySet()) {
            Map<String, Object> group = new LinkedHashMap<>();
            group.put("problemType", entry.getKey());
            group.put("subjects", entry.getValue());
            result.add(group);
        }
        return success(result);
    }


    /**
     * 小程序端  提交问卷逻辑
     */
    @PutMapping("/submit")
    public AjaxResult submitTo(@RequestBody SubmitVo submitVo) {
        QuestRouterUser questRouterUser = new QuestRouterUser();
        questRouterUser.setUserId(submitVo.getUserId());
        questRouterUser.setRouterId(submitVo.getRouterId());
        questRouterUser.setCreateDate(new Date());
        questRouterUserService.insertQuestRouterUser(questRouterUser);
        if (submitVo.getSubjects() != null) {
            for (SubjectVo subject : submitVo.getSubjects()) {
                QuestAnswer questAnswer = new QuestAnswer();
                questAnswer.setUserId(submitVo.getUserId());
                questAnswer.setRouterId(submitVo.getRouterId());
                questAnswer.setIssueId(subject.getIssueId());
                questAnswer.setAnswer(subject.getAnswer().toString());
                questAnswer.setAnswerResult(subject.getAnswerResult());
                questAnswer.setCreateDate(new Date());
                questAnswerService.insertQuestAnswer(questAnswer);
            }
        } else {
            return AjaxResult.error("提交失败，题目数据为空！");
        }
        return AjaxResult.success("提交成功");
    }
}

