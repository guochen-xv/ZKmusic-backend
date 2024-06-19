package com.android.adminService.mapper;

import com.android.adminService.entity.UserHistory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 歌单的歌曲表

一个歌单包含多个歌曲

多对多关系 Mapper 接口
 * </p>
 *
 * @author android
 * @since 2021-12-04
 */
public interface UserHistoryMapper extends BaseMapper<UserHistory> {
    List<Integer> getUserIdBySongId(String id);

    List<String> getSongByUserId(int id);

    @Select("select DISTINCT song_id from user_history")
    List<String> getSongId();
}
