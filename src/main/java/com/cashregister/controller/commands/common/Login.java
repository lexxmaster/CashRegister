package com.cashregister.controller.commands.common;

import com.cashregister.controller.commands.CommandResult;
import com.cashregister.controller.commands.ICommand;
import com.cashregister.controller.constants.Actions;
import com.cashregister.controller.constants.Attributes;
import com.cashregister.controller.constants.Paths;
import com.cashregister.model.dao.CheckoutShiftDAO;
import com.cashregister.model.dao.UserDAO;
import com.cashregister.model.dao.WarehouseDAO;
import com.cashregister.model.entity.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import static java.util.Objects.nonNull;

public class Login implements ICommand {
    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WarehouseDAO warehouseDAO = new WarehouseDAO();
        Warehouse warehouse = warehouseDAO.findById(Long.valueOf(1)).orElse(null);  //plug
        HttpSession session = req.getSession();
        session.setAttribute(Attributes.WAREHOUSE, warehouse);

        String login = (String) session.getAttribute(Attributes.LOGIN);

        Role role = (Role) session.getAttribute(Attributes.ROLE);
        if (role == Role.CASHIER || role == Role.SENIOR_CASHIER) {
            checkOpenedCheckoutShift(warehouse, session, login);
        }

        return new CommandResult(Paths.CONTROLLER + Actions.HOME, true);
    }

    private void checkOpenedCheckoutShift(Warehouse warehouse, HttpSession session, String login) {
        UserDAO userDAO = new UserDAO();
        User user = userDAO.findByLogin(login).orElseThrow();

        CheckoutShiftDAO checkoutShiftDAO = new CheckoutShiftDAO();
        CheckoutShift checkoutShift = checkoutShiftDAO.findOpenedCheckoutByUser(warehouse, user).orElse(null);

        if (nonNull(checkoutShift)) {
            session.setAttribute(Attributes.CHECKOUT_SHIFT, checkoutShift);
        }
    }
}
