package com.bjpowernode.seckill.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bjpowernode.seckill.service.OrderService;
import org.springframework.stereotype.Controller;



@Controller
public class OrdersController {
    @Reference
    private OrderService orderService;
}
