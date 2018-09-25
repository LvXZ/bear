package com.group3.dao;

import com.group3.entity.Order;

import java.util.List;

public interface OrderDAO {

    List<Order> selectByOrderUserPhone(String userPhone);


    List<Order> selectByShopOwner(String ownerId);

    int insertOrder(Order order);


    int deleteByPrimaryKey(Integer orderId);

    int insert(Order record);

    int updateDealByOrderId(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Integer orderId);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);
}