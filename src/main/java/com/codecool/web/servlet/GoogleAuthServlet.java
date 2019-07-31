package com.codecool.web.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;

import com.codecool.web.dao.AccountDao;
import com.codecool.web.dao.database.DatabaseAccountDao;
import com.codecool.web.model.Account;
import com.codecool.web.service.AccountService;
import com.codecool.web.service.LoginService;
import com.codecool.web.service.RegisterService;
import com.codecool.web.service.exception.ServiceException;
import com.codecool.web.service.simple.SimpleAccountService;
import com.codecool.web.service.simple.SimpleLoginService;
import com.codecool.web.service.simple.SimpleRegisterService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;



@WebServlet("/auth")
public final class GoogleAuthServlet extends AbstractServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            AccountDao accountDao = new DatabaseAccountDao(connection);
            RegisterService registerService = new SimpleRegisterService(accountDao);
            LoginService loginService = new SimpleLoginService(accountDao);
            AccountService accountService = new SimpleAccountService(accountDao);
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance())
                // Specify the CLIENT_ID of the app that accesses the backend:
                .setAudience(Collections.singletonList("354584955844-7q0r3acj2tjl0tp9o80o306rrikdtfmr.apps.googleusercontent.com"))
                // Or, if multiple clients access the backend:
                //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
                .build();

            String id_token = req.getParameter("id-token");
            GoogleIdToken idToken = verifier.verify(id_token);
            if (idToken != null) {
                Payload payload = idToken.getPayload();
                // Get profile information from payload
                Account account;
                String email = payload.getEmail();
                if (accountService.validateEmail(email)) {
                    account = accountService.findByEmail(email);
                } else {
                    String username = (String) payload.get("name");
                    String emailNew = payload.getEmail();
                    String password = null;
                    String permission = "regular";
                    registerService.registerUser(username, email, password, permission);
                    account = accountService.findByEmail(emailNew);
                }
                // loginService.loginUser(account.getEmail(),account.getPassword());
                req.getSession().setAttribute("account", account);
                sendMessage(resp, HttpServletResponse.SC_OK, account);
            }
        } catch (SQLException ex) {
            handleSqlError(resp, ex);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (ServiceException f) {
            f.printStackTrace();
        }
    }
}
