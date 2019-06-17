package com.codecool.web.dao.database;

import com.codecool.web.dao.AccountDao;
import com.codecool.web.model.Account;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public final class DatabaseAccountDao extends AbstractDao implements AccountDao {

    DatabaseAccountDao(Connection connection) {
        super(connection);
    }

    @Override
    public List<Account> findAll() throws SQLException {
        String sql ="SELECT id, username, email, password, permission from accounts";
        try (Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql)) {
            List<Account> accounts = new ArrayList<>();
            while(resultSet.next()){
                accounts.add(fetchAccount(resultSet));
            }
            return accounts;
        }
    }

    @Override
    public Account findByEmail(String email) throws SQLException {
        if(email == null || "".equals(email) ) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        String sql = "SELECT id, username, email, password, permission FROM accounts WHERE email = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, email);
            try(ResultSet resultSet = statement.executeQuery()){
                if (resultSet.next()){
                    return fetchAccount(resultSet);
                }
            }
        }
        return null;
    }

    public boolean accountAlreadyExists(String email) throws SQLException{
        List<Account> accounts = findAll();
        boolean valid = false;
        for (Account acc:accounts) {
            if(acc.getEmail().equalsIgnoreCase(email)){
                valid = true;
            }
        }return valid;
    }

    @Override
    public Account add(String username, String email, String password, String permission) throws SQLException{
        if (username == null || "".equals(username) || password == null || "".equals(password) ||
            email == null || "".equals(email) || permission == null || "".equals(permission)) {
            throw new IllegalArgumentException("Something is missing");
        }
        if(!accountAlreadyExists(email)){
            boolean autoCommit = connection.getAutoCommit();
            connection.setAutoCommit(false);
            String sql = "INSERT INTO accounts (username, email, password, permission) VALUES (?, ?, ?, ?)";
            try(PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, username);
                statement.setString(2, email);
                statement.setString(3,password);
                statement.setString(4, permission);
                executeInsert(statement);
                int id = fetchGeneratedId(statement);
                return new Account(id,username,email,password,permission);
            }catch (SQLException ex){
                connection.rollback();
                throw ex;
            } finally {
                connection.setAutoCommit(autoCommit);
            }
        }else{
            throw new IllegalArgumentException("Account already exists");
        }
    }

    @Override
    public void changePermission(String permission, String email) throws SQLException{
        String sql = "UPDATE accounts SET permission = ? WHERE email = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, permission);
            statement.setString(2,email);
            statement.executeUpdate();
            }catch (SQLException ex){
            throw ex;
            }
        }

    private Account fetchAccount(ResultSet resultSet)throws SQLException{
        int id = resultSet.getInt("id");
        String username = resultSet.getString("username");
        String email = resultSet.getString("email");
        String password = resultSet.getString("password");
        String permission = resultSet.getString("permission");
        return new Account(id,username,email,password,permission);
    }
}
