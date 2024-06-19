package com.android.adminService.service.impl;

import com.android.adminService.entity.Comment;
import com.android.adminService.mapper.CommentMapper;
import com.android.adminService.service.CommentService;
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
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

}
