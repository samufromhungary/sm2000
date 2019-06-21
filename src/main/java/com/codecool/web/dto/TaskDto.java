package com.codecool.web.dto;

import com.codecool.web.model.Schedule;
import com.codecool.web.model.Task;

import java.util.List;

public final class TaskDto {

    private final Task task;
    private final List<Schedule> schedules;
    private final List<Schedule> taskSchedules;


    public TaskDto(Task task, List<Schedule> schedules, List<Schedule> taskSchedules){
        this.task = task;
        this.schedules = schedules;
        this.taskSchedules = taskSchedules;
    }

    public Task getTask() {
        return task;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public List<Schedule> getTaskSchedules() {
        return taskSchedules;
    }
}
