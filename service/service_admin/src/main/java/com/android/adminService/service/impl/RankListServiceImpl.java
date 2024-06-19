package com.android.adminService.service.impl;

import com.android.adminService.entity.RankList;
import com.android.adminService.mapper.RankListMapper;
import com.android.adminService.service.RankListService;
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
 * @since 2021-02-06
 */
@Service
public class RankListServiceImpl extends ServiceImpl<RankListMapper, RankList> implements RankListService {

}
