package cn.lingmoe.iam.infra.repository.impl;

import cn.lingmoe.iam.infra.mapper.UserRoleMapper;
import cn.lingmoe.iam.infra.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 用户角色关系表服务实现类
 *
 * @author yukdawn
 * @since 2019-04-21 17:57:31
 */
@Component
public class UserRoleRepositoryImpl implements UserRoleRepository {
    @Autowired
    private UserRoleMapper userRoleMapper;

}