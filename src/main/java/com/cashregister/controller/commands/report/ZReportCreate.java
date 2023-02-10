package com.cashregister.controller.commands.report;

import com.cashregister.controller.commands.CommandResult;
import com.cashregister.controller.commands.ICommand;
import com.cashregister.controller.constants.Actions;
import com.cashregister.controller.constants.Attributes;
import com.cashregister.controller.constants.Parameters;
import com.cashregister.controller.constants.Paths;
import com.cashregister.model.dao.CheckoutShiftDAO;
import com.cashregister.model.dao.ReportDAO;
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

public class ZReportCreate implements ICommand {
    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        CheckoutShift checkoutShift = (CheckoutShift) session.getAttribute(Attributes.CHECKOUT_SHIFT);
        User user = new UserDAO().findById((Long) session.getAttribute(Attributes.USER_ID)).orElse(null);

        ReportGenerator reportGenerator = new ReportGenerator(checkoutShift, user);
        Report report = reportGenerator.execute();

        checkoutShift.setClosed(true);

        CheckoutShiftDAO checkoutShiftDAO = new CheckoutShiftDAO();
        checkoutShiftDAO.update(checkoutShift);

        session.removeAttribute(Attributes.CHECKOUT_SHIFT);

        ReportDAO reportDAO = new ReportDAO();
        reportDAO.create(report);

        req.setAttribute(Attributes.REPORT, report);
        String path = Paths.CONTROLLER + Actions.Z_REPORT_VIEW + "&" + Parameters.REPORT_ID + "=" + report.getId();

        return new CommandResult(path, true);
    }
}
