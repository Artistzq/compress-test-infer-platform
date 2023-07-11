package com.kerbalogy.ctip.auth.vanilla.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author yaozongqing@outlook.com
 * @date 2023-07-11
 * @description
 **/

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_role")
public class Role {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String roleName;

    private Integer isDisable;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

}
