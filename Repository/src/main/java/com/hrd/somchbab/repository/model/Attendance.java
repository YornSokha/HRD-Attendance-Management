package com.hrd.somchbab.repository.model;


import java.sql.Timestamp;

public class Attendance {
    private int id;
    private Classenroll classenroll;
    private String dateFrom;
    private String dateTo;
//    @NotNull
    private String leaveStatus;
    private Character teacherResponseStatus;
    private String adminResponseStatus;
    private String leaveTime;
    private String arriveTime;
    private int duration;
    private String amPm;
    private int permissionCount;
    private String reason;
    private boolean deleted;
    private String status;
    private Timestamp createdAt;

    public Attendance() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Classenroll getClassenroll() {
        return classenroll;
    }

    public void setClassenroll(Classenroll classenroll) {
        this.classenroll = classenroll;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    public String getLeaveStatus() {
        return leaveStatus;
    }

    public void setLeaveStatus(String leaveStatus) {
        this.leaveStatus = leaveStatus;
    }

    public Character getTeacherResponseStatus() {
        return teacherResponseStatus;
    }

    public void setTeacherResponseStatus(Character teacherResponseStatus) {
        this.teacherResponseStatus = teacherResponseStatus;
    }

    public String getAdminResponseStatus() {
        return adminResponseStatus;
    }

    public void setAdminResponseStatus(String adminResponseStatus) {
        this.adminResponseStatus = adminResponseStatus;
    }

    public String getLeaveTime() {
        return leaveTime;
    }

    public void setLeaveTime(String leaveTime) {
        this.leaveTime = leaveTime;
    }

    public String getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(String arriveTime) {
        this.arriveTime = arriveTime;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getAmPm() {
        return amPm;
    }

    public void setAmPm(String amPm) {
        this.amPm = amPm;
    }

    public int getPermissionCount() {
        return permissionCount;
    }

    public void setPermissionCount(int permissionCount) {
        this.permissionCount = permissionCount;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Attendance{" +
                "id=" + id +
                ", classenroll=" + classenroll +
                ", dateFrom='" + dateFrom + '\'' +
                ", dateTo='" + dateTo + '\'' +
                ", leaveStatus='" + leaveStatus + '\'' +
                ", teacherResponseStatus='" + teacherResponseStatus + '\'' +
                ", adminResponseStatus='" + adminResponseStatus + '\'' +
                ", leaveTime='" + leaveTime + '\'' +
                ", arriveTime='" + arriveTime + '\'' +
                ", duration=" + duration +
                ", amPm='" + amPm + '\'' +
                ", permissionCount=" + permissionCount +
                ", reason='" + reason + '\'' +
                ", deleted=" + deleted +
                ", status='" + status + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
