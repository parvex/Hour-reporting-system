package com.pik.application.repository;

import com.pik.application.dto.WRepDate;
import com.pik.application.dto.WRepUsrProj;
import com.pik.application.domain.WorkReport;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface WorkReportRepository extends JpaRepository<WorkReport, Long> {

    WorkReport findOneByComment(String comment);

    @Query("SELECT r FROM WorkReport r")
    List<WorkReport> getLimited(Pageable page);

    default Optional<WorkReport> getFirst() {
        return getLimited(PageRequest.of(0,1))
                .stream()
                .findFirst();
    }

    @Query("SELECT new com.pik.application.dto.WRepUsrProj(w.id, w.date, w.hours, w.user.id, w.user.name," +
            " w.user.surname, w.project.id, w.project.name, w.comment, w.accepted) " +
            "FROM WorkReport w WHERE w.id = :id")
    WRepUsrProj findByIdInfo(Long id);

    @Query("SELECT new com.pik.application.dto.WRepDate(w.date, w.hours, w.user.id, w.user.name, w.user.surname, w.comment, w.accepted," +
            " w.project.id, w.project.name) FROM WorkReport w WHERE w.date BETWEEN :dateFrom AND :dateTo AND (w.user.id IN (:employeeIds) " +
            " OR COALESCE(:employeeIds, NULL) IS NULL) AND (w.project.id IN (:projectIds) OR COALESCE(:projectIds, NULL) IS NULL)" +
            " AND w.user.supervisor.id = :loggedId OR w.user.id = :loggedId ORDER BY w.date DESC")
    List<WRepDate> findByDateBetweenOrderByDateAsc(Date dateFrom, Date dateTo, @Nullable List<Long> employeeIds,
                                                     @Nullable List<Long> projectIds, Long loggedId);

}
