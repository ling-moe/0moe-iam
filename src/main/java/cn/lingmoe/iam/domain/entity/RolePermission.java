package cn.lingmoe.iam.domain.entity;


import cn.lingmoe.mybatis.domain.BaseDomain;
import lombok.Getter;
import lombok.Setter;

/**
 * iam_role_permission
 * @author
 */
@Setter
@Getter
public class RolePermission extends BaseDomain {
    /**
     * 主键
     */
    private Long rolePermissionId;

    /**
     * 角色编码
     */
    private String roleCode;

    /**
     * 权限编码
     */
    private String permissionCode;
}