package com.bjpowernode.seckill.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bjpowernode.seckill.commons.CommonsConstants;
import com.bjpowernode.seckill.commons.CommonsReturnObject;
import com.bjpowernode.seckill.model.Goods;
import com.bjpowernode.seckill.service.GoodService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import java.util.List;

@Controller
public class GoodsController {
    @Reference(timeout = 15000)
    private GoodService goodService;

    @RequestMapping("/")
    public String index(Model model){
        List<Goods> list = goodService.queryGoodsAll();
        model.addAttribute("goodsList", list);
        return "index";
    }


    @RequestMapping("/goSeckill/{id}")
    public String goSeckill(Model model, @PathVariable Integer id){
        Goods goods = goodService.queryGoodById(id);
        model.addAttribute("goods", goods);
        return "goSeckill";
    }

    @RequestMapping("/getSystemTime")
    @ResponseBody
    public Object getSystemTime(){
        CommonsReturnObject returnObject = new CommonsReturnObject();
        returnObject.setData(System.currentTimeMillis());
        returnObject.setCode(CommonsConstants.OK);
        returnObject.setErrorMessage(null);
        return returnObject;
    }

    @RequestMapping("/getGoodsRandomNameById/{goodsId}")
    @ResponseBody
    public Object getGoodsRandomNameById(@PathVariable Integer goodsId){
        CommonsReturnObject returnObject = new CommonsReturnObject();
        Goods goods = goodService.queryGoodById(goodsId);
        Long newTime = System.currentTimeMillis();
        //判断当前商品是否还在秒杀期间
        if (newTime<goods.getStartTime().getTime()||newTime>goods.getEndTime().getTime()||null == goods.getRandomName()||"".equals(goods.getRandomName())) {
            returnObject.setCode(CommonsConstants.ERROR);
            returnObject.setErrorMessage("该商品不可秒杀");
            returnObject.setData(null);
            return returnObject;
        }
        returnObject.setCode(CommonsConstants.OK);
        returnObject.setErrorMessage(null);
        returnObject.setData(goods.getRandomName());
        return  returnObject;
    }

    @RequestMapping("/seckill/{goodsId}/{goodsRandomName}")
    @ResponseBody
    public Object seckill(@PathVariable Integer goodsId,@PathVariable String goodsRandomName){
        int uId = 1;
        CommonsReturnObject returnObject = goodService.seckill(uId,goodsId,goodsRandomName);
        return returnObject;
    }
}
