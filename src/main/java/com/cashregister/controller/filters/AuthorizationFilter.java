package com.cashregister.controller.filters;

import com.cashregister.controller.AccessRules;
import com.cashregister.controller.constants.Attributes;
import com.cashregister.controller.constants.Paths;
import com.cashregister.controller.services.UserAuthenticator;
import com.cashregister.model.dao.UserDAO;
import com.cashregister.model.entity.Role;
import com.cashregister.model.entity.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import static java.util.Objects.nonNull;

@WebFilter(filterName = "AuthorizationFilter", urlPatterns = {"/*"})
public class AuthorizationFilter implements Filter {
    protected static final Logger LOGGER = LogManager.getLogger(AuthorizationFilter.class);
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest httpRequest = (HttpServletRequest) request;
        final HttpServletResponse httpResponse = (HttpServletResponse) response;

        final String login = request.getParameter("login");
        final String password = request.getParameter("password");
        final String action = request.getParameter("action");

        final HttpSession session = httpRequest.getSession();

        if (nonNull(session) && nonNull(session.getAttribute(Attributes.LOGIN)) && nonNull(session.getAttribute(Attributes.ROLE))) {
//            Role role = (Role) session.getAttribute(Attributes.ROLE);
//            if (AccessRules.checkAccess(role, action)) {
                chain.doFilter(request, response);
//            } else {
//                //request.getRequestDispatcher(Paths.ACCESS_DENIED).forward(request, response);
//                throw new ServletException();
//            }
        } else if (nonNull(login)) {
            User user = null;
            try {
                user = UserAuthenticator.authenticateUser(login, password);
            } catch (AuthenticationException e) {
                request.getRequestDispatcher(Paths.LOGIN_ERROR).forward(request, response);
                return;
            }
            session.setAttribute(Attributes.LOGIN, user.getLogin());
            session.setAttribute(Attributes.USER_ID, user.getId());
            session.setAttribute(Attributes.ROLE, user.getRole());

            if (nonNull(action)) { // a little redundant
                if (AccessRules.checkAccess(user.getRole(), action)) {
                    chain.doFilter(request, response);
                } else {
                    request.getRequestDispatcher(Paths.ACCESS_DENIED).forward(request, response);
                }
            } else{
                request.getRequestDispatcher(Paths.LOGIN).forward(request, response);
            }
        } else{
            request.getRequestDispatcher(Paths.LOGIN).forward(request, response);
        }
    }
}
