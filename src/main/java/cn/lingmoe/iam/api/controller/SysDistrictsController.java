package cn.lingmoe.iam.api.controller;

import java.time.LocalDateTime;
import java.util.List;

import cn.lingmoe.common.Result.Results;
import cn.lingmoe.common.excel.utils.ExcelUtil;
import cn.lingmoe.core.security.helper.SecurityHelper;
import cn.lingmoe.iam.app.service.IDistrictsService;
import cn.lingmoe.iam.domain.entity.Districts;
import cn.lingmoe.log.annotation.OperLog;
import cn.lingmoe.log.enums.BusinessType;
import cn.lingmoe.mybatis.page.PageRequest;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 地区 信息操作处理
 *
 * @author ruoyi
 * @date 2018-12-19
 */
@RestController
@RequestMapping("/districts")
public class SysDistrictsController {
    @Autowired
    private IDistrictsService districtsService;

    /**
     * 查询地区列表
     */
//    @HasPermissions("system:districts:list")
    @RequestMapping("/list")
    public ResponseEntity<PageInfo<Districts>> list(Districts districts, PageRequest pageRequest) {
        return Results.ok(PageHelper.startPage(pageRequest).doSelectPageInfo(()->districtsService.selectDistrictsList(districts)));
    }

    /**
     * 导出地区列表
     */
//    @HasPermissions("system:districts:export")
    @OperLog(title = "地区", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public ResponseEntity<String> export(Districts districts) {
        List<Districts> list = districtsService.selectDistrictsList(districts);
        ExcelUtil<Districts> util = new ExcelUtil<Districts>(Districts.class);
        return Results.ok(util.exportExcel(list, "districts").getBody());
    }

    /**
     * 新增保存地区
     */
//    @HasPermissions("system:districts:add")
    @OperLog(title = "地区", businessType = BusinessType.INSERT)
    @PostMapping("/save")
    public ResponseEntity<Void> addSave(@RequestBody Districts districts) {
        districts.setPid(districts.getId() / 100);
        districts.setCreationDate(LocalDateTime.now());
        districts.setLastUpdateDate(LocalDateTime.now());
        districts.setOperator(SecurityHelper.getUserDetails().getRealName());
        districtsService.insertDistricts(districts);
        return Results.ok();
    }

    /**
     * /**
     * 修改保存地区
     */
//    @HasPermissions("system:districts:edit")
    @OperLog(title = "地区", businessType = BusinessType.UPDATE)
    @PostMapping("/update")
    public ResponseEntity<Void> editSave(@RequestBody Districts districts) {
        districts.setPid(districts.getId() / 100);
        districts.setOperator(SecurityHelper.getUserDetails().getRealName());
        districtsService.updateDistricts(districts);
        return Results.ok();
    }

    /**
     * 删除地区
     */
//    @HasPermissions("system:districts:remove")
    @OperLog(title = "地区", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    public ResponseEntity<Void> remove(String ids) {
        districtsService.deleteDistrictsByIds(ids);
        return Results.ok();
    }
}