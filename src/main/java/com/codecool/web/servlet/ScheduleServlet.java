package com.codecool.web.servlet;

import com.codecool.web.dao.CoordinatedDao;
import com.codecool.web.dao.ScheduleDao;
import com.codecool.web.dao.TaskDao;
import com.codecool.web.dao.database.DataBaseCoordinatedDao;
import com.codecool.web.dao.database.DatabaseScheduleDao;
import com.codecool.web.dao.database.DatabaseTaskDao;
import com.codecool.web.model.Account;
import com.codecool.web.model.Coordinated;
import com.codecool.web.model.Schedule;
import com.codecool.web.model.Task;
import com.codecool.web.service.CoordinatedService;
import com.codecool.web.service.LogService;
import com.codecool.web.service.ScheduleService;
import com.codecool.web.service.TaskService;
import com.codecool.web.service.exception.ServiceException;
import com.codecool.web.service.simple.SimpleCoordinatedService;
import com.codecool.web.service.simple.SimpleLogService;
import com.codecool.web.service.simple.SimpleScheduleService;
import com.codecool.web.service.simple.SimpleTaskService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebServlet("/protected/schedule")
public final class ScheduleServlet extends AbstractServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            ScheduleDao scheduleDao = new DatabaseScheduleDao(connection);
            TaskDao taskDao = new DatabaseTaskDao(connection);
            CoordinatedDao coordinatedDao = new DataBaseCoordinatedDao(connection);
            CoordinatedService coordinatedService = new SimpleCoordinatedService(coordinatedDao);
            ScheduleService scheduleService = new SimpleScheduleService(scheduleDao);
            TaskService taskService = new SimpleTaskService(taskDao, scheduleDao);
            Account account = (Account) req.getSession().getAttribute("account");
            LogService logService = new SimpleLogService();

            int accounts_id = account.getId();

            int id = Integer.valueOf(req.getParameter("id"));

            Schedule schedule = scheduleService.getScheduleId(id);
            req.getSession().setAttribute("schedule",schedule);

            Coordinated coordinated = coordinatedService.getCoordinated(schedule.getId());

            List<Task> tasks = taskService.getTasksById(accounts_id);


            Object [] tempStorage = new Object[3];
            tempStorage[0] = schedule;
            tempStorage[1] = tasks;
            tempStorage[2] = coordinated;

            sendMessage(resp, HttpServletResponse.SC_OK, tempStorage);


            logService.log("Schedule opened: " + schedule.getTitle() + " by user: " + account.getUsername());
        } catch (SQLException ex) {
            handleSqlError(resp, ex);
        }catch (ServiceException ex) {
            sendMessage(resp, HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Account account = (Account) req.getSession().getAttribute("account");
        Schedule schedule = (Schedule) req.getSession().getAttribute("schedule");

        LogService logService = new SimpleLogService();
        try (Connection connection = getConnection(req.getServletContext())) {
            ScheduleDao scheduleDao = new DatabaseScheduleDao(connection);
            ScheduleService scheduleService = new SimpleScheduleService(scheduleDao);
            TaskDao taskDao = new DatabaseTaskDao(connection);
            TaskService taskService = new SimpleTaskService(taskDao, scheduleDao);
            int accounts_id = account.getId();


            int tasksId = Integer.valueOf(req.getParameter("tasksId"));

            int schedulesId = schedule.getId();
            int days = Integer.valueOf(req.getParameter("days"));
            int startDate = Integer.valueOf(req.getParameter("startDate"));
            int endDate = Integer.valueOf(req.getParameter("endDate"));

            taskService.addToCoordinated(tasksId,schedulesId,days,startDate,endDate);
            Coordinated coordinated = new Coordinated(tasksId,schedulesId,days,startDate,endDate);

            sendMessage(resp, HttpServletResponse.SC_OK, "Added successfully");
            logService.log(account.getUsername() + " added a task to his/her schedule");
        } catch (ServiceException ex) {
            sendMessage(resp, HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
        } catch (SQLException ex) {
            handleSqlError(resp, ex);
        }
    }
}
