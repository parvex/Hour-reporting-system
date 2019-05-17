package com.pik.application.dto.EmployeeData;

import java.util.List;

public class EmailNameSurProjects {

    private String email;
    private String name;
    private String surname;
    private List<Long> projects;

    public EmailNameSurProjects(String email, String name, String surname, List<Long> projects) {
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.projects = projects;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public List<Long> getProjects() {
        return projects;
    }

    public void setProjects(List<Long> projects) {
        this.projects = projects;
    }
}
