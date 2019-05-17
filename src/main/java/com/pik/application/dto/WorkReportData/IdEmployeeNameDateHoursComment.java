package com.pik.application.dto.WorkReportData;

import java.util.Date;

public class IdEmployeeNameDateHoursComment {

    private Long id;
    private String employeeName;
    private Date date;
    private Long hours;
    private String comment;

    public IdEmployeeNameDateHoursComment(Long id, String employeeName, Date date, Long hours, String comment) {
        this.id = id;
        this.employeeName = employeeName;
        this.date = date;
        this.hours = hours;
        this.comment = comment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getHours() {
        return hours;
    }

    public void setHours(Long hours) {
        this.hours = hours;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
