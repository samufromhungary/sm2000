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
        try{
            if(username == null || "".equals(username) || email == null || "".equals(email) || password == null || "".equals(password)){
                if (accountDao.findByEmail(email) != null){
                    throw new ServiceException("Account on this email already exists");
                }
                throw new ServiceException("Something's missing");
            }else{
                Account account = accountDao.add(username,email,password,permission);
                return account;
            }
        }catch (IllegalArgumentException ex){
            throw new ServiceException(ex.getMessage());
        }
    }
}
