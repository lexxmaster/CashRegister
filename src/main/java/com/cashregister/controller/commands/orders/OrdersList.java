package com.cashregister.controller.commands.orders;

import com.cashregister.controller.commands.CommandResult;
import com.cashregister.controller.commands.ICommand;
import com.cashregister.controller.constants.Attributes;
import com.cashregister.controller.constants.Paths;
import com.cashregister.model.dao.OrderDAO;
import com.cashregister.model.entity.CheckoutShift;
import com.cashregister.model.entity.Order;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OrdersList implements ICommand {
    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        recordCurrentPage(req, Paths.ORDER_LIST);
        HttpSession session = req.getSession();
        CheckoutShift checkoutShift = (CheckoutShift) session.getAttribute(Attributes.CHECKOUT_SHIFT);
        List<Order> orders;
        if (checkoutShift == null) {
            //throw new ServletException();
            orders = new ArrayList<>();
        } else {
            orders = (new OrderDAO()).findByCheckout(checkoutShift);
        }

        req.setAttribute("orders", orders);

        return new CommandResult(Paths.ORDER_LIST);
    }
}
