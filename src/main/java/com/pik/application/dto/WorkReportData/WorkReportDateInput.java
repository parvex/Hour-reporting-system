package com.pik.application.dto.WorkReportData;

import java.util.Date;
import java.util.List;

public class WorkReportDateInput {

    private Date dateFrom;
    private Date dateTo;
    private List<Long> employeeIds;
    private List<Long> projectIds;

    public WorkReportDateInput(Date dateFrom, Date dateTo, List<Long> employeeIds, List<Long> projectIds) {
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.employeeIds = employeeIds;
        this.projectIds = projectIds;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public List<Long> getEmployeeIds() {
        return employeeIds;
    }

    public void setEmployeeIds(List<Long> employeeIds) {
        this.employeeIds = employeeIds;
    }

    public List<Long> getProjectIds() {
        return projectIds;
    }

    public void setProjectIds(List<Long> projectIds) {
        this.projectIds = projectIds;
    }
}
