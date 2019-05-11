package com.pik.application.repository;

import com.pik.application.domain.Project;
import com.pik.application.domain.WorkReport;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

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

    // get User that added report
}
