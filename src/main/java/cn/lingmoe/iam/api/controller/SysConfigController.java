package cn.lingmoe.iam.api.controller;

import cn.lingmoe.common.Result.Results;
import cn.lingmoe.iam.app.service.ISysConfigService;
import cn.lingmoe.iam.domain.entity.SysConfig;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 参数配置 提供者
 *
 * @author yukdawn@gmail.com
 * @date 2019-05-20
 */
@RestController
@RequestMapping("/config")
public class SysConfigController {

    @Autowired
    private ISysConfigService sysConfigService;

    /**
     * 查询参数配置
     */
    @GetMapping("/get/{configId}")
    public ResponseEntity<SysConfig> get(@PathVariable("configId") Long configId) {
        return Results.ok(sysConfigService.selectConfigById(configId));

    }

    /**
     * 查询参数配置列表
     */
    @GetMapping("/list")
    public ResponseEntity<PageInfo<SysConfig>> list(SysConfig sysConfig, Page<SysConfig> page) {
        return Results.ok(PageHelper.startPage(page).doSelectPageInfo(() -> sysConfigService.selectConfigList(sysConfig)));
    }


    /**
     * 新增保存参数配置
     */
    @PostMapping("/save")
    public ResponseEntity<Void> addSave(@RequestBody SysConfig sysConfig) {
        sysConfigService.insertConfig(sysConfig);
        return Results.ok();
    }

    /**
     * 修改保存参数配置
     */
    @PostMapping("/update")
    public ResponseEntity<Void> editSave(@RequestBody SysConfig sysConfig) {
        sysConfigService.updateConfig(sysConfig);
        return Results.ok();
    }

    /**
     * 删除参数配置
     */
    @PostMapping("/remove")
    public ResponseEntity<Void> remove(String ids) {
        sysConfigService.deleteConfigByIds(ids);
        return Results.ok();
    }

}
