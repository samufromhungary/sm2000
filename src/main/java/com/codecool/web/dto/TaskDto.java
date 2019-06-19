package com.codecool.web.dto;

import com.codecool.web.model.Schedule;
import com.codecool.web.model.Task;

import java.util.List;

public final class TaskDto {

    private final Task task;
    private final List<Task> tasks;
    private final Schedule schedule;
    private final List<Schedule> schedules;


    public TaskDto(Task task, List<Task> tasks, Schedule schedule, List<Schedule> schedules){
        this.task = task;
        this.tasks = tasks;
        this.schedule = schedule;
        this.schedules = schedules;
    }

    public Task getTask() {
        return task;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }
}
