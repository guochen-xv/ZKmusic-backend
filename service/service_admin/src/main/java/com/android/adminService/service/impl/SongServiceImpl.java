package com.android.adminService.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.android.adminService.entity.Song;
import com.android.adminService.mapper.SongMapper;
import com.android.adminService.query.SongFrontQuery;
import com.android.adminService.service.SongService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import commonutils.R;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author android
 * @since 2021-01-06
 */
@Service
public class SongServiceImpl extends ServiceImpl<SongMapper, Song> implements SongService {

    @Override
    public R getFrontSongList(Page<Song> pageSong, SongFrontQuery songFrontQuery) {
        QueryWrapper<Song> wrapper = new QueryWrapper<>();

        if(!StringUtils.isEmpty(songFrontQuery.getSubjectId())) {
            wrapper.eq("subject_id",songFrontQuery.getSubjectId());
        }
        if(!StringUtils.isEmpty(songFrontQuery.getLicenseNumSort())) { //播发量
            wrapper.orderByDesc("license_num");
        }
        if(!StringUtils.isEmpty(songFrontQuery.getPublishTimeSort())) {
            wrapper.orderByDesc("publish_time");
        }
        if(!StringUtils.isEmpty(songFrontQuery.getCommentNumSort())) {
            wrapper.orderByDesc("comment_num");
        }
        if(!StringUtils.isEmpty(songFrontQuery.getPriceSort())) {
            wrapper.orderByDesc("price");
        }
        baseMapper.selectPage(pageSong,wrapper);
        //把分页的数据获取出来放到map中
        List<Song> records = pageSong.getRecords();
        long total = pageSong.getTotal();
        return R.ok().data("total",total).data("rows",records);
    }

    @Override
    public List<String> getSongIDs() {
        return baseMapper.getAllSongIds();
    }
}
