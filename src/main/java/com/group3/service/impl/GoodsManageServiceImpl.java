package com.group3.service.impl;

import com.group3.dao.CollectionDAO;
import com.group3.dao.GoodsDAO;
import com.group3.dto.MessageDTO;
import com.group3.dto.ResponseInfoDTO;
import com.group3.entity.CollectGoods;
import com.group3.entity.Goods;
import com.group3.redis.RedisService;
import com.group3.redis.key.GoodsKey;
import com.group3.service.GoodsManageService;
import com.group3.util.FileUtil;
import com.group3.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName:
 * @Description:
 * @author: lvxz
 * @create: 2018-08-30 10:20
 */

@Service
public class GoodsManageServiceImpl implements GoodsManageService {

    @Autowired
    private GoodsDAO goodsDAO;

    @Autowired
    private RedisService redisService;

    @Autowired
    private CollectionDAO collectionDAO;


    @Override
    public ResponseInfoDTO<List<Goods>> searchGoodsByPriorityCeil(Integer priority, Integer startIndex) {


        Map<String,Object> map = new LinkedHashMap<String, Object>();
        map.put("priority",priority);
        map.put("startIndex",startIndex*10);
        map.put("pageSize",10);

        //GoodsKey goodsKey = GoodsKey.getGoodsKey;
        //List<Goods> goodsList= redisService.ZsetGet(goodsKey,startIndex*10,Goods.class);

        List<Goods> goodsList = goodsDAO.selectGoodsByPriorityCeil(map);
        return ResponseInfoDTO.success(goodsList);
    }

    @Override
    public ResponseInfoDTO<List<Goods>> searchGoodsByPriority(Integer priority) {

        List<Goods> goodsList = goodsDAO.selectGoodsByPriority(priority);
        GoodsKey goodsKey = GoodsKey.getGoodsKey;

        redisService.ZsetAddList(goodsKey,goodsList);

        return ResponseInfoDTO.success(goodsList);
    }

    @Override
    public ResponseInfoDTO<List<Goods>> searchGoodsByCategory(String category) {
        List<Goods> goodsList = goodsDAO.selectGoodsByCategory(category);
        return ResponseInfoDTO.success(goodsList);
    }

    @Override
    public ResponseInfoDTO<List<Goods>> searchGoodsByGoodsName(String goodsName) {
        List<Goods> goodsList = goodsDAO.selectGoodsByGoodName(goodsName);
        return ResponseInfoDTO.success(goodsList);
    }


    @Override
    public ResponseInfoDTO<Goods> addNewGoods(Goods goods, MultipartFile goodsImg) {
        int flag = 0;
        try{
            flag = goodsDAO.insertGoods(goods);
        }catch (Exception e){
            System.out.println("---0---");
            System.out.println(e.getMessage());
            throw new RuntimeException("上架商品失败");
        }

        if(flag == 1) {
            System.out.println("---1---");

            if(goodsImg != null){

                System.out.println("---2---");
                //存储图片
                try{
                    System.out.println("---3---");
                    addGoodsImg(goods,goodsImg);

                    flag = goodsDAO.updateImageByGoods(goods);
                    if (flag <= 0) {
                        throw new RuntimeException("创建图片地址失败");
                    }
                }catch (Exception e) {
                    throw new RuntimeException("--------------addGoods"+e.getMessage());
                }
            }
            return ResponseInfoDTO.success(MessageDTO.REGISTER_SUCCESS);
        }else {
            return ResponseInfoDTO.fail(MessageDTO.REGISTER_FAIL_2);
        }
    }

    @Override
    public ResponseInfoDTO<Goods> findGoodsInfoByGoodsId(Integer goodId) {
        Goods goods = goodsDAO.selectGoodsInfoByGoodsId(goodId);
        return ResponseInfoDTO.success(goods);
    }

    @Override
    public ResponseInfoDTO<Goods> modifyGoodsInfoByGoodsId(Goods goods) {

        int flag = goodsDAO.updateGoodsInfoByGoodsId(goods);
        if(flag == 1){
            return ResponseInfoDTO.success(goods);
        }else{
            return ResponseInfoDTO.fail("更新商品信息失败");
        }
    }

    @Override
    public ResponseInfoDTO<Goods> modifyGoodsImageByGoodsId(Goods goods, MultipartFile goodsImg) {

        Goods getGoods = goodsDAO.selectGoodsInfoByGoodsId(goods.getGoodId());
        File del_file = new File("D:/Download/o2o/images"+getGoods.getImage());
        System.out.println(del_file);
        if(del_file.isFile()){
            del_file.delete();
        }

        addGoodsImg(goods,goodsImg);

        // TODO Goods goodsImg
        int flag = goodsDAO.updateGoodsInfoByGoodsId(goods);
        if(flag == 1){
            return ResponseInfoDTO.success(goods);
        }else{
            return ResponseInfoDTO.fail("更新商品图片失败");
        }
    }




    @Override
    public ResponseInfoDTO<Goods> deleteGoodsByGoodsId(Integer goodsId) {
        int flag = goodsDAO.deleteByGoodsId(goodsId);
        if(flag == 1){
            return ResponseInfoDTO.success(MessageDTO.SUCCESS);
        }else{
            return ResponseInfoDTO.fail("删除商品失败");
        }
    }

    @Override
    public ResponseInfoDTO<List<Goods>> queryGoodsInfoByGoodsNameAndShopId(Goods goods) {
        return ResponseInfoDTO.success(goodsDAO.selectGoodsByGoodNameAndShopId(goods));
    }

    @Override
    public ResponseInfoDTO<List<Goods>> queryGoodsInfoByShopId(Goods goods) {
        goods.setGoodName(null);
        return ResponseInfoDTO.success(goodsDAO.selectGoodsByGoodNameAndShopId(goods));
    }

    @Override
    public ResponseInfoDTO<Goods> insertCollectionGoods(CollectGoods collectGoods) {

        List<CollectGoods> getCollectionList = collectionDAO.selectCollectionExist(collectGoods);
        if(getCollectionList.size() > 0){
            return ResponseInfoDTO.fail("该商品已收藏");
        }

        int flag = collectionDAO.insertCollectGoods(collectGoods);
        if(flag == 1){
            return ResponseInfoDTO.success(MessageDTO.SUCCESS);
        }else{
            return ResponseInfoDTO.fail("添加收藏失败");
        }
    }

    @Override
    public ResponseInfoDTO<Goods> deleteCollectionGoods(CollectGoods collectGoods) {
        int flag = collectionDAO.deleteByCollectGoods(collectGoods);
        if(flag == 1){
            return ResponseInfoDTO.success(MessageDTO.SUCCESS);
        }else{
            return ResponseInfoDTO.fail("删除收藏失败");
        }
    }

    @Override
    public ResponseInfoDTO<List<Goods>> selectCollectionGoodsByUserId(String userId) {

        List<Goods> goodsList = goodsDAO.selectGoodsByCollectGoodsAndUserId(userId);
        return ResponseInfoDTO.success(goodsList);
    }




    private void addGoodsImg(Goods goods, MultipartFile goodsImg) {
        String dest = FileUtil.getGoodsInfoImagePath(goods.getGoodId());
        String goodsImgAddr = ImageUtil.generateThumbnail(goodsImg, dest);
        goods.setImage(goodsImgAddr);
    }
}
