package com.android.adminService.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author android
 * @since 2021-02-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Rank对象", description="")
@TableName(value = "top_rank")
public class Rank implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    private String creatorId;

    @ApiModelProperty(value = "歌单标题")
    private String title;

    @ApiModelProperty(value = "歌单图片")
    private String pic;

    private String introduction;

    private Integer commentNum;

    private Integer collectNum;

    @ApiModelProperty(value = "播放数")
    private Integer playNum;

    @ApiModelProperty(value = "歌单类型")
    private String style;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @ApiModelProperty(value = "逻辑删除 1（true）已删除， 0（false）未删除")
    @TableLogic
    private Integer isDeleted;


}
