package com.group3.redis.key;

import com.group3.redis.BasePrefix;

/**
 * @ClassName:
 * @Description:
 * @author: lvxz
 * @create: 2018-08-30 13:18
 */


public class UserInfoKey extends BasePrefix {

    private boolean flag;//true存在该值,不需要set redis; false不存在该值,需要set redis

    public UserInfoKey(String prefix) {
        super(prefix);
    }

    public UserInfoKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public static UserInfoKey getByPhone = new UserInfoKey("phone");//默认无限时间

    public static UserInfoKey setExpireAndGetByPhone = new UserInfoKey(60*15,"phone");//默认设置15min

}
