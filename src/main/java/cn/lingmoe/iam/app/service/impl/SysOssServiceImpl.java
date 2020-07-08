package cn.lingmoe.iam.app.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import cn.lingmoe.iam.app.service.ISysOssService;
import cn.lingmoe.iam.domain.entity.SysOss;
import cn.lingmoe.iam.infra.mapper.SysOssMapper;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

/**
 * 文件上传 服务层实现
 *
 * @author yukdawn@gmail.com
 * @date 2019-05-16
 */
@Service
public class SysOssServiceImpl implements ISysOssService {
    @Autowired
    private SysOssMapper sysOssMapper;

    /**
     * 查询文件上传信息
     *
     * @param id 文件上传ID
     * @return 文件上传信息
     */
    @Override
    public SysOss selectSysOssById(Long id) {
        return sysOssMapper.selectById(id);
    }

    /**
     * 查询文件上传列表
     *
     * @param sysOss 文件上传信息
     * @return 文件上传集合
     */
    @Override
    public List<SysOss> selectSysOssList(SysOss sysOss) {
        QueryWrapper<SysOss> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(sysOss.getFileName())) {
            queryWrapper.like("fileName", sysOss.getFileName());
        }
        if (StringUtils.isNotBlank(sysOss.getFileSuffix())) {
            queryWrapper.eq("fileSuffix", sysOss.getFileSuffix());
        }
        if (StringUtils.isNotBlank(sysOss.getCreateBy())) {
            queryWrapper.like("createBy", sysOss.getCreateBy());
        }
        return sysOssMapper.selectList(queryWrapper);
    }

    /**
     * 新增文件上传
     *
     * @param sysOss 文件上传信息
     * @return 结果
     */
    @Override
    public int insertSysOss(SysOss sysOss) {
        return sysOssMapper.insert(sysOss);
    }

    /**
     * 修改文件上传
     *
     * @param sysOss 文件上传信息
     * @return 结果
     */
    @Override
    public int updateSysOss(SysOss sysOss) {
        return sysOssMapper.updateById(sysOss);
    }

    /**
     * 删除文件上传对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysOssByIds(String ids) {
        return sysOssMapper.deleteBatchIds(Stream.of(ids.split(",")).collect(Collectors.toList()));
    }

}
