import model.TestA;
import org.junit.Test;
import com.jinxlbj.easycache.serialization.impl.JavaSourceSerializationHandler;

public class JavaSourceTest {

    @Test
    public void test1() {
        TestA t1 = TestA.getRandomInstance();
        JavaSourceSerializationHandler javaSourceSerializationHandler = new JavaSourceSerializationHandler();
        byte[] bytes = javaSourceSerializationHandler.serialize(t1);
        TestA t2 = (TestA) javaSourceSerializationHandler.deserialize(bytes);
        System.out.println(t1.equals(t2));
    }

}
