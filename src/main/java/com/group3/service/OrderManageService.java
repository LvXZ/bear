package com.group3.service;

import com.group3.dto.ResponseInfoDTO;
import com.group3.entity.Order;
import com.group3.entity.Shop;

import java.util.List;

/**
 * @ClassName:
 * @Description:
 * @author: lvxz
 * @create: 2018-09-01 09:34
 */
public interface OrderManageService {


    /**
     * 购买完成  生成订单
     * @param order
     * @return
     */
    ResponseInfoDTO<Order> insertOrder(Order order) ;

    /**
     * 通过用户手机号 查询自己的订单
     * @param userPhone
     * @return
     */
    ResponseInfoDTO<List<Order>> queryDealOrdersByUserPhone(String userPhone);


    /**
     * 通过店铺主人id查询订单
     * @param ownerId
     * @return
     * @throws
     */
    ResponseInfoDTO<List<Order>> queryDealOrdersByShopOwnerId(String ownerId);


    /**
     * 通过订单id修改 deal
     * @param order
     */
    ResponseInfoDTO<Order> updateOrderDealByOrderId(Order order);
}
