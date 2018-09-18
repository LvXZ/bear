package com.group3.controller;


import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.group3.dto.MessageDTO;
import com.group3.dto.ResponseInfoDTO;
import com.group3.entity.CollectGoods;
import com.group3.entity.Goods;
import com.group3.entity.UserInfo;
import com.group3.service.GoodsManageService;
import com.group3.service.ShopManageService;
import com.group3.util.CookieUtil;
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
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private GoodsManageService goodsManageService;


    /**
     * index搜索引擎
     * null
     * all.priority >= ? or = ?
     */
    @PostMapping(value="/search_priority")
    public ResponseInfoDTO<List<Goods>> searchPriorityGoods(@RequestBody String params, HttpServletRequest request, HttpServletResponse response) {

        Integer priority = ParamsJSONUtil.getInteger(params,"priority");
        Integer startIndex = ParamsJSONUtil.getInteger(params,"startIndex");
        Integer relativeFlag = ParamsJSONUtil.getInteger(params,"flag");

        if(relativeFlag == 1){
            return goodsManageService.searchGoodsByPriorityCeil(priority,startIndex);
        }else{

            ResponseInfoDTO<List<Goods>> responseInfoDTO = goodsManageService.searchGoodsByPriority(priority);
            responseInfoDTO.getData();

            return responseInfoDTO;
        }

    }

    /**
     * 类别搜索商品
     * category
     * List<Goods>
     */
    @PostMapping(value="/search_category")
    public ResponseInfoDTO<List<Goods>> searchCategoryGoods(@RequestBody String params, HttpServletRequest request, HttpServletResponse response) {

        String category = ParamsJSONUtil.getString(params,"category");
        return goodsManageService.searchGoodsByCategory(category);

    }

    /**
     * 名称搜索商品
     * goodsName
     * List<Goods>
     */
    @PostMapping(value="/search_name")
    public ResponseInfoDTO<List<Goods>> searchNameGoods(@RequestBody String params, HttpServletRequest request, HttpServletResponse response) {


        String goodsName = ParamsJSONUtil.getString(params,"goodsName");
        return goodsManageService.searchGoodsByGoodsName(goodsName);
    }


    /**
     * 商家商品上架，执行有图片
     * all
     * null
     */
    @PostMapping(value="/add")
    public ResponseInfoDTO<Goods> registerGoods(HttpServletRequest request, HttpServletResponse response) {

        String goodsStr = RequestJSONUtil.getString(request, "objJson");
        Goods goods = JSON.parseObject(goodsStr, Goods.class);
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("objImg");
        if(files.size() > 0){
            MultipartFile goodsImg = files.get(0);
            return goodsManageService.addNewGoods(goods,goodsImg);
        }else{
            return ResponseInfoDTO.fail(MessageDTO.REGISTER_FAIL_3);
        }

    }

    /**
     * 简单查询单个商品
     * goodsId
     */
    @PostMapping(value="/find_goods")
    public ResponseInfoDTO<Goods> findGoods(@RequestBody String params, HttpServletRequest request, HttpServletResponse response) {

        Integer goodsId = ParamsJSONUtil.getInteger(params,"goodsId");
        return goodsManageService.findGoodsInfoByGoodsId(goodsId);
    }

    /**
     * 简单店铺所有商品
     * shopId
     */
    @PostMapping(value="/find_shop_goods")
    public ResponseInfoDTO<List<Goods>> findShopGoods(@RequestBody String params, HttpServletRequest request, HttpServletResponse response) {

        Goods goods = JSON.parseObject(params, Goods.class);
        return goodsManageService.queryGoodsInfoByShopId(goods);
    }


    /**
     * 店铺修改自家的商品信息
     * goodsId
     */
    @PostMapping(value="/modify_goods_info")
    public ResponseInfoDTO<Goods> modifyGoodsInfo(HttpServletRequest request, HttpServletResponse response) {

        // TODO Cookies 处理 +　modify　goodsImg
        String userCookiesValue = CookieUtil.getCookieUserInfoJson(request);
        System.out.println(userCookiesValue);

        String goodsStr = RequestJSONUtil.getString(request, "objJson");
        Goods goods = JSON.parseObject(goodsStr, Goods.class);

        return goodsManageService.modifyGoodsInfoByGoodsId(goods);


    }

    /**
     * 店铺修改自家的商品图片
     * goodsId
     */
    @PostMapping(value="/modify_goods_image")
    public ResponseInfoDTO<Goods> modifyGoodsImage(HttpServletRequest request, HttpServletResponse response) {

        // TODO Cookies 处理 +　modify　goodsImg
        String userCookiesValue = CookieUtil.getCookieUserInfoJson(request);
        System.out.println(userCookiesValue);

        String goodsStr = RequestJSONUtil.getString(request, "objJson");
        Goods goods = JSON.parseObject(goodsStr, Goods.class);
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("objImg");
        if(files.size() > 0){
            MultipartFile goodsImg = files.get(0);
            return goodsManageService.modifyGoodsImageByGoodsId(goods,goodsImg);
        }else{
            return ResponseInfoDTO.fail(MessageDTO.REGISTER_FAIL_3);
        }

    }



    /**
     * 下架商品
     * goodsId
     */
    @PostMapping(value="/delete_goods")
    public ResponseInfoDTO<Goods> deleteGoods(@RequestBody String params, HttpServletRequest request, HttpServletResponse response) {

        String userCookiesValue = CookieUtil.getCookieUserInfoJson(request);
        System.out.println(userCookiesValue);

        Integer goodsId = ParamsJSONUtil.getInteger(params,"goodsId");
        return goodsManageService.deleteGoodsByGoodsId(goodsId);
    }




    /**
     * 收藏商品
     * goodsId
     */
    @PostMapping(value="/collect_goods")
    public ResponseInfoDTO<Goods> collectGoods(@RequestBody String params, HttpServletRequest request, HttpServletResponse response) {

        String userCookiesValue = CookieUtil.getCookieUserInfoJson(request);
        if(userCookiesValue != null) {
            System.out.println(userCookiesValue);
            UserInfo userinfo = JSON.parseObject(userCookiesValue, UserInfo.class);

            CollectGoods collectGoods = JSON.parseObject(params, CollectGoods.class);
            collectGoods.setUserId(userinfo.getTelephone());

            return goodsManageService.insertCollectionGoods(collectGoods);
        }else{
            return ResponseInfoDTO.fail("您还未登录,收藏无效");
        }
    }

    /**
     * 取消收藏商品
     * goodsId
     */
    @PostMapping(value="/un_collect_goods")
    public ResponseInfoDTO<Goods> unCollectGoods(@RequestBody String params, HttpServletRequest request, HttpServletResponse response) {

        String userCookiesValue = CookieUtil.getCookieUserInfoJson(request);
        if(userCookiesValue != null){
            System.out.println(userCookiesValue);
            UserInfo userinfo = JSON.parseObject(userCookiesValue, UserInfo.class);

            CollectGoods collectGoods = JSON.parseObject(params, CollectGoods.class);
            collectGoods.setUserId(userinfo.getTelephone());


            return goodsManageService.deleteCollectionGoods(collectGoods);
        }else{
            return ResponseInfoDTO.fail("您还未登录,取消无效");
        }

    }


    /**
     * 查询个人收藏商品
     * userId
     */
    @PostMapping(value="/user_collect_goods")
    public ResponseInfoDTO<List<Goods>> userCollectGoods(HttpServletRequest request, HttpServletResponse response) {

        String userCookiesValue = CookieUtil.getCookieUserInfoJson(request);
        if(userCookiesValue != null){
            System.out.println(userCookiesValue);
            UserInfo userinfo = JSON.parseObject(userCookiesValue, UserInfo.class);
            return goodsManageService.selectCollectionGoodsByUserId(userinfo.getTelephone());
        }else{
            return ResponseInfoDTO.fail("您还未登录");
        }

    }





}
