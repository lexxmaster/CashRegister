package com.cashregister.controller.commands.common;

import com.cashregister.controller.commands.CommandResult;
import com.cashregister.controller.commands.ICommand;
import com.cashregister.controller.constants.Attributes;
import com.cashregister.controller.constants.Paths;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class Logout implements ICommand {
    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final HttpSession session = req.getSession();

        session.removeAttribute(Attributes.LOGIN);
        session.removeAttribute(Attributes.USER_ID);
        session.removeAttribute(Attributes.ROLE);
        session.removeAttribute(Attributes.WAREHOUSE);
        session.removeAttribute(Attributes.CHECKOUT_SHIFT);

        return new CommandResult(Paths.LOGIN);
    }
}
