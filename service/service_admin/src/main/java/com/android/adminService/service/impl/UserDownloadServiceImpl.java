package com.android.adminService.service.impl;

import com.android.adminService.entity.UserDownload;
import com.android.adminService.mapper.UserDownloadMapper;
import com.android.adminService.service.UserDownloadService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 歌单的歌曲表

一个歌单包含多个歌曲

多对多关系 服务实现类
 * </p>
 *
 * @author android
 * @since 2022-04-03
 */
@Service
public class UserDownloadServiceImpl extends ServiceImpl<UserDownloadMapper, UserDownload> implements UserDownloadService {

}
