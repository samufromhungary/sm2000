package com.codecool.web.service;

import com.codecool.web.model.Schedule;
import com.codecool.web.service.exception.ServiceException;

import java.sql.SQLException;
import java.util.List;

public interface ScheduleService {

    List<Schedule> getSchedules() throws SQLException;

    List<Schedule> getSchedulesById(int accounts_id) throws SQLException;

    Schedule getSchedule(String title) throws SQLException, ServiceException;

    Schedule addSchedule(String title, int days, int accounts_id) throws SQLException, ServiceException;

    void deleteSchedule(String title) throws SQLException, ServiceException;
}
