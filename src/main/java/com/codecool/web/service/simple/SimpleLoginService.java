package com.codecool.web.service.simple;

import com.codecool.web.dao.AccountDao;
import com.codecool.web.model.Account;
import com.codecool.web.service.LoginService;
import com.codecool.web.service.exception.ServiceException;

import java.sql.SQLException;


import com.codecool.web.dao.AccountDao;
import com.codecool.web.model.Account;
import com.codecool.web.service.LoginService;
import com.codecool.web.service.exception.ServiceException;

import java.sql.SQLException;

public final class SimpleLoginService implements LoginService {

    private final AccountDao accountDao;

    public SimpleLoginService(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public Account loginUser(String email, String password) throws SQLException, ServiceException {
        try {
            Account account = accountDao.findByEmail(email);
            if (account == null || !account.getPassword().equals(password)) {
                throw new ServiceException("Bad login");
            }
            return account;
        } catch (IllegalArgumentException ex) {
            throw new ServiceException(ex.getMessage());
        }
    }
}
