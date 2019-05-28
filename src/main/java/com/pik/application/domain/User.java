package com.pik.application.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.NaturalId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "user_tbl")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "name")
    private String name;

    @NotBlank
    @Column(name = "username", unique = true)
    private String username;

    @NotBlank
    @Column(name = "surname")
    private String surname;

    @NotBlank
    @NaturalId
    @Column(name = "email")
    private String email;

    @NotBlank
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "password")
    private String password;

    @ElementCollection
    private List<String> roles = new ArrayList<>();

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    @Column(name = "registered_at")
    private Date registeredAt;

    @OneToOne
    @JoinColumn(name="supervisor_id")
    private User supervisor;

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Project> projects = new HashSet<>();

    public Set<Project> getProjects() { return projects; }

    public void setProjects(Set<Project> projects) { this.projects = projects;}

    public void removeProjectByName(String name){
        Project tmp = null;

        for(Project project: projects)
        {
            if(project.getName().equals(name))
            {
                tmp = project;
            }
        }

        projects.remove(tmp);
    }

    public void addProject(Project project) { projects.add(project); }

    public User getSupervisor() { return supervisor; }

    public void setSupervisor(User supervisor) { this.supervisor = supervisor; }

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

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                ", registeredAt=" + registeredAt +
                ", supervisor=" + supervisor +
                ", projects=" + projects +
                '}';
    }
}
