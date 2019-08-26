package com.hrd.somchbab.repository.model;

public class Classenroll {
    private int id;
    private Classroom classroom;
    private User user;
    private boolean status;
    private boolean deleted;

    public Classenroll() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
        return "Classenroll{" +
                "id=" + id +
                ", classroom=" + classroom +
                ", user=" + user +
                ", status=" + status +
                ", deleted=" + deleted +
                '}';
    }
}
