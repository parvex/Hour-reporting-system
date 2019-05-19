package com.pik.application.dto;

import java.util.List;

public class LongStringStringBooleanListLong {

    private Long projectId;
    private String name;
    private String description;
    private Boolean keepEmployees;
    private List<Long> list;

    public LongStringStringBooleanListLong(Long projectId, String name, String description, Boolean keepEmployees, List<Long> list) {
        this.projectId = projectId;
        this.name = name;
        this.description = description;
        this.keepEmployees = keepEmployees;
        this.list = list;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getKeepEmployees() {
        return keepEmployees;
    }

    public void setKeepEmployees(Boolean keepEmployees) {
        this.keepEmployees = keepEmployees;
    }

    public List<Long> getList() {
        return list;
    }

    public void setList(List<Long> list) {
        this.list = list;
    }
}