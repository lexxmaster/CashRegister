package com.cashregister.controller.commands.common;

import com.cashregister.controller.commands.CommandResult;
import com.cashregister.controller.commands.ICommand;
import com.cashregister.controller.constants.Actions;
import com.cashregister.controller.constants.Attributes;
import com.cashregister.controller.constants.Paths;
import com.cashregister.model.dao.CheckoutShiftDAO;
import com.cashregister.model.dao.UserDAO;
import com.cashregister.model.entity.CheckoutShift;
import com.cashregister.model.entity.Role;
import com.cashregister.model.entity.User;
import com.cashregister.model.entity.Warehouse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import static java.util.Objects.nonNull;

public class Home implements ICommand {
    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final HttpServletRequest httpRequest = (HttpServletRequest) req;
        final HttpSession session = httpRequest.getSession();
        Role role = (Role) session.getAttribute(Attributes.ROLE);
        String login = (String) session.getAttribute(Attributes.LOGIN);

        CommandResult result = new CommandResult("", true);
        switch (role) {
            case CASHIER -> {
                Warehouse warehouse = (Warehouse) session.getAttribute(Attributes.WAREHOUSE);
                CheckoutShiftDAO checkoutShiftDAO = new CheckoutShiftDAO();

                User user = new UserDAO().findByLogin(login).orElse(null);
                CheckoutShift checkoutShift = checkoutShiftDAO.findOpenedCheckoutByUser(warehouse, user).orElse(null);
                if (nonNull(checkoutShift)) {
                    session.setAttribute(Attributes.CHECKOUT_SHIFT, checkoutShift);
                }
                result.setPath(Paths.CONTROLLER + Actions.ORDER_LIST);
                break;
            }
            case SENIOR_CASHIER -> {
                result.setPath(Paths.CONTROLLER + Actions.ORDER_LIST);
                break;
            }
            case COMMODITY_EXPERT -> {
                result.setPath(Paths.CONTROLLER + Actions.GOODS_LIST);
                break;
            }
            case ADMIN -> {
                result.setPath(Paths.CONTROLLER + Actions.USER_LIST);
                break;
            }

        }
        return result;
    }
}
