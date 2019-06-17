package com.codecool.web.dao;

import com.codecool.web.model.Schedule;

import java.sql.SQLException;
import java.util.List;

public interface ScheduleDao {

    List<Schedule> findAll() throws SQLException;

    Schedule findByTitle(String title) throws SQLException;

    Schedule add(String title, int days) throws SQLException;

    void modifyTitle(String oldTitle, String newTitle) throws SQLException;

    void modifyDays (String title, int days) throws SQLException;

    void delete (String title) throws SQLException;
}
