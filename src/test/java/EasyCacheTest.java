import core.EasyCache;
import core.EasyCacheContext;
import handler.impl.RedisCacheHandler;
import model.TestA;
import org.junit.Test;
import serialization.impl.HessianSerializationHandler;

/**
 * @author
 * @date 2021-10-26 21:39
 * @desc
 **/

public class EasyCacheTest {

    @Test
    public void test1() {
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
    }


}
