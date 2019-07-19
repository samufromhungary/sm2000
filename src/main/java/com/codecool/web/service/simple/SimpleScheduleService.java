package com.codecool.web.service.simple;

import com.codecool.web.dao.ScheduleDao;
import com.codecool.web.model.Schedule;
import com.codecool.web.service.ScheduleService;
import com.codecool.web.service.exception.ServiceException;

import java.security.Provider;
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
    public Schedule getScheduleId(int id) throws SQLException,ServiceException{
        try{
            Schedule schedule = scheduleDao.findScheduleId(id);
            if (schedule == null) {
                throw new ServiceException("Unknown schedule");
            }
            return schedule;
        }catch (IllegalArgumentException ex){
            throw new ServiceException(ex.getMessage());
        }
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

    @Override
    public void deleteSchedule(String title) throws SQLException, ServiceException{
        try{
            scheduleDao.delete(title);
        }catch (IllegalArgumentException ex){
            throw new ServiceException(ex.getMessage());
        }
    }

    @Override
    public void modifyTitle(String oldTitle, String newTitle) throws SQLException, ServiceException{
        try{
            scheduleDao.modifyTitle(oldTitle,newTitle);
        }catch (IllegalArgumentException ex){
            throw new ServiceException(ex.getMessage());
        }
    }

    @Override
    public void modifyDays(String title, int days) throws SQLException, ServiceException{
        try{
            scheduleDao.modifyDays(title,days);
        }catch (IllegalArgumentException ex){
            throw new ServiceException(ex.getMessage());
        }
    }
}
