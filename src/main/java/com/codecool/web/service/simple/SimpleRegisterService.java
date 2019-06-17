package com.codecool.web.service.simple;

import com.codecool.web.dao.AccountDao;
import com.codecool.web.model.Account;
import com.codecool.web.service.RegisterService;
import com.codecool.web.service.exception.ServiceException;

import java.sql.SQLException;

public final class SimpleRegisterService implements RegisterService {

    private final AccountDao accountDao;

    public SimpleRegisterService(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public Account registerUser(String username, String email, String password, String permission) throws SQLException, ServiceException {
        Account account = accountDao.add(username,email,password,permission);
        return account;
    }
}
