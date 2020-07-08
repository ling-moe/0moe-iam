package cn.lingmoe.iam.infra.oss;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;

import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

/**
 * 云存储配置信息
 */
@Data
public class CloudStorageConfig implements Serializable {
    //
    private static final long serialVersionUID = 9035033846176792944L;

    // 类型 1：七牛 2：阿里云 3：腾讯云
    @Range(min = 1, max = 3, message = "类型错误")
    private Integer type;

    // 七牛绑定的域名
    @NotBlank(message = "七牛绑定的域名不能为空", groups = QiniuGroup.class)
    @URL(message = "七牛绑定的域名格式不正确", groups = QiniuGroup.class)
    private String qiniuDomain;

    // 七牛路径前缀
    private String qiniuPrefix;

    // 七牛ACCESS_KEY
    @NotBlank(message = "七牛AccessKey不能为空", groups = QiniuGroup.class)
    private String qiniuAccessKey;

    // 七牛SECRET_KEY
    @NotBlank(message = "七牛SecretKey不能为空", groups = QiniuGroup.class)
    private String qiniuSecretKey;

    // 七牛存储空间名
    @NotBlank(message = "七牛空间名不能为空", groups = QiniuGroup.class)
    private String qiniuBucketName;

    /**
     * 校验组
     */
    public static interface QiniuGroup {
    }
}
