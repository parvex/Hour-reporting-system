package com.pik.application.service;

import com.pik.application.domain.Project;
import com.pik.application.domain.User;
import com.pik.application.domain.WorkReport;
import com.pik.application.dto.LongString;
import com.pik.application.dto.PageOptions;
import com.pik.application.dto.ProjectsData.IdNameDescription;
import com.pik.application.dto.ProjectsData.ListIdNameDescriptionTotal;
import com.pik.application.dto.LongStringStringBooleanListLong;
import com.pik.application.dto.ProjectsData.ProjectHoursWorked;
import com.pik.application.dto.ProjectsData.UsedLeave;
import com.pik.application.repository.ProjectRepository;
import org.hibernate.jdbc.Work;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserService userService;
    private final WorkReportService workReportService;

    public ProjectService(ProjectRepository projectRepository, @Lazy UserService userService, @Lazy WorkReportService workReportService) {
        this.projectRepository = projectRepository;
        this.userService = userService;
        this.workReportService = workReportService;
    }

    public List<Project> projects(){
        return projectRepository.findAll();
    }

    public ResponseEntity<Project> projectByID(Long id) {
        Optional<Project> project = projectRepository.findById(id);
        if(project.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else{
            return new ResponseEntity<>(project.get(), HttpStatus.OK);
        }
    }

    public ResponseEntity<Project> createProject(Project project) {
        if(projectRepository.findOneByName(project.getName()) != null)
            throw new RuntimeException("Project name exists");
        return new ResponseEntity<>(projectRepository.save(project), HttpStatus.ACCEPTED);
    }

    public Project updateProject(Project project) {
        if (projectRepository.findOneByName(project.getName()) != null
                && !projectRepository.findOneByName(project.getName()).getId().equals(project.getId())) {
            throw new RuntimeException("Project name already exist");
        }
        return projectRepository.save(project);
    }

    public ResponseEntity<Project> deleteProject(Long id) {
        Optional<Project> project = projectRepository.findById(id);
        if(project.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else{
            projectRepository.delete(project.get());
            return new ResponseEntity<>(project.get(), HttpStatus.OK);
        }
    }

    public ResponseEntity<List<LongString>> getProjectByPhrase(String phrase, List<Long> chosenIds) {
        Pageable page = PageRequest.of(0, 10);
        Long loggedUserId = userService.getLoggedUser().getId();
        if(chosenIds != null && chosenIds.isEmpty())
            chosenIds.add(-1L);
        List<LongString> body = projectRepository.findByPhrase(phrase, chosenIds, loggedUserId, page);
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    public Project findById(Long projectId) {
        Optional<Project> project = projectRepository.findById(projectId);
        if(project.isEmpty()){
            throw new RuntimeException("Project not found!");
        }
        return project.get();
    }

    public List<LongString> findProjectsForUser(Long id) {
        List<LongString> project = projectRepository.findProjectsForUser(id);
        if(project.isEmpty()){
            return new ArrayList<>();
        }
        return project;
    }

    public ResponseEntity<UsedLeave> getUsedLeaveByUserId(Long userId)
    {
        List<WorkReport> reports = workReportService.getUsedLeaveForUser(userId);
        UsedLeave result = new UsedLeave();

        result.setDays(reports.size());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    public ResponseEntity<List<ProjectHoursWorked>> getProjectHoursByUserId(Long userId)
    {
        List<WorkReport> reports = workReportService.getWorkReportsForUser(userId);
        HashMap<Long, ProjectHoursWorked> result = new HashMap<>();

        for(WorkReport report: reports)
        {
            Long projectId = report.getProject().getId();
            if(result.containsKey(projectId))
            {
                result.get(projectId).addHours(report.getHours());
            }
            else
            {
                result.put(projectId, new ProjectHoursWorked(report.getProject().getName(), report.getHours()));
            }
        }

        if(result.isEmpty())
        {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else
        {
            return new ResponseEntity<>(new ArrayList<>(result.values()), HttpStatus.OK);
        }
    }

    public ResponseEntity<ListIdNameDescriptionTotal> getProjectsChosen(List<Long> chosenIds, String order, PageOptions options) {
        Long loggedId = userService.getLoggedUser().getId();
        if(chosenIds != null && chosenIds.isEmpty())
            chosenIds.add(-1L);

        Pageable page = order.isBlank() ? PageRequest.of(options.getPage(), options.getCount())
                : order.equals("ASC") ? PageRequest.of(options.getPage(), options.getCount(), Sort.Direction.ASC, "name")
                : PageRequest.of(options.getPage(), options.getCount(), Sort.Direction.DESC, "name");

        List<IdNameDescription> projects =  projectRepository.findProjectsChosen(chosenIds, loggedId, page);
        if(projects.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }

        for(IdNameDescription project : projects){
            if(workReportService.checkNewReports(project.getId()))
                project.setHasNewReports(true);
            else project.setHasNewReports(false);
        }
        List<IdNameDescription> totalProjects =  projectRepository.findProjectsChosen(chosenIds, loggedId, null);
        ListIdNameDescriptionTotal bodyTotal = new ListIdNameDescriptionTotal(projects, totalProjects.size());
        return new ResponseEntity<>(bodyTotal, HttpStatus.OK);
    }

    public ResponseEntity<Project> createUpdateProject(LongStringStringBooleanListLong project) {
        Project newProject;
        if(project.getProjectId() != null){ // update
            Optional<Project> findProject = projectRepository.findById(project.getProjectId());
            if(findProject.isEmpty())
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            newProject = findProject.get();
        }
        else { // new project with unique name
            newProject = new Project();
            newProject.setName(project.getName());
        }
        newProject.setDescription(project.getDescription());
        projectRepository.saveAndFlush(newProject);
        Long projectId = newProject.getId();

        if(project.getProjectId() != null && !project.getKeepEmployees()){
            List<Long> employees = userService.findEmployeeIdsForProject(projectId);
            for(Long employeeId : employees){
                User employee = userService.findById(employeeId);
                employee.getProjects().remove(newProject);
                userService.updateEmployeeProjects(employee);
            }
        }
        for(Long employeeId : project.getList()) {
            User employee = userService.findById(employeeId);
            if(!employee.getProjects().contains(newProject)) {
                employee.getProjects().add(newProject);
                userService.updateEmployeeProjects(employee);
            }
        }
        return new ResponseEntity<>(newProject, HttpStatus.OK);
    }

    public ResponseEntity checkIfNameUnique(String name) {
        Optional<Project> project = projectRepository.findByName(name);
        if(project.isEmpty())
            return new ResponseEntity(HttpStatus.OK);
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }


}
