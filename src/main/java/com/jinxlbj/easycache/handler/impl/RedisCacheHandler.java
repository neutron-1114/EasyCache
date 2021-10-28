package com.jinxlbj.easycache.handler.impl;

import com.jinxlbj.easycache.handler.CacheHandler;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisCacheHandler implements CacheHandler {

    private JedisPool jedisPool;

    public RedisCacheHandler() {
        this.jedisPool = new JedisPool(new GenericObjectPoolConfig(), "127.0.0.1", 6379, 200, null, 0);
    }

    public RedisCacheHandler(final String host, int port,
                             int timeout, final String password, final int database) {
        this.jedisPool = new JedisPool(new GenericObjectPoolConfig(), host, port, timeout, password, database);
    }

    public RedisCacheHandler(final GenericObjectPoolConfig poolConfig, final String host, int port,
                             int timeout, final String password, final int database) {
        this.jedisPool = new JedisPool(poolConfig, host, port, timeout, password, database);
    }

    @Override
    public boolean set(String key, byte[] value, int ttl) {
        try (Jedis jedis = this.jedisPool.getResource()) {
            return "OK".equals(jedis.setex(key.getBytes(), ttl, value));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public byte[] get(String key) {
        try (Jedis jedis = this.jedisPool.getResource()) {
            return jedis.get(key.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean expire(String key) {
        try (Jedis jedis = this.jedisPool.getResource()) {
            return new Long(1).equals(jedis.del(key));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
