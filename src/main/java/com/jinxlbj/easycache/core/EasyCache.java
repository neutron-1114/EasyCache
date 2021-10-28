package com.jinxlbj.easycache.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;
import com.jinxlbj.easycache.handler.CacheHandler;
import com.jinxlbj.easycache.serialization.SerializationHandler;

/**
 * @author jinxLbj
 * @date 2021-10-26 16:52
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EasyCache {

    private String namespace;

    private CacheHandler cacheHandler;

    private SerializationHandler serializationHandler;

    private static int DEFAULT_TTL = 60 * 60;

    String getNamespace() {
        return namespace;
    }

    protected void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    protected CacheHandler getCacheHandler() {
        return cacheHandler;
    }

    protected void setCacheHandler(CacheHandler cacheHandler) {
        this.cacheHandler = cacheHandler;
    }

    protected SerializationHandler getSerializationHandler() {
        return serializationHandler;
    }

    protected void setSerializationHandler(SerializationHandler serializationHandler) {
        this.serializationHandler = serializationHandler;
    }

    public boolean set(String key, Object obj) {
        byte[] bytes = this.serializationHandler.serialize(obj);
        return cacheHandler.set(key, bytes, DEFAULT_TTL);
    }

    public boolean set(String key, Object obj, int ttl) {
        byte[] bytes = this.serializationHandler.serialize(obj);
        return cacheHandler.set(key, bytes, ttl);
    }

    public Optional<Object> get(String key) {
        byte[] value = cacheHandler.get(key);
        if (value == null) {
            return Optional.empty();
        }
        return Optional.of(serializationHandler.deserialize(value));
    }

    public boolean expire(String key) {
        return cacheHandler.expire(key);
    }

}
