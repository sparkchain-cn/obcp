package cn.obcp.user.shiro.serializer;

import cn.obcp.user.Exception.SerializationException;

public interface RedisSerializer<T> {

    byte[] serialize(T t) throws SerializationException;

    T deserialize(byte[] bytes) throws SerializationException;
}
