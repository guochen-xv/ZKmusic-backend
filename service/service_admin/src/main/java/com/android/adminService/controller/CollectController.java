package com.android.adminService.controller;


import com.android.adminService.entity.Collect;
import com.android.adminService.entity.Singer;
import com.android.adminService.entity.Song;
import com.android.adminService.entity.SongList;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.android.adminService.entity.*;
import com.android.adminService.service.CollectService;
import com.android.adminService.service.SingerService;
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
 * 用户收藏表 前端控制器
 * </p>
 *
 * @author android
 * @since 2021-01-06
 */
@RestController
@RequestMapping("/adminService/collect")
@CrossOrigin
public class CollectController {
    @Autowired
    private CollectService collectService;
    @Autowired
    private SongService songService;
    @Autowired
    private SingerService singerService;
    @Autowired
    private SongListService songListService;

    @ApiOperation("所有收藏列表")
    @GetMapping("/findAll")
    private R findAll(){
        List<Collect> list = collectService.list(null);
        return R.ok().data("items",list);
    }

    @ApiOperation("分页查询")
    @GetMapping("/pageCollect/{current}/{size}")
    private R pageSong(@PathVariable long current, @PathVariable long size){
        Page<Collect> page = new Page<>(current,size);
        collectService.page(page,null);
        long total = page.getTotal();
        List<Collect> records = page.getRecords();
        return R.ok().data("total",total).data("rows",records);
    }
    @ApiOperation("添加收藏")
    @PostMapping("/addCollect")
    private R addCollect(@RequestBody Collect collect){
        QueryWrapper<Collect> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",collect.getUserId());
        wrapper.eq("song_id",collect.getSongId());
        Collect c = collectService.getOne(wrapper);
        boolean save;
        if(c==null){
            save = collectService.save(collect);
        }else{
            save = collectService.updateById(c);
        }
        if(save){
            return R.ok();
        }else {
            return R.error();
        }
    }

    @ApiOperation("查询收藏")
    @GetMapping("/getCollect/{id}")
    private R getCollect(@PathVariable String id){
        Collect collect = collectService.getById(id);
        return R.ok().data("collect",collect);
    }

    @ApiOperation("更新收藏信息")
    @PostMapping("/updateCollect")
    private R updateCollect(@RequestBody Collect collect){
        boolean flag = collectService.updateById(collect);
        if(flag){
            return R.ok();
        }else {
            return R.error();
        }
    }
    @ApiOperation("删除收藏")
    @DeleteMapping("/{id}")
    private R removeCollect(
            @ApiParam(name = "id",value = "收藏id",required = true)
            @PathVariable int id ){
        boolean flag = collectService.removeById(id);
        if(flag){
            return R.ok();
        }else {
            return R.error();
        }
    }
    @ApiOperation("删除收藏")
    @DeleteMapping("deleteCollect/{userId}/{songId}")
    private R deleteCollect(
            @ApiParam(name = "user_id",value = "用户id",required = true)
            @PathVariable int userId ,@PathVariable int songId){
        QueryWrapper<Collect> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        wrapper.eq("song_id",songId);
        Collect c = collectService.getOne(wrapper);
        if(c!=null){
            collectService.removeById(c.getId());
            return R.ok();
        }else {
            return R.error();
        }
    }


    @ApiOperation("根据用户id查询收藏")
    @GetMapping("/getCollectByUser/{id}")
    private R getCollectByUser(@PathVariable int id ){
        QueryWrapper<Collect> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("create_time");
        wrapper.eq("user_id",id);
        List<Collect> list = collectService.list(wrapper);
        return R.ok().data("items",list);
    }

    @ApiOperation("根据条件id查询收藏")
    @PostMapping("/getCollectOne")
    private R getCollectOne(@RequestBody Collect collect){
        QueryWrapper<Collect> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(collect.getUserId())){
            wrapper.eq("user_id",collect.getUserId());
        }else {
            return R.ok().data("collect",null);
        }
        if(!StringUtils.isEmpty(collect.getSongId())){
            wrapper.eq("song_id",collect.getSongId());
        }
        if(!StringUtils.isEmpty(collect.getSingerId())){
            wrapper.eq("singer_id",collect.getSingerId());
        }
        if(!StringUtils.isEmpty(collect.getSongListId())){
            wrapper.eq("song_list_id",collect.getSongListId());
        }
        Collect one = collectService.getOne(wrapper);
        return R.ok().data("collect",one);
    }

    @ApiOperation("根据用户id查询收藏歌曲歌单歌手")
    @GetMapping("/getCollectInfo/{id}")
    private R getCollectInfo(@PathVariable int id ){
        QueryWrapper<Collect> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",id);
        List<Collect> list = collectService.list(wrapper);

        List<String> ids1 = new ArrayList<>();
        List<String> ids2 = new ArrayList<>();
        List<String> ids3 = new ArrayList<>();

        List<Singer> singers = null;
        List<SongList> songLists = null;
        List<Song> songs = null;

        for (Collect collect : list) {
            if (collect.getSongId() != null) {
                ids1.add(collect.getSongId());
            } else if (collect.getSingerId() != null) {
                ids2.add(collect.getSingerId());
            } else if (collect.getSongListId() != null) {
                ids3.add(collect.getSongListId());
            }
        }

        if(ids2.size()>0){
            singers = (List<Singer>) singerService.listByIds(ids2);
        }
        if(ids3.size()>0){
            songLists = (List<SongList>) songListService.listByIds(ids3);
        }
        if(ids1.size()>0){
            songs = (List<Song>) songService.listByIds(ids1);
        }
        return R.ok().data("songs",songs).data("singers",singers).data("songLists",songLists);
    }

    @ApiOperation("根据用户id查询收藏歌曲")
    @GetMapping("/getCollectSongByUserId/{id}")
    private R getCollectSongByUserId(@PathVariable int id ){
        QueryWrapper<Collect> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",id);
        wrapper.orderByDesc("update_time");
        List<Collect> list = collectService.list(wrapper);
        if(list.size()==0){
            return R.error();
        }

        List<Song> songs = new ArrayList<>();
        for ( Collect collect: list) {
            if(collect.getSongId()!=null) {
                songs.add(songService.getById(collect.getSongId()));
            }
        }
        return R.ok().data("items",songs);
    }
}

