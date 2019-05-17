package com.pik.application.dto;

public class LongStringBool {

    private Long id;
    private String name;
    private Boolean accepted;

    public LongStringBool(Long id, String name, Boolean accepted) {
        this.id = id;
        this.name = name;
        this.accepted = accepted;
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

    public Boolean getAccepted() {
        return accepted;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }
}
