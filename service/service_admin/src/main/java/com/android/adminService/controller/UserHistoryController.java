package com.android.adminService.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.android.adminService.entity.Song;
import com.android.adminService.entity.UserHistory;
import com.android.adminService.service.SongService;
import com.android.adminService.service.UserHistoryService;
import commonutils.R;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 *
 *
 * @author android
 * @since 2021-12-04
 */
@RestController
@RequestMapping("/adminService/user-history")
@CrossOrigin
public class UserHistoryController {
    @Autowired
    private UserHistoryService userHistoryService;
    @Autowired
    private SongService songService;
    @ApiOperation("所有历史歌曲列表")
    @GetMapping("/findAll")
    private R findAll(){
        List<UserHistory> list = userHistoryService.list(null);
        return R.ok().data("items",list);
    }

    @ApiOperation("用户所有历史歌曲记录列表")
    @GetMapping("/getUserHistoryByUserId/{id}")
    private R getUserHistoryByUserId(@PathVariable int id){
        QueryWrapper<UserHistory> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",id);
        wrapper.orderByDesc("update_time");
        List<UserHistory> list = userHistoryService.list(wrapper);
        return R.ok().data("items",list);
    }
    @ApiOperation("用户所有历史歌曲")
    @GetMapping("/getUserHistorySongByUserId/{id}")
    private R getUserHistorySongByUserId(@PathVariable int id){
        QueryWrapper<UserHistory> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",id);
        wrapper.orderByDesc("update_time");
        wrapper.last("limit 100");
        List<UserHistory> list = userHistoryService.list(wrapper);
        if(list.size()==0){
            return R.error();
        }
        List<Song> songs = new ArrayList<>();
        for ( UserHistory userhistory: list) {
            Song byId = songService.getById(userhistory.getSongId());
            if(byId==null){
                continue;
            }
            songs.add(byId);
        }
//        List<String> ids = new ArrayList<>();
//        for ( UserHistory userhistory: list) {
//            ids.add(userhistory.getSongId());
//        }
//        List<Song> songs = (List<Song>) songService.listByIds(ids);
//        Collections.reverse(songs);
        return R.ok().data("items",songs);
    }

    @ApiOperation("分页查询")
    @GetMapping("/pageUserHistory/{current}/{size}")
    private R pageUserHistory(@PathVariable long current, @PathVariable long size){
        Page<UserHistory> teacherPage = new Page<>(current,size);
        userHistoryService.page(teacherPage,null);
        long total = teacherPage.getTotal();
        List<UserHistory> records = teacherPage.getRecords();

        return R.ok().data("total",total).data("rows",records);
    }
    @ApiOperation("添加历史歌曲")
    @PostMapping("/addUserHistory")
    private R addUserHistory(@RequestBody UserHistory userHistory){
        QueryWrapper<UserHistory> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userHistory.getUserId());
        wrapper.eq("song_id",userHistory.getSongId());
        UserHistory uh = userHistoryService.getOne(wrapper);
        boolean save;
        if(uh==null){
            userHistory.setLicenseNum(1);
            save = userHistoryService.save(userHistory);
        }else{
            uh.setLicenseNum(uh.getLicenseNum()+1);
            uh.setUpdateTime(new Date());
            save = userHistoryService.updateById(uh);
        }
        if(save){
            return R.ok();
        }else {
            return R.error();
        }
    }

    @ApiOperation("查询历史歌曲")
    @GetMapping("/getUserHistory/{id}")
    private R getUserHistory(@PathVariable int id){
        UserHistory userHistory = userHistoryService.getById(id);
        return R.ok().data("userHistory",userHistory);
    }

    @ApiOperation("更新历史歌曲信息")
    @PostMapping("/updateUserHistory")
    private R updateUserHistory(@RequestBody UserHistory userHistory){
        boolean flag = userHistoryService.updateById(userHistory);
        if(flag){
            return R.ok();
        }else {
            return R.error();
        }
    }
    @ApiOperation("删除历史歌曲")
    @DeleteMapping("/{id}")
    private R removeUserHistory(
            @ApiParam(name = "id",value = "历史歌曲id",required = true)
            @PathVariable int id ){
        boolean flag = userHistoryService.removeById(id);
        if(flag){
            return R.ok();
        }else {
            return R.error();
        }
    }
}

