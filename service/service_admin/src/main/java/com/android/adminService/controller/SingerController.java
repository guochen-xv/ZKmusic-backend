package com.android.adminService.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.android.adminService.entity.Singer;
import com.android.adminService.entity.Song;
import com.android.adminService.query.SingerQuery;
import com.android.adminService.service.SingerService;
import com.android.adminService.service.SongService;
import commonutils.R;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
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
@RequestMapping("/adminService/singer")
@CrossOrigin
public class SingerController {

    @Autowired
    private SingerService singerService;
    @Autowired
    private SongService songService;

    @ApiOperation("所有歌手列表")
    @GetMapping("/findAll")
    private R findAll(){
        List<Singer> list = singerService.list(null);
        return R.ok().data("items",list);
    }

    @ApiOperation("分页查询")
    @GetMapping("/pageSinger/{current}/{size}")
    private R pageSinger(@PathVariable long current, @PathVariable long size){
        Page<Singer> teacherPage = new Page<>(current,size);
        singerService.page(teacherPage,null);
        long total = teacherPage.getTotal();
        List<Singer> records = teacherPage.getRecords();
        return R.ok().data("total",total).data("rows",records);
    }
    @ApiOperation("添加歌手")
    @PostMapping("/addSinger")
    private R addSinger(@RequestBody Singer singer){
        boolean save = singerService.save(singer);
        if(save){
            return R.ok();
        }else {
            return R.error();
        }
    }

    @ApiOperation("查询歌手")
    @GetMapping("/getSinger/{id}")
    private R getSinger(@PathVariable String id){
        Singer singer = singerService.getById(id);
        return R.ok().data("singer",singer);
    }
    @ApiOperation("更新歌手信息")
    @PostMapping("/updateSinger")
    private R updateSinger(@RequestBody Singer singer){
        boolean flag = singerService.updateById(singer);
        if(flag){
            return R.ok();
        }else {
            return R.error();
        }
    }

    @ApiOperation("删除歌手")
    @DeleteMapping("/{id}")
    private R removeSinger(
            @ApiParam(name = "id",value = "歌手id",required = true)
            @PathVariable String id ){
        //删除歌手
        boolean flag = singerService.removeById(id);
        if(flag){
            return R.ok();
        }else {
            return R.error();
        }
    }
    //@RequestBody用于接收前端传来的json数据，传送json数据只能用 @PostMapping
    @PostMapping("/pageSingerCondition/{current}/{size}")
    private R pageSingerCondition(@PathVariable long current,@PathVariable long size
            ,@RequestBody(required = false) SingerQuery singerQuery){
        Page<Singer> singerPage = new Page<>(current,size);
        //构造QueryWrapper
        QueryWrapper<Singer> queryWrapper = new QueryWrapper<>();
        String name = singerQuery.getName();
        Integer sex = singerQuery.getSex();
        String birth = singerQuery.getBirth();
        if(!StringUtils.isEmpty(name)){
            queryWrapper.like("name",name);
        }
        if(!StringUtils.isEmpty(sex)){
            queryWrapper.eq("sex",sex);
        }
        if(!StringUtils.isEmpty(birth)){
            queryWrapper.ge("birth",birth);
        }

        //排序
        //queryWrapper.orderByAsc("name");

        singerService.page(singerPage,queryWrapper);
        long total = singerPage.getTotal();
        List<Singer> records = singerPage.getRecords();

        return R.ok().data("total",total).data("rows",records);
    }

    //前台请求
    @ApiOperation(value = "给前台返回最新的8个歌手")
    @GetMapping("/getNewSinger")
    public R getNewSinger() {
        QueryWrapper<Singer> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("create_time");
        wrapper.last("limit 8");
        List<Singer> singerList = singerService.list(wrapper);
        return R.ok().data("singerList",singerList);
    }

    @ApiOperation(value = "根据歌手id查询歌手信息，以及歌手的歌曲")
    @GetMapping("/getSingerInfo/{id}")
    public R getSingerInfo(@PathVariable int id) {
        Singer singer = singerService.getById(id);
        QueryWrapper<Song> wrapper = new QueryWrapper<>();
        wrapper.eq("singer_id",id);
        List<Song> songs =  songService.list(wrapper);
        return R.ok().data("singer",singer).data("songs",songs);
    }
}

