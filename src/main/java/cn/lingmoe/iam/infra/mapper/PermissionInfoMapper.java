package cn.lingmoe.iam.infra.mapper;

import java.util.Set;

import cn.lingmoe.core.security.entity.Permission;
import cn.lingmoe.iam.domain.entity.PermissionInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author yukdawn@gmail.com 2020/5/13 下午11:18
 */
public interface PermissionInfoMapper extends BaseMapper<PermissionInfo> {

    Set<Permission> selectPermissionByRoleCode(@Param("roleCode") String roleCode);
}
