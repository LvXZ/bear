package com.group3.service;

/**
 * @ClassName:
 * @Description:
 * @author: lvxz
 * @create: 2018-09-01 09:35
 */
public interface ShoppingCarService {


    /**
     * 查询我的购物车
     * @param userId
     * @return esponseInfoDTO<List<Goods>>
     */
    //ResponseInfoDTO<List<Shoppingcar>> findShoppingCarByUserId(int userid,int index,int numPerPage);

    /**
     * 更新我的购物车
     * @param cart
     * @return esponseInfoDTO<List<Goods>>
     */
    //ResponseInfoDTO<List<Goods>> updateShoppingCarByUserId(Shoppingcar shoppingcar);

    /**
     * 添加购物车
     * @param cart
     * @return ResponseInfoDTO<Goods>
     */
    //ResponseInfoDTO<Goods> insertMyCart(Shoppingcar shoppingcar);


    /**
     * 删除购物车
     * @param userId
     * @return ResponseInfoDTO<Goods>
     */
    //ResponseInfoDTO<Goods> deleteMyCartByUserIdProductId(String user_id, String goods_id);

    /**
     * 删除购物车
     * @param userId
     * @return ResponseInfoDTO<Goods>
     */
    //ResponseInfoDTO<Goods> deleteMyCartByUserId(String userId);

    /**
     * 獲取購物車裏商品的數量
     * @return
     * @throws RemoteException
     */
    //int getAllgoodsNumber(int userid);
}
