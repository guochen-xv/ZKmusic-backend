package com.android.adminService.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SongFrontQuery implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "课程名称")
    private String name;
    @ApiModelProperty(value = "歌手id")
    private String singerId;

    @ApiModelProperty(value = "分类id")
    private String subjectId;

    @ApiModelProperty(value = "发行时间排序")
    private String publishTimeSort;

    @ApiModelProperty(value = "播发量排序")
    private String licenseNumSort;

    @ApiModelProperty(value = "评论数排序")
    private String commentNumSort;

    @ApiModelProperty(value = "评论数排序")
    private String priceSort;
}
