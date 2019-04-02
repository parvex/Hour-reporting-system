package com.pik.application.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
public class Project
{
    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    private String name;
    
    private String description;
}
