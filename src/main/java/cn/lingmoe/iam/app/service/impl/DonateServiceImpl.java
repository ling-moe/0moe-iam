/*
 * @(#)DonateServiceImpl.java 2019年12月20日 下午2:13:36
 * Copyright 2019 yukdawn@gmail.com, Inc. All rights reserved.
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package cn.lingmoe.iam.app.service.impl;

import java.util.List;
import java.util.Objects;

import cn.hutool.core.util.StrUtil;
import cn.lingmoe.iam.app.service.IDonateService;
import cn.lingmoe.iam.domain.entity.Donate;
import cn.lingmoe.iam.infra.mapper.DonateMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * <p>File：DonateServiceImpl.java</p>
 * <p>Title: </p>
 * <p>Description:</p>
 * <p>Copyright: Copyright (c) 2019 2019年12月20日 下午2:13:36</p>
 * <p>Company: yukdawn@gmail.comit.com </p>
 *
 * @author yukdawn@gmail.com
 * @version 1.0
 */
@Service
public class DonateServiceImpl extends ServiceImpl<DonateMapper, Donate> implements IDonateService {
    @Autowired
    private DonateMapper donateMapper;

    @Override
    public List<Donate> selectDistrictsList(Donate donate) {
        QueryWrapper<Donate> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StrUtil.isNotBlank(donate.getNick()), "nick", donate.getNick());
        queryWrapper.eq(Objects.isNull(donate.getCanal()), "canal", donate.getCanal());
        queryWrapper.ge(StrUtil.isNotBlank(donate.getBeginTime()), "createTime", donate.getBeginTime());
        queryWrapper.le(StrUtil.isNotBlank(donate.getEndTime()), "createTime", donate.getEndTime());
        return donateMapper.selectList(queryWrapper);
    }
}
