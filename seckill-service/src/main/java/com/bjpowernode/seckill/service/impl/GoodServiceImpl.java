package com.bjpowernode.seckill.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.bjpowernode.seckill.commons.CommonsConstants;
import com.bjpowernode.seckill.commons.CommonsReturnObject;
import com.bjpowernode.seckill.mapper.GoodsMapper;
import com.bjpowernode.seckill.model.Goods;
import com.bjpowernode.seckill.service.GoodService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Service(interfaceClass = GoodService.class,timeout = 15000)
@Component("goodService")

public class GoodServiceImpl implements GoodService {
    @Resource
    private GoodsMapper goodsMapper;

    @Resource
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public List<Goods> queryGoodsAll() {
        List<Goods> list = goodsMapper.selectAll();
        return list;
    }

    @Override
    public Goods queryGoodById(Integer id) {
        return goodsMapper.selectByPrimaryKey(id);
    }

    @Override
    public CommonsReturnObject seckill(int uId, Integer goodsId, String goodsRandomName) {
        CommonsReturnObject returnObject = new CommonsReturnObject();
        StringRedisSerializer redisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);
        redisTemplate.setValueSerializer(redisSerializer);

        //验证库存是否充足
        String goodsStoreStr = redisTemplate.opsForValue().get(CommonsConstants.SECKILL_STORE + goodsRandomName);
        if (null == goodsStoreStr) {
            returnObject.setCode(CommonsConstants.ERROR);
            returnObject.setErrorMessage("当前商品不可秒杀");
            returnObject.setData(null);
            return returnObject;
        }
        Integer store = Integer.valueOf(goodsStoreStr);
        if (store <= 0) {
            returnObject.setCode(CommonsConstants.ERROR);
            returnObject.setErrorMessage("当前商品已被抢光");
            returnObject.setData(null);
            return returnObject;
        }
        //验证该用户是否秒杀过该商品
        String str = redisTemplate.opsForValue().get(CommonsConstants.HANDLE_USER + goodsRandomName + uId);
        if (null != str) {
            returnObject.setCode(CommonsConstants.ERROR);
            returnObject.setErrorMessage("对不起！您已经抢购过该商品了");
            returnObject.setData(null);
            return returnObject;
        }

        //限流
        String limitingListStr = redisTemplate.opsForValue().get(CommonsConstants.LIMITING_LIST);
        if (null != limitingListStr && Integer.valueOf(limitingListStr) > 1000) {
            returnObject.setCode(CommonsConstants.ERROR);
            returnObject.setErrorMessage("对不起！网络繁忙请稍后重试");
            returnObject.setData(null);
            return returnObject;
        }

        redisTemplate.opsForValue().increment(CommonsConstants.LIMITING_LIST);
        redisTemplate.watch(CommonsConstants.SECKILL_STORE+goodsRandomName);
        return returnObject;
    }
}
