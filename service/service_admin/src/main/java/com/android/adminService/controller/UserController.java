package com.android.adminService.controller;


import com.android.adminService.entity.LoginVo;
import com.android.adminService.entity.RegisterVo;
import com.android.adminService.entity.User;
import com.android.adminService.service.UserService;
import com.android.serviceBase.handler.ZkException;
import commonutils.JwtUtils;
import commonutils.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 *
 * @author android
 * @since 2021-01-06
 */
@RestController
@RequestMapping("/adminService/user")
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;

    @ApiOperation("所有用户列表")
    @GetMapping("/findAll")
    private R findAll(){
        List<User> list = userService.list(null);
        return R.ok().data("items",list);
    }
    @ApiOperation(value = "用户登录")
    @PostMapping("/login")
    public R login(@RequestBody LoginVo loginVo) {
        String token=null;
        try {
            token = userService.login(loginVo);
        }catch (ZkException e){
            return R.error().data("message",e.getMsg());
        }

        return R.ok().data("token", token);
    }

    @ApiOperation(value = "用户注册")
    @PostMapping("/register")
    public R register(@RequestBody RegisterVo registerVo){
        try {
            userService.register(registerVo);
        }catch (ZkException e){
            return R.error().data("message",e.getMsg());
        }
        return R.ok();
    }

    @ApiOperation(value = "根据token获取登录信息")
    @GetMapping("/getUserByToken")
    public R getUserByToken(HttpServletRequest request){
        int userId = JwtUtils.getMemberIdByJwtToken(request);
        User user = userService.getById(userId);
        return R.ok().data("user", user);
    }



}

