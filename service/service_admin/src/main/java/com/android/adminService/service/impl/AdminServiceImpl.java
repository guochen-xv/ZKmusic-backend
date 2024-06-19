package com.android.adminService.service.impl;

import com.android.adminService.entity.Admin;
import com.android.adminService.mapper.AdminMapper;
import com.android.adminService.service.AdminService;
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
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

}
