package com.cashregister.controller.commands.users;

import com.cashregister.controller.commands.CommandResult;
import com.cashregister.controller.commands.ICommand;
import com.cashregister.controller.constants.Attributes;
import com.cashregister.controller.constants.Parameters;
import com.cashregister.controller.constants.Paths;
import com.cashregister.model.dao.UserDAO;
import com.cashregister.model.entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

import static com.cashregister.controller.constants.Common.RECORDS_PER_PAGE;
import static java.util.Objects.nonNull;

public class UserList implements ICommand {
    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int page = 1;
        int recordsPerPage = RECORDS_PER_PAGE;
        if (nonNull(req.getParameter(Parameters.PAGE))) {
            page = Integer.parseInt(req.getParameter(Parameters.PAGE));
        }
        UserDAO userDao = new UserDAO();
        int numOfPages = getNumOfPages(userDao, recordsPerPage);
        List<User> goodsList = userDao.findAllByPage((page - 1) * recordsPerPage, recordsPerPage);

        req.setAttribute(Attributes.USER_LIST, goodsList);
        req.setAttribute(Attributes.CURRENT_PAGE, page);
        req.setAttribute(Attributes.NUMBER_OF_PAGES, numOfPages);

        return new CommandResult(Paths.USER_LIST);
    }

    private int getNumOfPages(UserDAO userDao, int recordsPerPage) {
        int recordsCount = userDao.getRecordsCount();
        return (int) Math.ceil(recordsCount * 1.0/ recordsPerPage);
    }
}
