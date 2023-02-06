package com.cashregister.controller.commands.orders;

import com.cashregister.controller.commands.CommandResult;
import com.cashregister.controller.commands.ICommand;
import com.cashregister.controller.constants.Actions;
import com.cashregister.controller.constants.Attributes;
import com.cashregister.controller.constants.Paths;
import com.cashregister.model.dao.OrderDAO;
import com.cashregister.model.entity.Order;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class OrderClose implements ICommand {
    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Order order = (Order) session.getAttribute(Attributes.ORDER);
        order.setClosed(true);

        OrderDAO orderDAO = new OrderDAO();
        orderDAO.update(order);
        session.removeAttribute(Attributes.ORDER);

        return new CommandResult(Paths.CONTROLLER + Actions.ORDER_LIST, true);
    }
}
