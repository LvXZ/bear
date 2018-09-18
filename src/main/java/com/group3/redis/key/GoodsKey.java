package com.group3.redis.key;

import com.group3.redis.BasePrefix;

/**
 * @ClassName:
 * @Description:
 * @author: lvxz
 * @create: 2018-08-30 13:18
 */


public class GoodsKey extends BasePrefix {


    public GoodsKey(String prefix) {
        super(prefix);
    }

    public GoodsKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static GoodsKey getGoodsKey = new GoodsKey("score");
}
