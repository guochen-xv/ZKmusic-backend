package com.android.adminService.controller;


import com.android.adminService.entity.Rank;
import com.android.adminService.entity.RankList;
import com.android.adminService.entity.Song;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.android.adminService.entity.*;
import com.android.adminService.service.RankListService;
import com.android.adminService.service.RankService;
import com.android.adminService.service.SongService;
import commonutils.R;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author android
 * @since 2021-02-06
 */
@RestController
@RequestMapping("/adminService/rank")
@CrossOrigin
public class RankController {
    @Autowired
    private RankService rankService;

    @Autowired
    private SongService songService;

    @Autowired
    private RankListService rankListService;

    @ApiOperation("所有排行榜列表")
    @GetMapping("/findAll")
    private R findAll(){
        List<Rank> list = rankService.list(null);
        return R.ok().data("items",list);
    }

    @ApiOperation("分页查询")
    @GetMapping("/pageRank/{current}/{size}")
    private R pageSong(@PathVariable long current, @PathVariable long size){
        Page<Rank> page = new Page<>(current,size);
        rankService.page(page,null);
        long total = page.getTotal();
        List<Rank> records = page.getRecords();

        return R.ok().data("total",total).data("rows",records);
    }
    @ApiOperation("添加排行榜")
    @PostMapping("/addRank")
    private R addRank(@RequestBody Rank rank){
        boolean save = rankService.save(rank);
        if(save){
            return R.ok();
        }else {
            return R.error();
        }
    }

    @ApiOperation("查询排行榜")
    @GetMapping("/getRank/{id}")
    private R getRank(@PathVariable String id){
        Rank rank = rankService.getById(id);
        return R.ok().data("rank",rank);
    }

    @ApiOperation("更新排行榜信息")
    @PostMapping("/updateRank")
    private R updateRank(@RequestBody Rank rank){
        boolean flag = rankService.updateById(rank);
        if(flag){
            return R.ok();
        }else {
            return R.error();
        }
    }
    @ApiOperation("删除排行榜")
    @DeleteMapping("/{id}")
    private R removeRank(
            @ApiParam(name = "id",value = "排行榜id",required = true)
            @PathVariable String id ){
        boolean flag = rankService.removeById(id);
        if(flag){
            return R.ok();
        }else {
            return R.error();
        }
    }

    @ApiOperation("查询排行榜")
    @GetMapping("/getRankInfo/{id}")
    private R getRankInfo(@PathVariable String id){
        Rank rank = rankService.getById(id);
        QueryWrapper<RankList> wrapper = new QueryWrapper<>();
        wrapper.eq("song_list_id",rank.getId());
        List<RankList> list = rankListService.list(wrapper);
        ArrayList<String> songIds = new ArrayList<>();
        for(int i=0;i<list.size();i++){
            songIds.add(list.get(i).getSongId());
        }
        List<Song> songs = (List<Song>) songService.listByIds(songIds);
        return R.ok().data("items",songs);
    }


    //得到4个热门排行榜
    @ApiOperation("查询排行榜")
    @GetMapping("/getHotRank")
    private R getHotRank(){
        QueryWrapper<Rank> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("comment_num");
        wrapper.last("limit 4");
        List<Rank> list = rankService.list(wrapper);
        return R.ok().data("ranks",list);
    }

    //返回4个热门排行与其他排行
    @ApiOperation("热门排行与其他排行")
    @GetMapping("/getAllRank")
    private R getAllRank(){
        R r = getHotRank();
        QueryWrapper<Rank> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("comment_num");
        List<Rank> list = rankService.list(wrapper);
        list.subList(0,4).clear();
        return r.data("rankList",list);
    }

    @ApiOperation("获取排行榜歌曲")
    @GetMapping("/getRankSongs/{id}")
    private R getRankSongs(@PathVariable String id){
        Rank rank = rankService.getById(id);
        QueryWrapper<RankList> wrapper = new QueryWrapper<>();
        wrapper.eq("rank_id",rank.getId());
        List<RankList> list = rankListService.list(wrapper);
        ArrayList<String> songIds = new ArrayList<>();
        for(int i=0;i<list.size();i++){
            songIds.add(list.get(i).getSongId());
        }
        List<Song> songs = (List<Song>) songService.listByIds(songIds);
        return R.ok().data("items",songs);
    }
}

