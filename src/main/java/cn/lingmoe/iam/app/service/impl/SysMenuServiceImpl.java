package cn.lingmoe.iam.app.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.util.StrUtil;
import cn.lingmoe.common.constant.UserConstants;
import cn.lingmoe.common.datastruct.Ztree;
import cn.lingmoe.core.security.helper.SecurityHelper;
import cn.lingmoe.iam.app.service.ISysMenuService;
import cn.lingmoe.iam.domain.entity.RoleInfo;
import cn.lingmoe.iam.domain.entity.Menu;
import cn.lingmoe.iam.domain.entity.UserInfo;
import cn.lingmoe.iam.infra.mapper.MenuMapper;
import cn.lingmoe.iam.infra.mapper.SysRoleMenuMapper;
import cn.lingmoe.redis.cache.annotation.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 菜单 业务层处理
 *
 * @author ruoyi
 */
@Service
public class SysMenuServiceImpl implements ISysMenuService {

    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private SysRoleMenuMapper roleMenuMapper;

    /**
     * 根据用户查询菜单
     *
     * @param user 用户信息
     * @return 菜单列表
     */
    @Override
    public List<Tree<Long>> selectMenusByUser(UserInfo user) {
        System.out.println(SecurityHelper.getUserDetails());
        user.setUserId(2L);
        List<Menu> menus;
        menus = menuMapper.selectMenuNormalAll();
        // 管理员显示所有菜单信息
//        if (user.isAdmin()) {
//            menus = menuMapper.selectMenuNormalAll();
//        } else {
//            menus = menuMapper.selectMenusByUserId(user.getUserId());
//        }
        return this.menuListToTree(menus);
    }

    /**
     * 查询菜单集合
     *
     * @return 所有菜单信息
     */
    @Override
    public List<Tree<Long>> selectMenuList(Menu menuDTO) {
        List<Menu> menus = menuMapper.selectMenuList(menuDTO);
        return this.menuListToTree(menus);
    }

    /**
     * 查询菜单集合
     *
     * @return 所有菜单信息
     */
    @Override
    public List<Menu> selectMenuAll() {
        return menuMapper.selectMenuAll();
    }

    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    @Override
    @RedisCache(key = "user_perms", fieldKey = "#userId")
    public Set<String> selectPermsByUserId(Long userId) {
        List<String> perms = menuMapper.selectPermsByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (String perm : perms) {
            if (StrUtil.isNotBlank(perm)) {
                permsSet.addAll(Arrays.asList(perm.trim().split(",")));
            }
        }
        return permsSet;
    }

    @Override
    public List<Menu> selectMenuIdsByRoleId(Long roleId) {
        return menuMapper.selectMenuIdsByRoleId(roleId);
    }

    /**
     * 根据角色ID查询菜单
     *
     * @param role 角色对象
     * @return 菜单列表
     */
    @Override
    public List<Ztree> roleMenuTreeData(RoleInfo role) {
//        Long roleId = role.getRoleId();
//        List<Ztree> ztrees = new ArrayList<Ztree>();
//        List<SysMenu> menuList = menuMapper.selectMenuAll();
//        if (Objects.nonNull(roleId)) {
//            List<String> roleMenuList = menuMapper.selectMenuTree(roleId);
//            ztrees = initZtree(menuList, roleMenuList, true);
//        } else {
//            ztrees = initZtree(menuList, null, true);
//        }
        return null;
    }

    /**
     * 菜单list转tree
     * @param menuList 菜单list
     * @return 菜单树
     */
    private List<Tree<Long>> menuListToTree(List<Menu> menuList){
        return TreeUtil
                .build(menuList.stream()
                        .map(menu -> new TreeNode<>(menu.getMenuId(), menu.getParentId(), menu.getMenuName(), menu.getOrderNum())
                                .setExtra(BeanUtil.beanToMap(menu)))
                        .collect(Collectors.toList()), 0L);
    }

    /**
     * 删除菜单管理信息
     *
     * @param menuId 菜单ID
     * @return 结果
     */
    @Override
    public int deleteMenuById(Long menuId) {
        return menuMapper.deleteMenuById(menuId);
    }

    /**
     * 根据菜单ID查询信息
     *
     * @param menuId 菜单ID
     * @return 菜单信息
     */
    @Override
    public Menu selectMenuById(Long menuId) {
        return menuMapper.selectMenuById(menuId);
    }

    /**
     * 查询子菜单数量
     *
     * @param parentId 父级菜单ID
     * @return 结果
     */
    @Override
    public int selectCountMenuByParentId(Long parentId) {
        return menuMapper.selectCountMenuByParentId(parentId);
    }

    /**
     * 查询菜单使用数量
     *
     * @param menuId 菜单ID
     * @return 结果
     */
    @Override
    public int selectCountRoleMenuByMenuId(Long menuId) {
        return roleMenuMapper.selectCountRoleMenuByMenuId(menuId);
    }

    /**
     * 新增保存菜单信息
     *
     * @param menu 菜单信息
     * @return 结果
     */
    @Override
    public int insertMenu(Menu menu) {
        return menuMapper.insert(menu);
    }

    /**
     * 修改保存菜单信息
     *
     * @param menu 菜单信息
     * @return 结果
     */
    @Override
    public int updateMenu(Menu menu) {
        return menuMapper.updateMenu(menu);
    }

    /**
     * 校验菜单名称是否唯一
     *
     * @param menu 菜单信息
     * @return 结果
     */
    @Override
    public String checkMenuNameUnique(Menu menu) {
        Long menuId = Objects.isNull(menu.getMenuId()) ? -1L : menu.getMenuId();
        Menu info = menuMapper.checkMenuNameUnique(menu.getMenuName(), menu.getParentId());
        if (Objects.nonNull(info) && info.getMenuId().longValue() != menuId.longValue()) {
            return UserConstants.MENU_NAME_NOT_UNIQUE;
        }
        return UserConstants.MENU_NAME_UNIQUE;
    }

}
