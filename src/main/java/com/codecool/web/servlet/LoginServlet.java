package com.codecool.web.servlet;

import com.codecool.web.dao.AccountDao;
import com.codecool.web.dao.database.DatabaseAccountDao;
import com.codecool.web.model.Account;
import com.codecool.web.service.LogService;
import com.codecool.web.service.LoginService;
import com.codecool.web.service.exception.ServiceException;
import com.codecool.web.service.simple.SimpleLogService;
import com.codecool.web.service.simple.SimpleLoginService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/login")
public final class LoginServlet extends AbstractServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            AccountDao accountDao = new DatabaseAccountDao(connection);
            LoginService loginService = new SimpleLoginService(accountDao);
            LogService logService = new SimpleLogService();

            String email = req.getParameter("email");
            String password = req.getParameter("password");

            Account account = loginService.loginUser(email, password);
            req.getSession().setAttribute("account", account);

            sendMessage(resp, HttpServletResponse.SC_OK, account);
            logService.log(account.getUsername() + "(" + account.getEmail() + ") logged in.");
        } catch (ServiceException ex) {
            sendMessage(resp, HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
        } catch (SQLException ex) {
            handleSqlError(resp, ex);
        }
    }
}
