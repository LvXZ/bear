package com.group3.redis.Impl;

import com.alibaba.fastjson.JSON;
import com.group3.entity.Goods;
import com.group3.redis.KeyPrefix;
import com.group3.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName:
 * @Description:
 * @author: lvxz
 * @create: 2018-09-01 18:09
 */

@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    private JedisPool jedisPoolDAO;

    /*************************************String********************************************/

    public <T> T stringGet(KeyPrefix prefix, String key,  Class<T> clazz) {
        Jedis jedis = null;
        try {
            jedis =  jedisPoolDAO.getResource();
            //生成真正的key
            String realKey  = prefix.getPrefix() + key;
            String  str = jedis.get(realKey);
            T t =  JSONToBean(str, clazz);
            return t;
        }finally {
            returnToPool(jedis);
        }
    }

    public <T> boolean stringSet(KeyPrefix prefix, String key,  T value) {
        Jedis jedis = null;
        try {
            jedis =  jedisPoolDAO.getResource();
            String str = beanToJSON(value);
            if(str == null || str.length() <= 0) {
                return false;
            }
            //生成真正的key
            String realKey  = prefix.getPrefix() + key;
            int seconds =  prefix.getExpireSeconds();
            if(seconds <= 0) {
                jedis.set(realKey, str);
            }else {
                jedis.setex(realKey, seconds, str);
            }
            return true;
        }finally {
            returnToPool(jedis);
        }
    }

    public <T> boolean stringExists(KeyPrefix prefix, String key) {
        Jedis jedis = null;
        try {
            jedis =  jedisPoolDAO.getResource();
            //生成真正的key
            String realKey  = prefix.getPrefix() + key;
            return  jedis.exists(realKey);
        }finally {
            returnToPool(jedis);
        }
    }

    public <T> Long stringIncr(KeyPrefix prefix, String key) {
        Jedis jedis = null;
        try {
            jedis =  jedisPoolDAO.getResource();
            //生成真正的key
            String realKey  = prefix.getPrefix() + key;
            return  jedis.incr(realKey);
        }finally {
            returnToPool(jedis);
        }
    }

    public <T> Long stringDecr(KeyPrefix prefix, String key) {
        Jedis jedis = null;
        try {
            jedis =  jedisPoolDAO.getResource();
            //生成真正的key
            String realKey  = prefix.getPrefix() + key;
            return  jedis.decr(realKey);
        }finally {
            returnToPool(jedis);
        }
    }


    /*************************************Hash********************************************/

    public <T> T hashGet(KeyPrefix prefix, String secondKey,  Class<T> clazz) {
        Jedis jedis = null;
        try {
            jedis =  jedisPoolDAO.getResource();
            String firstKey = prefix.getPrefix();
            String  str = jedis.hget(firstKey, secondKey);
            T value =  JSONToBean(str, clazz);
            return value;
        }finally {
            returnToPool(jedis);
        }
    }

    public <T> boolean hashSet(KeyPrefix prefix, String secondKey,  T value) {
        Jedis jedis = null;
        try {
            jedis =  jedisPoolDAO.getResource();
            String str = beanToJSON(value);
            if(str == null || str.length() <= 0) {
                return false;
            }
            String firstKey = prefix.getPrefix();
            jedis.hset(firstKey, secondKey, str);


			/*int seconds =  prefix.getExpireSeconds();
			if(seconds <= 0) {
				jedis.hset(firstKey, secondKey, str);
			}else {
				jedis.hsetex(realKey, seconds, str);//无该功能
			}*/
            return true;
        }finally {
            returnToPool(jedis);
        }
    }

    public <T> boolean hashExists(KeyPrefix prefix, String secondKey) {
        Jedis jedis = null;
        try {
            jedis =  jedisPoolDAO.getResource();
            String firstKey = prefix.getPrefix();
            return  jedis.hexists(firstKey, secondKey);
        }finally {
            returnToPool(jedis);
        }
    }

    /*************************************Sorted set********************************************/

    public <T> List<T> ZsetGet(KeyPrefix prefix, long start,  Class<T> clazz) {
        Jedis jedis = null;
        try {
            jedis =  jedisPoolDAO.getResource();

            String firstKey = prefix.getPrefix();
            Set<String> stringSet = jedis.zrevrange(firstKey,start,start+9);
            List<T> valueList = new ArrayList<>();
            for(String str:stringSet){

                T value =  JSONToBean(str, clazz);
                valueList.add(value);
            }

            return valueList;
        }finally {
            returnToPool(jedis);
        }
    }


    public <T> boolean ZsetAdd(KeyPrefix prefix, Integer score,  T value) {
        Jedis jedis = null;
        try {
            jedis =  jedisPoolDAO.getResource();
            String str = beanToJSON(value);
            if(str == null || str.length() <= 0) {
                return false;
            }
            String firstKey = prefix.getPrefix();
            jedis.zadd(firstKey,score,str);

            return true;
        }finally {
            returnToPool(jedis);
        }
    }

    public boolean ZsetAddList(KeyPrefix prefix, List<Goods> valueList) {
        Jedis jedis = null;
        try {
            jedis =  jedisPoolDAO.getResource();

            String firstKey = prefix.getPrefix();
            for(Goods value:valueList){
                String str = beanToJSON(value);
                if(str == null || str.length() <= 0) {
                    return false;
                }
                jedis.zadd(firstKey,value.getPriority(),str);

            }
            return true;
        }finally {
            returnToPool(jedis);
        }
    }


    /*************************************JSON转换、pool关闭*****************************************/
    private <T> String beanToJSON(T value) {
        if(value == null) {
            return null;
        }
        Class<?> clazz = value.getClass();
        if(clazz == int.class || clazz == Integer.class) {
            return ""+value;
        }else if(clazz == String.class) {
            return (String)value;
        }else if(clazz == long.class || clazz == Long.class) {
            return ""+value;
        }else {
            return JSON.toJSONString(value);
        }
    }

    private <T> T JSONToBean(String str, Class<T> clazz) {
        if(str == null || str.length() <= 0 || clazz == null) {
            return null;
        }
        if(clazz == int.class || clazz == Integer.class) {
            return (T)Integer.valueOf(str);
        }else if(clazz == String.class) {
            return (T)str;
        }else if(clazz == long.class || clazz == Long.class) {
            return  (T)Long.valueOf(str);
        }else {
            return JSON.toJavaObject(JSON.parseObject(str), clazz);
        }
    }

    private void returnToPool(Jedis jedis) {
        if(jedis != null) {
            jedis.close();
        }
    }

}
