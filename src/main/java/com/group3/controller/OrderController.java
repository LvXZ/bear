package com.group3.controller;

import com.alibaba.fastjson.JSON;
import com.group3.dto.ResponseInfoDTO;
import com.group3.entity.Order;
import com.group3.entity.Shop;
import com.group3.entity.UserInfo;
import com.group3.service.OrderManageService;
import com.group3.util.CookieUtil;
import com.group3.util.ParamsJSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @ClassName:
 * @Description:
 * @author: lvxz
 * @create: 2018-09-03 10:25
 */

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderManageService orderManageService;

    /**
     * 生成订单
     * Order
     * null
     */
    @PostMapping(value="/make_order")
    public ResponseInfoDTO<Order> makeOrder(@RequestBody String params, HttpServletRequest request, HttpServletResponse response) {


        String userCookiesValue = CookieUtil.getCookieUserInfoJson(request);

        if(userCookiesValue != null){
            System.out.println(userCookiesValue);
            UserInfo userinfo = JSON.parseObject(userCookiesValue, UserInfo.class);

            Order order = JSON.parseObject(params, Order.class);
            order.setUserTelephone(userinfo.getTelephone());
            order.setDeal(0);


            return orderManageService.insertOrder(order);

        }else {
            return ResponseInfoDTO.fail("登录已经失效，请您重新登录");
        }


    }


    /**
     * 用户查询订单
     * userPhone
     * null
     */
    @PostMapping(value="/get_user_order")
    public ResponseInfoDTO<List<Order>> getUserOrder(HttpServletRequest request, HttpServletResponse response) {

        String userCookiesValue = CookieUtil.getCookieUserInfoJson(request);

        if(userCookiesValue != null){
            System.out.println(userCookiesValue);
            UserInfo userinfo = JSON.parseObject(userCookiesValue, UserInfo.class);


            return orderManageService.queryDealOrdersByUserPhone(userinfo.getTelephone());

        }else {
            return ResponseInfoDTO.fail("登录已经失效，请您重新登录");
        }

    }


    /**
     * 店铺查询订单
     * userPhone
     * null
     */
    @PostMapping(value="/get_shop_order")
    public ResponseInfoDTO<List<Order>> getShopOrder(HttpServletRequest request, HttpServletResponse response) {

        String userCookiesValue = CookieUtil.getCookieUserInfoJson(request);

        if(userCookiesValue != null){
            System.out.println(userCookiesValue);
            UserInfo userinfo = JSON.parseObject(userCookiesValue, UserInfo.class);


            return orderManageService.queryDealOrdersByShopOwnerId(userinfo.getTelephone());

        }else {
            return ResponseInfoDTO.fail("登录已经失效，请您重新登录");
        }
    }


    /**
     * 更新订单的deal
     *
     */
    @PostMapping(value="/update_order")
    public ResponseInfoDTO<Order> updateOrder(@RequestBody String params,HttpServletRequest request, HttpServletResponse response) {

        String userCookiesValue = CookieUtil.getCookieUserInfoJson(request);

        if(userCookiesValue != null){

            Order order = JSON.parseObject(params, Order.class);

            return orderManageService.updateOrderDealByOrderId(order);

        }else {
            return ResponseInfoDTO.fail("登录已经失效，请您重新登录");
        }
    }

}
