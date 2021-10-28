package com.jinxlbj.easycache.core;

import com.jinxlbj.easycache.handler.CacheHandler;
import com.jinxlbj.easycache.handler.impl.LocalCacheHandler;
import com.jinxlbj.easycache.serialization.SerializationHandler;
import com.jinxlbj.easycache.serialization.impl.JavaSourceSerializationHandler;

import java.util.UUID;

class EasyCacheBuilder {

    static EasyCache buildEasyCache() {
        return buildEasyCache(new LocalCacheHandler(), new JavaSourceSerializationHandler());
    }

    static EasyCache buildEasyCache(CacheHandler cacheHandler, SerializationHandler serializationHandler) {
        return buildEasyCache(UUID.randomUUID().toString(), cacheHandler, serializationHandler);
    }

    static EasyCache buildEasyCache(String namespace, CacheHandler cacheHandler, SerializationHandler serializationHandler) {
        return new EasyCache(namespace, cacheHandler, serializationHandler);
    }

}
