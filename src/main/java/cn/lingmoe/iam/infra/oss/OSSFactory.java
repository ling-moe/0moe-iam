package cn.lingmoe.iam.infra.oss;


import cn.hutool.json.JSONUtil;
import cn.lingmoe.common.utils.SpringUtils;
import cn.lingmoe.iam.app.service.ISysConfigService;

/**
 * 文件上传Factory
 */
public final class OSSFactory {
    private static ISysConfigService sysConfigService;

    static {
        OSSFactory.sysConfigService = SpringUtils.getBean(ISysConfigService.class);
    }

    public static CloudStorageService build() {
        String jsonconfig = sysConfigService.selectConfigByKey(CloudConstants.CLOUD_STORAGE_CONFIG_KEY);
        // 获取云存储配置信息
        CloudStorageConfig config = JSONUtil.parseObj(jsonconfig).toBean(CloudStorageConfig.class);
        if (config.getType() == CloudConstants.CloudService.QINIU.getValue()) {
            return new QiniuCloudStorageService(config);
        }
        return null;
    }
}
