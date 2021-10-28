package core;

import handler.CacheHandler;
import handler.impl.LocalCacheHandler;
import serialization.SerializationHandler;
import serialization.impl.JavaSourceSerializationHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @author jinxLbj
 * @date 2021-10-26 16:46
 **/

public class EasyCacheContext {

    private static Logger logger = Logger.getLogger(EasyCacheContext.class.getName());

    private static Map<String, EasyCache> easyCacheMap = new HashMap<>();

    private final static String DEFAULT_NAMESPACE= "default";

    static {
        addEasyCache(DEFAULT_NAMESPACE, new LocalCacheHandler(), new JavaSourceSerializationHandler());
    }

    public synchronized static void addEasyCache(String namespace, CacheHandler cacheHandler, SerializationHandler serializationHandler) {
        if (easyCacheMap.get(namespace) != null) {
            throw new RuntimeException(String.format("Namespace: %s has already existed...", namespace));
        }
        easyCacheMap.put(namespace, EasyCacheBuilder.buildEasyCache(cacheHandler, serializationHandler));
        logger.info(String.format("[InitEasyCache] Namespace: %s, cache: %s, serialization: %s",
                namespace, cacheHandler.getClass().getName(), serializationHandler.getClass().getName()));
    }

    public static EasyCache getEasyCache(String namespace) {
        return easyCacheMap.get(namespace);
    }

    public static EasyCache getEasyCache() {
        return getEasyCache(DEFAULT_NAMESPACE);
    }

}
