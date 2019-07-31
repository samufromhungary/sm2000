package com.codecool.web.service;

import com.codecool.web.model.Account;
import com.codecool.web.service.exception.ServiceException;

import java.sql.SQLException;
import java.util.List;


public interface AccountService {

    List<Account> findAll() throws SQLException, ServiceException;

    Account findByEmail(String email) throws SQLException, ServiceException;

    Account addAccount (String username,String email, String password, String permission) throws ServiceException, SQLException;

    boolean accountAlreadyExists(String email) throws SQLException, ServiceException;

    boolean validateEmail(String email) throws SQLException, ServiceException;
}
