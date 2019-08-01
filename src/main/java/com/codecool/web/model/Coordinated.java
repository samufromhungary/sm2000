package com.codecool.web.model;

import java.util.Objects;

public final class Coordinated{
    private int taskId;
    private int scheduleId;
    private int day;
    private int start_date;
    private int end_date;

    public Coordinated(int taskId,int scheduleId,int day, int start_date,int end_date) {
        this.taskId = taskId;
        this.scheduleId = scheduleId;
        this.day = day;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public int getTaskId() {
        return taskId;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public int getDay() {
        return day;
    }

    public int getStart_date() {
        return start_date;
    }

    public int getEnd_date() {
        return end_date;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Coordinated coordinated = (Coordinated) o;
        return Objects.equals(taskId, coordinated.taskId) &&
                Objects.equals(scheduleId, coordinated.scheduleId)&&
                Objects.equals(day, coordinated.day)&&
                Objects.equals(start_date, coordinated.start_date)&&
                Objects.equals(end_date, coordinated.end_date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), taskId, scheduleId,day,start_date,end_date);
    }
}


