package com.android.adminService.service;

import com.android.adminService.entity.LoginVo;
import com.android.adminService.entity.RegisterVo;
import com.android.adminService.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author android
 * @since 2021-01-06
 */
public interface UserService extends IService<User> {
    String login(LoginVo loginVo);

    void register(RegisterVo registerVo);
}
