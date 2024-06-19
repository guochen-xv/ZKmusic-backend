package com.android.adminService.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.android.adminService.entity.Banner;
import com.android.adminService.service.BannerService;
import commonutils.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/adminService/banner")
@CrossOrigin
public class BannerController {
    @Autowired
    private BannerService bannerService;

    @ApiOperation(value = "获取Banner分页列表")
    @GetMapping("/pageBanner/{current}/{size}")
    private R pageBanner(@PathVariable long current, @PathVariable long size){
        Page<Banner> bannerPage = new Page<>(current,size);
        bannerService.page(bannerPage,null);
        long total = bannerPage.getTotal();
        List<Banner> records = bannerPage.getRecords();
        return R.ok().data("total",total).data("rows",records);
    }

    @ApiOperation(value = "获取Banner")
    @GetMapping("/getBanner/{id}")
    public R get(@PathVariable String id) {
        Banner banner = bannerService.getById(id);
        return R.ok().data("banner", banner);
    }

    @ApiOperation(value = "新增Banner")
    @PostMapping("/addBanner")
    public R save(@RequestBody Banner banner) {
        boolean save = bannerService.save(banner);
        if(save){
            return R.ok();
        }else {
            return R.error();
        }
    }

    @ApiOperation(value = "修改Banner")
    @PostMapping("/updateBanner")
    public R updateById(@RequestBody Banner banner) {
        boolean flag = bannerService.updateById(banner);
        if(flag){
            return R.ok();
        }else {
            return R.error();
        }
    }

    @ApiOperation(value = "删除Banner")
    @DeleteMapping("/removeBanner/{id}")
    public R remove(@PathVariable int id) {
        boolean flag = bannerService.removeById(id);
        if(flag){
            return R.ok();
        }else {
            return R.error();
        }
    }


    //前台请求
    @ApiOperation(value = "给前台返回最新的6条Banner")
    @GetMapping("/getNewBanner")
    public R getNewBanner() {
        QueryWrapper<Banner> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("create_time");
        wrapper.last("limit 6");
        List<Banner> bannerList = bannerService.list(wrapper);
        return R.ok().data("bannerList",bannerList);
    }
}

