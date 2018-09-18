package com.group3.redis;


/**
 * key设置接口类
 */
public interface KeyPrefix {

	int getExpireSeconds();//过期时间

	String getPrefix();//key前缀

}
