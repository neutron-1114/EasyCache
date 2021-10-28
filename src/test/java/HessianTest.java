import model.TestA;
import org.junit.Test;
import com.jinxlbj.easycache.serialization.impl.HessianSerializationHandler;

public class HessianTest {

    @Test
    public void test1() {
        for (int i = 0; i < 100; i++) {
            TestA t1 = TestA.getRandomInstance();
            HessianSerializationHandler hessianSerializationHandler = new HessianSerializationHandler();
            byte[] bytes = hessianSerializationHandler.serialize(t1);
            TestA t2 = (TestA) hessianSerializationHandler.deserialize(bytes);
            System.out.println(t1.equals(t2));
        }
    }

}
