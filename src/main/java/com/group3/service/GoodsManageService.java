package com.group3.service;

import com.group3.dto.ResponseInfoDTO;
import com.group3.entity.CollectGoods;
import com.group3.entity.Goods;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @ClassName:
 * @Description:
 * @author: lvxz
 * @create: 2018-08-30 10:20
 */
public interface GoodsManageService {

    /**
     * 优先级搜索商品 >= ?
     * @param priority
     * @return
     */
    ResponseInfoDTO<List<Goods>> searchGoodsByPriorityCeil(Integer priority, Integer startIndex);

    /**
     * 优先级搜索商品 == ?
     * @param priority
     * @return
     */
    ResponseInfoDTO<List<Goods>> searchGoodsByPriority(Integer priority);

    /**
     * 类别搜索商品
     * @param category
     * @return
     */
    ResponseInfoDTO<List<Goods>> searchGoodsByCategory(String category);

    /**
     * 名称搜索商品
     * @param goodsName
     * @return
     */
    ResponseInfoDTO<List<Goods>> searchGoodsByGoodsName(String goodsName);

    /**
     * 店铺上架自家的商品
     * @param goods
     * @param goodsImg 图片
     * @return
     */
    ResponseInfoDTO<Goods> addNewGoods(Goods goods, MultipartFile goodsImg);

    /**
     * 简单查询单个商品
     * @param goodId
     * @return
     */
    ResponseInfoDTO<Goods> findGoodsInfoByGoodsId(Integer goodId);

    /**
     * 店铺修改自家的商品信息
     * @param goods
     * @return
     */
    ResponseInfoDTO<Goods> modifyGoodsInfoByGoodsId(Goods goods);

    /**
     * 店铺修改自家的商品图片
     * @param goods
     * @param goodsImg
     * @return
     */
    ResponseInfoDTO<Goods> modifyGoodsImageByGoodsId(Goods goods, MultipartFile goodsImg);




    /**
     * 下架商品
     * @return
     */
    ResponseInfoDTO<Goods> deleteGoodsByGoodsId(Integer goodsId);



    /**
     * 按名称查询商店的商品
     * @param goods
     * @return
     */
    ResponseInfoDTO<List<Goods>> queryGoodsInfoByGoodsNameAndShopId(Goods goods);


    /**
     * 直接查询店铺的商品
     * @return List<Goods>
     */
    ResponseInfoDTO<List<Goods>> queryGoodsInfoByShopId(Goods goods);



    /**
     * 添加收藏
     * @return List<Goods>
     */
    ResponseInfoDTO<Goods> insertCollectionGoods(CollectGoods collectGoods);


    /**
     * 删除收藏
     * @return List<Goods>
     */
    ResponseInfoDTO<Goods> deleteCollectionGoods(CollectGoods collectGoods);

    /**
     * 查询个人收藏
     * @return List<Goods>
     */
    ResponseInfoDTO<List<Goods>> selectCollectionGoodsByUserId(String userId);


    /***********************************************************/

    /**
     * 根据商户id去获取goods
     * @param user_id
     * @return list<goods>
     */
    //ResponseInfoDTO<List<Goods>> queryByUserId(String user_id,int number,int start);

    /**
     * 根据商品名获取商品列表
     * @param goodsname
     * @return list<goods>
     */
    //ResponseInfoDTO<List<Goods>> queryByGoodsName(String goodsname,int number,int start);

    /**
     *
     * @param number
     * @param start
     * @return list<goods>
     */
    //ResponseInfoDTO<List<Goods>> queryIndexGoods(int number,int start);



    /**
     * 获取商品的数量
     * @return
     * @throws
     */
    //int getGoodsNumber(String searchgoods);



}
