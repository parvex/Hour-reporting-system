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

    @ManyToOne
    private Project project;

    private String comment;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Date getDate() { return date; }

    public void setDate(Date date) { this.date = date; }

    public Date getReportedAt() { return reportedAt; }

    public void setReportedAt(Date reportedAt) { this.reportedAt = reportedAt; }

    public int getHours() { return hours; }

    public void setHours(int hours) { this.hours = hours; }

    public boolean isAccepted() { return accepted; }

    public void setAccepted(boolean accepted) { this.accepted = accepted; }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }

    public String getComment() { return comment; }

    public void setComment(String comment) { this.comment = comment; }

    public Project getProject() { return project; }

    public void setProject(Project project) { this.project = project; }
}
