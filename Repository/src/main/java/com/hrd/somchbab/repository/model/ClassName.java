package com.hrd.somchbab.repository.model;

import javax.validation.constraints.NotEmpty;

public class ClassName {
    private int id;
    @NotEmpty
    private String name;
    private boolean status;
    private boolean delete;

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

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    @Override
    public String toString() {
        return "ClassName{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", delete=" + delete +
                '}';
    }
}
