package com.cashregister.controller.commands.orders;

import com.cashregister.controller.commands.CommandResult;
import com.cashregister.controller.commands.ICommand;
import com.cashregister.controller.constants.Actions;
import com.cashregister.controller.constants.Parameters;
import com.cashregister.controller.constants.Paths;
import com.cashregister.model.dao.OrderDAO;
import com.cashregister.model.entity.Order;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class OrderDelete implements ICommand {
    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final long order_id = Long.parseLong(req.getParameter(Parameters.ORDER_ID));
        OrderDAO orderDAO = new OrderDAO();
        Order order = orderDAO.findById(order_id).orElseGet(null);
        orderDAO.delete(order);

        return new CommandResult(Paths.CONTROLLER + Actions.ORDER_LIST, true);
    }
}
