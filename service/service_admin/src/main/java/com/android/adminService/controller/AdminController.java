package com.android.adminService.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.android.adminService.entity.Admin;
import com.android.adminService.service.AdminService;
import commonutils.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author android
 * @since 2021-01-06
 */
@RestController
@RequestMapping("/adminService/admin")
@CrossOrigin
public class AdminController {
    @Autowired
    private AdminService adminService;

    @ApiOperation("所有管理员列表")
    @GetMapping("/findAll")
    private R findAll(){
        List<Admin> list = adminService.list(null);
        return R.ok().data("items",list);
    }

    @ApiOperation("分页查询")
    @GetMapping("/pageTeacher/{current}/{size}")
    private R pageAdmin(@PathVariable long current, @PathVariable long size){
        Page<Admin> teacherPage = new Page<>(current,size);
        adminService.page(teacherPage,null);
        long total = teacherPage.getTotal();
        List<Admin> records = teacherPage.getRecords();

        return R.ok().data("total",total).data("rows",records);
    }
    @ApiOperation("添加管理员")
    @PostMapping("/addAdmin")
    private R addAdmin(@RequestBody Admin admin){
        boolean save = adminService.save(admin);
        if(save){
            return R.ok();
        }else {
            return R.error();
        }
    }
    @ApiOperation("查询管理员")
    @GetMapping("/getAdmin/{id}")
    private R getAdmin(@PathVariable String id){
        Admin admin = adminService.getById(id);
        return R.ok().data("admin",admin);
    }
    @ApiOperation("更新管理员信息")
    @PostMapping("/updateAdmin")
    private R updateTeacher(@RequestBody Admin admin){
        boolean flag = adminService.updateById(admin);
        if(flag){
            return R.ok();
        }else {
            return R.error();
        }
    }
}

