package com.xy.dev.entity;

import lombok.Data;

import java.util.Random;

/**
 * Created by 袁意 on 2017/1/5.
 */
@Data
public class User {

    private int id;

    private String name;

    private int distance;

    private String phone;

    public User(){
        this.setDistance(new Random().nextInt(100));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 和商家的距离
     */
    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
