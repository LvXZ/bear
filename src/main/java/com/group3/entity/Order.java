package com.group3.entity;


import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Order {
    private Integer orderId;

    private Integer deal;

    private Integer goodsId;

    private Goods goodsObject;

    private Integer purchaseNumber;

    private Integer shopId;

    private Shop shopObject;

    private String  userTelephone;

    private UserInfo UserObject;

    private Double totalPrice;

    public Goods getGoodsObject() {
        return goodsObject;
    }

    public void setGoodsObject(Goods goodsObject) {
        this.goodsObject = goodsObject;
    }

    public Shop getShopObject() {
        return shopObject;
    }

    public void setShopObject(Shop shopObject) {
        this.shopObject = shopObject;
    }

    public UserInfo getUserObject() {
        return UserObject;
    }

    public void setUserObject(UserInfo userObject) {
        UserObject = userObject;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getDeal() {
        return deal;
    }

    public void setDeal(Integer deal) {
        this.deal = deal;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public String getUserTelephone() {
        return userTelephone;
    }

    public void setUserTelephone(String userTelephone) {
        this.userTelephone = userTelephone;
    }

    public Integer getPurchaseNumber() {
        return purchaseNumber;
    }

    public void setPurchaseNumber(Integer purchaseNumber) {
        this.purchaseNumber = purchaseNumber;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

}