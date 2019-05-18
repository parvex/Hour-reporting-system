package com.pik.application.dto.WorkReportData;

import java.util.Date;

public class WorkReportExtraInfo {

    private Long workReportId;
    private Boolean editable;
    private Date date;
    private Integer hoursNumber;
    private Long employeeId;
    private String employeeName;
    private String employeeSurname;
    private Long projectId;
    private String projectName;
    private String comment;
    private Boolean accepted;

    public WorkReportExtraInfo(Long workReportId, Date date, int hoursNumber, Long employeeId, String employeeName, String employeeSurname, Long projectId, String projectName, String comment, Boolean accepted) {
        this.workReportId = workReportId;
        this.date = date;
        this.hoursNumber = hoursNumber;
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.employeeSurname = employeeSurname;
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
        return hoursNumber;
    }

    public void setHoursNumber(int hoursNumber) {
        this.hoursNumber = hoursNumber;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeSurname() {
        return employeeSurname;
    }

    public void setEmployeeSurname(String employeeSurname) {
        this.employeeSurname = employeeSurname;
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

    public Boolean getAccepted() {
        return accepted;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }

    public Boolean getEditable() {
        return editable;
    }

    public void setEditable(Boolean editable) {
        this.editable = editable;
    }
}
