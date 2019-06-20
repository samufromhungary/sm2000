package com.codecool.web.servlet;

import com.codecool.web.dao.ScheduleDao;
import com.codecool.web.dao.database.DatabaseScheduleDao;
import com.codecool.web.service.ScheduleService;
import com.codecool.web.service.exception.ServiceException;
import com.codecool.web.service.simple.SimpleScheduleService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/protected/modifySchedule")
public final class SchedulesModifyServlet extends AbstractServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try(Connection connection = getConnection(req.getServletContext())){
            ScheduleDao scheduleDao = new DatabaseScheduleDao(connection);
            ScheduleService scheduleService = new SimpleScheduleService(scheduleDao);

            String title = req.getParameter("title");
            String newTitle = req.getParameter("newTitle");
            int days = Integer.valueOf(req.getParameter("days"));

            if (!"".equals(days)){
                scheduleService.modifyDays(title,days);
            }
            if(!"".equals(newTitle)){
                scheduleService.modifyTitle(title,newTitle);
            }
            sendMessage(resp, HttpServletResponse.SC_OK, "Schedule edited");
        }catch (ServiceException ex){
            sendMessage(resp, HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
        }catch (SQLException ex){
            handleSqlError(resp, ex);
        }
    }
}
