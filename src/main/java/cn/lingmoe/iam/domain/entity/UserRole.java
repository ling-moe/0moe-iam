package cn.lingmoe.iam.domain.entity;

import java.util.Date;

import cn.lingmoe.mybatis.domain.BaseDomain;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户角色关系表(UserRole)实体类
 *
 * @author yukdawn
 * @since 2019-04-21 17:57:31
 */
@Setter
@Getter
public class UserRole extends BaseDomain {
    /**
     * 表ID，主键，供其他表做外键
     **/
    @TableId(type = IdType.AUTO)
    private Long userRoleId;
    /**
     * 用户id
     **/
    private Long userId;
    /**
     * 角色编码
     **/
    private String roleCode;
    /**
     * 是否启用, 1是0否
     **/
    private boolean enabledFlag;
    /**
     * 是否是默认角色
     */
    private boolean defaultRoleFlag;
}