package com.kerbalogy.ctip.cache.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author yaozongqing@outlook.com
 * @date 2023/7/26 20:42
 * @description 缓存消息
 */
@Data
@NoArgsConstructor
public class CacheMessage implements Serializable {

    @Serial
    private static final long serialVersionUID = -1L;

    @Getter
    @AllArgsConstructor
    public static enum Type {

        DELETE(0);

        private Integer code;
    }

    private String cacheName;

    private Object key;

    private Type type;

    public CacheMessage(String cacheName, Object key) {
        super();
        this.cacheName = cacheName;
        this.key = key;
    }

}
