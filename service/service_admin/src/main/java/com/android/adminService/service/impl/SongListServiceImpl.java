package com.android.adminService.service.impl;

import com.android.adminService.entity.SongList;
import com.android.adminService.mapper.SongListMapper;
import com.android.adminService.service.SongListService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author android
 * @since 2021-01-06
 */
@Service
public class SongListServiceImpl extends ServiceImpl<SongListMapper, SongList> implements SongListService {

}
