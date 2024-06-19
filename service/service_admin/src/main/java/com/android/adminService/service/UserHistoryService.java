package com.android.adminService.service;

import com.android.adminService.entity.UserHistory;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 歌单的歌曲表

一个歌单包含多个歌曲

多对多关系 服务类
 * </p>
 *
 * @author android
 * @since 2021-12-04
 */
public interface UserHistoryService extends IService<UserHistory> {
    List<Integer> getUserIdBySongId(String id);

    List<String> getSongIdByUserId(int id);

    List<String> getSongIds();
}
