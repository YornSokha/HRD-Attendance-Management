package com.hrd.somchbab.helper;

public class Filter {
    private Integer academicId;
    private Integer generationId;
    private Integer courseId;
    private Integer classroomId;

    public Filter() {

    }

    public Filter(Integer academicId, Integer generationId, Integer courseId, Integer classroomId) {
        this.academicId = academicId;
        this.generationId = generationId;
        this.courseId = courseId;
        this.classroomId = classroomId;
    }

    public Integer getAcademicId() {
        return academicId;
    }

    public void setAcademicId(Integer academicId) {
        this.academicId = academicId;
    }

    public Integer getGenerationId() {
        return generationId;
    }

    public void setGenerationId(Integer generationId) {
        this.generationId = generationId;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Integer getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(Integer classroomId) {
        this.classroomId = classroomId;
    }

    @Override
    public String toString() {
        return "Filter{" +
                "academicId=" + academicId +
                ", generationId=" + generationId +
                ", courseId=" + courseId +
                ", classroomId=" + classroomId +
                '}';
    }
}
