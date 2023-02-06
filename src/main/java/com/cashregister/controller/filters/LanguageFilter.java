package com.cashregister.controller.filters;

import com.cashregister.controller.constants.Attributes;
import com.cashregister.controller.constants.Common;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(filterName = "LanguageFilter")
public class LanguageFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        final HttpSession session = httpRequest.getSession();

        String lang = getLanguageFromCookie(httpRequest);
        session.setAttribute(Attributes.LANG, lang);

        chain.doFilter(servletRequest, servletResponse);
    }

    private String getLanguageFromCookie(HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(Attributes.LANG)) {
                    return cookie.getValue();
                }
            }
        }
        return Common.DEFAULT_LANG;
    }
}
