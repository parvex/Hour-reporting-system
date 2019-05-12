package com.pik.application.dto;

public class UserIdName {

    private Long id;
    private String fullName;

    public UserIdName(Long id, String name) {
        this.id = id;
        this.fullName = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return fullName;
    }

    public void setName(String name) {
        this.fullName = name;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                "fullName='" + fullName + '\'' +
                '}';
    }
}
