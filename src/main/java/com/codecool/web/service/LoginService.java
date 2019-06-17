package com.codecool.web.service;

import com.codecool.web.model.Account;
import com.codecool.web.service.exception.ServiceException;

import java.sql.SQLException;

public interface LoginService {
    Account loginUser(String email, String password) throws SQLException, ServiceException;

}
