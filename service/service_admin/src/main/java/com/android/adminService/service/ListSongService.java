package com.android.adminService.service;

import com.android.adminService.entity.ListSong;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 歌单的歌曲表

一个歌单包含多个歌曲

多对多关系 服务类
 * </p>
 *
 * @author android
 * @since 2021-01-06
 */
public interface ListSongService extends IService<ListSong> {

}
