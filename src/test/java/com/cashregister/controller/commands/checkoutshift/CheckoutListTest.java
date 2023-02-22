package com.cashregister.controller.commands.checkoutshift;

import com.cashregister.controller.commands.CommandResult;
import com.cashregister.controller.constants.Actions;
import com.cashregister.controller.constants.Attributes;
import com.cashregister.controller.constants.Parameters;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mockConstructionWithAnswer;

public class CheckoutListTest {
    HttpServletResponse response;
    HttpServletRequest request;
    CheckoutList command;
    CheckoutShiftDAO checkoutShiftDAO;
    List<CheckoutShift> checkoutShiftList;
    UserDAO userDAO;
    CommandResult result;
    Warehouse warehouse;
    CheckoutShift checkoutShift;

    @BeforeEach
    public void setUp(){
        response = mock(HttpServletResponse.class);
        request = mock(HttpServletRequest.class);
        userDAO = mock(UserDAO.class);
        checkoutShiftDAO = mock(CheckoutShiftDAO.class);
        checkoutShiftList = new ArrayList<>();
        warehouse = new Warehouse(1, "main");
        checkoutShift = new CheckoutShift(warehouse);
        command = new CheckoutList();
    }

    @Test
    public void execute() throws ServletException, IOException {
        when(request.getParameter(Parameters.PAGE)).thenReturn("1");

        when(userDAO.findByLogin(any(String.class))).thenReturn(Optional.of(new User(1, "test", "")));
        when(checkoutShiftDAO.create(any(CheckoutShift.class))).thenReturn(true);

        try (MockedConstruction<CheckoutShiftDAO> mockedCheckoutShiftDAO = mockConstructionWithAnswer(CheckoutShiftDAO.class,
                new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                String methodName = invocation.getMethod().getName();
                if (methodName.equals("getNumOfPages")) {
                    return 1;
                } else if (methodName.equals("findAllByPage")) {
                    return checkoutShiftList;
                }
                return null;

            }
            })){
            result = command.execute(request, response);
        }

        Assertions.assertEquals(Paths.CHECKOUT_LIST, result.getPath());


    }

    @AfterEach
    public void tearDown(){
        response = null;
        request = null;
        userDAO = null;
        checkoutShiftDAO = null;
        command = null;
    }
}
