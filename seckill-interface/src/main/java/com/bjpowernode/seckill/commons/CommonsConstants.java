package com.bjpowernode.seckill.commons;


/**
 * 项目的常量类这里定义一个项目中所需要的所有常量值
 */
public class CommonsConstants {

    public static final String OK = "0";

    public static final String ERROR = "1";

    /**库存的redis key**/
    public static final String SECKILL_STORE = "SECKILL_STORE:";

    /**已经抢购过*/
    public static final String HANDLE_USER = "HANDLE_USER:";

    /**限流的列表key*/
    public static final String LIMITING_LIST = "LIMITING_LIST";

    /**限流为商品库存的倍数，100倍**/
    public static final Integer LIMIT_GOODS_MULTIPLE = 100;

    /**订单的List**/
    public static final String ORDER_LIST = "ORDER_LIST";

    /***秒杀订单结果key*/
    public static final String ORDER_RESULT = "ORDER_RESULT:";
}
