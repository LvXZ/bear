package com.group3.dao;

import com.group3.entity.CollectGoods;

import java.util.List;

public interface CollectionDAO {

    List<CollectGoods> selectCollectionByUserId(String userId);

    List<CollectGoods> selectCollectionExist(CollectGoods record);

    int deleteByCollectGoods(CollectGoods record);

    int insertCollectGoods(CollectGoods record);
}