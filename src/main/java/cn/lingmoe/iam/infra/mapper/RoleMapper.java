package cn.lingmoe.iam.infra.mapper;

import java.util.Set;

import cn.lingmoe.iam.domain.entity.RoleInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * 角色表(Role)表数据库访问层
 *
 * @author yukdawn
 * @since 2019-04-21 15:53:40
 */
public interface RoleMapper extends BaseMapper<RoleInfo> {

    Set<RoleInfo> selectRoleByUserId(@Param("userId") Long userId);
}