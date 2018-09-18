package com.group3.service;

import com.group3.dto.ResponseInfoDTO;
import com.group3.entity.Goods;
import com.group3.entity.Shop;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.List;

/**
 * @ClassName:
 * @Description:
 * @Author: lvxz
 * @Date: 2018-08-29  08:11
 */
public interface ShopManageService {

    /**
     * 开设店铺
     * @param shop
     * @param shopImg 图片
     * @return
     */
    ResponseInfoDTO<Shop> addNewShop(Shop shop, MultipartFile shopImg);

    /**
     * 查询单个店铺信息
     * @return
     */
    ResponseInfoDTO<Shop> queryShopInfoByShopId(Integer shopId);

    /**
     * 查询单个店铺信息
     * @return
     */
    ResponseInfoDTO<Shop> queryShopInfoByOwnerId(String ownerId);

    /**
     * 修改店铺信息
     * @return
     */
    ResponseInfoDTO<Shop> modifyShopInfoByShopId(Shop shop);

    /**
     * 修改店铺图片
     * @return
     */
    ResponseInfoDTO<Shop> modifyShopImageByShopId(Shop shop, MultipartFile shopImg);



    /**
     * 查询多个店铺信息--优先级、类别
     * @return List<Shop>
     */
    ResponseInfoDTO<List<Shop>> queryShopListByPriorityOrCategory(Shop shop);






//    /**
//     * 查询店铺的订单
//     * @throws RemoteException
//     * @return List<Order>
//     */
//    List<Order> queryOrderByShopId(String shopid) throws RemoteException;

    /**
     * 修改订单信息
     * @return 0/1
     */
    //int modifyOrderInfoByOrderId(int orderid,Order order);




}
