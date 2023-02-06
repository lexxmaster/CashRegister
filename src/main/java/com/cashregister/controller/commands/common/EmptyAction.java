package com.cashregister.controller.commands.common;

import com.cashregister.controller.commands.CommandResult;
import com.cashregister.controller.commands.ICommand;
import com.cashregister.controller.constants.Attributes;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class EmptyAction implements ICommand {
    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final HttpServletRequest httpRequest = (HttpServletRequest) req;
        final HttpSession session = httpRequest.getSession();
        String page = (String) session.getAttribute(Attributes.CURRENT_JSP);

        return new CommandResult(page);
    }
}
