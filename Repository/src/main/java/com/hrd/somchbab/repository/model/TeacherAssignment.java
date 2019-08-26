package com.hrd.somchbab.repository.model;

public class TeacherAssignment {
    private int id;
    private User user;
    private Classroom classroom;
    private boolean classTeacher;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    public boolean isClassTeacher() {
        return classTeacher;
    }

    public void setClassTeacher(boolean classTeacher) {
        this.classTeacher = classTeacher;
    }

    @Override
    public String toString() {
        return "TeacherAssignment{" +
                "id=" + id +
                ", user=" + user +
                ", classroom=" + classroom +
                ", classTeacher=" + classTeacher +
                '}';
    }
}
