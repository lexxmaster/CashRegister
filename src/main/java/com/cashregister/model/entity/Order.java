package com.cashregister.model.entity;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Order extends Entity{
    private Timestamp date;
    private Warehouse warehouse;
    private Map<Goods, GoodsAmount> goodsList;
    private boolean closed;
    private double total;
    private User user;
    private CheckoutShift checkoutShift;

    public Order(long id, Warehouse warehouse, Timestamp timestamp) {
        super(id);
        this.warehouse = warehouse;
        this.closed = false;
        this.date = timestamp;
        this.total = 0;
        this.goodsList = new HashMap<>();
    }

    public Order(Warehouse warehouse, CheckoutShift checkoutShift){
        this.warehouse = warehouse;
        this.checkoutShift = checkoutShift;
        this.date = Timestamp.valueOf(LocalDateTime.now());
        this.closed = false;
        this.goodsList = new HashMap<>();
    }

    public Timestamp getDate() {
        return date;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public Map<Goods, GoodsAmount> getGoodsList() {
        return goodsList;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public double getTotal() {
        return total;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public CheckoutShift getCheckoutShift() {
        return checkoutShift;
    }

    public void setCheckoutShift(CheckoutShift checkoutShift) {
        this.checkoutShift = checkoutShift;
    }

    public void updateTotal(){
        total = goodsList.values().stream().mapToDouble(item -> item.getTotal()).sum();
    }

    public double calcRemnant(Goods goods, double amount, double availableAmount){
        if (goodsList.containsKey(goods)) {
            GoodsAmount goodsAmount = goodsList.get(goods);
            availableAmount += goodsAmount.getAmount();
            if (goods.isWeight() && amount > 0) {
                amount = Math.min(goodsAmount.getAmount() + amount, availableAmount);
            } else {
                amount = Math.min(goodsAmount.getAmount() + 1, availableAmount);
            }
            availableAmount -= amount;
            return availableAmount;
        } else {
            if (!goods.isWeight() && availableAmount >= 1.0) {
                availableAmount -= 1.0;
            }

            return availableAmount;
        }
    }
    public boolean addGoods(Goods goods, double amount, double availableAmount){
        if (goodsList.containsKey(goods)) {
            GoodsAmount goodsAmount = goodsList.get(goods);
            availableAmount += goodsAmount.getAmount();
            if (goods.isWeight() && amount > 0) {
                amount = Math.min(goodsAmount.getAmount() + amount, availableAmount);
                goodsAmount.setAmount(amount);
            } else {
                amount = Math.min(goodsAmount.getAmount() + 1, availableAmount);
                goodsAmount.setAmount(amount);
            }
            return false;
        } else {
            GoodsAmount goodsAmount = new GoodsAmount();
            goodsAmount.setPrice(goods.getPrice());
            if (goods.isWeight()) {

            } else if (availableAmount >= 1.0) {
                goodsAmount.setAmount(1.0);
            }
            goodsList.put(goods, goodsAmount);
            return true;
        }
    }
    @Override
    public String toString() {
        return "" + date + "/" + user.toString() + "/" + Double.toString(total);
    }
}

