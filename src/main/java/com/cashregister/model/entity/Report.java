package com.cashregister.model.entity;

public class Report extends Entity{
    CheckoutShift checkoutShift;
    int checkAmount;
    double sum;
    User user;

    public Report() {
    }

    public Report(CheckoutShift checkoutShift, User user) {
        this.checkoutShift = checkoutShift;
        this.user = user;
    }

    public CheckoutShift getCheckoutShift() {
        return checkoutShift;
    }

    public void setCheckoutShift(CheckoutShift checkoutShift) {
        this.checkoutShift = checkoutShift;
    }

    public int getCheckAmount() {
        return checkAmount;
    }

    public void setCheckAmount(int checkAmount) {
        this.checkAmount = checkAmount;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return checkoutShift.toString() + "/" + user.toString() ;
    }
}
