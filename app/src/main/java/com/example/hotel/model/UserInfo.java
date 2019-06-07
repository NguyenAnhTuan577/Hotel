package com.example.hotel.model;

import java.util.ArrayList;

public class UserInfo {
    private String uid = "";
    private Integer balance = 0;
    private String userType = "";
    private ArrayList<String> favors = new ArrayList<>();
    private ArrayList<String> orders = new ArrayList<>();

    public UserInfo(String uid, Integer balance, String userType, ArrayList<String> favors, ArrayList<String> orders) {
        this.uid = uid;
        this.balance = balance;
        this.userType = userType;
        this.favors = favors;
        this.orders = orders;
    }

    public UserInfo(){

    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public ArrayList<String> getFavors() {
        return favors;
    }

    public void setFavors(ArrayList<String> favors) {
        this.favors = favors;
    }

    public ArrayList<String> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<String> orders) {
        this.orders = orders;
    }


}
