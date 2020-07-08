package cn.lingmoe.iam.api.controller;

import java.util.List;

import cn.lingmoe.common.Result.Results;
import cn.lingmoe.common.excel.utils.ExcelUtil;
import cn.lingmoe.iam.app.service.ISysOperLogService;
import cn.lingmoe.iam.domain.entity.SysOperLog;
import cn.lingmoe.log.annotation.OperLog;
import cn.lingmoe.log.enums.BusinessType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 操作日志记录 提供者
 *
 * @author yukdawn@gmail.com
 * @date 2019-05-20
 */
@RestController
@RequestMapping("/operLog")
public class SysOperLogController {
    @Autowired
    private ISysOperLogService sysOperLogService;

    /**
     * 查询操作日志记录
     */
    @GetMapping("/get/{operId}")
    public ResponseEntity<SysOperLog> get(@PathVariable("operId") Long operId) {
        return Results.ok(sysOperLogService.selectOperLogById(operId));
    }

    /**
     * 查询操作日志记录列表
     */
//    @HasPermissions("monitor:operlog:list")
    @RequestMapping("/list")
    public ResponseEntity<List<SysOperLog>> list(SysOperLog sysOperLog) {
        return Results.ok(sysOperLogService.selectOperLogList(sysOperLog));
    }

    @OperLog(title = "操作日志", businessType = BusinessType.EXPORT)
//    @HasPermissions("monitor:operlog:export")
    @PostMapping("/export")
    public ResponseEntity<String> export(SysOperLog operLog) {
        List<SysOperLog> list = sysOperLogService.selectOperLogList(operLog);
        ExcelUtil<SysOperLog> util = new ExcelUtil<>(SysOperLog.class);
        return Results.ok(util.exportExcel(list, "操作日志").getBody());
    }

    /**
     * 新增保存操作日志记录
     */
    @PostMapping("/save")
    public ResponseEntity<Void> addSave(@RequestBody SysOperLog sysOperLog) {
        sysOperLogService.insertOperlog(sysOperLog);
        return Results.ok();
    }

    /**
     * 删除操作日志记录
     */
//    @HasPermissions("monitor:operlog:remove")
    @PostMapping("/remove")
    public ResponseEntity<Void> remove(String ids) {
        sysOperLogService.deleteOperLogByIds(ids);
        return Results.ok();
    }

    @OperLog(title = "操作日志", businessType = BusinessType.CLEAN)
//    @HasPermissions("monitor:operlog:remove")
    @PostMapping("/clean")
    public ResponseEntity<Void> clean() {
        sysOperLogService.cleanOperLog();
        return Results.ok();
    }
}
