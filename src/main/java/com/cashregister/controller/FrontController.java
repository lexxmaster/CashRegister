package com.cashregister.controller;

import com.cashregister.controller.commands.CommandFactory;
import com.cashregister.controller.commands.CommandResult;
import com.cashregister.controller.constants.Parameters;
import com.cashregister.controller.constants.Paths;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@WebServlet("/controller")
public class FrontController extends HttpServlet {
    protected static final Logger LOGGER = LogManager.getLogger(FrontController.class);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        final String action = req.getParameter(Parameters.ACTION);

        LOGGER.info(action);

        CommandFactory commandFactory = new CommandFactory(action);
        CommandResult commandResult = commandFactory.getCommand().execute(req, resp);

        String path;
        if (commandResult.getPath() != null) {
            path = commandResult.getPath();
            if (commandResult.isRedirect()) {
                resp.sendRedirect(path);
            } else {
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(path);
                dispatcher.forward(req, resp);
            }
        } else {
            resp.sendRedirect(Paths.ERROR_404);
        }
    }

}
