package cn.lingmoe.iam.app.service.impl;

import cn.lingmoe.iam.app.service.RoleService;
import cn.lingmoe.iam.infra.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 角色表服务实现类
 *
 * @author yukdawn
 * @since 2019-04-21 15:53:40
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

}