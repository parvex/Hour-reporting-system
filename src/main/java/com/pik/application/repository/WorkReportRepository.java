package com.pik.application.repository;

import com.pik.application.domain.Project;
import com.pik.application.domain.WorkReport;
import org.hibernate.jdbc.Work;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface WorkReportRepository extends JpaRepository<WorkReport, Long> {
    @Query("SELECT r FROM WorkReport r")
    List<WorkReport> getLimited(Pageable page);

    default Optional<WorkReport> getFirst() {
        return getLimited(PageRequest.of(0,1))
                .stream()
                .findFirst();
    }

    WorkReport findOneByComment(String comment);

    @Query("SELECT w.date, w.hours, w.user.id, w.user.name, w.user.surname, w.comment, w.accepted," +
            " w.project.id, w.project.name" +
            " FROM WorkReport w WHERE w.date BETWEEN :dateFrom AND :dateTo AND" +
            " w.user.id in :employeesId AND w.project.id in :projectsId AND w.user.supervisor.username = :supervisorName ORDER BY w.date DESC")
    List<WorkReport> findByDateBetweenOrderByDateAsc(Date dateFrom, Date dateTo,
                                                     @Nullable List<Long> employeesId, @Nullable List<Long> projectsId,
                                                     String supervisorName);


}
