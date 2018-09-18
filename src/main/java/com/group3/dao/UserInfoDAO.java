package com.group3.dao;

import com.group3.entity.UserInfo;

public interface UserInfoDAO {

    /**
     * 查询是否存在，注册使用
     * @param phone
     * @return
     */
    UserInfo selectUserExistByPhone(String phone);

    /**
     * 登录查询
     * @param phone
     * @return
     */
    UserInfo selectUserPasswordByPhone(String phone);

    /**
     * 修改查询
     * @param phone
     * @return
     */
    UserInfo selectUserInfoByPhone(String phone);

    /**
     * 注册
     * @param userInfo
     * @return
     */
    int insertUserInfo(UserInfo userInfo);

    /**
     * 更新信息
     * @param userInfo
     * @return
     */
    int updateUserInfoByPhone(UserInfo userInfo);


    /**
     * 更新密码
     * @param userInfo
     * @return
     */
    int updatePasswordByPhone(UserInfo userInfo);

}