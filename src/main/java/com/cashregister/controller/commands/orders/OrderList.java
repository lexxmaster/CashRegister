package com.cashregister.controller.commands.orders;

import com.cashregister.controller.commands.CommandResult;
import com.cashregister.controller.commands.ICommand;
import com.cashregister.controller.constants.Attributes;
import com.cashregister.controller.constants.Parameters;
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

import static com.cashregister.controller.constants.Common.RECORDS_PER_PAGE;
import static java.util.Objects.nonNull;

public class OrderList implements ICommand {
    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int page = 1;
        int recordsPerPage = RECORDS_PER_PAGE;
        if (nonNull(req.getParameter(Parameters.PAGE))) {
            page = Integer.parseInt(req.getParameter(Parameters.PAGE));
        }
        HttpSession session = req.getSession();
        CheckoutShift checkoutShift = (CheckoutShift) session.getAttribute(Attributes.CHECKOUT_SHIFT);
        OrderDAO orderDAO = new OrderDAO();

        int numOfPages = getNumOfPages(orderDAO, recordsPerPage);
        List<Order> orders;
        if (checkoutShift == null) {
            orders = new ArrayList<>();
        } else {
            orders = orderDAO.findByCheckoutByPage(checkoutShift, (page - 1) * recordsPerPage, recordsPerPage);
        }

        req.setAttribute(Attributes.ORDER_LIST, orders);
        req.setAttribute(Attributes.CURRENT_PAGE, page);
        req.setAttribute(Attributes.NUMBER_OF_PAGES, numOfPages);

        return new CommandResult(Paths.ORDER_LIST);
    }
    private int getNumOfPages(OrderDAO orderDAO, int recordsPerPage) {
        int recordsCount = orderDAO.getRecordsCount();
        return (int) Math.ceil(recordsCount * 1.0/ recordsPerPage);
    }
}
