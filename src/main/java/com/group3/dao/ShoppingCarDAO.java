package com.group3.dao;

import com.group3.entity.ShoppingCar;

public interface ShoppingCarDAO {
    int deleteByPrimaryKey(Integer id);

    int insert(ShoppingCar record);

    int insertSelective(ShoppingCar record);

    ShoppingCar selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ShoppingCar record);

    int updateByPrimaryKey(ShoppingCar record);
}