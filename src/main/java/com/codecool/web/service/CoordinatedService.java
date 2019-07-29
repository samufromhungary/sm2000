package com.codecool.web.service;

import com.codecool.web.model.Coordinated;
import com.codecool.web.service.exception.ServiceException;

import java.sql.SQLException;

public interface CoordinatedService {
    Coordinated getCoordinated(int tasksId) throws SQLException, ServiceException;

}
