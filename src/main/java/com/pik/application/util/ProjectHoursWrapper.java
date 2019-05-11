package com.pik.application.util;

import com.pik.application.domain.Project;

public class ProjectHoursWrapper {
    private String name;
    private int hours;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
