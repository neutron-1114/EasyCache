# EasyCache
###有时候单独写个缓存实在是太累了，就写了个自用的缓存工具
#目的
####1、把序列化和缓存工具解耦，可以随时自己定制
####2、随时使用，不需要过多的配置
```$xslt
EasyCache easyCache = EasyCacheContext.getEasyCache();
for (int i = 0; i < 100; i++) {
    String key = String.valueOf(i);
    TestA ta = TestA.getRandomInstance();
    if (easyCache.set(key, ta, i + 1)) {
        TestA tb = (TestA) easyCache.get(key).orElse(new TestA());
        System.out.println(ta.equals(tb));
    }
}
EasyCacheContext.addEasyCache("cache-1", new RedisCacheHandler(), new HessianSerializationHandler());
easyCache = EasyCacheContext.getEasyCache("cache-1");
for (int i = 0; i < 100; i++) {
    String key = String.valueOf(i);
    TestA ta = TestA.getRandomInstance();
    if (easyCache.set(key, ta, i + 1)) {
        TestA tb = (TestA) easyCache.get(key).orElse(new TestA());
        System.out.println(ta.equals(tb));
    }
}
```