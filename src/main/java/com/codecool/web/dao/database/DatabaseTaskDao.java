package com.codecool.web.dao.database;

import com.codecool.web.dao.TaskDao;
import com.codecool.web.model.Schedule;
import com.codecool.web.model.Task;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public final class DatabaseTaskDao extends AbstractDao implements TaskDao {

    public DatabaseTaskDao(Connection connection){super(connection);}

    @Override
    public List<Task> findAll() throws SQLException {
        String sql ="SELECT id, title, accounts_id, description FROM tasks";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            List<Task> tasks = new ArrayList<>();
            while(resultSet.next()){
                tasks.add(fetchTask(resultSet));
            }
            return tasks;
        }
    }

    @Override
    public Task findByTitle(String title) throws SQLException {
        if(title == null || "".equals(title) ) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }
        String sql = "SELECT id, title, accounts_id, description FROM tasks WHERE title = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, title);
            try(ResultSet resultSet = statement.executeQuery()){
                if (resultSet.next()){
                    return fetchTask(resultSet);
                }
            }
        }
        return null;
    }

    public boolean taskAlreadyExists(String title) throws SQLException{
        List<Task> tasks = findAll();
        boolean valid = false;
        for (Task tas:tasks) {
            if(tas.getTitle().equalsIgnoreCase(title)){
                valid = true;
            }
        }return valid;
    }

    @Override
    public Task add(String title, int accounts_id, String description) throws SQLException {
        if (title == null || "".equals(title) || description == null || "".equals(description)) {
            throw new IllegalArgumentException("Something is missing");
        }
        if(!taskAlreadyExists(title)){
            boolean autoCommit = connection.getAutoCommit();
            connection.setAutoCommit(false);
            String sql = "INSERT INTO tasks (title, accounts_id, description) VALUES (?, ?, ?)";
            try(PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, title);
                statement.setInt(2,accounts_id);
                statement.setString(3, description);
                executeInsert(statement);
                int id = fetchGeneratedId(statement);
                return new Task(id,title, description);
            }catch (SQLException ex){
                connection.rollback();
                throw ex;
            } finally {
                connection.setAutoCommit(autoCommit);
            }
        }else{
            throw new IllegalArgumentException("Task already exists");
        }
    }

    @Override
    public void modifyTitle(String oldTitle, String newTitle) throws SQLException {
        String sql = "UPDATE tasks SET title = ? WHERE title = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, newTitle);
            statement.setString(2, oldTitle);
            statement.executeUpdate();
        }catch (SQLException ex){
            throw ex;
        }
    }

    @Override
    public void modifyDescription(String title, String description) throws SQLException {
        String sql = "UPDATE tasks SET description = ? WHERE title = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, description);
            statement.setString(2, title);
            statement.executeUpdate();
        }catch (SQLException ex){
            throw ex;
        }
    }

    @Override
    public void delete(String title) throws SQLException {
        boolean autoCommit = connection.getAutoCommit();
        connection.setAutoCommit(false);
        String sql ="DELETE FROM tasks WHERE title = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, title);
            executeInsert(statement);
        }catch (SQLException ex){
            connection.rollback();
            throw ex;
        }finally {
            connection.setAutoCommit(autoCommit);
        }
    }

    private Task fetchTask(ResultSet resultSet)throws SQLException{
        int id = resultSet.getInt("id");
        String title = resultSet.getString("title");
        String description = resultSet.getString("description");
        return new Task(id,title,description);
    }
}
