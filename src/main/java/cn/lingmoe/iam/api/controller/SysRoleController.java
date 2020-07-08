package cn.lingmoe.iam.api.controller;

import java.util.List;

import cn.lingmoe.common.Result.Results;
import cn.lingmoe.iam.app.service.ISysRoleService;
import cn.lingmoe.iam.domain.entity.RoleInfo;
import cn.lingmoe.log.annotation.OperLog;
import cn.lingmoe.log.enums.BusinessType;
import cn.lingmoe.mybatis.page.PageRequest;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 角色 提供者
 *
 * @author yukdawn@gmail.com
 * @date 2019-05-20
 */
@RestController
@RequestMapping("/role")
public class SysRoleController {
    @Autowired
    private ISysRoleService sysRoleService;

    /**
     * 查询角色
     */
    @GetMapping("/get/{roleId}")
    public ResponseEntity<RoleInfo> get(@PathVariable("roleId") Long roleId) {
        sysRoleService.selectRoleById(roleId);
        return Results.ok();
    }

    /**
     * 查询角色列表
     */
    @GetMapping("/list")
    public ResponseEntity<PageInfo<RoleInfo>> list(RoleInfo roleInfo, PageRequest pageRequest) {
        return Results.ok(PageHelper.startPage(pageRequest).doSelectPageInfo(()->sysRoleService.selectRoleList(roleInfo)));
    }

    @GetMapping("/all")
    public ResponseEntity<List<RoleInfo>> all() {
        return Results.ok(sysRoleService.selectRoleAll());
    }

    /**
     * 新增保存角色
     */
    @PostMapping("/save")
    @OperLog(title = "角色管理", businessType = BusinessType.INSERT)
    public ResponseEntity<Void> addSave(@RequestBody RoleInfo roleInfo) {
        sysRoleService.insertRole(roleInfo);
        return Results.ok();
    }

    /**
     * 修改保存角色
     */
    @OperLog(title = "角色管理", businessType = BusinessType.UPDATE)
    @PostMapping("/update")
    public ResponseEntity<Void> editSave(@RequestBody RoleInfo roleInfo) {
        sysRoleService.updateRole(roleInfo);
        return Results.ok();
    }

    /**
     * 修改保存角色
     */
    @OperLog(title = "角色管理", businessType = BusinessType.UPDATE)
    @PostMapping("/status")
    public ResponseEntity<Void> status(@RequestBody RoleInfo roleInfo) {
        sysRoleService.changeStatus(roleInfo);
        return Results.ok();
    }

    /**
     * 保存角色分配数据权限
     */
//    @HasPermissions("system:role:edit")
    @OperLog(title = "角色管理", businessType = BusinessType.UPDATE)
    @PostMapping("/authDataScope")
    public ResponseEntity<Void> authDataScopeSave(@RequestBody RoleInfo role) {
        if (sysRoleService.authDataScope(role) > 0) {
            return Results.ok();
        }
        return Results.error();
    }

    /**
     * 删除角色
     *
     * @throws Exception
     */
    @OperLog(title = "角色管理", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    public ResponseEntity<Void> remove(String ids) throws Exception {
        sysRoleService.deleteRoleByIds(ids);
        return Results.ok();
    }
}
