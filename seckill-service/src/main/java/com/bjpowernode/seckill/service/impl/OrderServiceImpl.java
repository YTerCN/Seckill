package com.bjpowernode.seckill.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.bjpowernode.seckill.mapper.OrdersMapper;
import com.bjpowernode.seckill.service.OrderService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Service(interfaceClass = OrderService.class)
@Component("orderService")
public class OrderServiceImpl implements OrderService {
    @Resource
    private OrdersMapper ordersMapper;
}
