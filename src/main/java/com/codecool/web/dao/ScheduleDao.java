package com.codecool.web.dao;

import com.codecool.web.model.Schedule;

import java.sql.SQLException;
import java.util.List;

public interface ScheduleDao {

    List<Schedule> findAll() throws SQLException;

    List<Schedule> findAllById(int accounts_id) throws SQLException;

    Schedule findByTitle(String title) throws SQLException;


    Schedule add(String title, int days, int accounts_id) throws SQLException;

    void modifyTitle(String oldTitle, String newTitle) throws SQLException;

    void modifyDays (String title, int days) throws SQLException;

    void delete (String title) throws SQLException;

    List<Schedule> findAllByTaskId(int taskId) throws SQLException;

    Schedule findScheduleId(int id) throws SQLException;

}
