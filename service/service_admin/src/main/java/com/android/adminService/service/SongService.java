package com.android.adminService.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.android.adminService.entity.Song;
import com.baomidou.mybatisplus.extension.service.IService;
import com.android.adminService.query.SongFrontQuery;
import commonutils.R;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author android
 * @since 2021-01-06
 */
public interface SongService extends IService<Song> {

    R getFrontSongList(Page<Song> pageSong, SongFrontQuery songFrontQuery);
    List<String> getSongIDs();
}
