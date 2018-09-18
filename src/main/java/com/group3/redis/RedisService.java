package com.group3.redis;

import com.group3.entity.Goods;
import com.group3.redis.KeyPrefix;

import java.util.List;

/**
 * @ClassName:
 * @Description:
 * @author: lvxz
 * @create: 2018-09-01 18:09
 */
public interface RedisService {

    /*************************************String********************************************/
    <T> T stringGet(KeyPrefix prefix, String key, Class<T> clazz);

    <T> boolean stringSet(KeyPrefix prefix, String key,  T value);

    <T> boolean stringExists(KeyPrefix prefix, String key);

    <T> Long stringIncr(KeyPrefix prefix, String key);

    <T> Long stringDecr(KeyPrefix prefix, String key);


    /*************************************Hash********************************************/
    <T> T hashGet(KeyPrefix prefix, String secondKey,  Class<T> clazz);

    <T> boolean hashSet(KeyPrefix prefix, String secondKey,  T value);

    <T> boolean hashExists(KeyPrefix prefix, String secondKey);

    /*************************************Sorted set********************************************/
    <T> List<T> ZsetGet(KeyPrefix prefix, long start,  Class<T> clazz);

    <T> boolean ZsetAdd(KeyPrefix prefix, Integer score,  T value);

    boolean ZsetAddList(KeyPrefix prefix, List<Goods> valueList);



}
