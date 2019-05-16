package com.pik.application.service;

import com.pik.application.domain.Project;
import com.pik.application.domain.User;
import com.pik.application.domain.WorkReport;
import com.pik.application.dto.WRepDate;
import com.pik.application.dto.WRepUsrProj;
import com.pik.application.repository.WorkReportRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class WorkReportService {

    private final WorkReportRepository workReportRepository;
    private final UserService userService;
    private final ProjectService projectService;

    public WorkReportService(WorkReportRepository workReportRepository, UserService userService, ProjectService projectService) {
        this.workReportRepository = workReportRepository;
        this.userService = userService;
        this.projectService = projectService;
    }

    public List<WorkReport> findAll() {
        return workReportRepository.findAll();
    }

    public ResponseEntity<WorkReport> findById(Long id) {
        Optional<WorkReport> workReport = workReportRepository.findById(id);
        if(workReport.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else{
            return new ResponseEntity<>(workReport.get(), HttpStatus.OK);
        }
    }

    public ResponseEntity<WorkReport> createWorkReport(WorkReport workReport) {
        if(workReportRepository.findOneByComment(workReport.getComment()) != null){
            throw new RuntimeException("WorkReport name exists");
        }
        return new ResponseEntity<>(workReportRepository.save(workReport), HttpStatus.ACCEPTED);
    }

    public WorkReport updateWorkReport(WorkReport workReport) {
        if (workReportRepository.findOneByComment(workReport.getComment()) != null
                && !workReportRepository.findOneByComment(workReport.getComment()).getId().equals(workReport.getId())) {
            throw new RuntimeException("Work report already exist");
        }
        return workReportRepository.save(workReport);
    }

    public ResponseEntity<WorkReport> deleteWorkReport(Long id) {
        Optional<WorkReport> workReport = workReportRepository.findById(id);
        if(workReport.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else{
            workReportRepository.delete(workReport.get());
            return new ResponseEntity<>(workReport.get(), HttpStatus.OK);
        }
    }

    public ResponseEntity<List<WRepDate>> getWorkReportByDate(Date dateFrom, Date dateTo, List<Long> employeeIds, List<Long> projectIds) {
        Long loggedId = userService.getLoggedUser().getId();
        if(employeeIds != null && employeeIds.isEmpty()) employeeIds.add(-1L);
        if(projectIds != null && projectIds.isEmpty()) projectIds.add(-1L);

        List<WRepDate> body = workReportRepository.findByDateBetweenOrderByDateAsc(dateFrom, dateTo, employeeIds, projectIds, loggedId);
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    public ResponseEntity<WRepUsrProj> getWorkReportInfo(Long id) {
        Optional<WRepUsrProj> workReport = Optional.ofNullable(workReportRepository.findByIdInfo(id));
        if(workReport.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else{
            return new ResponseEntity<>(workReport.get(), HttpStatus.OK);
        }
    }

    public ResponseEntity<WorkReport> addNewWorkReport(Date date, Integer hours, Long projectId, String projectName, String comment) {
        User loggedUser = userService.getLoggedUser();
        Date now = new Date();
        Project project = projectService.findById(projectId);
        WorkReport newWorkReport = new WorkReport(date, now, hours, true, loggedUser, project, comment);
        return new ResponseEntity<>(workReportRepository.save(newWorkReport), HttpStatus.CREATED);
    }
}
