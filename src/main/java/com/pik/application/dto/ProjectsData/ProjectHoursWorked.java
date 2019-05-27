package com.pik.application.dto.ProjectsData;

public class ProjectHoursWorked {
    private String projectName;
    private int hours;

    public ProjectHoursWorked(String projectName, int hours) {
        this.projectName = projectName;
        this.hours = hours;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public void addHours(int hours) {this.hours += hours;}
}
