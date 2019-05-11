package com.pik.application.restController;

import com.pik.application.domain.WorkReport;
import com.pik.application.dto.WRepUsrProj;
import com.pik.application.service.WorkReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class WorkReportRestController {

    private final WorkReportService workReportService;

    public WorkReportRestController(WorkReportService workReportService) {
        this.workReportService = workReportService;
    }

    @GetMapping(value = "/reports")
    public List<WorkReport> workReports(){
        return workReportService.findAll();
    }

    @GetMapping(value = "/reports/{id}")
    public ResponseEntity<WorkReport> workReportByID(@PathVariable Long id){
        return workReportService.findById(id);
    }

    @PostMapping(value = "/reports")
    public ResponseEntity<WorkReport> createWorkReport(@RequestBody WorkReport workReport){
        return workReportService.createWorkReport(workReport);
    }

    @PutMapping(value = "/reports")
    public WorkReport updateWorkReport(@RequestBody WorkReport workReport){
        return workReportService.updateWorkReport(workReport);
    }

    @DeleteMapping(value = "/reports/{id}")
    public ResponseEntity<WorkReport> deleteWorkReport(@PathVariable Long id){
        return workReportService.deleteWorkReport(id);
    }

    @PostMapping(value = "/work-reports")
    public List<WorkReport> getWorkReportsByDate(@RequestParam Date dateFrom,
                                              @RequestParam Date dateTo,
                                              @RequestParam List<Long> employeesId,
                                              @RequestParam List<Long> projectsId){

        return workReportService.getWorkReportByDate(dateFrom, dateTo, employeesId, projectsId);
    }

    @GetMapping(value = "/work-reports/{id}")
    public ResponseEntity<WRepUsrProj> getWorkReportInfo(@PathVariable Long id){
        return workReportService.getWorkReportInfo(id);
    }

    @PostMapping(value = "/work-reports-new")
    public ResponseEntity<WorkReport> addNewWorkReport(@RequestParam Date date,
                                              @RequestParam Integer hours,
                                              @RequestParam Long projectId,
                                              @RequestParam String projectName,
                                              @RequestParam String comment){

        return workReportService.addNewWorkReport(date, hours, projectId, projectName, comment);
    }
}
