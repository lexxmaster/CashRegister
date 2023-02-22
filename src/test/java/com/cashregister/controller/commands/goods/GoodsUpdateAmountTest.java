package com.cashregister.controller.commands.goods;

import com.cashregister.controller.commands.CommandResult;
import com.cashregister.controller.constants.Attributes;
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

import static org.mockito.Mockito.*;

public class GoodsUpdateAmountTest {
    HttpServletResponse response;
    HttpServletRequest request;
    HttpSession session;
    GoodsUpdateAmount command;
    CommandResult result;
    Warehouse warehouse;
    List<Goods> goodsList;

    @BeforeEach
    public void setUp(){
        response = mock(HttpServletResponse.class);
        request = mock(HttpServletRequest.class);
        session = mock(HttpSession.class);

        warehouse = new Warehouse(1, "main");
        command = new GoodsUpdateAmount();
        goodsList = new ArrayList<>();
    }

    @Test
    public void execute() throws ServletException, IOException {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(Attributes.WAREHOUSE)).thenReturn(warehouse);
        when(request.getParameter(Parameters.GOODS_ID)).thenReturn("2");
        when(request.getParameter(Parameters.GOODS_AMOUNT)).thenReturn("10.0");
        when(request.getHeader("referer")).thenReturn(Paths.GOODS_LIST);

        try (MockedConstruction<GoodsDAO> mockedGoodsDAO = mockConstructionWithAnswer(GoodsDAO.class, new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                return Boolean.TRUE;
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
