package com.pik.application.restController;

import com.pik.application.domain.Project;
import com.pik.application.repository.ProjectRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ProjectRestController {

    private final ProjectRepository projectRepository;

    public ProjectRestController(ProjectRepository projectRepositoryl) {
        this.projectRepository = projectRepositoryl;
    }

    @GetMapping(value = "/projects")
    public List<Project> projects(){ return projectRepository.findAll(); }

    @GetMapping(value = "/projects/{id}")
    public ResponseEntity<Project> projectByID(@PathVariable Long id){
        Optional<Project> project = projectRepository.findById(id);
        if(project.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else{
            return new ResponseEntity<>(project.get(), HttpStatus.OK);
        }
    }

    @PostMapping(value = "/project")
    public ResponseEntity<Project> createProject(@RequestBody Project project){
        if(projectRepository.findOneByName(project.getName()) != null){
            throw new RuntimeException("Project name exists");
        }
        return new ResponseEntity<>(projectRepository.save(project), HttpStatus.ACCEPTED);
    }

    @PutMapping(value = "/projects")
    public Project updateProject(@RequestBody Project project){
        if (projectRepository.findOneByName(project.getName()) != null
                && projectRepository.findOneByName(project.getName()).getId() != project.getId()) {
            throw new RuntimeException("projectname already exist");
        }
        return projectRepository.save(project);
    }

    @DeleteMapping(value = "/projects/{id}")
    public ResponseEntity<Project> deleteProject(@PathVariable Long id){
        Optional<Project> project = projectRepository.findById(id);
        if(project.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else{
            projectRepository.delete(project.get());
            return new ResponseEntity<>(project.get(), HttpStatus.OK);
        }
    }
}
