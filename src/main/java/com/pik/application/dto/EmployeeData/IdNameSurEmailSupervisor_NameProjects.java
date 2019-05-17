package com.pik.application.dto.EmployeeData;

import com.pik.application.dto.LongString;

import java.util.List;

public class IdNameSurEmailSupervisor_NameProjects {

    private Long id;
    private String name;
    private String surname;
    private String email;
    private String supervisorFullname;
    private List<LongString> projects;

    public IdNameSurEmailSupervisor_NameProjects(Long id, String name, String surname, String email, String supervisorFullname) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.supervisorFullname = supervisorFullname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSupervisorFullname() {
        return supervisorFullname;
    }

    public void setSupervisorFullname(String supervisorFullname) {
        this.supervisorFullname = supervisorFullname;
    }

    public List<LongString> getProjects() {
        return projects;
    }

    public void setProjects(List<LongString> projects) {
        this.projects = projects;
    }
}
