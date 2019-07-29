package com.codecool.web.dao.database;

import com.codecool.web.dao.CoordinatedDao;
import com.codecool.web.model.Coordinated;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class DataBaseCoordinatedDao extends AbstractDao implements CoordinatedDao {


    public DataBaseCoordinatedDao(Connection connection) {
        super(connection);
    }

    @Override
    public Coordinated findByTasksId(int tasksId) throws SQLException {
        if(tasksId == 0 || "".equals(tasksId) ) {
            throw new IllegalArgumentException("Task ID cannot be null or empty");
        }
        String sql = "SELECT tasks_id, schedules_id, day, start_date, end_date FROM coordinated WHERE tasks_id = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, tasksId);
            try(ResultSet resultSet = statement.executeQuery()){
                if (resultSet.next()){
                    return fetchCoordinated(resultSet);
                }
            }
        }
        return null;
    }

    private Coordinated fetchCoordinated(ResultSet resultSet)throws SQLException{
        int id = resultSet.getInt("id");
        int tasksId = resultSet.getInt("tasks_id");
        int schedulesId = resultSet.getInt("schedules_id");
        int day = resultSet.getInt("day");
        int startDate = resultSet.getInt("start_date");
        int endDate = resultSet.getInt("end_date");
        return new Coordinated(id,tasksId,schedulesId,day,startDate,endDate);
    }
}
