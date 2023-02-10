package com.cashregister.controller;

import com.cashregister.model.entity.Goods;

import java.util.regex.Pattern;

public class DataValidator {
    private static final String REGEX_LOGIN = "^([\\wa-zA-Z]{6,20})$";
    private static final String REGEX_PWD = "^([\\wа-яА-Я]{6,12})$";
    private static final String REGEX_SCANCODE = "^([\\d]{3,15})$";
    private static final int MIN_GOODS_NAME = 5;
    private static final double MIN_PRICE = 0.0;
    private static final double MAX_PRICE = 1000.0;

    public static boolean validateLogin(String login){
        return Pattern.matches(REGEX_LOGIN, login);
    }

    public static boolean validatePassword(String password){
        return Pattern.matches(REGEX_PWD, password);
    }

    public static boolean validateGoodsName(String name){
        return name.length() >= MIN_GOODS_NAME;
    }

    public static boolean validateScancode(String scancode){
        return Pattern.matches(REGEX_SCANCODE, scancode);
    }
    public static boolean validatePrice(double price){
        return price >= MIN_PRICE && price <= MAX_PRICE;
    }
    public static boolean validateAmount(double amount, Goods goods){
        boolean validated = true;
        if (!goods.isWeight() && amount % 1 != 0.0) {
            validated = false;
        }
        return validated;
    }

}
