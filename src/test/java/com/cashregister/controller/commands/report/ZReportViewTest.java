package com.cashregister.controller.commands.report;

import com.cashregister.controller.commands.CommandResult;
import com.cashregister.controller.constants.Parameters;
import com.cashregister.controller.constants.Paths;
import com.cashregister.model.dao.ReportDAO;
import com.cashregister.model.dao.UserDAO;
import com.cashregister.model.entity.Report;
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
import java.util.Optional;

import static org.mockito.Mockito.*;

public class ZReportViewTest {
    HttpServletResponse response;
    HttpServletRequest request;
    HttpSession session;
    CommandResult result;
    ZReportView command;
    Report report;

    @BeforeEach
    public void setUp(){
        response = mock(HttpServletResponse.class);
        request = mock(HttpServletRequest.class);
        session = mock(HttpSession.class);
        command = new ZReportView();
        report = new Report();
    }

    @Test
    public void execute() throws ServletException, IOException {
        when(request.getParameter(Parameters.REPORT_ID)).thenReturn("1");
        try (MockedConstruction<ReportDAO> mockedReportDAO = mockConstructionWithAnswer(ReportDAO.class, new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                return Optional.of(report);
            }
        })
        ){
            result = command.execute(request, response);
        }
        Assertions.assertEquals(Paths.Z_REPORT, result.getPath());
    }
}
