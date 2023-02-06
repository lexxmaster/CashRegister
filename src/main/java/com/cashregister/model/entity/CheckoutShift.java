package com.cashregister.model.entity;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class CheckoutShift extends Entity{
    private Timestamp date;
    private boolean closed;
    private Warehouse warehouse;
    private User user;

    public CheckoutShift(Timestamp date) {
        this.date = date;
        closed = false;
    }

    public CheckoutShift(Warehouse warehouse) {
        this.warehouse = warehouse;
        this.date = Timestamp.valueOf(LocalDateTime.now());
        closed = false;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    @Override
    public String toString() {
        return date.toString();
    }
}
