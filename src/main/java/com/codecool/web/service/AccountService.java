package com.codecool.web.service;

import com.codecool.web.model.Account;
import com.codecool.web.service.exception.ServiceException;

import java.sql.SQLException;
import java.util.List;


public interface AccountService {

    List<Account> findAll() throws SQLException, ServiceException;

    Account findByEmail(String email) throws SQLException, ServiceException;

    boolean accountAlreadyExists(String email) throws SQLException, ServiceException;
}
