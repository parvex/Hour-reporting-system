package com.pik.application.dto;

import java.util.Date;

public class WRepUsrProj {

    private Long workReportId;
    private Date date;
    private int hours;
    private Long userId;
    private String userName;
    private String userSurName;
    private Long projectId;
    private String projectName;
    private String comment;
    private Boolean accepted;

    public WRepUsrProj(Long workReportId, Date date, int hours, Long userId, String userName, String userSurName, Long projectId, String projectName, String coment, Boolean accepted) {
        this.workReportId = workReportId;
        this.date = date;
        this.hours = hours;
        this.userId = userId;
        this.userName = userName;
        this.userSurName = userSurName;
        this.projectId = projectId;
        this.projectName = projectName;
        this.comment = coment;
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

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
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

    public String getUserSurName() {
        return userSurName;
    }

    public void setUserSurName(String userSurName) {
        this.userSurName = userSurName;
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

    public String getComent() {
        return comment;
    }

    public void setComent(String coment) {
        this.comment = coment;
    }

    public Boolean getAccepted() {
        return accepted;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }
}
