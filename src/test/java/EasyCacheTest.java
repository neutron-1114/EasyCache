import com.jinxlbj.easycache.core.EasyCache;
import com.jinxlbj.easycache.handler.impl.RedisCacheHandler;
import com.jinxlbj.easycache.core.EasyCacheContext;
import model.TestA;
import org.junit.Test;
import com.jinxlbj.easycache.serialization.impl.HessianSerializationHandler;

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
