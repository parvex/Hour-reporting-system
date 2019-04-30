package com.pik.application.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Table(name = "project")
public class Project
{
    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    private String name;
    
    private String description;

    public void setName(String name) {this.name = name;}

    public String getName() {return name;}

    public void setDescription(String description) {this.description = description;}

    public String getDescription() {return description;}
}
