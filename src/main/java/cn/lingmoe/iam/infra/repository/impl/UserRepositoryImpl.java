package cn.lingmoe.iam.infra.repository.impl;

import cn.lingmoe.iam.infra.mapper.UserInfoMapper;
import cn.lingmoe.iam.infra.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 用户表服务实现类
 *
 * @author yukdawn
 * @since 2019-04-21 15:53:40
 */
@Component
public class UserRepositoryImpl implements UserRepository {
    @Autowired
    private UserInfoMapper userInfoMapper;

}