package com.cashregister.controller.commands.goods;

import com.cashregister.controller.commands.CommandResult;
import com.cashregister.controller.constants.Attributes;
import com.cashregister.controller.constants.Parameters;
import com.cashregister.controller.constants.Paths;
import com.cashregister.model.dao.GoodsDAO;
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

public class GoodsUpdatePriceTest {
    HttpServletResponse response;
    HttpServletRequest request;
    HttpSession session;
    GoodsUpdatePrice command;
    CommandResult result;
    Goods goods;

    @BeforeEach
    public void setUp(){
        response = mock(HttpServletResponse.class);
        request = mock(HttpServletRequest.class);
        session = mock(HttpSession.class);
        command = new GoodsUpdatePrice();
        goods = new Goods("test", "000");
    }

    @Test
    public void execute() throws ServletException, IOException {
        when(request.getSession()).thenReturn(session);
        when(request.getParameter(Parameters.GOODS_ID)).thenReturn("2");
        when(request.getParameter(Parameters.GOODS_PRICE)).thenReturn("10.0");
        when(request.getHeader("referer")).thenReturn(Paths.GOODS_LIST);

        try (MockedConstruction<GoodsDAO> mockedGoodsDAO = mockConstructionWithAnswer(GoodsDAO.class, new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                String action = invocation.getMethod().getName();
                if (action.equals("findById")) {
                    return Optional.of(goods);
                } else if (action.equals("update")) {
                    return Boolean.TRUE;
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
