package com.codecool.web.servlet;

import com.codecool.web.dao.TaskDao;
import com.codecool.web.dao.database.DatabaseTaskDao;
import com.codecool.web.service.TaskService;
import com.codecool.web.service.exception.ServiceException;
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
            TaskService taskService = new SimpleTaskService(taskDao);

            String title = req.getParameter("title");
            String newTitle = req.getParameter("newTitle");
            String description = req.getParameter("description");

            if (!"".equals(description)){
                taskService.modifyDescription(title,description);
            }
            if(!"".equals(newTitle)){
                taskService.modifyTitle(title,newTitle);
            }
            sendMessage(resp, HttpServletResponse.SC_OK, "Task edited");
        }catch (ServiceException ex){
            sendMessage(resp, HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
        }catch (SQLException ex){
            handleSqlError(resp, ex);
        }
    }
}
