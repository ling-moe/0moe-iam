package cn.lingmoe.iam.infra.mapper;

import java.util.List;

import cn.lingmoe.iam.domain.entity.Districts;


/**
 * 地区 数据层
 *
 * @author ruoyi
 * @date 2018-12-19
 */
public interface DistrictsMapper {
    /**
     * 查询地区信息
     *
     * @param id 地区ID
     * @return 地区信息
     */
    Districts selectDistrictsById(Integer id);

    /**
     * 查询地区列表
     *
     * @param districts 地区信息
     * @return 地区集合
     */
    List<Districts> selectDistrictsList(Districts districts);

    /**
     * 新增地区
     *
     * @param districts 地区信息
     * @return 结果
     */
    int insertDistricts(Districts districts);

    /**
     * 修改地区
     *
     * @param districts 地区信息
     * @return 结果
     */
    int updateDistricts(Districts districts);

    /**
     * 删除地区
     *
     * @param id 地区ID
     * @return 结果
     */
    int deleteDistrictsById(Integer id);

    /**
     * 批量删除地区
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteDistrictsByIds(String[] ids);

}