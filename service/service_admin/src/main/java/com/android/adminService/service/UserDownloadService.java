package com.android.adminService.service;

import com.android.adminService.entity.UserDownload;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 歌单的歌曲表

一个歌单包含多个歌曲

多对多关系 服务类
 * </p>
 *
 * @author android
 * @since 2022-04-03
 */
public interface UserDownloadService extends IService<UserDownload> {

}
