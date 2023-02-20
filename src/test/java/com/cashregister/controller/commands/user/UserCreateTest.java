package com.cashregister.controller.commands.user;

import com.cashregister.controller.commands.CommandResult;
import com.cashregister.controller.commands.users.UserCreate;
import com.cashregister.controller.constants.Paths;
import com.cashregister.model.dao.CheckoutShiftDAO;
import com.cashregister.model.dao.UserDAO;
import com.cashregister.model.entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.io.IOException;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockConstructionWithAnswer;

public class UserCreateTest {
    HttpServletResponse response;
    HttpServletRequest request;
    UserCreate command;
    CommandResult result;

    @BeforeEach
    public void setUp(){
        response = mock(HttpServletResponse.class);
        request = mock(HttpServletRequest.class);

        command = new UserCreate();
    }

    @Test
    public void execute() throws ServletException, IOException {
        result = command.execute(request, response);
        Assertions.assertEquals(Paths.USER, result.getPath());
    }
}
