package com.pik.application.util;

import com.pik.application.domain.Project;

public class ProjectHoursWrapper {
    private Project project;
    private int hours;

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public void increaseHours(int val) {
        this.hours += val;
    }
}
