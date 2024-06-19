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
 * @since 2021-01-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Song对象", description="")
public class Song implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    private String singerId;

    @ApiModelProperty(value = "歌手名")
    private String singerName;

    @ApiModelProperty(value = "歌名")
    private String name;

    @ApiModelProperty(value = "歌曲简介")
    private String introduction;

    @ApiModelProperty(value = "歌曲图片")
    private String pic;

    @ApiModelProperty(value = "歌曲歌词")
    private String lyric;

    @ApiModelProperty(value = "英文歌词翻译")
    private String transLyric;

    @ApiModelProperty(value = "歌曲地址")
    private String url;

    @ApiModelProperty(value = "歌曲发布时间")
    private String publishTime;

    @ApiModelProperty(value = "歌曲分类")
    private int subjectId;

    @ApiModelProperty(value = "价格")
    private int price;

    @ApiModelProperty(value = "收听数量")
    private int licenseNum;
    @ApiModelProperty(value = "评论数")
    private int commentNum;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @ApiModelProperty(value = "逻辑删除 1（true）已删除， 0（false）未删除")
    @TableLogic
    private Integer isDeleted;
}
