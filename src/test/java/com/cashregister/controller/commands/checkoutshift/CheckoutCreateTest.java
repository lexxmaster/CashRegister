package com.cashregister.controller.commands.checkoutshift;

import com.cashregister.controller.commands.CommandResult;
import com.cashregister.controller.constants.Actions;
import com.cashregister.controller.constants.Attributes;
import com.cashregister.controller.constants.Paths;
import com.cashregister.model.dao.CheckoutShiftDAO;
import com.cashregister.model.dao.UserDAO;
import com.cashregister.model.entity.CheckoutShift;
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

public class CheckoutCreateTest {
    HttpServletResponse response;
    HttpServletRequest request;
    HttpSession session;
    CheckoutCreate checkoutCreate;
    CheckoutClose checkoutClose;
    CheckoutShiftDAO checkoutShiftDAO;
    UserDAO userDAO;
    CommandResult result;

    @BeforeEach
    public void setUp(){
        response = mock(HttpServletResponse.class);
        request = mock(HttpServletRequest.class);
        session = mock(HttpSession.class);
        userDAO = mock(UserDAO.class);
        checkoutShiftDAO = mock(CheckoutShiftDAO.class);

        checkoutCreate = new CheckoutCreate();
        checkoutClose = new CheckoutClose();
    }

    @Test
    public void execute() throws ServletException, IOException {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(Attributes.WAREHOUSE)).thenReturn(new Warehouse(1, "main"));
        when(session.getAttribute(Attributes.LOGIN)).thenReturn("cashier");

        when(userDAO.findByLogin(any(String.class))).thenReturn(Optional.of(new User(1, "admin", "")));
        when(checkoutShiftDAO.create(any(CheckoutShift.class))).thenReturn(true);

        try (MockedConstruction<UserDAO> mockedUserDAO = mockConstructionWithAnswer(UserDAO.class, new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                return Optional.of(new User(1, "admin", ""));
            }
        });
             MockedConstruction<CheckoutShiftDAO> mockedCheckoutShiftDAO = mockConstructionWithAnswer(CheckoutShiftDAO.class, new Answer() {
                 @Override
                 public Object answer(InvocationOnMock invocation) throws Throwable {
                     return Boolean.TRUE;
                 }
             });){

            result = checkoutCreate.execute(request, response);
        }

        Assertions.assertEquals(Paths.CONTROLLER + Actions.ORDER_LIST, result.getPath());
        Assertions.assertEquals(true, result.isRedirect());

    }

    @AfterEach
    public void tearDown(){
        response = null;
        request = null;
        session = null;
        userDAO = null;
        checkoutShiftDAO = null;
        checkoutCreate = null;
        checkoutClose = null;
    }
}
