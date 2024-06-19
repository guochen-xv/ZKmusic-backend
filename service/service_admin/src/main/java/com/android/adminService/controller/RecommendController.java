package com.android.adminService.controller;

import com.android.adminService.service.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.android.adminService.entity.Song;
import com.android.adminService.entity.User;
import com.android.adminService.service.*;
import commonutils.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author xgc
 */
@RestController
@RequestMapping("/adminService/recommend")
@CrossOrigin
public class RecommendController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserDownloadService userDownloadService;
    @Autowired
    private UserHistoryService userHistoryService;
    @Autowired
    private CollectService collectService;
    @Autowired
    private SongService songService;
    class Node{
        String songID;
        double score;
        public Node(String songID,double score){
            this.songID = songID;
            this.score =score;
        }
    }
    @ApiOperation("协同过滤-推荐歌曲")
    @GetMapping("/recommendByCF/{id}")
    private R recommendByCF(@PathVariable int id){
        List<String> userSongIds = userHistoryService.getSongIdByUserId(id);
        //歌曲听过数量太少则直接返回播发量最高的几首歌曲
        if(userSongIds.size()<=10){
            return R.ok().data("items",getSongByLicenseNum());
        }
        List<String> songIDs = userHistoryService.getSongIds();
        //过滤掉已经听过歌曲
        songIDs.removeAll(userSongIds);
        List<String> recommendIds = computeJaccard(userSongIds,songIDs);
        if (recommendIds == null || recommendIds.isEmpty()) {
            // 如果推荐的ID列表为空，返回一个空列表或者其他默认推荐结果
            return R.ok().data("items", Collections.emptyList());
        }
        Collection<Song> songs = songService.listByIds(recommendIds);
        return R.ok().data("items",songs);
    }
    @ApiOperation("协同过滤-推荐歌曲分页")
    @GetMapping("/recommendByCF/page/{id}/{current}/{size}")
    private R recommendByCFPage(@PathVariable int id,@PathVariable long current, @PathVariable long size){
        List<String> userSongIds = userHistoryService.getSongIdByUserId(id);
        //歌曲听过数量太少则直接返回播发量最高的几首歌曲
        if(userSongIds.size()<=10){
            Page<Song> page = new Page<>(current,size);
            QueryWrapper<Song> wrapper = new QueryWrapper<>();
            wrapper.orderByDesc("license_num");
            songService.page(page,wrapper);
            long total = page.getTotal();
            List<Song> records = page.getRecords();
            return R.ok().data("total",50).data("rows",records);
        }
        List<String> songIDs = userHistoryService.getSongIds();
        //过滤掉已经听过歌曲
        songIDs.removeAll(userSongIds);
        List<String> recommendIds = computeJaccard(userSongIds,songIDs);
        Page<Song> page = new Page<>(current,size);
        QueryWrapper<Song> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id",recommendIds);
        songService.page(page,queryWrapper);
        long total = page.getTotal();
        List<Song> records = page.getRecords();
        return R.ok().data("total",total).data("rows",records);
    }

    @ApiOperation("SVD-推荐歌曲")
    @GetMapping("/recommendBySVD/{id}")
    private R recommendBySVD(@PathVariable int id){
        List<User> users = userService.list(null);
        for (int i = 0; i < users.size(); i++) {
            if(users.get(i).getId()==id){
                users.remove(i);
                break;
            }
        }
        List<String> userSongIds = userHistoryService.getSongIdByUserId(id);
        //歌曲听过数量太少则直接返回播发量最高的几首歌曲
        if(userSongIds.size()<=10){
            return R.ok().data("items",getSongByLicenseNum());
        }
        List<String> songIDs = userHistoryService.getSongIds();
        //过滤掉已经听过歌曲
        songIDs.removeAll(userSongIds);
        List<String> recommendIds = computeSVD(users,songIDs);
        Collection<Song> songs = songService.listByIds(recommendIds);
        return R.ok().data("items",songs);
    }

    private List<String> computeSVD(List<User> users, List<String> songIDs) {
        return null;
    }

    public List<Song> getSongByLicenseNum(){
        QueryWrapper<Song> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("license_num");
        wrapper.last("limit 50");
        return songService.list(wrapper);
    }
    public List<String> computeJaccard(List<String> userSongIds,List<String> songIds){
        double[][] jaccard = new double[userSongIds.size()][songIds.size()];
        //协同过滤
        for(int i=0;i<userSongIds.size();i++){
            for(int j=0;j<songIds.size();j++){
                List<Integer> list1 = userHistoryService.getUserIdBySongId(userSongIds.get(i));
                List<Integer> list2 = userHistoryService.getUserIdBySongId(songIds.get(j));
                int d = getUnite(list1,list2);
                if(d>0){
                    jaccard[i][j] = 1.0*d/getUnion(list1,list2);
                }else{
                    jaccard[i][j] = 0;
                }
            }
        }
        //求每一列的平均值
        Node[] res = new Node[songIds.size()];
        for(int i=0;i<songIds.size();i++){
            double sum = 0;
            for(int j=0;j<userSongIds.size();j++){
                sum+=jaccard[j][i];
            }
            res[i] = new Node(songIds.get(i),sum/userSongIds.size());
        }
        //按得分排序
        Arrays.sort(res,(p,q)->q.score>p.score?1:0);
        List<String> recommendIds = new ArrayList<>();
        for(int i=0;i<res.length&&i<50;i++){
            recommendIds.add(res[i].songID);
        }
        return recommendIds;
    }
    //交集
    public int getUnite(List<Integer> user, List<Integer> user2){
        Set<Integer> set = new HashSet<>(user);
        set.retainAll(user2);
        return set.size();
    }
    //并集
    public int getUnion(List<Integer> user, List<Integer> user2){
        Set<Integer> set = new HashSet<>(user);
        set.addAll(user2);
        return set.size();
    }
}
