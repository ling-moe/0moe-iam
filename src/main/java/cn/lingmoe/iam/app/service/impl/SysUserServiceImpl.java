package cn.lingmoe.iam.app.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.lingmoe.common.constant.UserConstants;
import cn.lingmoe.core.datascope.anno.DataScope;
import cn.lingmoe.core.exception.base.CommonException;
import cn.lingmoe.core.security.helper.SecurityHelper;
import cn.lingmoe.iam.app.service.ISysConfigService;
import cn.lingmoe.iam.app.service.ISysUserService;
import cn.lingmoe.iam.domain.entity.*;
import cn.lingmoe.iam.infra.mapper.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.lingmoe.iam.domain.entity.UserInfo;

/**
 * 用户 业务层处理
 *
 * @author ruoyi
 */
@Slf4j
@Service
public class SysUserServiceImpl implements ISysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysRoleMapper roleMapper;

    @Autowired
    private SysPostMapper postMapper;

    @Autowired
    private SysUserPostMapper userPostMapper;

    @Autowired
    private SysUserRoleMapper userRoleMapper;

    @Autowired
    private ISysConfigService configService;
    @Autowired
    private UserInfoMapper userInfoMapper;

    /**
     * 根据条件分页查询用户列表
     *
     * @param username 用户信息
     * @param password 密码
     */

    @Override
    public void register(String username, String password) {
        UserInfo userInfo = new UserInfo();
        userInfo.initUser(username, password);
        userInfoMapper.insert(userInfo);
    }
    @Override
    @DataScope(deptAlias = "d", userAlias = "u")
    public List<UserInfo> selectUserList(UserInfo user) {
        return sysUserMapper.selectUserList(user);
    }

    /**
     * 根据条件分页查询已分配用户角色列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    @DataScope(deptAlias = "d", userAlias = "u")
    public List<UserInfo> selectAllocatedList(UserInfo user) {
        return sysUserMapper.selectAllocatedList(user);
    }

    /**
     * 根据条件分页查询未分配用户角色列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    @DataScope(deptAlias = "d", userAlias = "u")
    public List<UserInfo> selectUnallocatedList(UserInfo user) {
        return sysUserMapper.selectUnallocatedList(user);
    }

    /**
     * 通过用户名查询用户
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    @Override
    public UserInfo selectUserByLoginName(String userName) {
        return sysUserMapper.selectUserByLoginName(userName);
    }

    /**
     * 通过手机号码查询用户
     *
     * @param phoneNumber 手机号码
     * @return 用户对象信息
     */
    @Override
    public UserInfo selectUserByPhoneNumber(String phoneNumber) {
        return sysUserMapper.selectUserByPhoneNumber(phoneNumber);
    }

    /**
     * 通过邮箱查询用户
     *
     * @param email 邮箱
     * @return 用户对象信息
     */
    @Override
    public UserInfo selectUserByEmail(String email) {
        return sysUserMapper.selectUserByEmail(email);
    }

    /**
     * 通过用户ID查询用户
     *
     * @param userId 用户ID
     * @return 用户对象信息
     */
    @Override
    public UserInfo selectUserById(Long userId) {
        return sysUserMapper.selectUserById(userId);
    }

    /**
     * 通过用户ID删除用户
     *
     * @param userId 用户ID
     * @return 结果
     */
    @Override
    public int deleteUserById(Long userId) {
        // 删除用户与角色关联
        userRoleMapper.deleteUserRoleByUserId(userId);
        // 删除用户与岗位表
        userPostMapper.deleteUserPostByUserId(userId);
        return sysUserMapper.deleteUserById(userId);
    }

    /**
     * 批量删除用户信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteUserByIds(String ids) throws CommonException {
        Long[] userIds = Convert.toLongArray(ids);
        for (Long userId : userIds) {
            if (UserInfo.isAdmin(userId)) {
                throw new CommonException("不允许删除超级管理员用户");
            }
        }
        return sysUserMapper.deleteUserByIds(userIds);
    }

    /**
     * 新增保存用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    @Transactional
    public int insertUser(UserInfo user) {
        // 新增用户信息
        int rows = sysUserMapper.insertUser(user);
        // 新增用户岗位关联
        insertUserPost(user);
        // 新增用户与角色管理
        insertUserRole(user);
        return rows;
    }

    /**
     * 修改保存用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    @Transactional
    public int updateUser(UserInfo user) {
        Long userId = user.getUserId();
        // 删除用户与角色关联
        userRoleMapper.deleteUserRoleByUserId(userId);
        // 新增用户与角色管理
        insertUserRole(user);
        // 删除用户与岗位关联
        userPostMapper.deleteUserPostByUserId(userId);
        // 新增用户与岗位管理
        insertUserPost(user);
        return sysUserMapper.updateUser(user);
    }

    /**
     * 修改用户个人详细信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int updateUserInfo(UserInfo user) {
        return sysUserMapper.updateUser(user);
    }

    /**
     * 修改用户密码
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int resetUserPwd(UserInfo user) {
        return updateUserInfo(user);
    }

    /**
     * 新增用户角色信息
     *
     * @param user 用户对象
     */
    public void insertUserRole(UserInfo user) {
        List<Long> roles = user.getRoleIds();
        if (CollUtil.isNotEmpty(roles)) {
            // 新增用户与角色管理
            List<SysUserRole> list = new ArrayList<SysUserRole>();
            for (Long roleId : roles) {
                SysUserRole ur = new SysUserRole();
                ur.setUserId(user.getUserId());
                ur.setRoleId(roleId);
                list.add(ur);
            }
            if (list.size() > 0) {
                userRoleMapper.batchUserRole(list);
            }
        }
    }

    /**
     * 新增用户岗位信息
     *
     * @param user 用户对象
     */
    public void insertUserPost(UserInfo user) {
        Long[] posts = user.getPostIds();
        if (ArrayUtil.isNotEmpty(posts)) {
            // 新增用户与岗位管理
            List<SysUserPost> list = new ArrayList<SysUserPost>();
            for (Long postId : posts) {
                SysUserPost up = new SysUserPost();
                up.setUserId(user.getUserId());
                up.setPostId(postId);
                list.add(up);
            }
            if (list.size() > 0) {
                userPostMapper.batchUserPost(list);
            }
        }
    }

    /**
     * 校验用户名称是否唯一
     *
     * @param loginName 用户名
     * @return
     */
    @Override
    public String checkLoginNameUnique(String loginName) {
        int count = sysUserMapper.checkLoginNameUnique(loginName);
        if (count > 0) {
            return UserConstants.USER_NAME_NOT_UNIQUE;
        }
        return UserConstants.USER_NAME_UNIQUE;
    }

    /**
     * 校验用户名称是否唯一
     *
     * @param user 用户信息
     * @return
     */
    @Override
    public String checkPhoneUnique(UserInfo user) {
        Long userId = Objects.isNull(user.getUserId()) ? -1L : user.getUserId();
        UserInfo info = sysUserMapper.checkPhoneUnique(user.getPhonenumber());
        if (Objects.nonNull(info) && info.getUserId().longValue() != userId.longValue()) {
            return UserConstants.USER_PHONE_NOT_UNIQUE;
        }
        return UserConstants.USER_PHONE_UNIQUE;
    }

    /**
     * 校验email是否唯一
     *
     * @param user 用户信息
     * @return
     */
    @Override
    public String checkEmailUnique(UserInfo user) {
        Long userId = Objects.isNull(user.getUserId()) ? -1L : user.getUserId();
        UserInfo info = sysUserMapper.checkEmailUnique(user.getEmail());
        if (Objects.nonNull(info) && info.getUserId().longValue() != userId.longValue()) {
            return UserConstants.USER_EMAIL_NOT_UNIQUE;
        }
        return UserConstants.USER_EMAIL_UNIQUE;
    }

    /**
     * 查询用户所属角色组
     *
     * @param userId 用户ID
     * @return 结果
     */
    @Override
    public String selectUserRoleGroup(Long userId) {
        List<RoleInfo> list = roleMapper.selectRolesByUserId(userId);
        StringBuffer idsStr = new StringBuffer();
        for (RoleInfo role : list) {
            idsStr.append(role.getRoleName()).append(",");
        }
        if (StrUtil.isNotBlank(idsStr.toString())) {
            return idsStr.substring(0, idsStr.length() - 1);
        }
        return idsStr.toString();
    }

    /**
     * 查询用户所属岗位组
     *
     * @param userId 用户ID
     * @return 结果
     */
    @Override
    public String selectUserPostGroup(Long userId) {
        List<SysPost> list = postMapper.selectPostsByUserId(userId);
        StringBuffer idsStr = new StringBuffer();
        for (SysPost post : list) {
            idsStr.append(post.getPostName()).append(",");
        }
        if (StrUtil.isNotBlank(idsStr.toString())) {
            return idsStr.substring(0, idsStr.length() - 1);
        }
        return idsStr.toString();
    }

    /**
     * 导入用户数据
     *
     * @param userList        用户数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName        操作用户
     * @return 结果
     */
    @Override
    public String importUser(List<UserInfo> userList, Boolean isUpdateSupport, String operName) {
        if (CollUtil.isNotEmpty(userList)) {
            throw new CommonException("导入用户数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        String password = configService.selectConfigByKey("sys.user.initPassword");
        for (UserInfo user : userList) {
            try {
                // 验证是否存在这个用户
                UserInfo u = sysUserMapper.selectUserByLoginName(user.getUsername());
                if (Objects.isNull(u)) {
                    user.setPassword(SecureUtil.md5(user.getUsername() + password));
                    user.setCreatedBy(SecurityHelper.getUserDetails().getUserId());
                    this.insertUser(user);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、账号 " + user.getUsername() + " 导入成功");
                } else if (isUpdateSupport) {
                    user.setCreatedBy(SecurityHelper.getUserDetails().getUserId());
                    this.updateUser(user);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、账号 " + user.getUsername() + " 更新成功");
                } else {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、账号 " + user.getUsername() + " 已存在");
                }
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、账号 " + user.getUsername() + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0) {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new CommonException(failureMsg.toString());
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }

    /**
     * 用户状态修改
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int changeStatus(UserInfo user) {
        if (UserInfo.isAdmin(user.getUserId())) {
            throw new CommonException("不允许修改超级管理员用户");
        }
        return sysUserMapper.updateUser(user);
    }

    /* (non-Javadoc)
     * @see com.ruoyi.system.service.ISysUserService#selectUserHasRole(java.lang.Long)
     */
    @Override
    public Set<Long> selectUserIdsHasRoles(Long[] roleIds) {
        return ArrayUtil.isNotEmpty(roleIds) ? sysUserMapper.selectUserIdsHasRoles(roleIds) : null;
    }

    /* (non-Javadoc)
     * @see com.ruoyi.system.service.ISysUserService#selectUserInDept(java.lang.Long)
     */
    @Override
    public Set<Long> selectUserIdsInDepts(Long[] deptIds) {
        return ArrayUtil.isNotEmpty(deptIds) ? sysUserMapper.selectUserIdsInDepts(deptIds) : null;
    }
}
