package com.pik.application.dto;

public class IdName {

    public Long id;
    public String name;
    public Boolean accepted;

    public IdName(Long id, String name, Boolean accepted) {
        this.id = id;
        this.name = name;
        this.accepted = accepted;
    }
}
