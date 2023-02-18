package com.cashregister.controller.commands.goods;

import com.cashregister.controller.commands.CommandResult;
import com.cashregister.controller.constants.Actions;
import com.cashregister.controller.constants.Parameters;
import com.cashregister.controller.constants.Paths;
import com.cashregister.model.dao.GoodsDAO;
import com.cashregister.model.entity.CheckoutShift;
import com.cashregister.model.entity.Goods;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class GoodsListTest {
    HttpServletResponse response;
    HttpServletRequest request;
    HttpSession session;
    GoodsList command;
    CommandResult result;
    Warehouse warehouse;
    CheckoutShift checkoutShift;
    List<Goods> goodsList;

    @BeforeEach
    public void setUp(){
        response = mock(HttpServletResponse.class);
        request = mock(HttpServletRequest.class);
        session = mock(HttpSession.class);

        warehouse = new Warehouse(1, "main");
        checkoutShift = new CheckoutShift(warehouse);
        command = new GoodsList();
        goodsList = new ArrayList<>();
    }

    @Test
    public void execute() throws ServletException, IOException {
        when(request.getSession()).thenReturn(session);
        when(request.getParameter(Parameters.GOODS_NAME)).thenReturn("test");
        when(request.getParameter(Parameters.GOODS_SCANCODE)).thenReturn("000");
        when(request.getParameter(Parameters.GOODS_PRICE)).thenReturn("10.0");
        when(request.getParameter(Parameters.GOODS_WEIGHT)).thenReturn("on");

        try (MockedConstruction<GoodsDAO> mockedGoodsDAO = mockConstructionWithAnswer(GoodsDAO.class, new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                String action = invocation.getMethod().getName();
                if (action.equals("getRecordsCount")) {
                    return Integer.valueOf(100);
                } else if (action.equals("findAllByPage")) {
                    return goodsList;
                } else if (action.equals("getAvailableAmount")) {
                    return Double.valueOf(10.0);
                }
                return null;
            }
        });){

            result = command.execute(request, response);
        }

        Assertions.assertEquals(Paths.GOODS_LIST, result.getPath());


    }

    @AfterEach
    public void tearDown(){
        response = null;
        request = null;
        session = null;
        command = null;
    }
}
