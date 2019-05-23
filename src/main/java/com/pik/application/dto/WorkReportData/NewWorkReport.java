package com.pik.application.dto.WorkReportData;

public class NewWorkReport {

    private Long workReportId;
    private String startDate;
    private String endDate;
    private Integer hoursNumber;
    private Long projectId;
    private String projectName;
    private String comment;

    public NewWorkReport(Long workReportId, String startDate, String endDate, Integer hoursNumber, Long projectId, String projectName, String comment) {
        this.workReportId = workReportId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.hoursNumber = hoursNumber;
        this.projectId = projectId;
        this.projectName = projectName;
        this.comment = comment;
    }

    public Long getId() {
        return workReportId;
    }

    public void setId(Long workReportId) {
        this.workReportId = workReportId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
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
