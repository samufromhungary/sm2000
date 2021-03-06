package com.codecool.web.dao;

import com.codecool.web.model.Task;

import java.sql.SQLException;
import java.util.List;

public interface TaskDao {

    List<Task> findAll() throws SQLException;

    List<Task> findAllById(int accounts_id) throws SQLException;

    Task findByTitle(String title) throws SQLException;

    Task add(String title, int accounts_id, String description) throws SQLException;

    void modifyTitle(String oldTitle, String newTitle) throws SQLException;

    void modifyDescription(String title, String description) throws SQLException;

    void delete(String title) throws SQLException;

    void add(int taskId, int... scheduleIds) throws SQLException;

    void addToCoordinated(int tasksId, int schedulesId, int day, int startDate, int endDate) throws SQLException;
}


