package cn.lingmoe.iam.domain.entity;

import java.io.Serializable;

import cn.lingmoe.mybatis.domain.BaseDomain;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * iam_permission_info
 * @author
 */
@Setter
@Getter
public class PermissionInfo {
    /**
     * 权限编码
     */
    @TableId
    private String permissionCode;

    /**
     * 权限名称
     */
    private String permissionName;

    /**
     * 权限路由
     */
    private String path;

    /**
     * http方法
     */
    private String httpMethod;

    /**
     * 服务名称
     */
    private String serviceName;

    /**
     * 是否公开
     */
    private Boolean publicFlag;

    /**
     * java方法
     */
    private String javaMethod;
}