package com.android.adminService.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.android.adminService.entity.Comment;
import com.android.adminService.entity.Song;
import com.android.adminService.service.CommentService;
import com.android.adminService.service.SongService;
import commonutils.R;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/adminService/comment")
@CrossOrigin
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private SongService songService;

    @ApiOperation("所有评论列表")
    @GetMapping("/findAll")
    private R findAll(){
        List<Comment> list = commentService.list(null);
        return R.ok().data("items",list);
    }

    @ApiOperation("所有评论列表")
    @GetMapping("/getCommentBySongId/{id}")
    private R getCommentBySongId(@PathVariable int id){
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.eq("song_id",id);
        wrapper.orderByDesc("create_time");
        List<Comment> list = commentService.list(wrapper);
        return R.ok().data("items",list);
    }

    @ApiOperation("分页查询")
    @GetMapping("/pageComment/{current}/{size}")
    private R pageComment(@PathVariable long current, @PathVariable long size){
        Page<Comment> teacherPage = new Page<>(current,size);
        commentService.page(teacherPage,null);
        long total = teacherPage.getTotal();
        List<Comment> records = teacherPage.getRecords();

        return R.ok().data("total",total).data("rows",records);
    }
    @ApiOperation("添加评论")
    @PostMapping("/addComment")
    private R addComment(@RequestBody Comment comment){
        boolean save = commentService.save(comment);
        Song song = songService.getById(comment.getSongId());
        song.setCommentNum(song.getCommentNum()+1);
        songService.updateById(song);
        if(save){
            return R.ok();
        }else {
            return R.error();
        }
    }

    @ApiOperation("查询评论")
    @GetMapping("/getComment/{id}")
    private R getComment(@PathVariable int id){
        Comment comment = commentService.getById(id);
        return R.ok().data("comment",comment);
    }

    @ApiOperation("更新评论信息")
    @PostMapping("/updateComment")
    private R updateComment(@RequestBody Comment comment){
        boolean flag = commentService.updateById(comment);
        if(flag){
            return R.ok();
        }else {
            return R.error();
        }
    }
    @ApiOperation("删除评论")
    @DeleteMapping("/{id}")
    private R removeComment(
            @ApiParam(name = "id",value = "评论id",required = true)
            @PathVariable int id ){
        boolean flag = commentService.removeById(id);
        if(flag){
            return R.ok();
        }else {
            return R.error();
        }
    }
    /*//@RequestBody用于接收前端传来的json数据，传送json数据只能用 @PostMapping
    @PostMapping("/pageCommentCondition/{current}/{size}")
    private R pageCommentCondition(@PathVariable long current,@PathVariable long size
            ,@RequestBody(required = false) CommentQuery commentQuery){
        Page<Comment> commentPage = new Page<>(current,size);
        //构造QueryWrapper
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        String name = commentQuery.getName();
        Integer singerId = commentQuery.getSingerId();
        String createTime = commentQuery.getCreateTime();
        if(!StringUtils.isEmpty(name)){
            queryWrapper.like("name",name);
        }
        if(!StringUtils.isEmpty(singerId)){
            queryWrapper.eq("singerId",singerId);
        }
        if(!StringUtils.isEmpty(createTime)){
            queryWrapper.ge("createTime",createTime);
        }

        //排序
        //queryWrapper.orderByAsc("name");

        commentService.page(commentPage,queryWrapper);
        long total = commentPage.getTotal();
        List<Comment> records = commentPage.getRecords();

        return R.ok().data("total",total).data("rows",records);
    }*/




    /***
     * 前台请求
     *
     *
     */
}

