package com.xy.dev.dataProvider;

import com.xy.dev.entity.Order;
import com.xy.dev.entity.OrderStatus;
import com.xy.dev.entity.User;

import java.util.Date;
import java.util.Random;

/**
 * Created by 袁意 on 2017/1/6.
 */
public class OrderProvider {

    private Random random = new Random();
    private String[] names = new String[]{"peter", "jack", "john"};

    public boolean hasNext(){
        return random.nextBoolean();
    }

    public Order next(){
        Order order = new Order();
        order.setStatus(OrderStatus.CREATED);
        order.setGoodName("田鸡叉烧饭");
        order.setAmount(random.nextInt(10));
        order.setPrice(random.nextInt(30));
        order.setDate(new Date());

        User user = new User();
        user.setId(random.nextInt());
        user.setName(names[random.nextInt(2)]);
        user.setPhone(random.nextLong()+"");
        order.setUser(user);

        return order;
    }
}
