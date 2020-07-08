package cn.lingmoe.iam.domain.entity;


import java.util.List;

import cn.lingmoe.common.excel.anno.Excel;
import cn.lingmoe.mybatis.domain.BaseDomain;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;
import lombok.Setter;

/**
 * 角色表(Role)实体类
 *
 * @author yukdawn
 * @since 2019-04-21 15:53:40
 */
@Getter
@Setter
public class RoleInfo extends BaseDomain {

    public static final String ROLE_BASE = "BASE";
    public static final String ROLE_MANAGER = "MANAGER";
    public static final String ROLE_ADMIN = "ADMIN";
    /**
     * 角色编码
     **/
    @TableId
    private String roleCode;
    /**
     * 角色名称
     **/
    private String roleName;
    /**
     * 是否启用
     */
    private boolean enabledFlag;

    private static final long serialVersionUID = 1L;



    /**
     * 角色权限
     */
    @Excel(name = "角色权限")
    private String roleKey;

    /**
     * 角色排序
     */
    @Excel(name = "角色排序")
    private String roleSort;

    /**
     * 数据范围（1：所有数据权限；2：自定数据权限）
     */
    @Excel(name = "数据范围", readConverterExp = "1=所有数据权限,2=自定义数据权限")
    private String dataScope;

    /**
     * 角色状态（0正常 1停用）
     */
    @Excel(name = "角色状态", readConverterExp = "0=正常,1=停用")
    private String status;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private String delFlag;

    /**
     * 用户是否存在此角色标识 默认不存在
     */
    private boolean flag = false;

    /**
     * 菜单组
     */
    private List<Long> menuIds;

    /**
     * 部门组（数据权限）
     */
    private Long[] deptIds;
}