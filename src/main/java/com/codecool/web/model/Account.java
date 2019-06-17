package com.codecool.web.model;

import java.util.Objects;

public final class Account extends AbstractModel {

    private final String username;
    private final String email;
    private final String password;
    private final String permission;

    public Account(int id, String username, String email, String password, String permission) {
        super(id);
        this.username = username;
        this.email = email;
        this.password = password;
        this.permission = permission;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPermission() {
        return permission;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Account account = (Account) o;
        return Objects.equals(username, account.username) &&
            Objects.equals(email, account.email) &&
            Objects.equals(password, account.password) &&
            Objects.equals(permission, account.permission);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), username, email, password, permission);
    }
}
