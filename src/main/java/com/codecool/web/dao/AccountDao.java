package com.codecool.web.dao;

import com.codecool.web.model.Account;

import java.sql.SQLException;
import java.util.List;

public interface AccountDao {

    List<Account> findAll() throws SQLException;

    Account findByEmail(String email) throws SQLException;

    Account add(String username, String email, String password, String permission) throws SQLException;

    void modifyPermission(String permission, String email) throws SQLException;

    boolean accountAlreadyExists(String email) throws SQLException;

    boolean validateEmail(String email) throws SQLException;
}
