package com.pik.application.dto.EmployeeData;

import com.pik.application.dto.LongString;
import java.util.List;

public class IdUserNameSurEmailProjectsSupervisorRoles {

    private Long id;
    private String username;
    private String name;
    private String surname;
    private String email;
    private List<LongString> projects;
    private Long supervisorId;
    private String supervisorFullname;
    private List<LongString> roles;

    public IdUserNameSurEmailProjectsSupervisorRoles(Long id, String username, String name, String surname, String email, Long supervisorId, String supervisorFullname) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.projects = projects;
        this.supervisorId = supervisorId;
        this.supervisorFullname = supervisorFullname;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public List<LongString> getProjects() {
        return projects;
    }

    public void setProjects(List<LongString> projects) {
        this.projects = projects;
    }

    public Long getSupervisorId() {
        return supervisorId;
    }

    public void setSupervisorId(Long supervisorId) {
        this.supervisorId = supervisorId;
    }

    public String getSupervisorFullname() {
        return supervisorFullname;
    }

    public void setSupervisorFullname(String supervisorFullname) {
        this.supervisorFullname = supervisorFullname;
    }

    public List<LongString> getRoles() {
        return roles;
    }

    public void setRoles(List<LongString> roles) {
        this.roles = roles;
    }
}
