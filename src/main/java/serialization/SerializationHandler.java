package serialization;

/**
 * @author jinxLbj
 * @date 2021-10-26 16:53
 **/

public interface SerializationHandler {

    Object deserialize(byte[] bytes);

    byte[] serialize(Object object);

}
