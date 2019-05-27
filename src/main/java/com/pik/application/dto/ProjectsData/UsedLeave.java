package com.pik.application.dto.ProjectsData;

public class UsedLeave {
    private int days;

    public UsedLeave(){
        this.days = 0;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public void addDay(){
        this.days++;
    }
}
