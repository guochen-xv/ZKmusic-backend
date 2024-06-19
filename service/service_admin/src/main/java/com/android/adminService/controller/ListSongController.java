package com.android.adminService.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.android.adminService.entity.ListSong;
import com.android.adminService.service.ListSongService;
import commonutils.R;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 歌单的歌曲表

一个歌单包含多个歌曲

多对多关系 前端控制器
 * </p>
 *
 * @author android
 * @since 2021-01-06
 */
@RestController
@RequestMapping("/adminService/list-song")
@CrossOrigin
public class ListSongController {
    @Autowired
    private ListSongService listSongService;

    @ApiOperation("添加歌单")
    @PostMapping("/addSongToPlaylist")
    private R addSongList(@RequestBody ListSong listSong){
        boolean save = listSongService.save(listSong);
        if(save){
            return R.ok();
        }else {
            return R.error();
        }
    }

    @ApiOperation("删除歌单里的歌曲")
    @DeleteMapping("/{id}/{songId}")
    private R removeListSong(
            @ApiParam(name = "id",value = "歌单id",required = true)
            @PathVariable String id, @ApiParam(name = "id",value = "歌曲id",required = true)
    @PathVariable String songId){
        QueryWrapper<ListSong> wrapper = new QueryWrapper<>();
        wrapper.eq("song_id",songId);
        wrapper.eq("song_list_id",id);
        ListSong listSong = listSongService.getOne(wrapper);
        boolean flag = listSongService.removeById(listSong.getId());
        if(flag){
            return R.ok();
        }else {
            return R.error();
        }
    }
}

