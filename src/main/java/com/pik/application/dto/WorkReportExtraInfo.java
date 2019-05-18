package com.pik.application.dto;

import java.util.Date;

public class WorkReportExtraInfo {

    private Long workReportId;
    private Date date;
    private int hours;
    private Long userId;
    private String userName;
    private String userSurname;
    private Long projectId;
    private String projectName;
    private String comment;
    private Boolean accepted;

    public WorkReportExtraInfo(Long workReportId, Date date, int hours, Long userId, String userName, String userSurname, Long projectId, String projectName, String comment, Boolean accepted) {
        this.workReportId = workReportId;
        this.date = date;
        this.hours = hours;
        this.userId = userId;
        this.userName = userName;
        this.userSurname = userSurname;
        this.projectId = projectId;
        this.projectName = projectName;
        this.comment = comment;
        this.accepted = accepted;
    }

    public Long getWorkReportId() {
        return workReportId;
    }

    public void setWorkReportId(Long workReportId) {
        this.workReportId = workReportId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getHoursNumber() {
        return hours;
    }

    public void setHoursNumber(int hours) {
        this.hours = hours;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserSurname() {
        return userSurname;
    }

    public void setUserSurname(String userSurName) {
        this.userSurname = userSurName;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String coment) {
        this.comment = coment;
    }

    public Boolean getAccepted() {
        return accepted;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }
}
