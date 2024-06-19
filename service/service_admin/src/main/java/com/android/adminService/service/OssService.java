package com.android.adminService.service;


import org.springframework.web.multipart.MultipartFile;

public interface OssService {
    //path表示要存放的oss路径，isUUID表示是否为上传的文件生成一串随机值
    String uploadOss(MultipartFile file, String path, boolean isUUID);

    //删除文件
    boolean deleteOss(String fileName);

    //判断文件是否存在
    //上传歌手头像到oss
    String uploadSingerAvatar(MultipartFile file);
    //上传歌曲封面
    String uploadSongPic(MultipartFile file);
    //上传歌曲
    String uploadSong(MultipartFile file);
}
