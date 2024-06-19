package com.android.adminService.controller;


import com.android.adminService.entity.ListSong;
import com.android.adminService.entity.Song;
import com.android.adminService.entity.SongList;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.android.adminService.entity.*;
import com.android.adminService.query.SongListQuery;
import com.android.adminService.service.ListSongService;
import com.android.adminService.service.SongListService;
import com.android.adminService.service.SongService;
import commonutils.R;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
@RequestMapping("/adminService/song-list")
@CrossOrigin
public class SongListController {
    @Autowired
    private SongListService songListService;

    @Autowired
    private SongService songService;

    @Autowired
    private ListSongService listSongService;

    @ApiOperation("所有歌单列表")
    @GetMapping("/findAll")
    private R findAll(){
        QueryWrapper<SongList> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("comment_num");
        wrapper.last("limit 20");
        List<SongList> list = songListService.list(wrapper);
        return R.ok().data("items",list);
    }

    @ApiOperation("分页查询")
    @GetMapping("/pageSongList/{current}/{size}")
    private R pageSong(@PathVariable long current, @PathVariable long size){
        Page<SongList> page = new Page<>(current,size);
        songListService.page(page,null);
        long total = page.getTotal();
        List<SongList> records = page.getRecords();

        return R.ok().data("total",total).data("rows",records);
    }
    @ApiOperation("添加歌单")
    @PostMapping("/addSongList")
    private R addSongList(@RequestBody SongList songList){
        boolean save = songListService.save(songList);
        if(save){
            return R.ok();
        }else {
            return R.error();
        }
    }
    @ApiOperation("条件查询歌单")
    @PostMapping("/pageSongListCondition/{current}/{size}")
    private R pageSongCondition(@PathVariable long current, @PathVariable long size
            , @RequestBody(required = false)SongListQuery songListQuery){
        String title = songListQuery.getTitle();
        Page<SongList> page = new Page<>(current,size);
        //构造QueryWrapper
        QueryWrapper<SongList> queryWrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(title)){
            queryWrapper.like("title",title);
        }
        IPage<SongList> page1 = songListService.page(page, queryWrapper);
        long total = page1.getTotal();
        List<SongList> records = page1.getRecords();
        return R.ok().data("total",total).data("rows",records);
    }
    @ApiOperation("查询歌单")
    @GetMapping("/getSongList/{id}")
    private R getSongList(@PathVariable String id){
        SongList songList = songListService.getById(id);
        return R.ok().data("songList",songList);
    }

    @ApiOperation("更新歌单信息")
    @PostMapping("/updateSongList")
    private R updateSongList(@RequestBody SongList songList){
        boolean flag = songListService.updateById(songList);
        if(flag){
            return R.ok();
        }else {
            return R.error();
        }
    }
    @ApiOperation("删除歌单")
    @DeleteMapping("/{id}")
    private R removeSongList(
            @ApiParam(name = "id",value = "歌单id",required = true)
            @PathVariable String id ){
        boolean flag = songListService.removeById(id);
        if(flag){
            return R.ok();
        }else {
            return R.error();
        }
    }

    @ApiOperation("查询歌单里歌曲信息")
    @GetMapping("/getSongListInfo/{id}")
    private R getSongListInfo(@PathVariable String id){
        SongList songList = songListService.getById(id);
        QueryWrapper<ListSong> wrapper = new QueryWrapper<>();
        wrapper.eq("song_list_id",songList.getId());
        List<ListSong> list = listSongService.list(wrapper);
        if(list.size()==0){
            return R.ok().data("songs",new ArrayList<Song>());
        }
        ArrayList<String> songIds = new ArrayList<>();
        for(int i=0;i<list.size();i++){
            songIds.add(list.get(i).getSongId());
        }
        List<Song> songs = (List<Song>) songService.listByIds(songIds);
        return R.ok().data("songs",songs);
    }

    @ApiOperation("得到4个热门歌单")
    @GetMapping("/getHotPlayList")
    private R getHotPlayList(){
        QueryWrapper<SongList> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("comment_num");
        wrapper.last("limit 4");
        List<SongList> list = songListService.list(wrapper);
        return R.ok().data("playList",list);
    }

    @ApiOperation("所有歌单列表")
    @GetMapping("/getUserPlaylist/{userId}")
    private R getUserPlaylist(@PathVariable String userId){
        QueryWrapper<SongList> wrapper = new QueryWrapper<>();
        wrapper.eq("creator_id",userId);
        List<SongList> list = songListService.list(wrapper);
        return R.ok().data("items",list);
    }
}

