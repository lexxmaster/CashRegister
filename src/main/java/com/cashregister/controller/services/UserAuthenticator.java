package com.cashregister.controller.services;

import com.cashregister.controller.filters.AuthenticationException;
import com.cashregister.model.dao.UserDAO;
import com.cashregister.model.entity.User;

public class UserAuthenticator {
    public static final User authenticateUser(String login, String password) throws AuthenticationException {
        UserDAO dao = new UserDAO();
        User user = dao.findByLogin(login).orElseGet(null);

        if (user == null || !user.getPasswd().equals(password)) {   // add hash function?
            throw new AuthenticationException();
        }
        return user;
    }
}
