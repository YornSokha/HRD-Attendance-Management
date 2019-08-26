package com.hrd.somchbab.repository.model;

public class Classroom {
    private int id;
    private boolean status;
    private boolean delete;
    private Course course;
    private ClassName className;

    public Classroom(String fullName) {
        this.className = new ClassName();
        className.setName(fullName);
    }

    @Override
    public String toString() {
        return "Classroom{" +
                "id=" + id +
                ", status=" + status +
                ", delete=" + delete +
                ", course=" + course +
                ", className=" + className +
                '}';
    }

    public Classroom() {
    }

    public Classroom(int id, boolean status, boolean delete, Course course, ClassName className) {
        this.id = id;
        this.status = status;
        this.delete = delete;
        this.course = course;
        this.className = className;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public ClassName getClassName() {
        return className;
    }

    public void setClassName(ClassName className) {
        this.className = className;
    }
}
