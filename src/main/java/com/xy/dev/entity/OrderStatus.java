package com.xy.dev.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 袁意 on 2017/1/5.
 */
public enum OrderStatus {
    /*创建订单*/
    CREATED,
    /*已支付*/
    PAYED,
    /*商家拒单*/
    MERCHANT_REJECTED,
    /*商家接受订单*/
    ACCEPTED,
    /*配送中*/
    DELIVERING,
    /*商品已送达*/
    ARRIVED,
    /*用户拒收*/
    USER_REJECTED,
    /*订单结束*/
    FINISH,
    UNKNOW;


    private static final Map<String, OrderStatus> mapping = new HashMap<String, OrderStatus>(){
        {
            put("新订单", OrderStatus.CREATED);
            put("已支付", OrderStatus.PAYED);
            put("商家已接受", OrderStatus.ACCEPTED);
            put("商家已拒收", OrderStatus.MERCHANT_REJECTED);
            put("配送中", OrderStatus.DELIVERING);
            put("已送达", OrderStatus.ARRIVED);
            put("用户拒收", OrderStatus.USER_REJECTED);
            put("完成", OrderStatus.FINISH);
        }
    };
    public static OrderStatus messageOf(String message){
        OrderStatus status = mapping.get(message);
        if(status == null) return UNKNOW;
        return status;
    }
}
