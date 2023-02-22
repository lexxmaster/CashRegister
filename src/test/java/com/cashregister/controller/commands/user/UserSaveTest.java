package com.cashregister.controller.commands.user;

import com.cashregister.controller.commands.CommandResult;
import com.cashregister.controller.commands.users.UserOpen;
import com.cashregister.controller.commands.users.UserSave;
import com.cashregister.controller.constants.Actions;
import com.cashregister.controller.constants.Parameters;
import com.cashregister.controller.constants.Paths;
import com.cashregister.model.dao.UserDAO;
import com.cashregister.model.entity.Role;
import com.cashregister.model.entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.io.IOException;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class UserSaveTest {
    HttpServletResponse response;
    HttpServletRequest request;
    HttpSession session;
    UserSave command;
    CommandResult result;
    User user;


    @BeforeEach
    public void setUp(){
        response = mock(HttpServletResponse.class);
        request = mock(HttpServletRequest.class);
        session = mock(HttpSession.class);

        command = new UserSave();
        user = new User(1l, "test", "test");
        user.setRole(Role.CASHIER);
    }

    @Test
    public void execute() throws ServletException, IOException {
        when(request.getSession()).thenReturn(session);
        when(request.getParameter(Parameters.LOGIN)).thenReturn("test");
        when(request.getParameter(Parameters.PASS)).thenReturn("test");
        when(request.getParameter(Parameters.USER_ID)).thenReturn("1");
        when(request.getParameter(Parameters.ROLE)).thenReturn("1");

        try (MockedConstruction<UserDAO> mockedUserDAO = mockConstructionWithAnswer(UserDAO.class, new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                return Boolean.TRUE;
            }
        })){

            result = command.execute(request, response);
        }

        Assertions.assertEquals(Paths.USER, result.getPath());

        when(request.getParameter(Parameters.LOGIN)).thenReturn("testUser");
        when(request.getParameter(Parameters.PASS)).thenReturn("test123");

        try (MockedConstruction<UserDAO> mockedUserDAO = mockConstructionWithAnswer(UserDAO.class, new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                return Boolean.TRUE;
            }
        })){

            result = command.execute(request, response);
        }

        Assertions.assertEquals(Paths.CONTROLLER + Actions.USER_LIST, result.getPath());
    }

    @AfterEach
    public void tearDown(){
        response = null;
        request = null;
        session = null;
        command = null;
        result = null;
        user = null;
    }
}
