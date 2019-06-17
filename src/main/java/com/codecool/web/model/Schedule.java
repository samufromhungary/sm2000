package com.codecool.web.model;

import java.util.Objects;

public final class Schedule extends AbstractModel {

    private String title;
    private int days;

    public Schedule(int id, String title, int days) {
        super(id);
        this.title = title;
        this.days = days;
    }

    public String getTitle() {
        return title;
    }

    public int getDays() {
        return days;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Schedule schedule = (Schedule) o;
        return Objects.equals(title, schedule.title) &&
            Objects.equals(days, schedule.days);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), title, days);
    }
}

