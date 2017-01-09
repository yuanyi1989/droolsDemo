package com.xy.dev.coupon;

import com.xy.dev.entity.User;

/**
 * Created by 袁意 on 2017/1/5.
 */
public class Coupon {

    private int id;

    private User user;

    private int price;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
