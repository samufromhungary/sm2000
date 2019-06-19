package com.codecool.web.service.simple;

import com.codecool.web.dao.TaskDao;
import com.codecool.web.model.Task;
import com.codecool.web.service.TaskService;
import com.codecool.web.service.exception.ServiceException;

import java.sql.SQLException;
import java.util.List;

public final class SimpleTaskService implements TaskService {

    private final TaskDao taskDao;

    public SimpleTaskService(TaskDao taskDao) {
        this.taskDao = taskDao;
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
}
