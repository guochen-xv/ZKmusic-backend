package com.android.adminService.service.impl;

import com.android.adminService.entity.UserHistory;
import com.android.adminService.mapper.UserHistoryMapper;
import com.android.adminService.service.UserHistoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 歌单的歌曲表

一个歌单包含多个歌曲

多对多关系 服务实现类
 * </p>
 *
 * @author android
 * @since 2021-12-04
 */
@Service
public class UserHistoryServiceImpl extends ServiceImpl<UserHistoryMapper, UserHistory> implements UserHistoryService {

    @Override
    public List<Integer> getUserIdBySongId(String id) {
        return this.baseMapper.getUserIdBySongId(id);
    }

    @Override
    public List<String> getSongIdByUserId(int id) {
        return this.baseMapper.getSongByUserId(id);
    }

    @Override
    public List<String> getSongIds() {
        return this.baseMapper.getSongId();
    }
}
