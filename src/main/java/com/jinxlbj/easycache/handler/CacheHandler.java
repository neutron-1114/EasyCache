package com.jinxlbj.easycache.handler;

/**
 * @author jinxLbj
 * @date 2021-10-26 16:51
 **/

public interface CacheHandler {

    boolean set(String key, byte[] value, int ttl);

    byte[] get(String key);

    boolean expire(String key);

}
