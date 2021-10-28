package com.jinxlbj.easycache.handler.impl;

import com.jinxlbj.easycache.handler.CacheHandler;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

public class LocalCacheHandler implements CacheHandler {

    private static final int SCAN_INTERVAL = 10;

    private static final int EXPIRED_SLEEP_INTERVAL = 2;

    class Element {
        byte[] value;
        long timestamp;

        Element(byte[] value, long timestamp) {
            this.value = value;
            this.timestamp = timestamp;
        }
    }

    private ConcurrentHashMap<String, Element> cacheMap = new ConcurrentHashMap<>();

    private Queue<String> expireQueue = new LinkedList<>();

    {
        new Thread(() -> {
            while (true) {
                cacheMap.forEach((key, value) -> {
                    if (value.timestamp < System.currentTimeMillis()) {
                        expireQueue.add(key);
                    }
                });
                try {
                    Thread.sleep(SCAN_INTERVAL * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(() -> {
            while (true) {
                String key = expireQueue.poll();
                if (key == null) {
                    try {
                        Thread.sleep(EXPIRED_SLEEP_INTERVAL * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    cacheMap.remove(key);
                }
            }
        }).start();
    }

    @Override
    public boolean set(String key, byte[] value, int ttl) {
        Element e = new Element(value, System.currentTimeMillis() + ttl * 1000);
        cacheMap.put(key, e);
        return true;
    }

    @Override
    public byte[] get(String key) {
        Element e = cacheMap.get(key);
        if (e == null) {
            return null;
        } else if (e.timestamp < System.currentTimeMillis()) {
            cacheMap.remove(key);
            return null;
        } else {
            return e.value;
        }
    }

    @Override
    public boolean expire(String key) {
        Element e = cacheMap.get(key);
        if (e == null) {
            return false;
        } else {
            cacheMap.remove(key);
            return true;
        }
    }
}
