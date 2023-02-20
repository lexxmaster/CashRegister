package com.cashregister.controller.commands.orders;

import com.cashregister.controller.commands.CommandResult;
import com.cashregister.controller.commands.ICommand;
import com.cashregister.controller.commands.common.EmptyAction;
import com.cashregister.controller.constants.Attributes;
import com.cashregister.controller.constants.Parameters;
import com.cashregister.controller.constants.Paths;
import com.cashregister.model.dao.OrderDAO;
import com.cashregister.model.entity.Order;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import static java.util.Objects.nonNull;

public class OrderOpen implements ICommand {
    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final long order_id = Long.parseLong(req.getParameter(Parameters.ORDER_ID));
        Order order = (new OrderDAO()).findById(order_id).orElseGet(null);
        HttpSession session = req.getSession();

        CommandResult result = new CommandResult();
        if (nonNull(order)) {
            req.setAttribute(Attributes.ORDER, order);
            session.setAttribute(Attributes.ORDER, order);

            result.setPath(Paths.ORDER);
        } else {
            ICommand command = new EmptyAction();
            command.execute(req, resp);
        }

        return result;
    }
}
