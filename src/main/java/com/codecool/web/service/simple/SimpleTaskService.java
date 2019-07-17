package com.codecool.web.service.simple;

import com.codecool.web.dao.ScheduleDao;
import com.codecool.web.dao.TaskDao;
import com.codecool.web.model.Schedule;
import com.codecool.web.model.Task;
import com.codecool.web.service.TaskService;
import com.codecool.web.service.exception.ServiceException;

import java.security.Provider;
import java.sql.SQLException;
import java.util.List;

public final class SimpleTaskService implements TaskService {

    private final TaskDao taskDao;
    private final ScheduleDao scheduleDao;

    public SimpleTaskService(TaskDao taskDao, ScheduleDao scheduleDao) {
        this.taskDao = taskDao; this.scheduleDao = scheduleDao;
    }

    @Override
    public List<Task> getTasks() throws SQLException {
        return taskDao.findAll();
    }

    @Override
    public List<Task> getTasksById(int accounts_id) throws SQLException{
        return taskDao.findAllById(accounts_id);
    }

    @Override
    public Task getTask(String title) throws SQLException, ServiceException {
        try {
            return taskDao.findByTitle(title);
        } catch (NumberFormatException ex) {
            throw new ServiceException("Task title must be a String");
        } catch (IllegalArgumentException ex) {
            throw new ServiceException(ex.getMessage());
        }
    }

    @Override
    public Task addTask(String title, int accounts_id, String description) throws SQLException, ServiceException {
        try {
            return taskDao.add(title, accounts_id, description);
        }catch (IllegalArgumentException ex) {
            throw new ServiceException(ex.getMessage());
        }
    }

    @Override
    public void deleteTask(String title) throws SQLException, ServiceException{
        try{
            taskDao.delete(title);
        }catch (IllegalArgumentException ex){
            throw new ServiceException(ex.getMessage());
        }
    }

    @Override
    public void modifyTitle(String oldTitle, String newTitle) throws SQLException, ServiceException{
        try{
            taskDao.modifyTitle(oldTitle,newTitle);
        }catch (IllegalArgumentException ex){
            throw new ServiceException(ex.getMessage());
        }
    }

    @Override
    public void modifyDescription(String title, String description) throws SQLException, ServiceException{
        try{
            taskDao.modifyDescription(title,description);
        }catch (IllegalArgumentException ex){
            throw new ServiceException(ex.getMessage());
        }
    }

    @Override
    public void addTaskToSchedules(int taskId, String... scheduleIds) throws SQLException, ServiceException{
        if( scheduleIds == null || scheduleIds.length == 0){
            throw new ServiceException("Schedule ids cannot be null or empty.");
        }
        try{
            taskDao.add(taskId, parseScheduleIds(scheduleIds));
        }catch (NumberFormatException ex){
            throw new ServiceException("Task id must be an integer");
        }catch (IllegalArgumentException ex){
            throw new ServiceException(ex.getMessage());
        }
    }

    @Override
    public List<Schedule> getTaskSchedules(int taskId) throws SQLException, ServiceException{
        try{
            return scheduleDao.findAllByTaskId(taskId);
        }catch (NumberFormatException ex){
            throw new ServiceException("Task id must be an integer");
        }catch (IllegalArgumentException ex){
            throw new ServiceException(ex.getMessage());
        }
    }

    @Override
    public void addToCoordinated(int tasksId, int schedulesId, int day, int startDate, int endDate) throws SQLException, ServiceException{
        try{
            taskDao.addToCoordinated(tasksId, schedulesId, day, startDate, endDate);
        }catch (NumberFormatException ex){
            throw new ServiceException("Must use integers!");
        }catch (IllegalArgumentException ex){
            throw new ServiceException(ex.getMessage());
        }
    }

    private int[] parseScheduleIds(String[] scheduleIds) throws ServiceException{
        try{
            int[] ids = new int[scheduleIds.length];
            for (int i = 0; i < scheduleIds.length ; i++) {
                String scheduleId = scheduleIds[i];
                ids[i] = Integer.parseInt(scheduleId);
            }
            return ids;
        }catch (NumberFormatException ex){
            throw new ServiceException("Schedule ids must be integers");
        }
    }
}
