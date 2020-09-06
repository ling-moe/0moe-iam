package cn.lingmoe.iam.app.service;

import java.util.List;
import java.util.Set;

import cn.hutool.core.lang.tree.Tree;
import cn.lingmoe.common.datastruct.Ztree;
import cn.lingmoe.iam.domain.entity.RoleInfo;
import cn.lingmoe.iam.domain.entity.Menu;
import cn.lingmoe.iam.domain.entity.UserInfo;


/**
 * 菜单 业务层
 *
 * @author ruoyi
 */
public interface ISysMenuService {
    /**
     * 根据用户ID查询菜单
     *
     * @param user 用户信息
     * @return 菜单列表
     */
    List<Tree<Long>> selectMenusByUser(UserInfo user);

    /**
     * 查询系统菜单列表
     *
     * @param menu 菜单信息
     * @return 菜单列表
     */
    List<Tree<Long>> selectMenuList(Menu menu);

    /**
     * 查询菜单集合
     *
     * @return 所有菜单信息
     */
    List<Menu> selectMenuAll();

    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    Set<String> selectPermsByUserId(Long userId);


    /**
     * 根据角色ID查询菜单ID
     *
     * @param roleId 角色ID
     * @return 权限列表
     */
    List<Menu> selectMenuIdsByRoleId(Long roleId);

    /**
     * 根据角色ID查询菜单
     *
     * @param role 角色对象
     * @return 菜单列表
     */
    List<Ztree> roleMenuTreeData(RoleInfo role);

    /**
     * 删除菜单管理信息
     *
     * @param menuId 菜单ID
     * @return 结果
     */
    int deleteMenuById(Long menuId);

    /**
     * 根据菜单ID查询信息
     *
     * @param menuId 菜单ID
     * @return 菜单信息
     */
    Menu selectMenuById(Long menuId);

    /**
     * 查询菜单数量
     *
     * @param parentId 菜单父ID
     * @return 结果
     */
    int selectCountMenuByParentId(Long parentId);

    /**
     * 查询菜单使用数量
     *
     * @param menuId 菜单ID
     * @return 结果
     */
    int selectCountRoleMenuByMenuId(Long menuId);

    /**
     * 新增保存菜单信息
     *
     * @param menu 菜单信息
     * @return 结果
     */
    int insertMenu(Menu menu);

    /**
     * 修改保存菜单信息
     *
     * @param menu 菜单信息
     * @return 结果
     */
    int updateMenu(Menu menu);

    /**
     * 校验菜单名称是否唯一
     *
     * @param menu 菜单信息
     * @return 结果
     */
    String checkMenuNameUnique(Menu menu);
}
