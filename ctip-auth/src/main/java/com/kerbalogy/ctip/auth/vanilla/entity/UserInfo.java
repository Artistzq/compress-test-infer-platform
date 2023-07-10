package com.kerbalogy.ctip.auth.vanilla.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author yaozongqing@outlook.com
 * @date 2023/7/10 22:04
 * @description
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_user_info")
public class UserInfo {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String email;

    private String nickname;

    private String avatar;

    private String intro;

    private String website;

    private Integer isSubscribe;

    private Integer isDisable;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

}
