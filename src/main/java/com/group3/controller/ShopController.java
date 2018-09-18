package com.group3.controller;

import com.alibaba.fastjson.JSON;
import com.group3.dto.MessageDTO;
import com.group3.dto.ResponseInfoDTO;
import com.group3.entity.Shop;
import com.group3.service.ShopManageService;
import com.group3.util.ParamsJSONUtil;
import com.group3.util.RequestJSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @ClassName:
 * @Description:
 * @Author: lvxz
 * @Date: 2018-08-28  11:28
 */

@RestController
@RequestMapping("/shop")
public class ShopController {

    @Autowired
    private ShopManageService shopManageService;

    /**
     * 商家注册，执行有图片
     * all
     * null
     */
    @PostMapping(value="/register")
    public ResponseInfoDTO<Shop> registerShop(HttpServletRequest request, HttpServletResponse response) {

        String shopStr = RequestJSONUtil.getString(request, "objJson");
        Shop shop = JSON.parseObject(shopStr, Shop.class);
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("objImg");
        MultipartFile shopImg = null;
        if(files.size() > 0){
            shopImg = files.get(0);
            return shopManageService.addNewShop(shop,shopImg);
        }else{
            return ResponseInfoDTO.fail(MessageDTO.REGISTER_FAIL_3);
        }
    }

    /**
     * 查询单个店铺信息，通过店铺id
     * shopId
     * null
     */
    @PostMapping(value="/get_shop")
    public ResponseInfoDTO<Shop> getShopInfo(@RequestBody String params, HttpServletRequest request, HttpServletResponse response) {

        Integer shopId = ParamsJSONUtil.getInteger(params,"shopId");
        return shopManageService.queryShopInfoByShopId(shopId);
    }


    /**
     * 查询单个店铺信息,通过用户id
     * ownerId
     * null
     */
    @PostMapping(value="/get_shop_2")
    public ResponseInfoDTO<Shop> getShop2Info(@RequestBody String params, HttpServletRequest request, HttpServletResponse response) {

        String ownerId = ParamsJSONUtil.getString(params,"ownerId");
        return shopManageService.queryShopInfoByOwnerId(ownerId);
    }

    /**
     * 修改店铺信息
     * shopId
     * null
     */
    @PostMapping(value="/modify_shop_info")
    public ResponseInfoDTO<Shop> modifyShopInfo(HttpServletRequest request, HttpServletResponse response) {

        String shopStr = RequestJSONUtil.getString(request, "objJson");
        Shop shop = JSON.parseObject(shopStr, Shop.class);

        return shopManageService.modifyShopInfoByShopId(shop);

    }

    /**
     * 修改店铺图片
     * shopId
     * null
     */
    @PostMapping(value="/modify_shop_image")
    public ResponseInfoDTO<Shop> modifyShop(HttpServletRequest request, HttpServletResponse response) {

        String shopStr = RequestJSONUtil.getString(request, "objJson");
        Shop shop = JSON.parseObject(shopStr, Shop.class);
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("objImg");
        if(files.size() > 0){
            MultipartFile goodsImg = files.get(0);
            return shopManageService.modifyShopImageByShopId(shop,goodsImg);
        }else{
            return ResponseInfoDTO.fail(MessageDTO.REGISTER_FAIL_3);
        }


    }

    /**
     * 查询多个店铺信息--优先级
     * shopId
     * null
     */
    @PostMapping(value="/get_shop_priority")
    public ResponseInfoDTO<List<Shop>> getShopListPriority(@RequestBody String params, HttpServletRequest request, HttpServletResponse response) {

        Shop shop = JSON.parseObject(params, Shop.class);
        return shopManageService.queryShopListByPriorityOrCategory(shop);
    }

    /**
     * 查询多个店铺信息--类别
     * shopId
     * null
     */
    @PostMapping(value="/get_shop_category")
    public ResponseInfoDTO<List<Shop>> getShopListCategory(@RequestBody String params, HttpServletRequest request, HttpServletResponse response) {

        Shop shop = JSON.parseObject(params, Shop.class);
        return shopManageService.queryShopListByPriorityOrCategory(shop);
    }

}
