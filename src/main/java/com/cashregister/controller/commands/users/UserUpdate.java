package com.cashregister.controller.commands.users;

import com.cashregister.controller.commands.CommandResult;
import com.cashregister.controller.commands.ICommand;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class UserUpdate implements ICommand {
    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        return null;
    }
}
