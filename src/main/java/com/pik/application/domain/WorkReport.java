package com.pik.application.domain;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class WorkReport
{
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private Date date;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date reportedAt;

    @NotNull
    private int hours;

    @Column(name = "accepted", nullable = false, columnDefinition = "boolean default false")
    private boolean accepted;

    @OneToOne
    private User user;

    private String comment;


}
