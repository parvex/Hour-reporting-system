package com.pik.application.restController;

import com.pik.application.domain.Project;
import com.pik.application.dto.IdName;
import com.pik.application.dto.PhraseList;
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

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(value = "/projects")
    public List<Project> projects(){
        return projectService.projects();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(value = "/projects/{id}")
    public ResponseEntity<Project> projectByID(@PathVariable Long id){
        return projectService.projectByID(id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(value = "/project")
    public ResponseEntity<Project> createProject(@RequestBody Project project){
       return projectService.createProject(project);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping(value = "/projects")
    public Project updateProject(@RequestBody Project project){
        return projectService.updateProject(project);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping(value = "/projects/{id}")
    public ResponseEntity<Project> deleteProject(@PathVariable Long id){
        return projectService.deleteProject(id);
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping(value = "/projects")
    public ResponseEntity<List<IdName>> getProjectsByPhrase(@RequestBody(required = false) PhraseList body){

        if(body != null)
            return projectService.getProjectByPhrase(body.getPhrase(), body.getChosenIds());
        else
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
