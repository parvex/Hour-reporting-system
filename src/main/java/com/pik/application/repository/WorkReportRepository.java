package com.pik.application.repository;

import com.pik.application.dto.ProjectsData.UsedLeave;
import com.pik.application.dto.WorkReportData.IdEmployeeNameDateHoursComment;
import com.pik.application.dto.WorkReportData.WorkReportExtraInfo;
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

    @Query("SELECT new com.pik.application.dto.WorkReportData.WorkReportExtraInfo(w.id, w.date, w.hours, w.user.id, w.user.name, w.user.surname, w.project.id, w.project.name, w.comment, w.accepted)" +
            " FROM WorkReport w WHERE w.id = :id")
    WorkReportExtraInfo findByIdInfo(Long id);

    @Query("SELECT new com.pik.application.dto.WorkReportData.WorkReportExtraInfo(w.id, w.date, w.hours, w.user.id, w.user.name, w.user.surname, w.project.id, w.project.name, w.comment, w.accepted) " +
            "FROM WorkReport w WHERE ((:dateFrom IS NULL AND :dateTo IS NULL) OR (:dateTo IS NULL AND w.date >= :dateFrom) OR (:dateFrom IS NULL AND w.date <= :dateTo) " +
            "OR (w.date BETWEEN :dateFrom AND :dateTo)) AND (w.user.id IN (:employeeIds) OR (-1 IN (:employeeIds)) OR COALESCE(:employeeIds, NULL) IS NULL) " +
            "AND (w.project.id IN (:projectIds) OR (-1 IN (:projectIds)) OR COALESCE(:projectIds, NULL) IS NULL) " +
            "AND (w.user.supervisor.id = :loggedId OR w.user.id = :loggedId OR :loggedId = 1811) ORDER BY w.date DESC")
    List<WorkReportExtraInfo> findByDateBetweenOrderByDateAsc(@Nullable Date dateFrom, @Nullable Date dateTo, @Nullable List<Long> employeeIds, @Nullable List<Long> projectIds, Long loggedId);

    @Query("SELECT new com.pik.application.dto.WorkReportData.IdEmployeeNameDateHoursComment(w.id, CONCAT(w.user.name,' ',w.user.surname), w.date, w.hours, w.comment) " +
            "FROM WorkReport w WHERE (w.project.id = :projectId) AND (w.accepted = :state) AND (w.user.supervisor.id = :loggedId OR :loggedId=1811)")
    List<IdEmployeeNameDateHoursComment> findWorkReportsByState(Long projectId, Boolean state, Long loggedId, Pageable page);

    @Query("SELECT COUNT(w) FROM WorkReport w WHERE w.project.id = :id AND w.accepted = false AND (w.user.supervisor.id = :loggedId OR :loggedId = 1811)")
    Integer checkNewReports(Long id, Long loggedId);

    @Query("select new com.pik.application.domain.WorkReport(w.date, w.reportedAt, w.hours, w.accepted, w.user, w.project, w.comment)" +
            " from WorkReport w where w.user.id = :userId and w.project.name <> 'Employee Leave' order by w.project.name desc")
    List<WorkReport> findWorkReportsForUser(Long userId);

    @Query("select new com.pik.application.domain.WorkReport(w.date, w.reportedAt, w.hours, w.accepted, w.user, w.project, w.comment)" +
            "from WorkReport w where w.user.id = :userId and w.project.name = 'Employee Leave' ")
    List<WorkReport> findUsedLeaveForUser(Long userId);
}
