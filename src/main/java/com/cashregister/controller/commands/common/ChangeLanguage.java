package com.cashregister.controller.commands.common;

import com.cashregister.controller.commands.CommandResult;
import com.cashregister.controller.commands.ICommand;
import com.cashregister.controller.constants.Attributes;
import com.cashregister.controller.constants.Common;
import com.cashregister.controller.constants.Parameters;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class ChangeLanguage implements ICommand {
    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String lang = req.getParameter(Parameters.LANG);
        if (lang == null) {
            lang = "en";
        }
        setLanguageCookie(resp, lang);

        final HttpServletRequest httpRequest = (HttpServletRequest) req;
        final HttpSession session = httpRequest.getSession();
        String page = (String) session.getAttribute(Attributes.CURRENT_JSP);
        session.setAttribute(Attributes.LANG, lang);

        return new CommandResult(req.getHeader("referer"), true);
    }

    private void setLanguageCookie(HttpServletResponse resp, String lang){
        Cookie langCookie = new Cookie(Attributes.LANG, lang);
        langCookie.setMaxAge(Common.COOKIES_AGE);
        resp.addCookie(langCookie);
    }
}
