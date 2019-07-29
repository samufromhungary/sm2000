package com.codecool.web.service.simple;

import com.codecool.web.dao.CoordinatedDao;
import com.codecool.web.model.Coordinated;
import com.codecool.web.service.CoordinatedService;
import com.codecool.web.service.exception.ServiceException;

import java.sql.SQLException;

public final class SimpleCoordinatedService implements CoordinatedService {
    private final CoordinatedDao coordinatedDao;

    public SimpleCoordinatedService(CoordinatedDao coordinatedDao) {
        this.coordinatedDao = coordinatedDao;
    }

    @Override
    public Coordinated getCoordinated(int tasksId) throws SQLException, ServiceException {
        try {
            return coordinatedDao.findByTasksId(tasksId);
        } catch (NumberFormatException ex) {
            throw new ServiceException("Task ID must be an Int");
        } catch (IllegalArgumentException ex) {
            throw new ServiceException(ex.getMessage());
        }
    }

}
