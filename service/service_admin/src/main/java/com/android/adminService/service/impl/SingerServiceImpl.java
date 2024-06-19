package com.android.adminService.service.impl;

import com.android.adminService.service.SingerService;
import com.android.adminService.entity.Singer;
import com.android.adminService.mapper.SingerMapper;
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
public class SingerServiceImpl extends ServiceImpl<SingerMapper, Singer> implements SingerService {

}
