package com.cashregister.model.generator;

import com.cashregister.model.dao.OrderDAO;
import com.cashregister.model.entity.CheckoutShift;
import com.cashregister.model.entity.Order;
import com.cashregister.model.entity.Report;
import com.cashregister.model.entity.User;

import java.util.ArrayList;
import java.util.List;

public class ReportGenerator {
    private CheckoutShift checkoutShift;
    private User user;

    public ReportGenerator(CheckoutShift checkoutShift, User user) {
        this.checkoutShift = checkoutShift;
        this.user = user;
    }

    public Report execute(){
        Report report = new Report(checkoutShift, user);

        OrderDAO orderDAO = new OrderDAO();
        ArrayList<Order> orders = (ArrayList<Order>) orderDAO.findByCheckout(checkoutShift);

        double sum = orders.stream().mapToDouble(Order::getTotal).sum();
        int checkAmount = orders.size();

        report.setSum(sum);
        report.setCheckAmount(checkAmount);

        return report;
    }
}
