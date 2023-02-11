package com.cashregister.controller.commands.users;

import com.cashregister.controller.commands.CommandResult;
import com.cashregister.controller.commands.ICommand;
import com.cashregister.controller.constants.Actions;
import com.cashregister.controller.constants.Parameters;
import com.cashregister.controller.constants.Paths;
import com.cashregister.model.dao.GoodsDAO;
import com.cashregister.model.dao.UserDAO;
import com.cashregister.model.entity.Goods;
import com.cashregister.model.entity.Role;
import com.cashregister.model.entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class UserSave implements ICommand {
    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter(Parameters.LOGIN);
        String password = req.getParameter(Parameters.PASS);
        long id;
        try {
            id = Long.parseLong(req.getParameter(Parameters.USER_ID));
        } catch (Exception e){
            id = 0;
        }

        int role_id = Integer.parseInt(req.getParameter(Parameters.ROLE)) - 1;
        Role role = Role.values()[role_id];

        User user = new User(id, login, password);
        user.setRole(role);
        UserDAO userDAO = new UserDAO();

        if (id == 0) {
            userDAO.create(user);
        } else {
            userDAO.create(user);
        }

        return new CommandResult(Paths.CONTROLLER + Actions.USER_LIST, true);
    }
}
