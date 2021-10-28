import model.TestA;
import org.junit.Test;
import serialization.impl.HessianSerializationHandler;
import serialization.impl.JavaSourceSerializationHandler;

/**
 * @author
 * @date 2021-10-26 18:36
 * @desc
 **/

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
