package com.android.adminService.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SongQuery {
    @ApiModelProperty(value = "歌曲名称,模糊查询")
    private String name;

    @ApiModelProperty(value = "歌手姓名")
    private String singerName;

    @ApiModelProperty(value = "创建时间", example = "2019-01-01")
    private String createTime;//注意，这里使用的是String类型，前端传过来的数据无需进行类型转换
}
