package cn.lingmoe.iam.app.service.impl;

import java.util.List;

import cn.hutool.core.convert.Convert;
import cn.lingmoe.iam.app.service.IDistrictsService;
import cn.lingmoe.iam.domain.entity.Districts;
import cn.lingmoe.iam.infra.mapper.DistrictsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 地区 服务层实现
 *
 * @author ruoyi
 * @date 2018-12-19
 */
@Service
public class DistrictsServiceImpl implements IDistrictsService {
    @Autowired
    private DistrictsMapper districtsMapper;

    /**
     * 查询地区信息
     *
     * @param id 地区ID
     * @return 地区信息
     */
    @Override
    public Districts selectDistrictsById(Integer id) {
        return districtsMapper.selectDistrictsById(id);
    }

    /**
     * 查询地区列表
     *
     * @param districts 地区信息
     * @return 地区集合
     */
    @Override
    public List<Districts> selectDistrictsList(Districts districts) {
        return districtsMapper.selectDistrictsList(districts);
    }

    /**
     * 新增地区
     *
     * @param districts 地区信息
     * @return 结果
     */
    @Override
    public int insertDistricts(Districts districts) {
        return districtsMapper.insertDistricts(districts);
    }

    /**
     * 修改地区
     *
     * @param districts 地区信息
     * @return 结果
     */
    @Override
    public int updateDistricts(Districts districts) {
        return districtsMapper.updateDistricts(districts);
    }

    /**
     * 删除地区对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteDistrictsByIds(String ids) {
        return districtsMapper.deleteDistrictsByIds(Convert.toStrArray(ids));
    }

}
