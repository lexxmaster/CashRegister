package com.cashregister.controller.commands.checkoutshift;

import com.cashregister.controller.commands.CommandResult;
import com.cashregister.controller.commands.ICommand;
import com.cashregister.controller.constants.Actions;
import com.cashregister.controller.constants.Attributes;
import com.cashregister.controller.constants.Paths;
import com.cashregister.model.dao.CheckoutShiftDAO;
import com.cashregister.model.dao.UserDAO;
import com.cashregister.model.entity.CheckoutShift;
import com.cashregister.model.entity.User;
import com.cashregister.model.entity.Warehouse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class CheckoutCreate implements ICommand {
    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (session.getAttribute(Attributes.CHECKOUT_SHIFT) != null) {
            return new CommandResult(Paths.CONTROLLER + Actions.ORDER_LIST, true);
        }
        Warehouse warehouse = (Warehouse) session.getAttribute(Attributes.WAREHOUSE);

        String login = (String) session.getAttribute(Attributes.LOGIN);
        UserDAO userDAO = new UserDAO();
        User user = userDAO.findByLogin(login).orElse(null);
        if (user == null){
            throw new ServletException();
        }
        CheckoutShift checkoutShift = new CheckoutShift(warehouse);
        checkoutShift.setUser(user);

        CheckoutShiftDAO checkoutShiftDAO = new CheckoutShiftDAO();
        checkoutShiftDAO.create(checkoutShift);

        session.setAttribute(Attributes.CHECKOUT_SHIFT, checkoutShift);

        return new CommandResult(Paths.CONTROLLER + Actions.ORDER_LIST, true);
    }
}
