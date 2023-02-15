package com.cashregister.controller.commands.common;

import com.cashregister.controller.commands.CommandResult;
import com.cashregister.controller.constants.Actions;
import com.cashregister.controller.constants.Attributes;
import com.cashregister.controller.constants.Paths;
import com.cashregister.model.dao.CheckoutShiftDAO;
import com.cashregister.model.dao.UserDAO;
import com.cashregister.model.entity.CheckoutShift;
import com.cashregister.model.entity.Role;
import com.cashregister.model.entity.User;
import com.cashregister.model.entity.Warehouse;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mockConstructionWithAnswer;

public class HomeTest {
    HttpServletResponse response;
    HttpServletRequest request;
    HttpSession session;
    User user;
    Warehouse warehouse;
    Home command;
    CheckoutShift checkoutShift;
    UserDAO userDAO;
    CheckoutShiftDAO checkoutShiftDAO;
    CommandResult result;

    @BeforeEach
    public void setUp(){
        response = mock(HttpServletResponse.class);
        request = mock(HttpServletRequest.class);
        session = mock(HttpSession.class);
        command = new Home();
        userDAO = mock(UserDAO.class);
        user = new User(1,"test","");
        user.setRole(Role.CASHIER);
        warehouse = new Warehouse(1, "test");
        checkoutShift = new CheckoutShift(warehouse);
    }

    @Test
    public void execute() throws ServletException, IOException {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(Attributes.ROLE)).thenReturn(Role.CASHIER);
        when(session.getAttribute(Attributes.WAREHOUSE)).thenReturn(warehouse);
        when(session.getAttribute(Attributes.LOGIN)).thenReturn("test");

        try (MockedConstruction<UserDAO> mockedUserDAO = mockConstructionWithAnswer(UserDAO.class, new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                return Optional.of(user);
            }
        });
             MockedConstruction<CheckoutShiftDAO> mockedCheckoutShiftDAO = mockConstructionWithAnswer(CheckoutShiftDAO.class, new Answer() {
                 @Override
                 public Object answer(InvocationOnMock invocation) throws Throwable {
                     return Optional.of(checkoutShift);
                 }
             });){

            result = command.execute(request, response);
        }

        Assertions.assertEquals(result.getPath(), Paths.CONTROLLER + Actions.ORDER_LIST);


    }

    @AfterEach
    public void tearDown(){
        response = null;
        request = null;
        session = null;
        command = null;
        userDAO = null;
        warehouse = null;
        checkoutShift = null;
        checkoutShiftDAO = null;
        result = null;
    }
}
