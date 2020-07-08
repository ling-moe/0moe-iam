package cn.lingmoe.iam.infra.repository.impl;

import cn.lingmoe.iam.infra.mapper.RoleMapper;
import cn.lingmoe.iam.infra.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 角色表服务实现类
 *
 * @author yukdawn
 * @since 2019-04-21 15:53:40
 */
@Component
public class RoleRepositoryImpl implements RoleRepository {
    @Autowired
    private RoleMapper roleMapper;

}