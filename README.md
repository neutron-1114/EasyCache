# EasyCache
### 有时候单独写个缓存实在是太累了，就写了个自用的缓存工具
 Spring的注解还不太好使，我还在努力！
# 目的
* 1、把序列化和缓存工具解耦，可以随时自己定制
* 2、随时使用，不需要过多的配置
```$xslt
//本地缓存 + JAVA自带的序列化工具
EasyCache easyCache = EasyCacheContext.getEasyCache();
for (int i = 0; i < 100; i++) {
    String key = String.valueOf(i);
    TestA ta = TestA.getRandomInstance();
    if (easyCache.set(key, ta, i + 1)) {
        TestA tb = (TestA) easyCache.get(key).orElse(new TestA());
        System.out.println(ta.equals(tb));
    }
}
//REDIS缓存 + Hessian序列化工具
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