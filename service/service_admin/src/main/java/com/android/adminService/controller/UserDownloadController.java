package com.android.adminService.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.android.adminService.entity.Song;
import com.android.adminService.entity.UserDownload;
import com.android.adminService.service.SongService;
import com.android.adminService.service.UserDownloadService;
import commonutils.R;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 * @author android
 * @since 2021-12-04
 */
@RestController
@RequestMapping("/adminService/user-download")
@CrossOrigin
public class UserDownloadController {
    @Autowired
    private UserDownloadService userDownloadService;
    @Autowired
    private SongService songService;
    @ApiOperation("所有下载歌曲列表")
    @GetMapping("/findAll")
    private R findAll(){
        List<UserDownload> list = userDownloadService.list(null);
        return R.ok().data("items",list);
    }

    @ApiOperation("用户所有下载歌曲记录列表")
    @GetMapping("/getUserDownloadByUserId/{id}")
    private R getUserDownloadByUserId(@PathVariable int id){
        QueryWrapper<UserDownload> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",id);
        wrapper.orderByDesc("update_time");
        List<UserDownload> list = userDownloadService.list(wrapper);
        return R.ok().data("items",list);
    }
    @ApiOperation("用户所有下载歌曲")
    @GetMapping("/getUserDownloadSongByUserId/{id}")
    private R getUserDownloadSongByUserId(@PathVariable int id){
        QueryWrapper<UserDownload> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",id);
        wrapper.orderByDesc("update_time");
        List<UserDownload> list = userDownloadService.list(wrapper);
        if(list.size()==0){
            return R.error();
        }
        List<Song> songs = new ArrayList<>();
        for ( UserDownload userDownload: list) {
            Song song = songService.getById(userDownload.getSongId());
            if(song==null){
                continue;
            }
            song.setUrl(userDownload.getUrl());
            songs.add(song);
        }
        return R.ok().data("items",songs);
    }

    @ApiOperation("分页查询")
    @GetMapping("/pageUserDownload/{current}/{size}")
    private R pageUserDownload(@PathVariable long current, @PathVariable long size){
        Page<UserDownload> teacherPage = new Page<>(current,size);
        userDownloadService.page(teacherPage,null);
        long total = teacherPage.getTotal();
        List<UserDownload> records = teacherPage.getRecords();

        return R.ok().data("total",total).data("rows",records);
    }
    @ApiOperation("添加下载歌曲")
    @PostMapping("/addUserDownload")
    private R addUserDownload(@RequestBody UserDownload userDownload){
        QueryWrapper<UserDownload> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userDownload.getUserId());
        wrapper.eq("song_id",userDownload.getSongId());
        UserDownload ud = userDownloadService.getOne(wrapper);
        boolean save;
        if(ud==null){
            save = userDownloadService.save(userDownload);
        }else{
            ud.setUrl(userDownload.getUrl());
            save = userDownloadService.updateById(ud);
        }
        if(save){
            return R.ok();
        }else {
            return R.error();
        }
    }
    @ApiOperation("删除歌曲")
    @DeleteMapping("deleteUserDownload/{userId}/{songId}")
    private R deleteDownload(@PathVariable String userId ,@PathVariable String songId){
        QueryWrapper<UserDownload> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        wrapper.eq("song_id",songId);
        UserDownload ud = userDownloadService.getOne(wrapper);
        if(ud!=null){
            userDownloadService.removeById(ud.getId());
            return R.ok();
        }else {
            return R.error();
        }
    }
    @ApiOperation("查询下载歌曲")
    @GetMapping("/getUserDownload/{id}")
    private R getUserDownload(@PathVariable int id){
        UserDownload userDownload = userDownloadService.getById(id);
        return R.ok().data("userDownload",userDownload);
    }

    @ApiOperation("更新下载歌曲信息")
    @PostMapping("/updateUserDownload")
    private R updateUserDownload(@RequestBody UserDownload userDownload){
        boolean flag = userDownloadService.updateById(userDownload);
        if(flag){
            return R.ok();
        }else {
            return R.error();
        }
    }
    @ApiOperation("删除下载歌曲记录")
    @DeleteMapping("/{id}")
    private R removeUserDownload(
            @ApiParam(name = "id",value = "下载歌曲id",required = true)
            @PathVariable int id ){
        boolean flag = userDownloadService.removeById(id);
        if(flag){
            return R.ok();
        }else {
            return R.error();
        }
    }
}

