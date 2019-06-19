package com.codecool.web.service.simple;

import com.codecool.web.dao.ScheduleDao;
import com.codecool.web.model.Schedule;
import com.codecool.web.service.ScheduleService;
import com.codecool.web.service.exception.ServiceException;

import java.sql.SQLException;
import java.util.List;

public final class SimpleScheduleService implements ScheduleService {

    final ScheduleDao scheduleDao;

    public SimpleScheduleService(ScheduleDao scheduleDao){this.scheduleDao = scheduleDao;}

    @Override
    public List<Schedule> getSchedules() throws SQLException {
        return scheduleDao.findAll();
    }

    @Override
    public List<Schedule> getSchedulesById(int accounts_id) throws SQLException{
        return scheduleDao.findAllById(accounts_id);
    }

    @Override
    public Schedule getSchedule(String title) throws SQLException, ServiceException {
        try{
            Schedule schedule = scheduleDao.findByTitle(title);
            if (schedule == null) {
                throw new ServiceException("Unknown schedule");
            }
            return schedule;
        }catch (IllegalArgumentException ex){
            throw new ServiceException(ex.getMessage());
        }
    }

    @Override
    public Schedule addSchedule(String title, int days, int accounts_id) throws SQLException, ServiceException {
        try{
            return scheduleDao.add(title, days, accounts_id);
        }catch (IllegalArgumentException ex){
            throw new ServiceException(ex.getMessage());
        }
    }
}
