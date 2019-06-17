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
    public Schedule add(String title, int days) throws SQLException {
        if (title == null || "".equals(title) || days <= 0 || days > 7) {
            throw new IllegalArgumentException("Something is wrong");
        }
        if(!scheduleAlreadyExists(title)){
            boolean autoCommit = connection.getAutoCommit();
            connection.setAutoCommit(false);
            String sql = "INSERT INTO schedules (title, days) VALUES (?, ?)";
            try(PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, title);
                statement.setInt(2, days);
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
            statement.setString(1, newTitle);
            statement.setString(2, oldTitle);
            statement.executeUpdate();
        }catch (SQLException ex){
            throw ex;
        }
    }

    @Override
    public void modifyDays(String title, int days) throws SQLException {
        String sql = "UPDATE schedules SET days = ? WHERE title = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, days);
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

    private Schedule fetchSchedule(ResultSet resultSet)throws SQLException{
        int id = resultSet.getInt("id");
        String title = resultSet.getString("title");
        int days = resultSet.getInt("days");
        return new Schedule(id, title, days);
    }
}
