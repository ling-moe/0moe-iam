package cn.lingmoe.iam.app.service.impl;

import java.util.*;

import cn.hutool.core.convert.Convert;
import cn.lingmoe.common.constant.UserConstants;
import cn.lingmoe.core.datascope.anno.DataScope;
import cn.lingmoe.core.exception.base.CommonException;
import cn.lingmoe.iam.app.service.ISysRoleService;
import cn.lingmoe.iam.domain.entity.RoleInfo;
import cn.lingmoe.iam.domain.entity.SysRoleDept;
import cn.lingmoe.iam.domain.entity.SysRoleMenu;
import cn.lingmoe.iam.domain.entity.SysUserRole;
import cn.lingmoe.iam.infra.mapper.SysRoleDeptMapper;
import cn.lingmoe.iam.infra.mapper.SysRoleMapper;
import cn.lingmoe.iam.infra.mapper.SysRoleMenuMapper;
import cn.lingmoe.iam.infra.mapper.SysUserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 角色 业务层处理
 *
 * @author ruoyi
 */
@Service
public class SysRoleServiceImpl implements ISysRoleService {
    @Autowired
    private SysRoleMapper roleMapper;

    @Autowired
    private SysRoleMenuMapper roleMenuMapper;

    @Autowired
    private SysUserRoleMapper userRoleMapper;

    @Autowired
    private SysRoleDeptMapper roleDeptMapper;

    /**
     * 根据条件分页查询角色数据
     *
     * @param role 角色信息
     * @return 角色数据集合信息
     */
    @Override
    @DataScope(deptAlias = "d")
    public List<RoleInfo> selectRoleList(RoleInfo role) {
        return roleMapper.selectRoleList(role);
    }

    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    @Override
    public Set<String> selectRoleKeys(Long userId) {
        List<RoleInfo> perms = roleMapper.selectRolesByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (RoleInfo perm : perms) {
            if (Objects.nonNull(perm)) {
                permsSet.addAll(Arrays.asList(perm.getRoleKey().trim().split(",")));
            }
        }
        return permsSet;
    }

    /**
     * 根据用户ID查询角色
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    @Override
    public List<RoleInfo> selectRolesByUserId(Long userId) {
        List<RoleInfo> userRoles = roleMapper.selectRolesByUserId(userId);
        List<RoleInfo> roles = selectRoleAll();
        for (RoleInfo role : roles) {
            for (RoleInfo userRole : userRoles) {
                if (Objects.equals(role.getRoleCode(), userRole.getRoleCode())) {
                    role.setFlag(true);
                    break;
                }
            }
        }
        return roles;
    }

    /**
     * 查询所有角色
     *
     * @return 角色列表
     */
    @Override
    public List<RoleInfo> selectRoleAll() {
        return selectRoleList(new RoleInfo());
    }

    /**
     * 通过角色ID查询角色
     *
     * @param roleId 角色ID
     * @return 角色对象信息
     */
    @Override
    public RoleInfo selectRoleById(Long roleId) {
        return roleMapper.selectRoleById(roleId);
    }

    /**
     * 通过角色ID删除角色
     *
     * @param roleId 角色ID
     * @return 结果
     */
    @Override
    public boolean deleteRoleById(Long roleId) {
        return roleMapper.deleteRoleById(roleId) > 0 ? true : false;
    }

    /**
     * 批量删除角色信息
     *
     * @param ids 需要删除的数据ID
     * @throws Exception
     */
    @Override
    public int deleteRoleByIds(String ids) throws CommonException {
        Long[] roleIds = Convert.toLongArray(ids);
        for (Long roleId : roleIds) {
            RoleInfo role = selectRoleById(roleId);
            if (countUserRoleByRoleId(roleId) > 0) {
                throw new CommonException(String.format("%1$s已分配,不能删除", role.getRoleName()));
            }
        }
        if (roleIds.length > 0) return roleMapper.deleteRoleByIds(roleIds);
        return 0;
    }

    /**
     * 新增保存角色信息
     *
     * @param role 角色信息
     * @return 结果
     */
    @Override
    @Transactional
    public int insertRole(RoleInfo role) {
        // 新增角色信息
        roleMapper.insertRole(role);
        return insertRoleMenu(role);
    }

    /**
     * 修改保存角色信息
     *
     * @param role 角色信息
     * @return 结果
     */
    @Override
    @Transactional
    public int updateRole(RoleInfo role) {
        // 修改角色信息
        roleMapper.updateRole(role);
        // 删除角色与菜单关联
//        roleMenuMapper.deleteRoleMenuByRoleId(role.getRoleCode());
        return insertRoleMenu(role);
    }

