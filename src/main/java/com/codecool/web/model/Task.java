package com.codecool.web.model;

import java.util.Objects;

public final class Task extends AbstractModel {

    private String title;
    private String description;

    public Task(int id, String title, String description) {
        super(id);
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Task task = (Task) o;
        return Objects.equals(title, task.title) &&
            Objects.equals(description, task.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), title, description);
    }
}
