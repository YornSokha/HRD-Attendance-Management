package com.hrd.somchbab.repository.model;

import javax.validation.constraints.NotEmpty;

public class Academic {
    private int id;
    @NotEmpty
    private String name;
    private boolean status;
    private boolean deleted;

    public Academic() {
    }

    public Academic(int id, @NotEmpty String name, boolean status, boolean deleted) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.deleted = deleted;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "Academic{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", deleted=" + deleted +
                '}';
    }
}
