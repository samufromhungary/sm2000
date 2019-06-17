package com.codecool.web.model;

import java.util.Objects;

public final class Schedule extends AbstractModel {

    private String title;
    private int days;
    private int accounts_id;

    public Schedule(int id, String title, int days, int accounts_id) {
        super(id);
        this.days = days;
        this.accounts_id = accounts_id;
    }

    public String getTitle() {
        return title;
    }

    public int getDays() {
        return days;
    }

    public int getAccounts_id() {
        return accounts_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Schedule schedule = (Schedule) o;
        return Objects.equals(title, schedule.title) &&
            Objects.equals(days, schedule.days) &&
            Objects.equals(accounts_id, schedule.accounts_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), title, days, accounts_id);
    }
}

