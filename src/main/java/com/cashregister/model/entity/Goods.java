package com.cashregister.model.entity;

public class Goods extends Entity{
    private String name;
    private String scancode;

    private boolean weight;
    private double price;

    public Goods(long id, String name) {
        super(id);
        this.name = name;
        this.weight = false;
    }

    public Goods(String name, String scancode) {
        this.name = name;
        this.scancode = scancode;
        this.weight = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScancode() {
        return scancode;
    }

    public void setScancode(String scancode) {
        this.scancode = scancode;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isWeight() {
        return weight;
    }

    public void setWeight(boolean weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(super.getId());
    }

    @Override
    public boolean equals(Object object) {
        Goods temp = (Goods) object;
        return super.getId() == temp.getId();
    }
}
