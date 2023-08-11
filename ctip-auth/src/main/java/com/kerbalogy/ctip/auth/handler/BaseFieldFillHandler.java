package com.kerbalogy.ctip.auth.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

/**
 * @author yaozongqing@outlook.com
 * @date 2023-08-09
 * @description
 **/
@Component
public class BaseFieldFillHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        Object createBy = getFieldValByName("createdBy", metaObject);
        Object createDate = getFieldValByName("createdDate", metaObject);

        if (createBy == null) {
//            this.strictInsertFill(metaObject, "createdBy", String.class, Optional.ofNullable(SecurityUtils.getCurrentUsername()).orElse("system"));
            this.strictInsertFill(metaObject, "createdBy", String.class, "system");
        }

        if (createDate == null) {
            this.strictInsertFill(metaObject, "createdDate", Date.class, new Date());
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Object updateBy = getFieldValByName("updatedBy", metaObject);
        Object updateDate = getFieldValByName("updatedDate", metaObject);

        if (updateBy == null) {
            this.strictUpdateFill(metaObject, "updatedBy", String.class, "system");
        }

        if (updateDate == null) {
            this.strictUpdateFill(metaObject, "updatedDate", Date.class, new Date());
        }
    }
}
