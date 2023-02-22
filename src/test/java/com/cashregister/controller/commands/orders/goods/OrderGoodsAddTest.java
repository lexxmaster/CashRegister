package com.cashregister.controller.commands.orders.goods;

import com.cashregister.controller.commands.CommandResult;
import com.cashregister.controller.constants.Actions;
import com.cashregister.controller.constants.Attributes;
import com.cashregister.controller.constants.Parameters;
import com.cashregister.controller.constants.Paths;
import com.cashregister.model.dao.GoodsDAO;
import com.cashregister.model.dao.OrderDAO;
import com.cashregister.model.dao.UserDAO;
import com.cashregister.model.entity.*;
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
import static org.mockito.Mockito.mockConstructionWithAnswer;

public class OrderGoodsAddTest {
    HttpServletResponse response;
    HttpServletRequest request;
    HttpSession session;
    OrderGoodsAdd command;
    CommandResult result;
    CheckoutShift checkoutShift;
    Warehouse warehouse;
    Order order;
    User user;
    Goods goods;

    @BeforeEach
    public void setUp(){
        response = mock(HttpServletResponse.class);
        request = mock(HttpServletRequest.class);
        session = mock(HttpSession.class);

        warehouse = new Warehouse(1, "main");
        checkoutShift = new CheckoutShift(warehouse);
        order = new Order(warehouse, checkoutShift);
        command = new OrderGoodsAdd();
        goods = new Goods("test", "001");
        user = new User(1, "test", "");
    }

    @Test
    public void execute() throws ServletException, IOException {
        when(request.getSession()).thenReturn(session);
        when(request.getParameter(Parameters.SEARCH_GOODS)).thenReturn("201");
        when(session.getAttribute(Attributes.ORDER)).thenReturn(order);
        when(session.getAttribute(Attributes.WAREHOUSE)).thenReturn(warehouse);

        try (MockedConstruction<OrderDAO> mockedOrderDAO = mockConstructionWithAnswer(OrderDAO.class, new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                return Boolean.valueOf(true);
            }
        });
             MockedConstruction<GoodsDAO> mockedGoodsDAO = mockConstructionWithAnswer(GoodsDAO.class, new Answer() {
                 @Override
                 public Object answer(InvocationOnMock invocation) throws Throwable {
                     String action = invocation.getMethod().getName();
                     if (action.equals("findByScancode")) {
                         return Optional.of(goods);
                     } else if (action.equals("findByName")) {
                         return Optional.of(goods);
                     } else if (action.equals("getAvailableAmount")) {
                         return Double.valueOf(100.0);
                     } else if (action.equals("setAvailableAmount")) {
                         return Boolean.valueOf(true);
                     }
                     return null;
                 }
             })){

            result = command.execute(request, response);
        }
        String path = Paths.CONTROLLER + Actions.ORDER_OPEN + "&" + Parameters.ORDER_ID + "=" + order.getId();
        Assertions.assertEquals(path, result.getPath());

        when(request.getParameter(Parameters.SEARCH_GOODS)).thenReturn("test");

        try (MockedConstruction<OrderDAO> mockedOrderDAO = mockConstructionWithAnswer(OrderDAO.class, new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                return Boolean.valueOf(true);
            }
        });
             MockedConstruction<GoodsDAO> mockedGoodsDAO = mockConstructionWithAnswer(GoodsDAO.class, new Answer() {
                 @Override
                 public Object answer(InvocationOnMock invocation) throws Throwable {
                     String action = invocation.getMethod().getName();
                     if (action.equals("findByScancode")) {
                         return Optional.of(goods);
                     } else if (action.equals("findByName")) {
                         return Optional.of(goods);
                     } else if (action.equals("getAvailableAmount")) {
                         return 100.0;
                     } else if (action.equals("setAvailableAmount")) {
                         return Boolean.TRUE;
                     }
                     return null;
                 }
             })){

            result = command.execute(request, response);
        }
        path = Paths.CONTROLLER + Actions.ORDER_OPEN + "&" + Parameters.ORDER_ID + "=" + order.getId();
        Assertions.assertEquals(path, result.getPath());
    }

    @AfterEach
    public void tearDown(){
        response = null;
        request = null;
        session = null;
        command = null;
        result = null;
        checkoutShift = null;
        warehouse = null;
        order = null;
        user = null;
        goods = null;
    }
}
