package com.group3.service;

/**
 * @ClassName:
 * @Description: 交易业务的接口
 * @author: lvxz
 * @create: 2018-09-01 09:40
 */


public interface TransactionService {


    /**
     * 收藏商品
     * @param goodsId
     * @return 0/1
     */
    //int CollectGoods(String goodsId,int i);


    /**
     * 把商品加入購物車
     * @param goodsId
     * @return 0/1
     */
    //int addIntoShoppingcar(Shoppingcar shopcar);

    /**
     * 取出收藏信息
     * @param goodsId 商品的id
     * @param userId 用戶的id
     * @return
     * @return CollectGoods
     */
    //List<Goods> getCollectionByUserId(int userid);
}
