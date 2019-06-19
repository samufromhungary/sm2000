package com.codecool.web.service;

import com.codecool.web.model.Task;
import com.codecool.web.service.exception.ServiceException;

import java.sql.SQLException;
import java.util.List;

public interface TaskService {

    List<Task> getTasks() throws SQLException;

    Task getTask(String title) throws SQLException, ServiceException;

    Task addTask(String title, String description) throws SQLException, ServiceException;
}

