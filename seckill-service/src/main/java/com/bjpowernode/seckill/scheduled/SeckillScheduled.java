package com.bjpowernode.seckill.scheduled;

import com.bjpowernode.seckill.commons.CommonsConstants;
import com.bjpowernode.seckill.mapper.GoodsMapper;
import com.bjpowernode.seckill.model.Goods;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@EnableScheduling
@Component
public class SeckillScheduled {
    @Resource
    private GoodsMapper goodsMapper;
    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Scheduled(cron = "0/5 * * * * *")
    public void initSeckill() {
        StringRedisSerializer redisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);
        redisTemplate.setValueSerializer(redisSerializer);
        //获取数据库中符合时间规则的秒杀产品列表
        List<Goods> list = goodsMapper.selectAll();
        for (Goods goods : list) {
            if (redisTemplate.opsForValue().get(CommonsConstants.SECKILL_STORE + goods.getRandomName()) == null) {
                redisTemplate.opsForValue().set(CommonsConstants.SECKILL_STORE+goods.getRandomName(), goods.getStore()+"");
            }
        }

    }
}
