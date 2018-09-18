package com.group3.service.impl;

import com.group3.dao.GoodsDAO;
import com.group3.dao.ShopDAO;
import com.group3.dto.MessageDTO;
import com.group3.dto.ResponseInfoDTO;
import com.group3.entity.Goods;
import com.group3.entity.Shop;
import com.group3.service.ShopManageService;
import com.group3.util.FileUtil;
import com.group3.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.util.List;


/**
 * @ClassName:
 * @Description:
 * @Author: lvxz
 * @Date: 2018-08-29  08:31
 */

@Service
public class ShopManageServiceImpl implements ShopManageService {

    @Autowired
    private ShopDAO shopDAO;

    @Autowired
    private GoodsDAO goodsDAO;


    @Override
    @Transactional
    public ResponseInfoDTO<Shop> addNewShop(Shop shop, MultipartFile shopImg) {

        if(shopDAO.selectShopExistByOwnerId(shop.getOwnerId()) != null){
            return ResponseInfoDTO.fail("店铺已经存在");
        }

        int flag = 0;
        try{
            flag = shopDAO.insertShop(shop);
        }catch (Exception e){
            throw new RuntimeException("注册失败");
        }

        if(flag == 1) {

            if(shopImg != null){
                //存储图片
                try{
                    addShopImg(shop,shopImg);

                    flag = shopDAO.updateImageByShop(shop);
                    if (flag <= 0) {
                        throw new RuntimeException("创建图片地址失败");
                    }
                }catch (Exception e) {
                    throw new RuntimeException("--------------addShop"+e.getMessage());
                }
            }
            return ResponseInfoDTO.success(MessageDTO.REGISTER_SUCCESS);
        }else {
            return ResponseInfoDTO.fail("店铺注册失败，数据库拒绝添加");
        }
    }

    private void addShopImg(Shop shop, MultipartFile shopImg) {
        String dest = FileUtil.getShopImagePath(shop.getShopId());
        String shopImgAddr = ImageUtil.generateThumbnail(shopImg, dest);
        shop.setImage(shopImgAddr);
    }

    @Override
    public ResponseInfoDTO<Shop> queryShopInfoByShopId(Integer shopId) {
        Shop shop = shopDAO.selectShopInfoByShopId(shopId);
        if(shop == null){
            return ResponseInfoDTO.fail("获取店铺信息失败");
        }else{
            return ResponseInfoDTO.success(shop);
        }
    }

    @Override
    public ResponseInfoDTO<Shop> queryShopInfoByOwnerId(String ownerId) {
        Shop shop = shopDAO.selectShopExistByOwnerId(ownerId);
        if(shop == null){
            return ResponseInfoDTO.fail("未申请店铺");
        }else{
            return ResponseInfoDTO.success(shop);
        }
    }

    @Override
    public ResponseInfoDTO<Shop> modifyShopInfoByShopId(Shop shop) {


        int flag = shopDAO.updateShopInfoByShopId(shop);
        if(flag == 1){
            return ResponseInfoDTO.success(shop);
        }else{
            return ResponseInfoDTO.fail("更新店铺信息失败");
        }

    }

    @Override
    public ResponseInfoDTO<Shop> modifyShopImageByShopId(Shop shop, MultipartFile shopImg) {

        Shop getShop = shopDAO.selectShopInfoByShopId(shop.getShopId());
        File del_file = new File("D:/Download/o2o/images"+getShop.getImage());
        System.out.println(del_file);
        if(del_file.isFile()){
            del_file.delete();
        }

        addShopImg(shop,shopImg);

        int flag = shopDAO.updateShopInfoByShopId(shop);
        if(flag == 1){
            return ResponseInfoDTO.success(shop);
        }else{
            return ResponseInfoDTO.fail("更新店铺图片失败");
        }

    }




    @Override
    public ResponseInfoDTO<List<Shop>> queryShopListByPriorityOrCategory(Shop shop) {
        List<Shop> shopList = shopDAO.selectShopListByPriorityOrCategory(shop);
        return ResponseInfoDTO.success(shopList);
    }

}
