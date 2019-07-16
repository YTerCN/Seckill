package com.bjpowernode.seckill;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubboConfiguration
public class SeckillWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeckillWebApplication.class, args);
    }

}
