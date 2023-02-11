package com.cashregister.controller.commands.users;

import com.cashregister.controller.commands.CommandResult;
import com.cashregister.controller.commands.ICommand;
import com.cashregister.controller.commands.common.EmptyAction;
import com.cashregister.controller.constants.Attributes;
import com.cashregister.controller.constants.Parameters;
import com.cashregister.controller.constants.Paths;
import com.cashregister.model.dao.OrderDAO;
import com.cashregister.model.dao.UserDAO;
import com.cashregister.model.entity.Order;
import com.cashregister.model.entity.User;
import com.cashregister.model.entity.UserDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import static java.util.Objects.nonNull;

public class UserOpen implements ICommand {
    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final long user_id = Long.parseLong(req.getParameter(Parameters.USER_ID));
        User user = (new UserDAO()).findById(user_id).orElseGet(null);

        CommandResult result = new CommandResult();
        if (nonNull(user)) {
            req.setAttribute(Attributes.USER, new UserDTO(user));

            result.setPath(Paths.USER);
        } else {
            ICommand command = new EmptyAction();
            command.execute(req, resp);
        }

        return result;
    }
}
