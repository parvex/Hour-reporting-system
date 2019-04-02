package com.pik.application.domain;

import org.hibernate.annotations.ManyToAny;
import org.hibernate.annotations.NaturalId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String surname;

    @NotBlank
    @NaturalId
    private String email;

    @NotBlank
    private String passwordHash;


    @NotNull
    private SystemRole systemRole;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date registeredAt;

    @OneToOne
    private User supervisor;

    @ManyToMany
    private Set<Project> projects = new HashSet<Project>();

    public Set<Project> getProjects() { return projects; }

    public void setProjects(Set<Project> projects) { this.projects = projects;}

    public User getSupervisor() { return supervisor; }

    public void setSupervisor(User supervisor) { this.supervisor = supervisor; }

    public SystemRole getSystemRole() {return systemRole; }

    public void setSystemRole(SystemRole systemRole) { this.systemRole = systemRole; }

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

    public Date getRegisteredAt() {
        return registeredAt;
    }

    public void setRegisteredAt(Date registeredAt) { this.registeredAt = registeredAt; }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

}
