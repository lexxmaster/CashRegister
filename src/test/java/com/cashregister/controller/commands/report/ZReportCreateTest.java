package com.cashregister.controller.commands.report;

import com.cashregister.controller.commands.CommandResult;
import com.cashregister.controller.commands.orders.goods.OrderGoodsAdd;
import com.cashregister.controller.commands.orders.goods.OrderGoodsAmount;
import com.cashregister.controller.commands.report.XReportView;
import com.cashregister.controller.constants.Actions;
import com.cashregister.controller.constants.Attributes;
import com.cashregister.controller.constants.Parameters;
import com.cashregister.controller.constants.Paths;
import com.cashregister.model.dao.*;
import com.cashregister.model.entity.*;
import com.cashregister.model.generator.ReportGenerator;
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

public class ZReportCreateTest {
    HttpServletResponse response;
    HttpServletRequest request;
    HttpSession session;
    ZReportCreate command;
    ReportGenerator reportGenerator;
    Report report;
    CommandResult result;
    CheckoutShift checkoutShift;
    Warehouse warehouse;
    User user;
    Order order;
    Goods goods;

    @BeforeEach
    public void setUp(){
        response = mock(HttpServletResponse.class);
        request = mock(HttpServletRequest.class);
        session = mock(HttpSession.class);

        command = new ZReportCreate();

        report = new Report();
        warehouse = new Warehouse(1, "main");
        checkoutShift = new CheckoutShift(warehouse);
        order = new Order(warehouse, checkoutShift);
        goods = new Goods("test", "001");
        order.addGoods(goods, 1, 10);

        user = new User(1, "test", "");
    }

    @Test
    public void execute() throws ServletException, IOException {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(Attributes.CHECKOUT_SHIFT)).thenReturn(checkoutShift);
        when(session.getAttribute(Attributes.USER_ID)).thenReturn(1l);

        try (MockedConstruction<UserDAO> mockedUserDAO = mockConstructionWithAnswer(UserDAO.class, new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                return Optional.of(user);
            }
        });
             MockedConstruction<CheckoutShiftDAO> mockedCheckoutShiftDAO = mockConstructionWithAnswer(CheckoutShiftDAO.class, new Answer() {
                 @Override
                 public Object answer(InvocationOnMock invocation) throws Throwable {
                     return Boolean.TRUE;
                 }
             });
             MockedConstruction<ReportDAO> mockedReportDAO = mockConstructionWithAnswer(ReportDAO.class, new Answer() {
                 @Override
                 public Object answer(InvocationOnMock invocation) throws Throwable {
                     return Boolean.TRUE;
                 }
             });
             MockedConstruction<ReportGenerator> mockedReportGenerator = mockConstructionWithAnswer(ReportGenerator.class, new Answer() {
                 @Override
                 public Object answer(InvocationOnMock invocation) throws Throwable {
                     return report;
                 }
             })
        ){

            result = command.execute(request, response);
        }

        String path = Paths.CONTROLLER + Actions.Z_REPORT_VIEW + "&" + Parameters.REPORT_ID + "=" + report.getId();

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
