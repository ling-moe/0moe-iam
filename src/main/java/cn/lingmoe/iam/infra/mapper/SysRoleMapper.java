package cn.lingmoe.iam.infra.mapper;

import java.util.List;

import cn.lingmoe.iam.domain.entity.RoleInfo;


/**
 * 角色表 数据层
 *
 * @author ruoyi
 */
public interface SysRoleMapper {
    /**
     * 根据条件分页查询角色数据
     *
     * @param role 角色信息
     * @return 角色数据集合信息
     */
    List<RoleInfo> selectRoleList(RoleInfo role);

    /**
     * 根据用户ID查询角色
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    List<RoleInfo> selectRolesByUserId(Long userId);

    /**
     * 通过角色ID查询角色
     *
     * @param roleId 角色ID
     * @return 角色对象信息
     */
    RoleInfo selectRoleById(Long roleId);

    /**
     * 通过角色ID删除角色
     *
     * @param roleId 角色ID
     * @return 结果
     */
    int deleteRoleById(Long roleId);

    /**
     * 批量角色用户信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteRoleByIds(Long[] ids);

    /**
     * 修改角色信息
     *
     * @param role 角色信息
     * @return 结果
     */
    int updateRole(RoleInfo role);

    /**
     * 新增角色信息
     *
     * @param role 角色信息
     * @return 结果
     */
    int insertRole(RoleInfo role);

    /**
     * 校验角色名称是否唯一
     *
     * @param roleName 角色名称
     * @return 角色信息
     */
    RoleInfo checkRoleNameUnique(String roleName);

    /**
     * 校验角色权限是否唯一
     *
     * @param roleKey 角色权限
     * @return 角色信息
     */
    RoleInfo checkRoleKeyUnique(String roleKey);
}
