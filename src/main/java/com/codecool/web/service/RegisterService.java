package com.codecool.web.service;

import com.codecool.web.model.Account;
import com.codecool.web.service.exception.ServiceException;

import java.sql.SQLException;

public interface RegisterService {
    Account registerUser(String username, String email, String password, String permission) throws SQLException, ServiceException;

}
