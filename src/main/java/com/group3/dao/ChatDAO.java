package com.group3.dao;

import com.group3.entity.Chat;

public interface ChatDAO {
    int deleteByPrimaryKey(Integer id);

    int insert(Chat record);

    int insertSelective(Chat record);

    Chat selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Chat record);

    int updateByPrimaryKeyWithBLOBs(Chat record);

    int updateByPrimaryKey(Chat record);
}