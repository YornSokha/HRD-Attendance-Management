package com.hrd.somchbab.repository.model;

import javax.validation.constraints.NotEmpty;

public class Course {
    private int id;
    @NotEmpty
    private String name;
    private Generation generation;
    private boolean status;
    private boolean deleted;

    public Course() {
    }

    public Course(int id, String name, Generation generation, boolean status, boolean deleted) {
        this.id = id;
        this.name = name;
        this.generation = generation;
        this.status = status;
        this.deleted = deleted;
    }

    public Course(Generation generation) {
        this.generation = generation;
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

    public Generation getGeneration() {
        return generation;
    }

    public void setGeneration(Generation generation) {
        this.generation = generation;
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
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", generation=" + generation +
                ", status=" + status +
                ", deleted=" + deleted +
                '}';
    }
}
