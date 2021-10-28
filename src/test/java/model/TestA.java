package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author
 * @date 2021-10-27 12:06
 * @desc
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestA implements Serializable {

    private int var1;

    private String var2;

    private byte[] var3;

    private BigDecimal var4;

    private List<BigDecimal> var5;

    private Map<String, BigDecimal> var6;

    public static TestA getRandomInstance() {
        TestA ta = new TestA();
        ta.setVar1(new Random().nextInt());
        ta.setVar2(UUID.randomUUID().toString());
        ta.setVar3(UUID.randomUUID().toString().getBytes());
        ta.setVar4(new BigDecimal(new Random().nextDouble()));
        ta.setVar5(new ArrayList<BigDecimal>() {{
            add(new BigDecimal(new Random().nextDouble()));
            add(new BigDecimal(new Random().nextFloat()));
            add(new BigDecimal(new Random().nextGaussian()));
        }});
        ta.setVar6(new HashMap<String, BigDecimal>() {{
            put(UUID.randomUUID().toString(), new BigDecimal(new Random().nextDouble()));
            put(UUID.randomUUID().toString(), new BigDecimal(new Random().nextFloat()));
            put(UUID.randomUUID().toString(), new BigDecimal(new Random().nextGaussian()));
        }});
        return ta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestA testA = (TestA) o;
        return var1 == testA.var1 &&
                Objects.equals(var2, testA.var2) &&
                Arrays.equals(var3, testA.var3) &&
                Objects.equals(var4, testA.var4) &&
                Objects.equals(var5, testA.var5) &&
                Objects.equals(var6, testA.var6);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(var1, var2, var4, var5, var6);
        result = 31 * result + Arrays.hashCode(var3);
        return result;
    }
}
