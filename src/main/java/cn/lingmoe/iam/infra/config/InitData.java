package cn.lingmoe.iam.infra.config;



import cn.lingmoe.iam.infra.mapper.PermissionInfoMapper;
import cn.lingmoe.redis.multidb.RedisHelper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author yukdawn@gmail.com 2020/5/12 下午9:13
 */
@Component
public class InitData implements InitializingBean {
    @Autowired
    private PermissionInfoMapper permissionInfoMapper;
    @Autowired
    private RedisHelper redisHelper;

    @Override
    public void afterPropertiesSet() {
//        List<PermissionInfo> permissionInfoList = permissionInfoMapper.selectList(new QueryWrapper<PermissionInfo>().lambda().eq(PermissionInfo::getPublicFlag, true));
//        permissionInfoList.stream()
//                .collect(Collectors.groupingBy(permissionInfo ->
//                        new MultiKey<>(permissionInfo.getServiceName(), permissionInfo.getHttpMethod())))
//                .forEach((k, v)->
//                        redisHelper.setAdd(GatewayClientConstants.getPublicRouterKey(k.getKey(0),k.getKey(1)),
//                                v.stream().map(JSONUtil::toJsonStr).toArray(String[]::new)));
    }
}
