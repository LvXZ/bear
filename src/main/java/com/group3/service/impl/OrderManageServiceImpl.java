package com.group3.service.impl;

import com.group3.dao.OrderDAO;
import com.group3.dto.MessageDTO;
import com.group3.dto.ResponseInfoDTO;
import com.group3.entity.Order;
import com.group3.service.OrderManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName:
 * @Description:
 * @author: lvxz
 * @create: 2018-09-02 20:12
 */

@Service
public class OrderManageServiceImpl implements OrderManageService {

    @Autowired
    private OrderDAO orderDAO;

    @Override
    public ResponseInfoDTO<Order> insertOrder(Order order) {

        order.setDeal(0);

        int flag = orderDAO.insertOrder(order);
        if(flag == 1){
            return ResponseInfoDTO.success(MessageDTO.SUCCESS);
        }else{
            return ResponseInfoDTO.fail("订单生成失败");
        }

    }

    @Override
    public ResponseInfoDTO<List<Order>> queryDealOrdersByUserPhone(String userPhone) {

        List<Order> orderList = orderDAO.selectByOrderUserPhone(userPhone);

        return ResponseInfoDTO.success(orderList);
    }

    @Override
    public ResponseInfoDTO<List<Order>> queryDealOrdersByShopOwnerId(String ownerId) {
        List<Order> orderList = orderDAO.selectByShopOwner(ownerId);
        return ResponseInfoDTO.success(orderList);
    }
}
