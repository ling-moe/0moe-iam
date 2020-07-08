package cn.lingmoe.iam.api.controller;

import cn.lingmoe.iam.app.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 角色表控制层
 *
 * @author yukdawn
 * @since 2019-04-21 15:53:40
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

}