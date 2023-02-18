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
        return new CommandResult(req.getHeader("referer"), true);
    }
}
