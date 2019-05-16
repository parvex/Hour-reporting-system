package com.pik.application.restController;

import com.pik.application.domain.WorkReport;
import com.pik.application.dto.*;
import com.pik.application.service.WorkReportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api")
public class WorkReportRestController {

    private final WorkReportService workReportService;

    public WorkReportRestController(WorkReportService workReportService) {
        this.workReportService = workReportService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(value = "/reports")
    public List<WorkReport> workReports(){
        return workReportService.findAll();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(value = "/reports/{id}")
    public ResponseEntity<WorkReport> workReportByID(@PathVariable Long id){
        return workReportService.findById(id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(value = "/reports")
    public ResponseEntity<WorkReport> createWorkReport(@RequestBody WorkReport workReport){
        return workReportService.createWorkReport(workReport);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping(value = "/reports")
    public WorkReport updateWorkReport(@RequestBody WorkReport workReport){
        return workReportService.updateWorkReport(workReport);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping(value = "/reports/{id}")
    public ResponseEntity<WorkReport> deleteWorkReport(@PathVariable Long id){
        return workReportService.deleteWorkReport(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR')")
    @PostMapping(value = "/work-reports")
    public ResponseEntity<List<WRepDate>> getWorkReportsByDate(@RequestBody(required = false) WRepDateReq body){

        if(body != null)
            return workReportService.getWorkReportByDate(body.getDateFrom(), body.getDateTo(), body.getEmployeeIds(), body.getProjectIds());
        else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR')")
    @GetMapping(value = "/work-reports/{id}")
    public ResponseEntity<WorkReportExtraInfo> getWorkReportInfo(@PathVariable Long id){
        return workReportService.getWorkReportInfo(id);
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping(value = "/work-reports-new")
    public ResponseEntity<WorkReport> addNewWorkReport(@RequestBody(required = false) NewWorkReport body){

        if(body != null)
            return workReportService.addNewWorkReport(body.getDate(), body.getHoursNumber(), body.getProjectId(), body.getProjectName(), body.getComment());
        else
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR')")
    @PostMapping(value = "/work-reports-accepted")
    public ResponseEntity<List<WorkReportExtraInfo>> getAcceptedWorkReports(@RequestBody(required = false) IdName body){
        if(body != null) {
            System.out.println(body.id + " " + body.name + " " + body.accepted);
            return workReportService.getWorkReportsAccepted(body.id, body.accepted);
        }else
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
