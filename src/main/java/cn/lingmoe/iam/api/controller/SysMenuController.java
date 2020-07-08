package cn.lingmoe.iam.api.controller;

import java.util.List;
import java.util.Set;

import cn.hutool.core.lang.tree.Tree;
import cn.lingmoe.common.Result.Results;
import cn.lingmoe.iam.app.service.ISysMenuService;
import cn.lingmoe.iam.domain.entity.SysMenu;
import cn.lingmoe.iam.domain.entity.UserInfo;
import cn.lingmoe.log.annotation.OperLog;
import cn.lingmoe.log.enums.BusinessType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 菜单权限
 *
 * @author yukdawn@gmail.com
 * @date 2019-05-20
 */
@RestController
@RequestMapping("/menu")
public class SysMenuController {
    @Autowired
    private ISysMenuService sysMenuService;

    /**
     * 查询菜单权限
     */
    @GetMapping("/get/{menuId}")
    public ResponseEntity<SysMenu> get(@PathVariable("menuId") Long menuId) {
        return Results.ok(sysMenuService.selectMenuById(menuId));
    }

    @GetMapping("/perms/{userId}")
    public ResponseEntity<Set<String>> perms(@PathVariable("userId") Long userId) {
        return Results.ok(sysMenuService.selectPermsByUserId(userId));
    }

    /**
     * 查询菜单权限
     */
    @GetMapping("/user")
    public ResponseEntity<List<Tree<Long>>> user(UserInfo userInfo) {
        return Results.ok(sysMenuService.selectMenusByUser(userInfo));
    }

    /**
     * 根据角色编号查询菜单编号（用于勾选）
     *
     * @param roleId
     * @return
     * @author yukdawn@gmail.com
     */
    @GetMapping("/role/{roleId}")
    public ResponseEntity<List<SysMenu>> role(@PathVariable("roleId") Long roleId) {
        return Results.ok(sysMenuService.selectMenuIdsByRoleId(roleId));
    }

    /**
     * 查询菜单权限列表
     */
//    @HasPermissions("system:menu:view")
    @GetMapping("/list")
    public ResponseEntity<List<Tree<Long>>> list(SysMenu sysMenu) {
        return Results.ok(sysMenuService.selectMenuList(sysMenu));
    }

    /**
     * 新增保存菜单权限
     */
    @PostMapping("/save")
    @OperLog(title = "菜单管理", businessType = BusinessType.INSERT)
    public ResponseEntity<Void> addSave(@RequestBody SysMenu sysMenu) {
        sysMenuService.insertMenu(sysMenu);
        return Results.ok();
    }

    /**
     * 修改保存菜单权限
     */
    @OperLog(title = "菜单管理", businessType = BusinessType.UPDATE)
    @PostMapping("/update")
    public ResponseEntity<Void> editSave(@RequestBody SysMenu sysMenu) {
        sysMenuService.updateMenu(sysMenu);
        return Results.ok();
    }

    /**
     * 删除菜单权限
     */
    @OperLog(title = "菜单管理", businessType = BusinessType.DELETE)
    @PostMapping("/remove/{menuId}")
    public ResponseEntity<Void> remove(@PathVariable("menuId") Long menuId) {
        sysMenuService.deleteMenuById(menuId);
        return Results.ok();
    }
}
