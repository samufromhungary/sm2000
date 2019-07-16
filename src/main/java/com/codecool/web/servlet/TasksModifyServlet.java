package com.codecool.web.servlet;

import com.codecool.web.dao.ScheduleDao;
import com.codecool.web.dao.TaskDao;
import com.codecool.web.dao.database.DatabaseScheduleDao;
import com.codecool.web.dao.database.DatabaseTaskDao;
import com.codecool.web.model.Account;
import com.codecool.web.service.LogService;
import com.codecool.web.service.TaskService;
import com.codecool.web.service.exception.ServiceException;
import com.codecool.web.service.simple.SimpleLogService;
import com.codecool.web.service.simple.SimpleTaskService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/protected/modifyTask")
public class TasksModifyServlet extends AbstractServlet {


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try(Connection connection = getConnection(req.getServletContext())){
            TaskDao taskDao = new DatabaseTaskDao(connection);
            ScheduleDao scheduleDao = new DatabaseScheduleDao(connection);
            TaskService taskService = new SimpleTaskService(taskDao, scheduleDao);
            Account account = (Account) req.getSession().getAttribute("account");
            LogService logService = new SimpleLogService();

            String title = req.getParameter("title");
            String newTitle = req.getParameter("newTitle");
            String description = req.getParameter("description");

            if (!"".equals(description)){
                taskService.modifyDescription(title,description);
                logService.log("Edited description of schedule: " + title + " to: " + description + " by user: " + account.getUsername());
            }
            if(!"".equals(newTitle)){
                taskService.modifyTitle(title,newTitle);
                logService.log("Edited title of schedule: " + title + " to: " + newTitle + " by user: " + account.getUsername());
            }
            sendMessage(resp, HttpServletResponse.SC_OK, "Task edited");
        }catch (ServiceException ex){
            sendMessage(resp, HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
        }catch (SQLException ex){
            handleSqlError(resp, ex);
        }
    }
}
