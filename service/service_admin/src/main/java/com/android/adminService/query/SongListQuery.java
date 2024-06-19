package com.android.adminService.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SongListQuery {
    @ApiModelProperty(value = "歌单标题,模糊查询")
    private String title;
}
