package com.pik.application.dto;

import java.util.Date;

public class WRepNew {

    private Date date;
    private Integer hoursNumber;
    private Long projectId;
    private String projectName;
    private String comment;

    public WRepNew(Date date, Integer hoursNumber, Long projectId, String projectName, String comment) {
        this.date = date;
        this.hoursNumber = hoursNumber;
        this.projectId = projectId;
        this.projectName = projectName;
        this.comment = comment;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getHoursNumber() {
        return hoursNumber;
    }

    public void setHoursNumber(Integer hoursNumber) {
        this.hoursNumber = hoursNumber;
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

    public void setComment(String comment) {
        this.comment = comment;
    }
}
