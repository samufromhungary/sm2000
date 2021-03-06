package com.codecool.web.servlet;

import com.codecool.web.dao.ScheduleDao;
import com.codecool.web.dao.TaskDao;
import com.codecool.web.dao.database.DatabaseScheduleDao;
import com.codecool.web.dao.database.DatabaseTaskDao;
import com.codecool.web.model.Account;
import com.codecool.web.model.Schedule;
import com.codecool.web.model.Task;
import com.codecool.web.service.LogService;
import com.codecool.web.service.TaskService;
import com.codecool.web.service.exception.ServiceException;
import com.codecool.web.service.simple.SimpleLogService;
import com.codecool.web.service.simple.SimpleTaskService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/protected/tasks")
public final class TasksServlet extends AbstractServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            TaskDao taskDao = new DatabaseTaskDao(connection);
            ScheduleDao scheduleDao = new DatabaseScheduleDao(connection);
            TaskService taskService = new SimpleTaskService(taskDao, scheduleDao);
            Account account = (Account) req.getSession().getAttribute("account");
            LogService logService = new SimpleLogService();
            int accounts_id = account.getId();

            if(account.getPermission().equalsIgnoreCase("admin")){
                List<Task> tasks = taskService.getTasks();
                sendMessage(resp, HttpServletResponse.SC_OK, tasks);
            }else{
                List<Task> tasks = taskService.getTasksById(accounts_id);
                sendMessage(resp, HttpServletResponse.SC_OK, tasks);
            }

            logService.log(account.getUsername() + " opened his/her tasks");
        } catch (SQLException ex) {
            handleSqlError(resp, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            TaskDao taskDao = new DatabaseTaskDao(connection);
            ScheduleDao scheduleDao = new DatabaseScheduleDao(connection);
            TaskService taskService = new SimpleTaskService(taskDao, scheduleDao);
            Account account = (Account) req.getSession().getAttribute("account");
            LogService logService = new SimpleLogService();
            int accounts_id = account.getId();


            String title = req.getParameter("title");
            String description = req.getParameter("description");

            Task task = taskService.addTask(title,accounts_id,description);

            sendMessage(resp, HttpServletResponse.SC_OK, task);
            logService.log(account.getUsername() + " created a task under the title: " + title);
        } catch (ServiceException ex) {
            sendMessage(resp, HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
        } catch (SQLException ex) {
            handleSqlError(resp, ex);
        }
    }
}
