package com.choxsu.redis.serializer;

import com.jfinal.plugin.redis.serializer.ISerializer;
import redis.clients.util.SafeEncoder;

/**
 * @author xiao qiu.su
 * @date 2019/4/26 0026
 */
public class MyRedisSerializer implements ISerializer {
    @Override
    public byte[] keyToBytes(String key) {
        return SafeEncoder.encode(key);
    }

    @Override
    public String keyFromBytes(byte[] bytes) {
        return SafeEncoder.encode(bytes);
    }

    @Override
    public byte[] fieldToBytes(Object field) {
        return valueToBytes(field);
    }

    @Override
    public Object fieldFromBytes(byte[] bytes) {
        return valueFromBytes(bytes);
    }

    @Override
    public byte[] valueToBytes(Object value) {
        return SafeEncoder.encode(value.toString());
    }

    @Override
    public Object valueFromBytes(byte[] bytes) {
        if (bytes == null || bytes.length == 0)
            return null;
        return SafeEncoder.encode(bytes);
    }
}
