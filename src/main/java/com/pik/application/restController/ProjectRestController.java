package com.pik.application.restController;

import com.pik.application.domain.Project;
import com.pik.application.dto.LongString;
import com.pik.application.dto.PhraseList;
import com.pik.application.dto.ProjectsData.ListIdNameDescriptionTotal;
import com.pik.application.dto.ProjectsData.ListIdsOrderPage;
import com.pik.application.dto.LongStringStringBooleanListLong;
import com.pik.application.service.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ProjectRestController {

    private final ProjectService projectService;
    public ProjectRestController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR')")
    @GetMapping(value = "/projects")
    public List<Project> projects(){
        return projectService.projects();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR')")
    @GetMapping(value = "/projects/{id}")
    public ResponseEntity<Project> projectByID(@PathVariable Long id){
        return projectService.projectByID(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR')")
    @PostMapping(value = "/project")
    public ResponseEntity<Project> createProject(@RequestBody Project project){
       return projectService.createProject(project);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR')")
    @PutMapping(value = "/projects")
    public Project updateProject(@RequestBody Project project){
        return projectService.updateProject(project);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR')")
    @DeleteMapping(value = "/projects/{id}")
    public ResponseEntity<Project> deleteProject(@PathVariable Long id){
        return projectService.deleteProject(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR','USER')")
    @PostMapping(value = "/projects")
    public ResponseEntity<List<LongString>> getProjectsByPhrase(@RequestBody(required = false) PhraseList body){

        if(body != null)
            return projectService.getProjectByPhrase(body.getPhrase(), body.getChosenIds());
        else
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR')")
    @PostMapping(value = "/projects-chosen")
    public ResponseEntity<ListIdNameDescriptionTotal> getProjectsChosen(@RequestBody(required = false) ListIdsOrderPage body){

        if(body != null)
            return projectService.getProjectsChosen(body.getCriteria(), body.getOrder(), body.getOptions());
        else
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR')")
    @GetMapping(value = "/unique-project-name")
    public ResponseEntity getProjectName(@RequestParam String name){
        return projectService.checkIfNameUnique(name);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR')")
    @PostMapping(value = "/project-employees")
    public ResponseEntity<Project> createProjectWithEmployees(@RequestBody LongStringStringBooleanListLong project){
        return projectService.createUpdateProject(project);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR')")
    @PutMapping(value = "/project-employees")
    public ResponseEntity<Project> updateProjectWithEmployees(@RequestBody LongStringStringBooleanListLong project){
        return createProjectWithEmployees(project);
    }
}
