package com.cashregister.model.entity;

public class GoodsAmount {
    private double amount;
    private double price;
    private double total;

    public GoodsAmount() {
        this.amount = 0.0;
        this.price = 0.0;
        this.total = 0.0;
    }

    public GoodsAmount(double amount, double price) {
        this.amount = amount;
        this.price = price;
        calcTotal();
    }

    public GoodsAmount(double amount, double price, double total) {
        this.amount = amount;
        this.price = price;
        this.total = total;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
        calcTotal();
    }

    private void calcTotal() {
        this.total = this.price * this.amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
        calcTotal();
    }

    public double getTotal() {
        return total;
    }

}
