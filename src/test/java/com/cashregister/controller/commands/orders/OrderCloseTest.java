package com.cashregister.controller.commands.orders;

import com.cashregister.controller.commands.CommandResult;
import com.cashregister.controller.commands.goods.GoodsCreate;
import com.cashregister.controller.constants.Actions;
import com.cashregister.controller.constants.Attributes;
import com.cashregister.controller.constants.Parameters;
import com.cashregister.controller.constants.Paths;
import com.cashregister.model.dao.GoodsDAO;
import com.cashregister.model.dao.OrderDAO;
import com.cashregister.model.entity.CheckoutShift;
import com.cashregister.model.entity.Order;
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

public class OrderCloseTest {
    HttpServletResponse response;
    HttpServletRequest request;
    HttpSession session;
    OrderClose command;
    CommandResult result;
    Warehouse warehouse;
    CheckoutShift checkoutShift;
    Order order;

    @BeforeEach
    public void setUp(){
        response = mock(HttpServletResponse.class);
        request = mock(HttpServletRequest.class);
        session = mock(HttpSession.class);

        warehouse = new Warehouse(1, "main");
        checkoutShift = new CheckoutShift(warehouse);
        order = new Order(warehouse, checkoutShift);
        command = new OrderClose();
    }

    @Test
    public void execute() throws ServletException, IOException {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(Attributes.ORDER)).thenReturn(order);

        try (MockedConstruction<OrderDAO> mockedOrderDAO = mockConstructionWithAnswer(OrderDAO.class, new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                return Boolean.TRUE;
            }
        });){

            result = command.execute(request, response);
        }

        Assertions.assertEquals(Paths.CONTROLLER + Actions.ORDER_LIST, result.getPath());
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
        order = null;
    }
}
