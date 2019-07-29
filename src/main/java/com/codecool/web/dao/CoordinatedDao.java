package com.codecool.web.dao;

import com.codecool.web.model.Coordinated;

import java.sql.SQLException;

public interface CoordinatedDao {
    Coordinated findByTasksId (int tasksId) throws SQLException;
}
