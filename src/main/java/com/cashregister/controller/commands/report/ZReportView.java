package com.cashregister.controller.commands.report;

import com.cashregister.controller.commands.CommandResult;
import com.cashregister.controller.commands.ICommand;
import com.cashregister.controller.constants.Actions;
import com.cashregister.controller.constants.Attributes;
import com.cashregister.controller.constants.Parameters;
import com.cashregister.controller.constants.Paths;
import com.cashregister.model.dao.ReportDAO;
import com.cashregister.model.entity.Report;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class ZReportView implements ICommand {
    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        ReportDAO reportDAO = new ReportDAO();

        long report_id = Long.parseLong(req.getParameter(Parameters.REPORT_ID));

        Report report = reportDAO.findById(report_id).orElse(null);
        req.setAttribute(Attributes.REPORT, report);

        return new CommandResult(Paths.X_REPORT);
    }
}
