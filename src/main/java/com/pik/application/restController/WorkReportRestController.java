package com.pik.application.restController;

import com.pik.application.domain.WorkReport;
import com.pik.application.dto.WorkReportData.*;
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

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @PostMapping(value = "/reports")
    public ResponseEntity<WorkReport> createWorkReport(@RequestBody WorkReport workReport){
        return workReportService.createWorkReport(workReport);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @PutMapping(value = "/reports")
    public WorkReport updateWorkReport(@RequestBody WorkReport workReport){
        return workReportService.updateWorkReport(workReport);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR', 'USER')")
    @DeleteMapping(value = "/reports/{id}")
    public ResponseEntity<WorkReport> deleteWorkReport(@PathVariable Long id){
        return workReportService.deleteWorkReport(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR', 'USER')")
    @PostMapping(value = "/work-reports")
    public ResponseEntity<List<WorkReportExtraInfo>> getWorkReportsByDate(@RequestBody(required = false) WorkReportDateInput body){

        if(body != null)
            return workReportService.getWorkReportByDate(body.getDateFrom(), body.getDateTo(), body.getEmployeeIds(), body.getProjectIds());
        else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR', 'USER')")
    @GetMapping(value = "/work-reports/{id}")
    public ResponseEntity<WorkReportExtraInfo> getWorkReportInfo(@PathVariable Long id){
        return workReportService.getWorkReportInfo(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @PostMapping(value = "/work-reports-new")
    public ResponseEntity<WorkReport> addNewWorkReport(@RequestBody(required = false) NewWorkReport body){
        return body != null ? workReportService.addNewWorkReport(body) : new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @PutMapping(value = "/work-reports-new")
    public ResponseEntity<WorkReport> updateWorkReport(@RequestBody(required = false) NewWorkReport body){
        if(body == null || body.getId() == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return addNewWorkReport(body);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR', 'USER')")
    @PostMapping(value = "/work-reports-state")
    public ResponseEntity<ListIdEmployeeNameDateHoursCommentTotal> getWorkReportsByState(@RequestBody(required = false) IdStateOrderPage body){
        if(body != null) {
            return workReportService.getWorkReportsByState(body.getCriteria(), body.getOptions(), body.getState(), body.getOrder());
        }else
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
