package cn.lingmoe.iam.domain.entity;

import java.util.Date;
import java.util.List;
import java.util.Set;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ReUtil;
import cn.lingmoe.common.constant.BaseRegexs;
import cn.lingmoe.common.excel.anno.Excel;
import cn.lingmoe.iamclient.utils.UserPasswordEncoder;
import cn.lingmoe.mybatis.domain.BaseDomain;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户表(User)实体类
 *
 * @author yukdawn
 * @since 2019-04-21 15:53:40
 */
@Getter
@Setter
public class UserInfo extends BaseDomain {

    public static final String FIELD_ROLE_LIST = "roleList";

    public void initUser(String username, String password){
        Assert.isTrue(ReUtil.isMatch(BaseRegexs.USERNAME, username), "用户名不符合要求, 只能包含数字和字母");
//        Assert.isTrue(ReUtil.isMatch(Regexs.PASSWORD, password), "密码强度低, 至少包含两种不同类型字符");
        this.username = username;
        this.password = UserPasswordEncoder.passwordEncoder().encode(password);
        this.nickName = username;
        this.expiredFlag = Boolean.FALSE;
        this.locakedFlag = Boolean.FALSE;
        this.passwordExpiredFlag = Boolean.FALSE;
        this.enabledFlag = Boolean.TRUE;
    }

    public UserInfo(String username) {
        this.username = username;
    }

    public UserInfo() {
    }

    /**
     * 表ID，主键，供其他表做外键
     **/
    @TableId(type = IdType.AUTO)
    private Long userId;
    /**
     * 登录名
     **/
    private String username;
    /**
     * 昵称
     **/
    private String nickName;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 密码
     **/
    private String password;
    /**
     * 是否过期, 1过期0没过期
     **/
    private Boolean expiredFlag;
    /**
     * 是否锁定, 1是0否
     **/
    private Boolean locakedFlag;
    /**
     * 是否启用, 1是0否
     **/
    private Boolean enabledFlag;
    /**
     * 密码是否过期, 1是0否
     **/
    private Boolean passwordExpiredFlag;
    /**
     * 角色List
     */
    @TableField(exist = false)
    private Set<RoleInfo> roleInfoList;

    public boolean isAdmin() {
        return isAdmin(this.userId);
    }

    public static boolean isAdmin(Long userId) {
        return userId != null && 1L == userId;
    }


    private static final long serialVersionUID = 1L;


    /**
     * 部门ID
     */
    @TableField(exist = false)
    private Long deptId;

    /**
     * 部门父ID
     */
    @TableField(exist = false)
    private Long parentId;

    /**
     * 手机号码
     */
    @Excel(name = "手机号码")
    private String phonenumber;

    /**
     * 用户性别
     */
    @Excel(name = "用户性别", readConverterExp = "0=男,1=女,2=未知")
    private String sex;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 盐加密
     */
    @TableField(exist = false)
    private String salt;

    /**
     * 帐号状态（0正常 1停用）
     */
    @Excel(name = "帐号状态", readConverterExp = "0=正常,1=停用")
    private String status;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private String delFlag;

    /**
     * 最后登陆IP
     */
    @Excel(name = "最后登陆IP", type = Excel.Type.EXPORT)
    private String loginIp;

    /**
     * 最后登陆时间
     */
    @Excel(name = "最后登陆时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss", type = Excel.Type.EXPORT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date loginDate;

    /**
     * 部门对象
     */
    @Excel(name = "部门名称", targetAttr = "deptName", type = Excel.Type.EXPORT)
    @TableField(exist = false)
    private SysDept dept;

    @TableField(exist = false)
    private List<RoleInfo> roles;

    /**
     * 角色组
     */
    @TableField(exist = false)
    private List<Long> roleIds;

    /**
     * 岗位组
     */
    @TableField(exist = false)
    private Long[] postIds;

    @TableField(exist = false)
    private Set<String> buttons;
}