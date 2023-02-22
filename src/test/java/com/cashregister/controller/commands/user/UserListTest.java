package com.cashregister.controller.commands.user;

import com.cashregister.controller.commands.CommandResult;
import com.cashregister.controller.commands.users.UserList;
import com.cashregister.controller.constants.Parameters;
import com.cashregister.controller.constants.Paths;
import com.cashregister.model.dao.UserDAO;
import com.cashregister.model.entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class UserListTest {
    HttpServletResponse response;
    HttpServletRequest request;
    UserList command;
    List<User> userList;
    CommandResult result;

    @BeforeEach
    public void setUp(){
        response = mock(HttpServletResponse.class);
        request = mock(HttpServletRequest.class);
        userList = new ArrayList<>();
        command = new UserList();
    }

    @Test
    public void execute() throws ServletException, IOException {
        when(request.getParameter(Parameters.PAGE)).thenReturn("1");
        try (MockedConstruction<UserDAO> mockedUserDAO = mockConstructionWithAnswer(UserDAO.class,
                new Answer() {
                    @Override
                    public Object answer(InvocationOnMock invocation) throws Throwable {
                        String methodName = invocation.getMethod().getName();
                        if (methodName.equals("getRecordsCount")) {
                            return 1;
                        } else if (methodName.equals("findAllByPage")) {
                            return userList;
                        }
                        return null;

                    }
                })){
            result = command.execute(request, response);
        }

        Assertions.assertEquals(Paths.USER_LIST, result.getPath());


    }

    @AfterEach
    public void tearDown(){
        response = null;
        request = null;
        command = null;
        userList = null;
        result = null;
    }
}
