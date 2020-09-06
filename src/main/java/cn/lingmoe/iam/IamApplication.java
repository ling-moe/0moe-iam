package cn.lingmoe.iam;

import cn.lingmoe.common.json.EnableObjectMapper;
import cn.lingmoe.core.anno.Enable0moeDiscoveryClient;
import cn.lingmoe.core.security.anno.Enable0moeAuth;
import cn.lingmoe.iam.infra.mapper.MenuMapper;
import cn.lingmoe.redis.anno.Enable0moeMultiDbRedis;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;

@Enable0moeMultiDbRedis
@Enable0moeDiscoveryClient
@Enable0moeAuth
@EnableObjectMapper
@MapperScan("cn.lingmoe.iam.infra.mapper")
public class IamApplication {

    @Autowired
    private MenuMapper menuMapper;

    public static void main(String[] args) {
        SpringApplication.run(IamApplication.class, args);
    }

//    2020-08-29 22:23:44.056  INFO 364 --- [           main] com.alibaba.druid.pool.DruidDataSource   : {dataSource-1} inited
//2020-08-29 22:23:44.059 DEBUG 364 --- [           main] c.l.iam.infra.mapper.MenuMapper.update   : ==>  Preparing: UPDATE iam_menu SET icon=?, object_version_number=?, menu_code=? WHERE (router = ? AND object_version_number = ?)
//2020-08-29 22:23:44.126 DEBUG 364 --- [           main] c.l.iam.infra.mapper.MenuMapper.update   : ==> Parameters: qweqe(String), 11(Long), noticeee(String), notice(String), 10(Long)
//            2020-08-29 22:23:44.143 DEBUG 364 --- [           main] c.l.iam.infra.mapper.MenuMapper.update   : <==    Updates: 0
//            2020-08-29 22:23:44.145 DEBUG 364 --- [           main] c.l.i.i.mapper.MenuMapper.updateById     : ==>  Preparing: UPDATE iam_menu SET icon=?, object_version_number=? WHERE menu_id=? AND object_version_number=?
//            2020-08-29 22:23:44.145 DEBUG 364 --- [           main] c.l.i.i.mapper.MenuMapper.updateById     : ==> Parameters: qweqe(String), 12(Long), 107(Long), 11(Long)
//            2020-08-29 22:23:44.161 DEBUG 364 --- [           main] c.l.i.i.mapper.MenuMapper.updateById     : <==    Updates: 0
}
