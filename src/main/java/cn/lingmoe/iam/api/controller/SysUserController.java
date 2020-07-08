package cn.lingmoe.iam.api.controller;

import java.util.Set;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;
import cn.lingmoe.common.Result.Results;
import cn.lingmoe.common.constant.UserConstants;
import cn.lingmoe.core.exception.base.CommonException;
import cn.lingmoe.iam.app.service.ISysMenuService;
import cn.lingmoe.iam.app.service.ISysUserService;
import cn.lingmoe.iam.domain.entity.UserInfo;
import cn.lingmoe.log.annotation.OperLog;
import cn.lingmoe.log.enums.BusinessType;
import cn.lingmoe.mybatis.page.PageRequest;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 用户 提供者
 *
 * @author yukdawn@gmail.com
 * @date 2019-05-20
 */
@RestController
@RequestMapping("/user")
public class SysUserController {
    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private ISysMenuService sysMenuService;

    /**
     * 查询用户
     */
    @GetMapping("/get/{userId}")
    public ResponseEntity<UserInfo> get(@PathVariable("userId") Long userId) {
        return Results.ok(sysUserService.selectUserById(userId));
    }

    @GetMapping("/info")
    public ResponseEntity<UserInfo> info(UserInfo userInfo) {
        userInfo.setButtons(sysMenuService.selectPermsByUserId(userInfo.getUserId()));
        return Results.ok(userInfo);
    }

    /**
     * 查询用户
     */
    @GetMapping("/find/{username}")
    public ResponseEntity<UserInfo> findByUsername(@PathVariable("username") String username) {
        return Results.ok(sysUserService.selectUserByLoginName(username));
    }

    /**
     * 查询拥有当前角色的所有用户
     */
    @GetMapping("/hasRoles")
    public ResponseEntity<Set<Long>> hasRoles(String roleIds) {
        Long[] arr = Convert.toLongArray(roleIds);
        return Results.ok(sysUserService.selectUserIdsHasRoles(arr));
    }

    /**
     * 查询所有当前部门中的用户
     */
    @GetMapping("/inDepts")
    public ResponseEntity<Set<Long>> inDept(String deptIds) {
        Long[] arr = Convert.toLongArray(deptIds);
        return Results.ok(sysUserService.selectUserIdsInDepts(arr));
    }

    /**
     * 查询用户列表
     */
    @GetMapping("/list")
    public ResponseEntity<PageInfo<UserInfo>> list(UserInfo userInfo, PageRequest pageRequest) {
        return Results.ok(PageHelper.startPage(pageRequest).doSelectPageInfo(()-> sysUserService.selectUserList(userInfo)));
    }

    /**
     * 新增保存用户
     */
//    @HasPermissions("system:user:add")
    @PostMapping("/save")
    @OperLog(title = "用户管理", businessType = BusinessType.INSERT)
    public ResponseEntity<Void> addSave(@RequestBody UserInfo userInfo) {
        if (UserConstants.USER_NAME_NOT_UNIQUE.equals(sysUserService.checkLoginNameUnique(userInfo.getUsername()))) {
            throw new CommonException("新增用户'" + userInfo.getUsername() + "'失败，登录账号已存在");
        } else if (UserConstants.USER_PHONE_NOT_UNIQUE.equals(sysUserService.checkPhoneUnique(userInfo))) {
            throw new CommonException("新增用户'" + userInfo.getUsername() + "'失败，手机号码已存在");
        } else if (UserConstants.USER_EMAIL_NOT_UNIQUE.equals(sysUserService.checkEmailUnique(userInfo))) {
            throw new CommonException("新增用户'" + userInfo.getUsername() + "'失败，邮箱账号已存在");
        }
        userInfo.setSalt(RandomUtil.randomString(6));
        userInfo.setPassword(SecureUtil.md5((userInfo.getUsername()+ userInfo.getPassword()+ userInfo.getSalt())));
        sysUserService.insertUser(userInfo);
        return Results.ok();
    }

    /**
     * 修改保存用户
     */
//    @HasPermissions("system:user:edit")
    @OperLog(title = "用户管理", businessType = BusinessType.UPDATE)
    @PostMapping("/update")
    public ResponseEntity<Void> editSave(@RequestBody UserInfo userInfo) {
        if (null != userInfo.getUserId() && UserInfo.isAdmin(userInfo.getUserId())) {
            throw new CommonException("不允许修改超级管理员用户");
        } else if (UserConstants.USER_PHONE_NOT_UNIQUE.equals(sysUserService.checkPhoneUnique(userInfo))) {
            throw new CommonException("修改用户'" + userInfo.getUsername() + "'失败，手机号码已存在");
        } else if (UserConstants.USER_EMAIL_NOT_UNIQUE.equals(sysUserService.checkEmailUnique(userInfo))) {
            throw new CommonException("修改用户'" + userInfo.getUsername() + "'失败，邮箱账号已存在");
        }
        sysUserService.updateUser(userInfo);
        return Results.ok();
    }

    /**
     * 修改用户信息
     *
     * @param userInfo
     * @return
     * @author yukdawn@gmail.com
     */
//    @HasPermissions("system:user:edit")
    @PostMapping("/update/info")
    @OperLog(title = "用户管理", businessType = BusinessType.UPDATE)
    public ResponseEntity<Void> updateInfo(@RequestBody UserInfo userInfo) {
        sysUserService.updateUserInfo(userInfo);
        return Results.ok();
    }

    /**
     * 记录登陆信息
     *
     * @param userInfo
     * @return
     * @author yukdawn@gmail.com
     */
    @PostMapping("/update/login")
    public ResponseEntity<Void> updateLoginRecord(@RequestBody UserInfo userInfo) {
        sysUserService.updateUser(userInfo);
        return Results.ok();
    }

//    @HasPermissions("system:user:resetPwd")
    @OperLog(title = "重置密码", businessType = BusinessType.UPDATE)
    @PostMapping("/resetPwd")
    public ResponseEntity<Void> resetPwdSave(@RequestBody UserInfo user) {
        user.setSalt(RandomUtil.randomString(6));
        user.setPassword(SecureUtil.md5(user.getUsername()+user.getPassword()+user.getSalt()));
        sysUserService.resetUserPwd(user);
        return Results.ok();
    }

    /**
     * 修改状态
     *
     * @param userInfo
     * @return
     * @author yukdawn@gmail.com
     */
//    @HasPermissions("system:user:edit")
    @PostMapping("/status")
    @OperLog(title = "用户管理", businessType = BusinessType.UPDATE)
    public ResponseEntity<Void> status(@RequestBody UserInfo userInfo) {
        sysUserService.changeStatus(userInfo);
        return Results.ok();
    }

    /**
     * 删除用户
     *
     * @throws Exception
     */
//    @HasPermissions("system:user:remove")
    @OperLog(title = "用户管理", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    public ResponseEntity<Void> remove(String ids) throws Exception {
        sysUserService.deleteUserByIds(ids);
        return Results.ok();
    }
}
