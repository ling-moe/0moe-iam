package cn.lingmoe.iam.api.controller;


import cn.lingmoe.common.Result.Results;
import cn.lingmoe.iam.app.service.ISysDictTypeService;
import cn.lingmoe.iam.domain.entity.SysDictType;
import cn.lingmoe.log.annotation.OperLog;
import cn.lingmoe.log.enums.BusinessType;
import cn.lingmoe.mybatis.page.PageRequest;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 字典类型 提供者
 *
 * @author yukdawn@gmail.com
 * @date 2019-05-20
 */
@RestController
@RequestMapping("/dict/type")
public class SysDictTypeController {

    @Autowired
    private ISysDictTypeService sysDictTypeService;

    /**
     * 查询字典类型
     */
    @GetMapping("/get/{dictId}")
    public ResponseEntity<SysDictType> get(@PathVariable("dictId") Long dictId) {
        return Results.ok(sysDictTypeService.selectDictTypeById(dictId));

    }

    /**
     * 查询字典类型列表
     */
    @GetMapping("/list")
//	@HasPermissions("system:dict:list")
    public ResponseEntity<PageInfo<SysDictType>> list(SysDictType sysDictType, PageRequest pageRequest) {
        return Results.ok(PageHelper.startPage(pageRequest).doSelectPageInfo(() -> sysDictTypeService.selectDictTypeList(sysDictType)));
    }


    /**
     * 新增保存字典类型
     */
    @OperLog(title = "字典类型", businessType = BusinessType.INSERT)
//    @HasPermissions("system:dict:add")
    @PostMapping("/save")
    public ResponseEntity<Void> addSave(@RequestBody SysDictType sysDictType) {
        sysDictTypeService.insertDictType(sysDictType);
        return Results.ok();
    }

    /**
     * 修改保存字典类型
     */
    @OperLog(title = "字典类型", businessType = BusinessType.UPDATE)
//    @HasPermissions("system:dict:edit")
    @PostMapping("/update")
    public ResponseEntity<Void> editSave(@RequestBody SysDictType sysDictType) {
        sysDictTypeService.updateDictType(sysDictType);
        return Results.ok();
    }

    /**
     * 删除字典类型
     *
     * @throws Exception
     */
    @OperLog(title = "字典类型", businessType = BusinessType.DELETE)
//    @HasPermissions("system:dict:remove")
    @PostMapping("/remove")
    public ResponseEntity<Void> remove(String ids) throws Exception {
        sysDictTypeService.deleteDictTypeByIds(ids);
        return Results.ok();
    }

}
