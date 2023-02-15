package com.cashregister.controller.commands.checkoutshift;

import com.cashregister.controller.commands.CommandResult;
import com.cashregister.controller.commands.ICommand;
import com.cashregister.controller.constants.Actions;
import com.cashregister.controller.constants.Attributes;
import com.cashregister.controller.constants.Parameters;
import com.cashregister.controller.constants.Paths;
import com.cashregister.model.dao.CheckoutShiftDAO;
import com.cashregister.model.dao.UserDAO;
import com.cashregister.model.entity.CheckoutShift;
import com.cashregister.model.entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import static java.util.Objects.nonNull;

public class CheckoutOpen implements ICommand {
    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        long checkout_id = Long.parseLong(req.getParameter(Parameters.CHECKOUT_ID));
        CheckoutShiftDAO checkoutShiftDAO = new CheckoutShiftDAO();

        CommandResult result = new CommandResult("", true);
        CheckoutShift checkoutShift = checkoutShiftDAO.findById(checkout_id).orElse(null);
        if (nonNull(checkoutShift)) {
            session.setAttribute(Attributes.CHECKOUT_SHIFT, checkoutShift);
            result.setPath(Paths.CONTROLLER + Actions.ORDER_LIST);
        } else {
            result.setPath(Paths.CONTROLLER + Actions.CHECKOUT_LIST);
        }

        return result;
    }
}
