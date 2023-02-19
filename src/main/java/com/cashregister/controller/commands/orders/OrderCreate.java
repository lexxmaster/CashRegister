package com.cashregister.controller.commands.orders;

import com.cashregister.controller.commands.CommandResult;
import com.cashregister.controller.commands.ICommand;
import com.cashregister.controller.constants.Actions;
import com.cashregister.controller.constants.Attributes;
import com.cashregister.controller.constants.Parameters;
import com.cashregister.controller.constants.Paths;
import com.cashregister.model.dao.OrderDAO;
import com.cashregister.model.dao.UserDAO;
import com.cashregister.model.entity.CheckoutShift;
import com.cashregister.model.entity.Order;
import com.cashregister.model.entity.Warehouse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class OrderCreate implements ICommand {
    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (session.getAttribute(Attributes.CHECKOUT_SHIFT) == null) {
            return new CommandResult(Paths.CONTROLLER + Actions.ORDER_LIST, true);
        }
        String login = (String) session.getAttribute(Attributes.LOGIN);
        CheckoutShift checkoutShift = (CheckoutShift) session.getAttribute(Attributes.CHECKOUT_SHIFT);
        Warehouse warehouse = (Warehouse) session.getAttribute(Attributes.WAREHOUSE);

        Order order = new Order(warehouse, checkoutShift);
        order.setUser(new UserDAO().findByLogin(login).get());
        OrderDAO orderDAO = new OrderDAO();
        orderDAO.create(order);

        String path = Paths.CONTROLLER + Actions.ORDER_OPEN + "&" + Parameters.ORDER_ID + "=" + order.getId();
        return new CommandResult(path, true);
    }
}
