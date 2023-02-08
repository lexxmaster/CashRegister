package com.cashregister.controller.commands.report;

import com.cashregister.controller.commands.CommandResult;
import com.cashregister.controller.commands.ICommand;
import com.cashregister.controller.constants.Attributes;
import com.cashregister.controller.constants.Paths;
import com.cashregister.model.dao.UserDAO;
import com.cashregister.model.entity.CheckoutShift;
import com.cashregister.model.entity.Report;
import com.cashregister.model.entity.User;
import com.cashregister.model.generator.ReportGenerator;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class XReportView implements ICommand {
    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        CheckoutShift checkoutShift = (CheckoutShift) session.getAttribute(Attributes.CHECKOUT_SHIFT);
        User user = new UserDAO().findById((Long) session.getAttribute(Attributes.USER_ID)).orElse(null);

        ReportGenerator reportGenerator = new ReportGenerator(checkoutShift, user);
        Report report = reportGenerator.execute();

        req.setAttribute(Attributes.REPORT, report);

        return new CommandResult(Paths.X_REPORT);
    }
}
