package com.android.adminService.service.impl;

import com.android.adminService.entity.ListSong;
import com.android.adminService.mapper.ListSongMapper;
import com.android.adminService.service.ListSongService;
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
 * @since 2021-01-06
 */
@Service
public class ListSongServiceImpl extends ServiceImpl<ListSongMapper, ListSong> implements ListSongService {

}
