package com.kerbalogy.ctip.auth.vanilla.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yaozongqing@outlook.com
 * @date 2023/7/10 22:04
 * @description
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_user_role")
public class UserRole {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer userId;

    private Integer roleId;
}