    /**
     * 修改数据权限信息
     *
     * @param role 角色信息
     * @return 结果
     */
    @Override
    @Transactional
    public int authDataScope(RoleInfo role) {
        // 修改角色信息
        roleMapper.updateRole(role);
        // 删除角色与部门关联
//        roleDeptMapper.deleteRoleDeptByRoleId(role.getRoleCode());
        // 新增角色和部门信息（数据权限）
        return insertRoleDept(role);
    }

    /**
     * 新增角色菜单信息
     *
     * @param role 角色对象
     */
    public int insertRoleMenu(RoleInfo role) {
        int rows = 1;
        // 新增用户与角色管理
        List<SysRoleMenu> list = new ArrayList<SysRoleMenu>();
        for (Long menuId : role.getMenuIds()) {
            SysRoleMenu rm = new SysRoleMenu();
//            rm.setRoleId(role.getRoleId());
            rm.setMenuId(menuId);
            list.add(rm);
        }
        if (list.size() > 0) {
            rows = roleMenuMapper.batchRoleMenu(list);
        }
        return rows;
    }

    /**
     * 新增角色部门信息(数据权限)
     *
     * @param role 角色对象
     */
    public int insertRoleDept(RoleInfo role) {
        int rows = 1;
        // 新增角色与部门（数据权限）管理
        List<SysRoleDept> list = new ArrayList<SysRoleDept>();
        for (Long deptId : role.getDeptIds()) {
            SysRoleDept rd = new SysRoleDept();
//            rd.setRoleId(role.getRoleCode());
            rd.setDeptId(deptId);
            list.add(rd);
        }
        if (list.size() > 0) {
            rows = roleDeptMapper.batchRoleDept(list);
        }
        return rows;
    }

    /**
     * 校验角色名称是否唯一
     *
     * @param role 角色信息
     * @return 结果
     */
    @Override
    public String checkRoleNameUnique(RoleInfo role) {
//        Long roleId = Objects.isNull(role.getRoleCode()) ? -1L : role.getRoleId();
//        RoleInfo info = roleMapper.checkRoleNameUnique(role.getRoleName());
//        if (Objects.nonNull(info) && info.getRoleId().longValue() != roleId.longValue()) {
//            return UserConstants.ROLE_NAME_NOT_UNIQUE;
//        }
        return UserConstants.ROLE_NAME_UNIQUE;
    }

    /**
     * 校验角色权限是否唯一
     *
     * @param role 角色信息
     * @return 结果
     */
    @Override
    public String checkRoleKeyUnique(RoleInfo role) {
//        Long roleId = Objects.isNull(role.getRoleCode()) ? -1L : role.getRoleId();
//        RoleInfo info = roleMapper.checkRoleKeyUnique(role.getRoleKey());
//        if (Objects.nonNull(info) && info.getRoleId().longValue() != roleId.longValue()) {
//            return UserConstants.ROLE_KEY_NOT_UNIQUE;
//        }
        return UserConstants.ROLE_KEY_UNIQUE;
    }

    /**
     * 通过角色ID查询角色使用数量
     *
     * @param roleId 角色ID
     * @return 结果
     */
    @Override
    public int countUserRoleByRoleId(Long roleId) {
        return userRoleMapper.countUserRoleByRoleId(roleId);
    }

    /**
     * 角色状态修改
     *
     * @param role 角色信息
     * @return 结果
     */
    @Override
    public int changeStatus(RoleInfo role) {
        return roleMapper.updateRole(role);
    }

    /**
     * 取消授权用户角色
     *
     * @param userRole 用户和角色关联信息
     * @return 结果
     */
    @Override
    public int deleteAuthUser(SysUserRole userRole) {
        return userRoleMapper.deleteUserRoleInfo(userRole);
    }

    /**
     * 批量取消授权用户角色
     *
     * @param roleId  角色ID
     * @param userIds 需要删除的用户数据ID
     * @return 结果
     */
    public int deleteAuthUsers(Long roleId, String userIds) {
        return userRoleMapper.deleteUserRoleInfos(roleId, Convert.toLongArray(userIds));
    }

    /**
     * 批量选择授权用户角色
     *
     * @param roleId  角色ID
     * @param userIds 需要删除的用户数据ID
     * @return 结果
     */
    public int insertAuthUsers(Long roleId, String userIds) {
        Long[] users = Convert.toLongArray(userIds);
        // 新增用户与角色管理
        List<SysUserRole> list = new ArrayList<SysUserRole>();
        for (Long userId : users) {
            SysUserRole ur = new SysUserRole();
            ur.setUserId(userId);
            ur.setRoleId(roleId);
            list.add(ur);
        }
        return userRoleMapper.batchUserRole(list);
    }
}
