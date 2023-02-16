package com.cashregister.controller.services;

import com.cashregister.controller.filters.AuthenticationException;
import com.cashregister.model.dao.UserDAO;
import com.cashregister.model.entity.User;

import java.util.Optional;

public class UserAuthenticator {
    public static final User authenticateUser(String login, String password) throws AuthenticationException {
        UserDAO dao = new UserDAO();
        Optional<User> optUser = dao.findByLogin(login);
        if (optUser.isEmpty()) {
            throw new AuthenticationException();
        }
        User user = optUser.orElseGet(null);

        if (user == null || !user.getPasswd().equals(password)) {
            throw new AuthenticationException();
        }
        return user;
    }
}
