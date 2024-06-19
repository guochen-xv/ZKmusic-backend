package com.android.adminService.mapper;

import com.android.adminService.entity.Song;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author android
 * @since 2021-01-06
 */
public interface SongMapper extends BaseMapper<Song> {
    List<String> getAllSongIds();
}
