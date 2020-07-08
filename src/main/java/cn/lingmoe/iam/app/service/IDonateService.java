package cn.lingmoe.iam.app.service;

import java.util.List;

import cn.lingmoe.iam.domain.entity.Donate;
import com.baomidou.mybatisplus.extension.service.IService;


/**
 * 捐赠 服务层
 */
public interface IDonateService extends IService<Donate> {
    /**
     * 查询捐赠列表
     *
     * @param donate 捐赠信息
     * @return 捐赠集合
     */
    public List<Donate> selectDistrictsList(Donate donate);
}
