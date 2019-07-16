package com.bjpowernode.seckill.service;

import com.bjpowernode.seckill.commons.CommonsReturnObject;
import com.bjpowernode.seckill.model.Goods;

import java.util.List;

public interface GoodService {
    List<Goods> queryGoodsAll();

    Goods queryGoodById(Integer id);

    CommonsReturnObject seckill(int uId, Integer goodsId, String goodsRandomName);
}
