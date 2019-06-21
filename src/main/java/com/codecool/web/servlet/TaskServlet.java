package com.codecool.web.servlet;

import com.codecool.web.dao.ScheduleDao;
import com.codecool.web.dao.TaskDao;
import com.codecool.web.dao.database.DatabaseScheduleDao;
import com.codecool.web.dao.database.DatabaseTaskDao;
import com.codecool.web.dto.TaskDto;
import com.codecool.web.model.Schedule;
import com.codecool.web.model.Task;
import com.codecool.web.service.ScheduleService;
import com.codecool.web.service.TaskService;
import com.codecool.web.service.exception.ServiceException;
import com.codecool.web.service.simple.SimpleScheduleService;
import com.codecool.web.service.simple.SimpleTaskService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/protected/task")
public final class TaskServlet extends AbstractServlet {

    private static final String SQL_ERROR_CODE_UNIQUE_VIOLATION = "23505";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try(Connection connection = getConnection(req.getServletContext())) {
            ScheduleDao scheduleDao = new DatabaseScheduleDao(connection);
            TaskDao taskDao = new DatabaseTaskDao(connection);
            ScheduleService scheduleService = new SimpleScheduleService(scheduleDao);
            TaskService taskService = new SimpleTaskService(taskDao, scheduleDao);

            String title = req.getParameter("title");

            Task task = taskService.getTask(title);
            List<Schedule> schedules = scheduleService.getSchedules();
            List<Schedule> taskSchedules = taskService.getTaskSchedules(task.getId());

            sendMessage(resp, HttpServletResponse.SC_OK, new TaskDto(task, schedules, taskSchedules));
        }catch (ServiceException ex){
            sendMessage(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
        }catch (SQLException ex){
            handleSqlError(resp, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try(Connection connection = getConnection(req.getServletContext())){
            ScheduleDao scheduleDao = new DatabaseScheduleDao(connection);
            TaskDao taskDao = new DatabaseTaskDao(connection);
            TaskService taskService = new SimpleTaskService(taskDao, scheduleDao);

            int taskId = Integer.valueOf(req.getParameter("id"));
            String[] scheduleIds = req.getParameterValues("scheduleIds");

            taskService.addTaskToSchedules(taskId, scheduleIds);

            doGet(req, resp);
        }catch (SQLException ex){
            if (SQL_ERROR_CODE_UNIQUE_VIOLATION.equals(ex.getSQLState())){
                sendMessage(resp, HttpServletResponse.SC_BAD_REQUEST, "Task has been already added to schedule");
            }else{
                handleSqlError(resp,ex);
            }
        }catch (ServiceException ex){
            sendMessage(resp, HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
        }
    }
}
