package com.pik.application.service;

import com.pik.application.domain.Project;
import com.pik.application.domain.User;
import com.pik.application.domain.WorkReport;
import com.pik.application.dto.WorkReportData.ListIdEmployeeNameDateHoursCommentTotal;
import com.pik.application.dto.WorkReportData.NewWorkReport;
import com.pik.application.dto.PageOptions;
import com.pik.application.dto.WorkReportData.IdEmployeeNameDateHoursComment;
import com.pik.application.dto.WorkReportData.WorkReportExtraInfo;
import com.pik.application.repository.WorkReportRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class WorkReportService {

    private final WorkReportRepository workReportRepository;
    private final UserService userService;
    private final ProjectService projectService;

    public WorkReportService(WorkReportRepository workReportRepository, @Lazy UserService userService, @Lazy ProjectService projectService) {
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

    public ResponseEntity<List<WorkReportExtraInfo>> getWorkReportByDate(Date dateFrom, Date dateTo, List<Long> employeeIds, List<Long> projectIds) {
        Long loggedId = userService.getLoggedUser().getId();
        if(employeeIds != null && employeeIds.isEmpty()) employeeIds.add(-1L);
        if(projectIds != null && projectIds.isEmpty()) projectIds.add(-1L);

        List<WorkReportExtraInfo> body = workReportRepository.findByDateBetweenOrderByDateAsc(dateFrom, dateTo, employeeIds, projectIds, loggedId);
        for(WorkReportExtraInfo rep : body){
            if(rep.getEmployeeId().equals(loggedId))
                rep.setEditable(true);
            else
                rep.setEditable(false);
        }

        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    public ResponseEntity<WorkReportExtraInfo> getWorkReportInfo(Long id) {
        Long loggedId = userService.getLoggedUser().getId();
        Optional<WorkReportExtraInfo> workReport = Optional.ofNullable(workReportRepository.findByIdInfo(id));
        if(workReport.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else{
            WorkReportExtraInfo rep = workReport.get();
            if(rep.getEmployeeId().equals(loggedId))
                rep.setEditable(true);
            else
                rep.setEditable(false);

            return new ResponseEntity<>(workReport.get(), HttpStatus.OK);
        }
    }

    public ResponseEntity<WorkReport> addNewWorkReport(NewWorkReport body){
        User loggedUser = userService.getLoggedUser();
        Date now = new Date();
        Project project = projectService.findById(body.getProjectId());
        WorkReport workReport;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date startDate = null; Date endDate = null;
        try {
            startDate = sdf.parse(body.getStartDate());
            endDate = sdf.parse(body.getEndDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(startDate.after(endDate))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Calendar start = Calendar.getInstance(); start.setTime(startDate);
        Calendar end = Calendar.getInstance(); end.setTime(endDate);

        if(body.getId() != null){ // update
            Optional<WorkReport> findWorkReport = workReportRepository.findById(body.getId());
            if(findWorkReport.isEmpty())
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            else{
                workReport = findWorkReport.get();
                if(start.get(Calendar.DAY_OF_YEAR) == end.get(Calendar.DAY_OF_YEAR)
                        && start.get(Calendar.YEAR) == end.get(Calendar.YEAR)){
                    workReport.setUser(loggedUser); workReport.setProject(project); workReport.setComment(body.getComment());
                    workReport.setDate(startDate); workReport.setHours(body.getHoursNumber());
                    workReportRepository.save(workReport);
                    return new ResponseEntity<>(workReport, HttpStatus.OK);
                }
                else{ workReportRepository.delete(workReport); }
            }
        }
        for(Date date = start.getTime(); !start.after(end); start.add(Calendar.DATE, 1), date = start.getTime()) {
            workReport = new WorkReport(date, now, body.getHoursNumber(), false, loggedUser, project, body.getComment());
            workReportRepository.save(workReport);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    public ResponseEntity<ListIdEmployeeNameDateHoursCommentTotal> getWorkReportsByState(Long projectId, PageOptions options, Boolean state, String order) {
        Long loggedId = userService.getLoggedUser().getId();

        Pageable page = order.isBlank() ? PageRequest.of(options.getPage(), options.getCount())
                : order.equals("ASC") ? PageRequest.of(options.getPage(), options.getCount(), Sort.Direction.ASC, "date")
                : PageRequest.of(options.getPage(), options.getCount(), Sort.Direction.DESC, "date");

        List<IdEmployeeNameDateHoursComment> reports = workReportRepository.findWorkReportsByState(projectId, state, loggedId, page);
        List<IdEmployeeNameDateHoursComment> totalReports = workReportRepository.findWorkReportsByState(projectId, state, loggedId, null);
        if(reports.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        ListIdEmployeeNameDateHoursCommentTotal bodyTotal = new ListIdEmployeeNameDateHoursCommentTotal(reports, totalReports.size());
        return new ResponseEntity<>(bodyTotal, HttpStatus.OK);
    }
}
