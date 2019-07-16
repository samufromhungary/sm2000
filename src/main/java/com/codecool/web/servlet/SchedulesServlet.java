package com.codecool.web.servlet;

import com.codecool.web.dao.ScheduleDao;
import com.codecool.web.dao.database.DatabaseScheduleDao;
import com.codecool.web.model.Account;
import com.codecool.web.model.Schedule;
import com.codecool.web.service.LogService;
import com.codecool.web.service.ScheduleService;
import com.codecool.web.service.exception.ServiceException;
import com.codecool.web.service.simple.SimpleLogService;
import com.codecool.web.service.simple.SimpleScheduleService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/protected/schedules")
public final class SchedulesServlet extends AbstractServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            ScheduleDao scheduleDao = new DatabaseScheduleDao(connection);
            ScheduleService scheduleService = new SimpleScheduleService(scheduleDao);
            Account account = (Account) req.getSession().getAttribute("account");
            LogService logService = new SimpleLogService();
            int accounts_id = account.getId();

            List<Schedule> schedules = scheduleService.getSchedulesById(accounts_id);

            logService.log(account.getUsername() + " opened his/her schedules");
            sendMessage(resp, HttpServletResponse.SC_OK, schedules);
        } catch (SQLException ex) {
            handleSqlError(resp, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Account account = (Account) req.getSession().getAttribute("account");
        LogService logService = new SimpleLogService();
        try (Connection connection = getConnection(req.getServletContext())) {
            ScheduleDao scheduleDao = new DatabaseScheduleDao(connection);
            ScheduleService scheduleService = new SimpleScheduleService(scheduleDao);
            int accounts_id = account.getId();

            String title = req.getParameter("title");
            int days = Integer.valueOf(req.getParameter("days"));

            Schedule schedule = scheduleService.addSchedule(title,days, accounts_id);

            sendMessage(resp, HttpServletResponse.SC_OK, schedule);
            logService.log(account.getUsername() + " created a schedule under the title: " + title);
        } catch (ServiceException ex) {
            sendMessage(resp, HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
            logService.log(account.getUsername() + " failed to create a schedule.");
        } catch (SQLException ex) {
            handleSqlError(resp, ex);
            logService.log(account.getUsername() + " failed to create a schedule.");
        }
    }
}
