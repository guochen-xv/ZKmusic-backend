package com.android.adminService.controller;


import com.android.adminService.entity.Subject;
import com.android.adminService.service.SubjectService;
import commonutils.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author android
 * @since 2021-01-25
 */
@RestController
@RequestMapping("/adminService/subject")
@CrossOrigin
public class SubjectController {
    @Autowired
    private SubjectService subjectService;

    @ApiOperation("所有分类列表")
    @GetMapping("/getAllSubject")
    private R getAllSubject(){
        List<Subject> list = subjectService.list(null);
        return R.ok().data("items",list);
    }

    @ApiOperation("查询分类")
    @GetMapping("/getSubject/{id}")
    private R getSubject(@PathVariable int id){
        Subject subject = subjectService.getById(id);
        return R.ok().data("subject",subject);
    }
}

