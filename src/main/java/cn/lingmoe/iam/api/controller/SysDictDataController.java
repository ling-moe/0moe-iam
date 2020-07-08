package cn.lingmoe.iam.api.controller;

import java.util.List;

import cn.lingmoe.common.Result.Results;
import cn.lingmoe.iam.app.service.ISysDictDataService;
import cn.lingmoe.iam.domain.entity.SysDictData;
import cn.lingmoe.log.annotation.OperLog;
import cn.lingmoe.log.enums.BusinessType;
import com.github.pagehelper.IPage;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 字典数据 提供者
 *
 * @author yukdawn@gmail.com
 * @date 2019-05-20
 */
@RestController
@RequestMapping("/dict/data")
public class SysDictDataController {

    @Autowired
    private ISysDictDataService sysDictDataService;

    /**
     * 查询字典数据
     */
    @GetMapping("/get/{dictCode}")
    public ResponseEntity<SysDictData> get(@PathVariable("dictCode") Long dictCode) {
        return Results.ok(sysDictDataService.selectDictDataById(dictCode));

    }

    /**
     * 查询字典数据列表
     */
    @GetMapping("/list")
//    @HasPermissions("system:dict:list")
    public ResponseEntity<PageInfo<SysDictData>> list(SysDictData sysDictData, IPage page) {
        return Results.ok(PageHelper.startPage(page).doSelectPageInfo(() -> sysDictDataService.selectDictDataList(sysDictData)));
    }

    /**
     * 根据字典类型查询字典数据信息
     *
     * @param dictType 字典类型
     * @return 参数键值
     */
    @GetMapping("/type")
    public ResponseEntity<List<SysDictData>> getType(String dictType) {
        return Results.ok(sysDictDataService.selectDictDataByType(dictType));
    }

    /**
     * 根据字典类型和字典键值查询字典数据信息
     *
     * @param dictType  字典类型
     * @param dictValue 字典键值
     * @return 字典标签
     */
    @GetMapping("/label")
    public ResponseEntity<String> getLabel(String dictType, String dictValue) {
        return Results.ok(sysDictDataService.selectDictLabel(dictType, dictValue));
    }


    /**
     * 新增保存字典数据
     */
    @OperLog(title = "字典数据", businessType = BusinessType.INSERT)
//    @HasPermissions("system:dict:add")
    @PostMapping("/save")
    public ResponseEntity<Void> addSave(@RequestBody SysDictData sysDictData) {
        sysDictDataService.insertDictData(sysDictData);
        return Results.ok();
    }

    /**
     * 修改保存字典数据
     */
    @OperLog(title = "字典数据", businessType = BusinessType.UPDATE)
//    @HasPermissions("system:dict:edit")
    @PostMapping("/update")
    public ResponseEntity<Void> editSave(@RequestBody SysDictData sysDictData) {
        sysDictDataService.updateDictData(sysDictData);
        return Results.ok();
    }

    /**
     * 删除字典数据
     */
    @OperLog(title = "字典数据", businessType = BusinessType.DELETE)
//    @HasPermissions("system:dict:remove")
    @PostMapping("/remove")
    public ResponseEntity<Void> remove(String ids) {
        sysDictDataService.deleteDictDataByIds(ids);
        return Results.ok();
    }

}
