package com.android.adminService.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/*用于条件查询的对象*/
@Data
public class SingerQuery {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "歌手名称,模糊查询")
    private String name;

    @ApiModelProperty(value = "性别 1男 0女")
    private Integer sex;

    @ApiModelProperty(value = "生日", example = "2019-01-01")
    private String birth;//注意，这里使用的是String类型，前端传过来的数据无需进行类型转换

}
