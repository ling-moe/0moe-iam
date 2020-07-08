package cn.lingmoe.iam.app.service.impl;

import cn.lingmoe.iam.app.service.UserRoleService;
import cn.lingmoe.iam.infra.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户角色关系表服务实现类
 *
 * @author yukdawn
 * @since 2019-04-21 17:57:31
 */
@Service
public class UserRoleServiceImpl implements UserRoleService {
    @Autowired
    private UserRoleRepository userRoleRepository;

}