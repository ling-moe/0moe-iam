package cn.lingmoe.iam.app.service.impl;

import java.util.Set;

import cn.hutool.core.bean.BeanUtil;
import cn.lingmoe.core.security.entity.Permission;
import cn.lingmoe.core.security.entity.User;
import cn.lingmoe.iam.domain.entity.UserInfo;
import cn.lingmoe.iam.infra.mapper.PermissionInfoMapper;
import cn.lingmoe.iam.infra.mapper.UserInfoMapper;
import cn.lingmoe.iamclient.provider.IamClient;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yukdawn@gmail.com 2020/5/13 下午8:56
 */
@Service
@DubboService
public class IamClientImpl implements IamClient {
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private PermissionInfoMapper permissionInfoMapper;
    @Override
    public User selectByUsername(String username) {
        UserInfo iamUserInfo = userInfoMapper.selectOne(new QueryWrapper<UserInfo>().lambda().eq(UserInfo::getUsername,username));
        User user = new User();
        BeanUtil.copyProperties(iamUserInfo, user);
        user.setAdminFlag(true);
        return user;
    }

    @Override
    public Set<Permission> selectByRoleCode(String roleCode) {
        return permissionInfoMapper.selectPermissionByRoleCode(roleCode);
    }
}
