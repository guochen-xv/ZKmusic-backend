package com.android.adminService.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.android.adminService.entity.Singer;
import com.android.adminService.entity.Song;
import com.android.adminService.entity.Subject;
import com.android.adminService.query.SongFrontQuery;
import com.android.adminService.query.SongQuery;
import com.android.adminService.service.SingerService;
import com.android.adminService.service.SongListService;
import com.android.adminService.service.SongService;
import com.android.adminService.service.SubjectService;
import commonutils.R;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
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
@RequestMapping("/adminService/song")
@CrossOrigin
public class SongController {

    @Autowired
    private SongService songService;
    @Autowired
    private SingerService singerService;
    @Autowired
    private SongListService songListService;
    @Autowired
    private SubjectService subjectService;

    @ApiOperation("所有歌曲列表")
    @GetMapping("/findAll")
    private R findAll(){
        List<Song> list = songService.list(null);
        return R.ok().data("items",list);
    }

    @ApiOperation("分页查询")
    @GetMapping("/pageSong/{current}/{size}")
    private R pageSong(@PathVariable long current, @PathVariable long size){
        Page<Song> teacherPage = new Page<>(current,size);
        songService.page(teacherPage,null);
        long total = teacherPage.getTotal();
        List<Song> records = teacherPage.getRecords();

        return R.ok().data("total",total).data("rows",records);
    }
    @ApiOperation("添加歌曲")
    @PostMapping("/addSong")
    private R addSong(@RequestBody Song song){
        boolean save = songService.save(song);
        if(save){
            return R.ok();
        }else {
            return R.error();
        }
    }

    @ApiOperation("查询歌曲")
    @GetMapping("/getSong/{id}")
    private R getSong(@PathVariable String id){
        Song song = songService.getById(id);
        return R.ok().data("song",song);
    }

    @ApiOperation("更新歌曲信息")
    @PostMapping("/updateSong")
    private R updateSong(@RequestBody Song song){
        boolean flag = songService.updateById(song);
        if(flag){
            return R.ok();
        }else {
            return R.error();
        }
    }
    @ApiOperation("删除歌曲")
    @DeleteMapping("/{id}")
    private R removeSong(
            @ApiParam(name = "id",value = "歌曲id",required = true)
            @PathVariable String id ){
        boolean flag = songService.removeById(id);
        if(flag){
            return R.ok();
        }else {
            return R.error();
        }
    }
    //@RequestBody用于接收前端传来的json数据，传送json数据只能用 @PostMapping
    @PostMapping("/pageSongCondition/{current}/{size}")
    private R pageSongCondition(@PathVariable long current,@PathVariable long size
            ,@RequestBody(required = false) SongQuery songQuery){
        Page<Song> songPage = new Page<>(current,size);
        //构造QueryWrapper
        QueryWrapper<Song> queryWrapper = new QueryWrapper<>();
        String name = songQuery.getName();
        String singerName = songQuery.getSingerName();
        String createTime = songQuery.getCreateTime();
        if(!StringUtils.isEmpty(name)){
            queryWrapper.like("name",name);
        }
        if(!StringUtils.isEmpty(singerName)){
            queryWrapper.eq("singer_name",singerName);
        }
        if(!StringUtils.isEmpty(createTime)){
            queryWrapper.ge("create_time",createTime);
        }

        //排序
        //queryWrapper.orderByAsc("name");

        songService.page(songPage,queryWrapper);
        long total = songPage.getTotal();
        List<Song> records = songPage.getRecords();

        return R.ok().data("total",total).data("rows",records);
    }




    /***
     * 前台请求
     *
     *
     */

    @ApiOperation(value = "给前台返回最新的8个歌曲")
    @GetMapping("/getNewSong")
    public R getNewSong() {
        QueryWrapper<Song> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("license_num");
        wrapper.last("limit 8");
        List<Song> songList = songService.list(wrapper);
        return R.ok().data("songList",songList);
    }

    //条件查询带分页
    @PostMapping("/getFrontSongList/{page}/{limit}")
    public R getFrontSongList(@PathVariable long page, @PathVariable long limit,
                                @RequestBody(required = false) SongFrontQuery songFrontQuery) {
        Page<Song> pageSong = new Page<>(page,limit);
        return this.songService.getFrontSongList(pageSong, songFrontQuery);
    }

    //根据歌手id查询歌曲
    @GetMapping("/getFrontSongInfo/{id}")
    public R getFrontSongInfo(@PathVariable String id) {
        //根据id查询歌曲信息
        Song song  = songService.getById(id);
        if(song==null){
            return R.error().message("查询歌曲详细信息失败");
        }
        //查询歌手
        Singer singer = singerService.getById(song.getSingerId());
        //查询同一歌手的曲目
        QueryWrapper<Song> wrapper = new QueryWrapper<>();
        wrapper.eq("singer_id",song.getSingerId());
        List<Song> songsSinger =  songService.list(wrapper);

        //根据分类id查询分类名
        Subject subject = subjectService.getById(song.getSubjectId());
        //查询同一类别的歌曲
        QueryWrapper<Song> wrapper2 = new QueryWrapper<>();
        wrapper2.eq("subject_id",song.getSubjectId());
        List<Song> songsSubject =  songService.list(wrapper);
        return R.ok().data("singer",singer).data("subject",subject)
                .data("song",song).data("songsSinger",songsSinger)
                .data("songsSubject",songsSubject);

    }

    //搜索歌曲，歌手，歌单  TODO MV
    @GetMapping("/search")
    public R search(@RequestParam String key) {
        //查询歌手
        QueryWrapper<Song> wrapper1 = new QueryWrapper<>();
        wrapper1.like("name",key).or().like("singer_name",key);
        List<Song> list1 = songService.list(wrapper1);
        return R.ok().data("songs",list1);
        /*//查询歌手
        QueryWrapper<Singer> wrapper2 = new QueryWrapper<>();
        wrapper2.like("name",key);
        List<Singer> list2 = singerService.list(wrapper2);
        //查询歌单
        QueryWrapper<SongList> wrapper3 = new QueryWrapper<>();
        wrapper3.like("title",key);
        List<SongList> list3 =songListService.list(wrapper3);
        return R.ok().data("songs",list1).data("singers",list2).data("songLists",list3);*/
    }


    @ApiOperation("歌曲播放量加一")//实际上不可能这么做的
    @GetMapping("/addNum/{id}")
    private R addNum(@PathVariable String id){
        Song song = songService.getById(id);
        int num = song.getLicenseNum();
        song.setLicenseNum(++num);
        boolean flag = songService.updateById(song);
        if(flag){
            return R.ok();
        }else {
            return R.error();
        }
    }

    @GetMapping("/download")
    public void downLoad(@RequestParam String url, @RequestParam String fileName, HttpServletResponse response){
        try {
            URL u = new URL(url);
            URLConnection conn = u.openConnection();
            InputStream inputStream = conn.getInputStream();

            // 设置response的Header
            response.setContentType("application/x-download");
            response.addHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8"));
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            int byteread;
            byte[] buffer = new byte[1024];
            while ((byteread = inputStream.read(buffer)) != -1) {
                toClient.write(buffer, 0, byteread);
            }
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
            inputStream.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}

