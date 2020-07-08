package cn.lingmoe.iam.api.controller;

import cn.lingmoe.common.Result.Results;
import cn.lingmoe.iam.app.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户表控制层
 *
 * @author yukdawn
 * @since 2019-04-21 15:53:40
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private ISysUserService userService;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestParam("username") String username, @RequestParam("password") String password) {
        userService.register(username, password);
        return Results.ok();
    }


}