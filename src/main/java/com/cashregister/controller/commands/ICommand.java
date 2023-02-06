package com.cashregister.controller.commands;

import com.cashregister.controller.constants.Attributes;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public interface ICommand {
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;

    default public void recordCurrentPage(HttpServletRequest req, String page) throws ServletException, IOException{
        final HttpServletRequest httpRequest = (HttpServletRequest) req;
        final HttpSession session = httpRequest.getSession();

        session.setAttribute(Attributes.CURRENT_JSP, page);
    };
}
