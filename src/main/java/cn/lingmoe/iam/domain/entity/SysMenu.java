package cn.lingmoe.iam.domain.entity;

import java.util.List;

import cn.lingmoe.mybatis.domain.BaseDomain;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 菜单权限表 sys_menu
 *
 * @author ruoyi
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysMenu extends BaseDomain {

    /**
     * 菜单ID
     */
    private Long menuId;
    /**
     * 菜单编码
     */
    private String menuCode;
    /**
     * 菜单名称
     */
    private String menuName;
    /**
     * 快捷方式
     */
    private String shortcut;
    /**
     * 父菜单ID
     */
    private Long parentId;
    /**
     * 菜单层级
     */
    private Integer level;
    /**
     * 显示顺序
     */
    private String orderNum;
    /**
     * 路由
      */
    private String router;
    /**
     * 类型:R目录,D菜单,M按钮
     */
    private String menuType;
    /**
     * 菜单图标
     */
    private String icon;
    /**
     * 菜单的绝对路径
     */
    private String levelPath;
    private boolean enabledFlag;
    /**
     * 子菜单
     */
    @TableField(exist = false)
    private List<SysMenu> children;
}
