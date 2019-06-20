package com.codecool.web.service;

import com.codecool.web.model.Task;
import com.codecool.web.service.exception.ServiceException;

import java.sql.SQLException;
import java.util.List;

public interface TaskService {

    List<Task> getTasks() throws SQLException;

    List<Task> getTasksById(int accounts_Id) throws SQLException;

    Task getTask(String title) throws SQLException, ServiceException;

    Task addTask(String title, int accounts_id, String description) throws SQLException, ServiceException;

    void deleteTask(String title) throws SQLException, ServiceException;

    void modifyTitle(String oldTitle, String newTitle) throws SQLException, ServiceException;

    void modifyDescription(String title, String description) throws SQLException, ServiceException;
}

