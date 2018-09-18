package com.group3.dao;

import com.group3.entity.Shop;

import java.util.List;

public interface ShopDAO {

    /**
     * 查看店铺是否存在
     * @param ownerId
     * @return
     */
    Shop selectShopExistByOwnerId(String ownerId);

    /**
     * 店铺插入
     * @param shop
     * @return
     */
    int insertShop(Shop shop);

    /**
     * 更新店铺图片（为店铺插入铺垫）
     * @param shop
     * @return
     */
    int updateImageByShop(Shop shop);

    /**
     * 查询店铺信息
     * @param shopId
     * @return
     */
    Shop selectShopInfoByShopId(Integer shopId);

    /**
     * 更新店铺信息
     * @param shop
     * @return
     */
    int updateShopInfoByShopId(Shop shop);

    /**
     * 查询多个店铺信息--优先级、类别
     * @param shop
     * @return
     */
    List<Shop> selectShopListByPriorityOrCategory(Shop shop);


    int deleteByPrimaryKey(Integer shopId);

}