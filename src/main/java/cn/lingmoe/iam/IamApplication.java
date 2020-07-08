package cn.lingmoe.iam;

import cn.lingmoe.core.anno.Enable0moeDiscoveryClient;
import cn.lingmoe.core.security.anno.Enable0moeOauthClient;
import cn.lingmoe.redis.anno.Enable0moeMultiDbRedis;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;

@Enable0moeMultiDbRedis
@Enable0moeDiscoveryClient
@Enable0moeOauthClient
@MapperScan("cn.lingmoe.iam.infra.mapper")
public class IamApplication {

    public static void main(String[] args) {
        SpringApplication.run(IamApplication.class, args);
    }

}
