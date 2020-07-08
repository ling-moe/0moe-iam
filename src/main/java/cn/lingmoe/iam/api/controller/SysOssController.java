package cn.lingmoe.iam.api.controller;

import java.io.IOException;
import java.util.Date;

import cn.hutool.json.JSONUtil;
import cn.lingmoe.common.Result.Results;
import cn.lingmoe.core.exception.file.OssException;
import cn.lingmoe.core.security.helper.SecurityHelper;
import cn.lingmoe.core.valid.ValidatorUtils;
import cn.lingmoe.iam.app.service.ISysConfigService;
import cn.lingmoe.iam.app.service.ISysOssService;
import cn.lingmoe.iam.domain.entity.SysOss;
import cn.lingmoe.iam.infra.oss.CloudConstants;
import cn.lingmoe.iam.infra.oss.CloudStorageConfig;
import cn.lingmoe.iam.infra.oss.CloudStorageService;
import cn.lingmoe.iam.infra.oss.OSSFactory;
import cn.lingmoe.mybatis.page.PageRequest;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传 提供者
 *
 * @author yukdawn@gmail.com
 * @date 2019-05-16
 */
@RestController
@RequestMapping("/oss")
public class SysOssController {
    private final static String KEY = CloudConstants.CLOUD_STORAGE_CONFIG_KEY;

    @Autowired
    private ISysOssService sysOssService;

    @Autowired
    private ISysConfigService sysConfigService;

    /**
     * 云存储配置信息
     */
    @RequestMapping("/config")
//    @HasPermissions("sys:oss:config")
    public ResponseEntity<CloudStorageConfig> config() {
        String jsonconfig = sysConfigService.selectConfigByKey(CloudConstants.CLOUD_STORAGE_CONFIG_KEY);
        // 获取云存储配置信息
        CloudStorageConfig config = JSONUtil.parse(jsonconfig).toBean(CloudStorageConfig.class);
        return Results.ok(config);
    }

    /**
     * 保存云存储配置信息
     */
    @RequestMapping("/saveConfig")
//    @HasPermissions("sys:oss:config")
    public ResponseEntity<Void> saveConfig(CloudStorageConfig config) {
        // 校验类型
        ValidatorUtils.validateEntity(config);
        if (config.getType() == CloudConstants.CloudService.QINIU.getValue()) {
            // 校验七牛数据
            ValidatorUtils.validateEntity(config, CloudStorageConfig.QiniuGroup.class);
        }
        sysConfigService.updateValueByKey(KEY, new Gson().toJson(config));
        return Results.ok();
    }

    /**
     * 查询文件上传
     */
    @GetMapping("/get/{id}")
    public ResponseEntity<SysOss> get(@PathVariable("id") Long id) {
        return Results.ok(sysOssService.selectSysOssById(id));
    }

    /**
     * 查询文件上传列表
     */
    @GetMapping("/list")
    public ResponseEntity<PageInfo<SysOss>> list(SysOss sysOss, PageRequest pageRequest) {
        return Results.ok(PageHelper.startPage(pageRequest).doSelectPageInfo(()->sysOssService.selectSysOssList(sysOss)));
    }

    /**
     * 修改
     */
    @PostMapping("/update")
//    @HasPermissions("sys:oss:edit")
    public ResponseEntity<Void> editSave(@RequestBody SysOss sysOss) {
        sysOssService.updateSysOss(sysOss);
        return Results.ok();
    }

    /**
     * 修改保存文件上传
     *
     * @throws IOException
     */
    @PostMapping("/upload")
//    @HasPermissions("sys:oss:add")
    public ResponseEntity<Void> editSave(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new OssException("上传文件不能为空");
        }
        // 上传文件
        String fileName = file.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        CloudStorageService storage = OSSFactory.build();
        String url = storage.uploadSuffix(file.getBytes(), suffix);
        // 保存文件信息
        SysOss ossEntity = new SysOss();
        ossEntity.setUrl(url);
        ossEntity.setFileSuffix(suffix);
        ossEntity.setCreateBy(SecurityHelper.getUserDetails().getUsername());
        ossEntity.setFileName(fileName);
        ossEntity.setCreateTime(new Date());
        ossEntity.setService(storage.getService());
        sysOssService.insertSysOss(ossEntity);
        return Results.ok();
    }

    /**
     * 删除文件上传
     */
    @PostMapping("/remove")
//    @HasPermissions("sys:oss:remove")
    public ResponseEntity<Void> remove(String ids) {
        sysOssService.deleteSysOssByIds(ids);
        return Results.ok();
    }
}
