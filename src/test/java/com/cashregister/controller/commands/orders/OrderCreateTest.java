package com.cashregister.controller.commands.orders;

import com.cashregister.controller.commands.CommandResult;
import com.cashregister.controller.constants.Actions;
import com.cashregister.controller.constants.Attributes;
import com.cashregister.controller.constants.Paths;
import com.cashregister.model.dao.GoodsDAO;
import com.cashregister.model.dao.OrderDAO;
import com.cashregister.model.dao.UserDAO;
import com.cashregister.model.entity.CheckoutShift;
import com.cashregister.model.entity.Order;
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

import static org.mockito.Mockito.*;

public class OrderCreateTest {
    HttpServletResponse response;
    HttpServletRequest request;
    HttpSession session;
    OrderCreate command;
    CommandResult result;
    Warehouse warehouse;
    CheckoutShift checkoutShift;
    User user;

    @BeforeEach
    public void setUp(){
        response = mock(HttpServletResponse.class);
        request = mock(HttpServletRequest.class);
        session = mock(HttpSession.class);

        warehouse = new Warehouse(1, "main");
        checkoutShift = new CheckoutShift(warehouse);
        command = new OrderCreate();
        user = new User(1, "test", "");
    }

    @Test
    public void execute() throws ServletException, IOException {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(Attributes.LOGIN)).thenReturn("test");
        when(session.getAttribute(Attributes.CHECKOUT_SHIFT)).thenReturn(checkoutShift);
        when(session.getAttribute(Attributes.WAREHOUSE)).thenReturn(warehouse);

        try (MockedConstruction<OrderDAO> mockedOrderDAO = mockConstructionWithAnswer(OrderDAO.class, new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                return Boolean.valueOf(true);
            }
        });
             MockedConstruction<UserDAO> mockedUserDAO = mockConstructionWithAnswer(UserDAO.class, new Answer() {
                 @Override
                 public Object answer(InvocationOnMock invocation) throws Throwable {
                     return Optional.of(user);
                 }
             })){

            result = command.execute(request, response);
        }

        Assertions.assertEquals("/CashRegister/controller?action=order_open&order_id=0", result.getPath());
    }

    @AfterEach
    public void tearDown(){
        response = null;
        request = null;
        session = null;
        command = null;
        result = null;
        warehouse = null;
        checkoutShift = null;
    }
}
