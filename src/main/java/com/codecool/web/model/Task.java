package com.codecool.web.model;

import java.util.Objects;

public final class Task extends AbstractModel {

    private String title;
    private int accounts_id;
    private String content;

    public Task(int id, String title, int accounts_id, String content) {
        super(id);
        this.title = title;
        this.accounts_id = accounts_id;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public int getAccounts_id() {
        return accounts_id;
    }

    public String getContent() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Task task = (Task) o;
        return Objects.equals(title, task.title) &&
            Objects.equals(accounts_id, task.accounts_id) &&
            Objects.equals(content, task.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), title, accounts_id, content);
    }
}
