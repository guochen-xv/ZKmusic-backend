package com.android.adminService.controller;


import com.android.adminService.service.OssService;
import commonutils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/oss")
@CrossOrigin
public class OssController {
    @Autowired
    private OssService ossService;

    //上传对象方法
    @PostMapping("/singeravatar")
    public R uploadSingerAvatar(MultipartFile file){
        String url = ossService.uploadOss(file,"android-music/singerAvatar/",true);
        return R.ok().data("url",url);
    }
    @PostMapping("/songPic")
    public R uploadsongPic(MultipartFile file){
        String url = ossService.uploadOss(file,"android-music/songPic/",true);
        return R.ok().data("url",url);
    }
    @PostMapping("/song")
    public R uploadSong(MultipartFile file){
        String url = ossService.uploadOss(file,"android-music/song/",true);
        return R.ok().data("url",url);
    }
    @PostMapping("/banner")
    public R uploadBanner(MultipartFile file){
        String url = ossService.uploadOss(file,"android-music/banner/",true);
        return R.ok().data("url",url);
    }

    //删除阿里云上的文件
    @DeleteMapping("/deleteFile")
    public R deleteSong(@RequestBody String fileName){
        boolean isSuccess = ossService.deleteOss(fileName);
        if(isSuccess){
            return R.ok();
        }else {
            return R.error();
        }
    }

}
