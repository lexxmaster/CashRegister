package com.cashregister.controller.commands.checkoutshift;

import com.cashregister.controller.commands.CommandResult;
import com.cashregister.controller.commands.ICommand;
import com.cashregister.controller.constants.Attributes;
import com.cashregister.controller.constants.Parameters;
import com.cashregister.controller.constants.Paths;
import com.cashregister.model.dao.CheckoutShiftDAO;
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

import static com.cashregister.controller.constants.Common.RECORDS_PER_PAGE;
import static java.util.Objects.nonNull;

public class CheckoutList implements ICommand {
    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int page = 1;
        int recordsPerPage = RECORDS_PER_PAGE;
        if (nonNull(req.getParameter(Parameters.PAGE))) {
            page = Integer.parseInt(req.getParameter(Parameters.PAGE));
        }
        HttpSession session = req.getSession();
        CheckoutShiftDAO checkoutShiftDAO = new CheckoutShiftDAO();

        int numOfPages = checkoutShiftDAO.getNumOfPages(recordsPerPage);
        List<CheckoutShift> checkoutShifts;
        checkoutShifts = checkoutShiftDAO.findAllByPage((page - 1) * recordsPerPage, recordsPerPage);

        req.setAttribute(Attributes.CHECKOUT_SHIFT_LIST, checkoutShifts);
        req.setAttribute(Attributes.CURRENT_PAGE, page);
        req.setAttribute(Attributes.NUMBER_OF_PAGES, numOfPages);

        return new CommandResult(Paths.CHECKOUT_LIST);
    }
}
