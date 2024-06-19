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
@ApiModel(value="User对象", description="")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "微信的openid")
    private String openid;

    private String name;

    private String passward;

    @ApiModelProperty(value = "性别 1男 0女")
    private Boolean sex;

    private String phoneNum;

    private Integer age;

    @ApiModelProperty(value = "个性签名")
    private String introduction;

    @ApiModelProperty(value = "头像，实际存储的是图片地址")
    private String avatar;

    @ApiModelProperty(value = "是否禁用 1true禁用   0false未禁用")
    private Boolean isDisable;

    @ApiModelProperty(value = "逻辑删除 1（true）已删除， 0（false）未删除")
    @TableLogic
    private Integer isDeleted;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

}
