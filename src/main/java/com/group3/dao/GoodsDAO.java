package com.group3.dao;

import com.group3.entity.Goods;

import java.util.List;
import java.util.Map;

public interface GoodsDAO {

    /**
     * 店铺上架商品
     * @param goods
     * @return
     */
    int insertGoods(Goods goods);

    /**
     * 更新商品图片
     * @param goods
     * @return
     */
    int updateImageByGoods(Goods goods);

    /**
     * 优先级搜索商品 >= ?
     * @param map
     * @return
     */
    List<Goods> selectGoodsByPriorityCeil(Map map);

    /**
     * 优先级搜索商品 == ?
     * @param priority
     * @return
     */
    List<Goods> selectGoodsByPriority(Integer priority);

    /**
     * 类别搜索商品
     * @param category
     * @return
     */
    List<Goods> selectGoodsByCategory(String category);

    /**
     * 名称模糊搜索商品
     * @param godName
     * @return
     */
    List<Goods> selectGoodsByGoodName(String godName);

    /**
     * goodsId查询商品
     * @param goodsId
     * @return
     */
    Goods selectGoodsInfoByGoodsId(Integer goodsId);



    int updateGoodsInfoByGoodsId(Goods goods);


    int deleteByGoodsId(Integer goodId);


    List<Goods> selectGoodsByGoodNameAndShopId(Goods goods);

    /**
     * 查询用户收藏的商品
     * @param userId
     * @return
     */
    List<Goods> selectGoodsByCollectGoodsAndUserId(String userId);

}