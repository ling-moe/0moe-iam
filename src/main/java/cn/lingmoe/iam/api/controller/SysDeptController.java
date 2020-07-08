package cn.lingmoe.iam.api.controller;

import java.util.List;
import java.util.Set;

import cn.lingmoe.common.Result.Results;
import cn.lingmoe.iam.app.service.ISysDeptService;
import cn.lingmoe.iam.domain.entity.SysDept;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 部门 提供者
 *
 * @author yukdawn@gmail.com
 * @date 2019-05-20
 */
@RestController
@RequestMapping("/dept")
public class SysDeptController {
    @Autowired
    private ISysDeptService sysDeptService;

    /**
     * 查询部门
     */
    @GetMapping("/get/{deptId}")
    public ResponseEntity<SysDept> get(@PathVariable("deptId") Long deptId) {
        sysDeptService.selectDeptById(deptId);
        return Results.ok();
    }

    /**
     * 查询部门列表
     */
    @GetMapping("/list")
    public ResponseEntity<PageInfo<SysDept>> list(SysDept sysDept, Page<SysDept> page) {
        return Results.ok(PageHelper.startPage(page).doSelectPageInfo(() -> sysDeptService.selectDeptList(sysDept)));
    }

    /**
     * 查询所有可用部门
     */
    @GetMapping("/list/enable")
    public ResponseEntity<List<SysDept>> listEnable(SysDept sysDept) {
        sysDept.setStatus("0");
        return Results.ok(sysDeptService.selectDeptList(sysDept));
    }

    /**
     * 新增保存部门
     */
    @PostMapping("/save")
    public ResponseEntity<Void> addSave(@RequestBody SysDept sysDept) {
        sysDeptService.insertDept(sysDept);
        return Results.ok();
    }

    /**
     * 修改保存部门
     */
    @PostMapping("/update")
    public ResponseEntity<Void> editSave(@RequestBody SysDept sysDept) {
        sysDeptService.updateDept(sysDept);
        return Results.ok();
    }

    /**
     * 删除部门
     */
    @PostMapping("/remove/{deptId}")
    public ResponseEntity<Void> remove(@PathVariable("deptId") Long deptId) {
        sysDeptService.deleteDeptById(deptId);
        return Results.ok();
    }

    /**
     * 加载角色部门（数据权限）列表树
     */
    @GetMapping("/role/{roleId}")
    public ResponseEntity<Set<String>> deptTreeData(@PathVariable("roleId") Long roleId) {
        return Results.ok(sysDeptService.roleDeptIds(roleId));
    }
}
