/*
 * @(#)DonateController.java 2019年12月20日 下午2:32:25
 * Copyright 2019 yukdawn@gmail.com, Inc. All rights reserved.
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package cn.lingmoe.iam.api.controller;


import cn.lingmoe.common.Result.Results;
import cn.lingmoe.iam.app.service.IDonateService;
import cn.lingmoe.iam.domain.entity.Donate;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author yukdawn@gmail.com
 * @version 1.0
 */
@RestController
@RequestMapping("/donate")
public class DonateController {
    @Autowired
    private IDonateService donateService;

    @GetMapping("/list")
    public ResponseEntity<PageInfo<Donate>> list(Donate donate, @SortDefault Page<Donate> page) {
        return Results.ok(PageHelper.startPage(page)
                .doSelectPageInfo(() -> donateService.selectDistrictsList(donate)));
    }
}
