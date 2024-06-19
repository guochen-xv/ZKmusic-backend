package com.android.adminService.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.android.adminService.service.OssService;
import com.android.adminService.util.PropertiesUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {

    // Endpoint以杭州为例，其它Region请按实际情况填写。
    @Value("${aliyun.oss.file.endpoint}")
    String endpoint;
    // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
    @Value("${aliyun.oss.file.keyid}")
    String accessKeyId;

    @Value("${aliyun.oss.file.keysecret}")
    String accessKeySecret;

    @Value("${aliyun.oss.file.bucketname}")
    String bucketName;

    @Override
    public String uploadOss(MultipartFile file, String path, boolean isUUID) {
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 上传文件流。
        InputStream inputStream = null;
        String url="";

        //文件原始名
        String filename = file.getOriginalFilename();
        //文件名
        //String filename = file.getName();
        //System.out.print(filename+"  "+file.getOriginalFilename());
        if(isUUID){
             //给文件添加uuid
            String uuid = UUID.randomUUID().toString().replaceAll("-","");
            filename =path+uuid+filename;
        }else {
            filename =path+filename;
        }
        try {
            inputStream =file.getInputStream();
            ossClient.putObject(bucketName,
                    filename,
                    inputStream);
            // 关闭OSSClient。
            ossClient.shutdown();
            url = "http://" + bucketName + "." + endpoint+ "/" + filename;
        } catch (IOException e) {
            e.printStackTrace();
        }


        return url;
    }

    @Override
    public boolean deleteOss(String fileName) {
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        //文件是否存在
        boolean found = ossClient.doesObjectExist(bucketName, fileName);
        if(found){
            // 删除文件。如需删除文件夹，请将ObjectName设置为对应的文件夹名称。如果文件夹非空，则需要将文件夹下的所有object删除后才能删除该文件夹。
            ossClient.deleteObject(bucketName, fileName);
            return true;
        }else{
            return false;
        }

    }
    @Override
    public String uploadSingerAvatar(MultipartFile file) {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = PropertiesUtil.END_POINT;
        // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
        String accessKeyId = PropertiesUtil.ACCESS_KEY_ID;
        String accessKeySecret = PropertiesUtil.ACCESS_KEY_SECRET;

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 上传文件流。
        InputStream inputStream = null;
        String url="";
        //文件名
        String filename = file.getOriginalFilename();
        //给文件添加uuid
        String uuid = UUID.randomUUID().toString().replaceAll("-","");
        filename ="android-music/singerAvatar/"+uuid+filename;
        filename ="android-music/singerAvatar/"+filename;
        try {
            inputStream =file.getInputStream();
            ossClient.putObject(PropertiesUtil.BUCKET_NAME,
                    filename,
                    inputStream);
            // 关闭OSSClient。
            ossClient.shutdown();
            url = "http://" + PropertiesUtil.BUCKET_NAME + "." + PropertiesUtil.END_POINT+ "/" + filename;
        } catch (IOException e) {
            e.printStackTrace();
        }


        return url;
    }

    @Override
    public String uploadSongPic(MultipartFile file) {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = PropertiesUtil.END_POINT;
        // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
        String accessKeyId = PropertiesUtil.ACCESS_KEY_ID;
        String accessKeySecret = PropertiesUtil.ACCESS_KEY_SECRET;

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 上传文件流。
        InputStream inputStream = null;
        String url="";
        //文件名
        String filename = file.getOriginalFilename();
        //给文件添加uuid
        String uuid = UUID.randomUUID().toString().replaceAll("-","");
        filename ="android-music/songPic/"+uuid+filename;
        filename ="android-music/songPic/"+filename;
        try {
            inputStream =file.getInputStream();
            ossClient.putObject(PropertiesUtil.BUCKET_NAME,
                    filename,
                    inputStream);
            // 关闭OSSClient。
            ossClient.shutdown();
            url = "http://" + PropertiesUtil.BUCKET_NAME + "." + PropertiesUtil.END_POINT+ "/" + filename;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return url;
    }

    @Override
    public String uploadSong(MultipartFile file) {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = PropertiesUtil.END_POINT;
        // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
        String accessKeyId = PropertiesUtil.ACCESS_KEY_ID;
        String accessKeySecret = PropertiesUtil.ACCESS_KEY_SECRET;

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 上传文件流。
        InputStream inputStream = null;
        String url="";
        //文件名
        String filename = file.getOriginalFilename();

        //给文件添加uuid
        String uuid = UUID.randomUUID().toString().replaceAll("-","");
        filename ="android-music/song/"+uuid+filename;
        filename ="android-music/song/"+filename;
        try {
            inputStream =file.getInputStream();
            ossClient.putObject(PropertiesUtil.BUCKET_NAME,
                    filename,
                    inputStream);
            // 关闭OSSClient。
            ossClient.shutdown();
            url = "http://" + PropertiesUtil.BUCKET_NAME + "." + PropertiesUtil.END_POINT+ "/" + filename;
        } catch (IOException e) {
            e.printStackTrace();
        }


        return url;
    }
}
