package com.android.adminService.mapper;

import com.android.adminService.entity.RankList;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 歌单的歌曲表

一个歌单包含多个歌曲

多对多关系 Mapper 接口
 * </p>
 *
 * @author android
 * @since 2021-02-06
 */
public interface RankListMapper extends BaseMapper<RankList> {

}
