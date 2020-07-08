package cn.lingmoe.iam.domain.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * 用户和角色关联 sys_user_role
 *
 * @author ruoyi
 */
@Getter
@Setter
public class SysUserRole {
    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 角色ID
     */
    private Long roleId;
}
