package com.cashregister.controller.commands.checkoutshift;

import com.cashregister.controller.commands.CommandResult;
import com.cashregister.controller.commands.ICommand;
import com.cashregister.controller.constants.Actions;
import com.cashregister.controller.constants.Attributes;
import com.cashregister.controller.constants.Paths;
import com.cashregister.model.dao.CheckoutShiftDAO;
import com.cashregister.model.entity.CheckoutShift;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class CheckoutClose implements ICommand {
    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        CheckoutShift checkoutShift = (CheckoutShift) session.getAttribute(Attributes.CHECKOUT_SHIFT);
        checkoutShift.setClosed(true);

        CheckoutShiftDAO checkoutShiftDAO = new CheckoutShiftDAO();
        checkoutShiftDAO.update(checkoutShift);

        session.removeAttribute(Attributes.CHECKOUT_SHIFT);

        return new CommandResult(Paths.CONTROLLER + Actions.ORDER_LIST, true);
    }
}
