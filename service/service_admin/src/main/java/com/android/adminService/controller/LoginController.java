package com.android.adminService.controller;

import commonutils.R;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/adminService")
@CrossOrigin//解决跨域
public class LoginController {

    @PostMapping("login")
    public R login(){
        return R.ok().data("token","admin");
    }

    @GetMapping("getInfo")
    public R getInfo(){
        return R.ok().data("roles","admin").data("name","android").data("avatar","http://01.minipic.eastday.com/20170515/20170515000032_d41d8cd98f00b204e9800998ecf8427e_9.jpeg");
    }
}
