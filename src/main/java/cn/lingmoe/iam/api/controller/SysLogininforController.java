package cn.lingmoe.iam.api.controller;


import cn.lingmoe.common.Result.Results;
import cn.lingmoe.iam.app.service.ISysLogininforService;
import cn.lingmoe.iam.domain.entity.SysLogininfor;
import cn.lingmoe.log.annotation.OperLog;
import cn.lingmoe.log.enums.BusinessType;
import cn.lingmoe.mybatis.page.PageRequest;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 系统访问记录 提供者
 *
 * @author yukdawn@gmail.com
 * @date 2019-05-20
 */
@RestController
@RequestMapping("/logininfor")
public class SysLogininforController {
    @Autowired
    private ISysLogininforService sysLogininforService;

    /**
     * 查询系统访问记录列表
     */
    @GetMapping("/list")
    public ResponseEntity<PageInfo<SysLogininfor>> list(SysLogininfor sysLogininfor, PageRequest pageRequest) {
        return Results.ok(PageHelper.startPage(pageRequest).doSelectPageInfo(()-> sysLogininforService.selectLogininforList(sysLogininfor)));
    }

    /**
     * 新增保存系统访问记录
     */
    @PostMapping("/save")
    public void addSave(@RequestBody SysLogininfor sysLogininfor) {
        sysLogininforService.insertLogininfor(sysLogininfor);
    }


    /**
     * 删除系统访问记录
     */
    @OperLog(title = "访问日志", businessType = BusinessType.DELETE)
//    @HasPermissions("monitor:loginlog:remove")
    @PostMapping("/remove")
    public ResponseEntity<Void> remove(String ids) {
        sysLogininforService.deleteLogininforByIds(ids);
        return Results.ok();
    }

    @OperLog(title = "访问日志", businessType = BusinessType.CLEAN)
//    @HasPermissions("monitor:loginlog:remove")
    @PostMapping("/clean")
    public ResponseEntity<Void> clean() {
        sysLogininforService.cleanLogininfor();
        return Results.ok();
    }

}
