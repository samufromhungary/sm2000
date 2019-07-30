package com.codecool.web.servlet;

import com.codecool.web.dao.AccountDao;
import com.codecool.web.dao.database.DatabaseAccountDao;
import com.codecool.web.model.Account;
import com.codecool.web.service.LogService;
import com.codecool.web.service.LoginService;
import com.codecool.web.service.RegisterService;
import com.codecool.web.service.exception.ServiceException;
import com.codecool.web.service.simple.SimpleLogService;
import com.codecool.web.service.simple.SimpleLoginService;
import com.codecool.web.service.simple.SimpleRegisterService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/register")
public class RegisterServlet extends AbstractServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            AccountDao accountDao = new DatabaseAccountDao(connection);
            RegisterService registerService = new SimpleRegisterService(accountDao);
            LogService logService = new SimpleLogService();

            String username = req.getParameter("username");
            String email = req.getParameter("email");
            String password = req.getParameter("password");


            Account account = registerService.registerUser(username, email, password, "regular");
            req.getSession().setAttribute("account", account);

            sendMessage(resp, HttpServletResponse.SC_OK, account);
            logService.log("Account created on username: " + account.getUsername() + " email:" + account.getEmail());
        } catch (ServiceException ex) {
            sendMessage(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
        } catch (SQLException ex) {
            handleSqlError(resp, ex);
        }
    }

}
