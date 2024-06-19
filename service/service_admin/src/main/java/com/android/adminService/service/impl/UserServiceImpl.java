package com.android.adminService.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.android.adminService.entity.LoginVo;
import com.android.adminService.entity.RegisterVo;
import com.android.adminService.entity.User;
import com.android.adminService.mapper.UserMapper;
import com.android.adminService.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.android.serviceBase.handler.ZkException;
import commonutils.JwtUtils;
import commonutils.MD5;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author android
 * @since 2021-01-06
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    /**
     * 用户登录
     */
    @Override
    public String login(LoginVo loginVo) {
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();
        //校验参数
        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password) ) {
            throw  new ZkException(20001,"密码和手机号不能为空");
        }
        //获取用户信息
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("phone_num",mobile);
        User user = baseMapper.selectOne(wrapper);
        if(null == user) {
            throw  new ZkException(20001,"该手机号未注册");
        }

        //校验密码
        if( !MD5.encrypt(password).equals(user.getPassward()) ){
            throw  new ZkException(20001,"用户密码错误");
        }
        //校验是否被禁用
        if(user.getIsDisable()) {
            throw  new ZkException(20001,"该用户禁止使用");
        }
        //使用JWT生成token字符串
        String token = JwtUtils.getJwtToken(user.getId(), user.getName());
        return token;
    }
    /**
     * 会员注册
     * @param registerVo
     */
    @Override
    public void register(RegisterVo registerVo) {
        //获取注册信息，进行校验
        String nickname = registerVo.getNickname();
        String mobile = registerVo.getMobile();
        String password = registerVo.getPassword();
        String code = registerVo.getCode();
        //校验参数StringUtils.isEmpty(code)
        if(StringUtils.isEmpty(mobile) ||
                StringUtils.isEmpty(mobile) ||
                StringUtils.isEmpty(password)
                ) {
            throw new ZkException(20001,"用户注册失败");
        }
        //校验校验验证码
        //从redis获取发送的验证码
        //TODO
       /* String mobleCode = redisTemplate.opsForValue().get(mobile);
        if(!code.equals(mobleCode)) {
            throw new ZkException(20001,"验证码错误");
        }*/
        //查询数据库中是否存在相同的手机号码
        Integer count = baseMapper.selectCount(new QueryWrapper<User>().eq("phone_num", mobile));
        if(count > 0) {
            throw new ZkException(20001,"该用户已注册");
        }
        //添加注册信息到数据库
        User member = new User();
        member.setName(nickname);
        member.setPhoneNum(registerVo.getMobile());
        member.setPassward(MD5.encrypt(password));
        //TODO 默认头像
        member.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132");
        this.save(member);
    }
}
