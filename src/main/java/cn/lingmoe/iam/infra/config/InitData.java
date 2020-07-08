package cn.lingmoe.iam.infra.config;

import java.util.List;
import java.util.stream.Collectors;

import javax.print.attribute.standard.PrinterURI;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.json.JSONUtil;
import cn.lingmoe.gatewayclient.constants.GatewayClientConstants;
import cn.lingmoe.iam.domain.entity.PermissionInfo;
import cn.lingmoe.iam.infra.mapper.PermissionInfoMapper;
import cn.lingmoe.redis.multidb.RedisHelper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.collections4.keyvalue.MultiKey;
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
