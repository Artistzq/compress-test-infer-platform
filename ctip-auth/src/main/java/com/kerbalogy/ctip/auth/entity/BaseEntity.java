package com.kerbalogy.ctip.auth.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author yaozongqing@outlook.com
 * @date 2023-08-09
 * @description
 **/
@Getter
@Setter
//@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "className", visible = true)
public class BaseEntity{
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "created_by", fill = FieldFill.INSERT)
    private String createdBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(value = "created_date", fill = FieldFill.INSERT)
    private Date createdDate;

    @TableField(value = "updated_by", fill = FieldFill.INSERT_UPDATE)
    private String updatedBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(value = "updated_date", fill = FieldFill.INSERT_UPDATE)
    private Date updatedDate;

    @TableLogic
    @TableField("deleted")
    private Boolean deleted;
}
