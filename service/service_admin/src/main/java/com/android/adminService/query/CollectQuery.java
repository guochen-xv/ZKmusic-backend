package com.android.adminService.query;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;

/*用于条件查询的对象*/
@Data
public class CollectQuery {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "歌曲id")
    private String songId;

    @ApiModelProperty(value = "歌单id")
    private String songListId;

}
