package com.pik.application.restController;

import com.pik.application.domain.User;
import com.pik.application.domain.WorkReport;
import com.pik.application.repository.UserRepository;
import com.pik.application.repository.WorkReportRepository;
import org.fluttercode.datafactory.impl.DataFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class WorkReportRestController {

    private final WorkReportRepository workReportRepository;

    public WorkReportRestController(WorkReportRepository workReportRepositoryl) {
        this.workReportRepository = workReportRepositoryl;
    }

    @GetMapping(value = "/reports")
    public List<WorkReport> workReports(){ return workReportRepository.findAll(); }

    @GetMapping(value = "/reports/{id}")
    public ResponseEntity<WorkReport> workReportByID(@PathVariable Long id){
        Optional<WorkReport> WorkReport = workReportRepository.findById(id);
        if(WorkReport.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else{
            return new ResponseEntity<>(WorkReport.get(), HttpStatus.OK);
        }
    }

    @PostMapping(value = "/reports")
    public ResponseEntity<WorkReport> createWorkReport(@RequestBody WorkReport workReport){
        if(workReportRepository.findOneByComment(workReport.getComment()) != null){
            throw new RuntimeException("WorkReport name exists");
        }
        return new ResponseEntity<>(workReportRepository.save(workReport), HttpStatus.ACCEPTED);
    }

    @PutMapping(value = "/reports")
    public WorkReport updateWorkReport(@RequestBody WorkReport workReport){
        if (workReportRepository.findOneByComment(workReport.getComment()) != null
                && workReportRepository.findOneByComment(workReport.getComment()).getId() != workReport.getId()) {
            throw new RuntimeException("Work report already exist");
        }
        return workReportRepository.save(workReport);
    }

    @DeleteMapping(value = "/reports/{id}")
    public ResponseEntity<WorkReport> deleteWorkReport(@PathVariable Long id){
        Optional<WorkReport> workReport = workReportRepository.findById(id);
        if(workReport.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else{
            workReportRepository.delete(workReport.get());
            return new ResponseEntity<>(workReport.get(), HttpStatus.OK);
        }
    }

    @PostMapping(value = "/work-reports")
    public List<WorkReport> workReportsByDate(@RequestParam Date dateFrom,
                                              @RequestParam Date dateTo,
                                              @RequestParam List<Long> employeesId,
                                              @RequestParam List<Long> projectsId){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String loggedUsername = auth.getName();

        return workReportRepository.findByDateBetweenOrderByDateAsc(dateFrom, dateTo, employeesId, projectsId, loggedUsername);
    }
}
