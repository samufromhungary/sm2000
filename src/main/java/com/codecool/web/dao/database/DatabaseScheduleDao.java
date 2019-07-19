package com.codecool.web.dao.database;

import com.codecool.web.dao.ScheduleDao;
import com.codecool.web.model.Account;
import com.codecool.web.model.Schedule;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public final class DatabaseScheduleDao extends AbstractDao implements ScheduleDao {

    public DatabaseScheduleDao(Connection connection){
        super(connection);
    }

    @Override
    public List<Schedule> findAll() throws SQLException {
        String sql ="SELECT id, title, days, accounts_id from schedules";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            List<Schedule> schedules = new ArrayList<>();
            while(resultSet.next()){
                schedules.add(fetchSchedule(resultSet));
            }
            return schedules;
        }
    }

    @Override
    public List<Schedule> findAllById(int accounts_id) throws SQLException{
        String sql = "SELECT id, title, days, accounts_id FROM schedules WHERE accounts_id = ? GROUP BY id";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, accounts_id);
            List<Schedule> schedules = new ArrayList<>();
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    schedules.add(fetchSchedule(resultSet));
                }
                return schedules;
            }
        }
    }

    @Override
    public Schedule findByTitle(String title) throws SQLException {
        if(title == null || "".equals(title) ) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }
        String sql = "SELECT id, title, days, accounts_id FROM schedules WHERE title = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, title);
            try(ResultSet resultSet = statement.executeQuery()){
                if (resultSet.next()){
                    return fetchSchedule(resultSet);
                }
            }
        }
        return null;
    }

    public boolean scheduleAlreadyExists(String title) throws SQLException{
        List<Schedule> schedules = findAll();
        boolean valid = false;
        for (Schedule sch:schedules) {
            if(sch.getTitle().equalsIgnoreCase(title)){
                valid = true;
            }
        }return valid;
    }

    @Override
    public Schedule add(String title, int days, int accounts_id) throws SQLException {
        if (title == null || "".equals(title)) {
            throw new IllegalArgumentException("Title cannot be empty");
        }
        if(days <= 0 || days > 7){
            throw new IllegalArgumentException("Days cannot be less than 1, or more than 7");
        }
        if(true){
            boolean autoCommit = connection.getAutoCommit();
            connection.setAutoCommit(false);
            String sql = "INSERT INTO schedules (title, days, accounts_id) VALUES (?, ?, ?)";
            try(PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, title);
                statement.setInt(2, days);
                statement.setInt(3, accounts_id);
                executeInsert(statement);
                int id = fetchGeneratedId(statement);
                return new Schedule(id,title,days);
            }catch (SQLException ex){
                connection.rollback();
                throw ex;
            } finally {
                connection.setAutoCommit(autoCommit);
            }
        }else{
            throw new IllegalArgumentException("Schedule already exists");
        }
    }

    @Override
    public void modifyTitle(String oldTitle, String newTitle) throws SQLException {
        String sql = "UPDATE schedules SET title = ? WHERE title = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            if(!newTitle.isBlank()){
                statement.setString(1, newTitle);
                statement.setString(2, oldTitle);
                statement.executeUpdate();
            }else{
                throw new IllegalArgumentException("Title is empty");
            }

        }catch (SQLException ex){
            throw ex;
        }
    }

    @Override
    public void modifyDays(String title, int days) throws SQLException {
        String sql = "UPDATE schedules SET days = ? WHERE title = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            if (days > 7 || days < 1){
                throw new IllegalArgumentException("Incorrect days value");
            }else{
                statement.setInt(1, days);
                statement.setString(2, title);
                statement.executeUpdate();
            }
        }catch (SQLException ex){
            throw ex;
        }
    }

    @Override
    public void delete(String title) throws SQLException {
        boolean autoCommit = connection.getAutoCommit();
        connection.setAutoCommit(false);
        String sql ="DELETE FROM schedules WHERE title = ?";
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

    @Override
    public List<Schedule> findAllByTaskId(int taskId) throws SQLException{
        List<Schedule> schedules = new ArrayList<>();
        String sql = "SELECT s.id, s.title " +
            "FROM schedules AS s " +
            "JOIN coordinated AS co ON s.id = co.schedules_id " +
            "JOIN tasks AS t ON t.id = co.tasks_id " +
            "WHERE t.id = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, taskId);
            try(ResultSet resultSet = statement.executeQuery()){
                while (resultSet.next()){
                    schedules.add(fetchSchedule(resultSet));
                }
            }
        }
        return schedules;
    }


    @Override
    public Schedule findScheduleId(int id) throws SQLException {
        if(id == 0 || "".equals(id) ) {
            throw new IllegalArgumentException("ID cannot be null or empty");
        }
        String sql = "SELECT id, title, days, accounts_id FROM schedules WHERE id = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, id);
            try(ResultSet resultSet = statement.executeQuery()){
                if (resultSet.next()){
                    return fetchSchedule(resultSet);
                }
            }
        }
        return null;
    }

    private Schedule fetchSchedule(ResultSet resultSet)throws SQLException{
        int id = resultSet.getInt("id");
        String title = resultSet.getString("title");
        int days = resultSet.getInt("days");
        return new Schedule(id, title, days);
    }
}
