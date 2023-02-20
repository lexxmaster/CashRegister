package com.cashregister.controller.model.generator;

import com.cashregister.controller.commands.orders.OrderList;
import com.cashregister.model.dao.OrderDAO;
import com.cashregister.model.entity.*;
import com.cashregister.model.generator.ReportGenerator;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockConstructionWithAnswer;

public class ReportGeneratorTest {
    Warehouse warehouse;
    CheckoutShift checkoutShift;
    User user;
    Order order;
    List<Order> orderList;
    ReportGenerator reportGenerator;
    Report report;

    @BeforeEach
    public void setUp(){
        user = new User(1, "test", "");
        warehouse = new Warehouse(1, "main");
        checkoutShift = new CheckoutShift(warehouse);
        order = new Order(warehouse, checkoutShift);
        orderList = new ArrayList<>();
        orderList.add(order);
        reportGenerator = new ReportGenerator(checkoutShift, user);
    }
    @Test
    public void execute()  {
        try (MockedConstruction<OrderDAO> mockedOrderDAO = mockConstructionWithAnswer(OrderDAO.class, new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                return orderList;
            }
        })){

            report = reportGenerator.execute();
        }

        Assertions.assertEquals(1, report.getCheckAmount());
        Assertions.assertEquals(0, report.getSum());
    }
}
