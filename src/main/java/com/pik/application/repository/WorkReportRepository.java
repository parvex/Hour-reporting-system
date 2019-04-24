package com.pik.application.repository;

import com.pik.application.domain.WorkReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkReportRepository extends JpaRepository<WorkReport, Long> {
}
