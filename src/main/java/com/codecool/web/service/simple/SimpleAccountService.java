package com.codecool.web.service.simple;

import com.codecool.web.dao.AccountDao;
import com.codecool.web.model.Account;
import com.codecool.web.service.AccountService;
import com.codecool.web.service.exception.ServiceException;

import java.sql.SQLException;
import java.util.List;

public final class SimpleAccountService implements AccountService {

    private final AccountDao accountDao;

    public SimpleAccountService(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public List<Account> findAll() throws SQLException, ServiceException{
        return accountDao.findAll();
    }

    @Override
    public Account findByEmail(String email) throws SQLException, ServiceException{
        try{
            return accountDao.findByEmail(email);
        } catch (IllegalArgumentException ex) {
            throw new ServiceException(ex.getMessage());
        }
    }

    @Override
    public Account addAccount(String username, String email, String password, String permission) throws ServiceException, SQLException {
        return accountDao.addAccount(username,email,password,permission);
    }

    @Override
    public boolean accountAlreadyExists(String email) throws SQLException, ServiceException{
        return accountDao.accountAlreadyExists(email);
    }
}
